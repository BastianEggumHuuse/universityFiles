#include <assert.h>
#include <elf.h>
#include <errno.h>
#include <stdarg.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#define IMAGE_FILE "image"
#define ARGS "[--extended] [--vm] <bootblock> <executable-file> ..."

#define SECTOR_SIZE 512       /* USB sector size in bytes */
#define OS_SIZE_LOC 2         /* OS position within bootblock */  
#define BOOT_MEM_LOC 0x7c00   /* bootblock memory location */
#define OS_MEM_LOC 0x8000     /* kernel memory location */

/// @brief Reads an ELF file and extracts the .text and .data parts 
/// @param path 
/// @param out_prog Pointer to were to store the read data    
/// @param out_size Pointer to were to store the read data size 
/// @param hex_dump True to do a hex dump of the .text and .data 
/// @return return code  
int readFile(char* path, char** out_prog, int* out_size, bool hex_dump, bool section_tables) {
  printf("\n======       Reading  %s   ======\n", path);
  Elf32_Ehdr header;

  FILE* stream = fopen(path, "rb");
  if(!stream) {
      printf("Failed opening file: %s\n", strerror(errno));
      return 2;
  }

  // Read the header
  if (fread(&header, sizeof(header), 1, stream) == 0){
    printf("Error reading header: %s\n", strerror(errno));
    fclose(stream);
    return 3;
  }

  // Check if correct type and machine
  if (header.e_type == 2){
    printf("Debug: File type: Executable file \n");
  } else {
    printf("Debug: File type: Not executable file \n");
  }

  if (header.e_machine == 3) {
    printf("Debug: Machine type: Intel 80386 \n");
  } else {
    printf("Debug: Machine type: not Intel 80386 \n");
  }


  Elf32_Phdr program_head;
  Elf32_Shdr sec_head;

  // Read program header
  if (fseek(stream, header.e_phoff, SEEK_SET) != 0) {
    printf("Error seeking program: %s\n", strerror(errno));
    fclose(stream);
    return 4;
  }
  if (fread(&program_head, sizeof(Elf32_Phdr), 1, stream) != 1){
    printf("Error reading program header: %s\n", strerror(errno));
    fclose(stream);
    return 5;
  }  

  // Read section table header
  Elf32_Shdr* sh_table = malloc(header.e_shnum * header.e_shentsize);
  if (!sh_table) {
    printf("Error allocating memory: %s\n", strerror(errno));
    fclose(stream);
    return 6;
  }

  if (fseek(stream, header.e_shoff, SEEK_SET) != 0) {
    printf("Error seeking section: %s\n", strerror(errno));
    fclose(stream);
    return 7;
  }

  if (fread(sh_table, header.e_shentsize, header.e_shnum, stream) != header.e_shnum){
    printf("Error reading section header: %s\n", strerror(errno));
    fclose(stream);
    return 8;
  }

  Elf32_Shdr sh_strtab_section = sh_table[header.e_shstrndx];

  char* sh_strtab = malloc(sh_strtab_section.sh_size);
  if (!sh_strtab) {
    printf("Error allocating memory: %s\n", strerror(errno));
    fclose(stream);
    free(sh_table);
    return 9;
  }

  if (fseek(stream, sh_strtab_section.sh_offset, SEEK_SET) != 0){
    printf("Error seeking section: %s\n", strerror(errno));
    fclose(stream);
    free(sh_table);
    free(sh_strtab);
    return 10;
  }
  if (fread(sh_strtab, sh_strtab_section.sh_size, 1, stream) != 1){
    printf("Error reading section: %s\n", strerror(errno));
    fclose(stream);
    free(sh_table);
    free(sh_strtab);
    return 11;
  }

  // Print sections 
  if (section_tables) printf("\n--- Section Table ---\n");
  int text_start = 0;
  int text_size = 0;
  int data_start = 0;
  int data_size = 0;
  for (int i = 0; i < header.e_shnum; i++) {
    char* name = sh_strtab + sh_table[i].sh_name;
    
    if (section_tables){
      printf("Section %02d: Name: %-20s Offset: 0x%08x Size: 0x%08x\n", 
              i, 
              name, 
              sh_table[i].sh_offset, 
              sh_table[i].sh_size);
    }
    if (strcmp(name, ".text") == 0){        // Store the offset and size of the .text section 
      text_start = sh_table[i].sh_offset;
      text_size = sh_table[i].sh_size;
    } else if (strcmp(name, ".data") == 0){ // Store the offset and size of the .data section 
      data_start = sh_table[i].sh_offset;
      data_size = sh_table[i].sh_size;
    }
  }
  if (section_tables) printf("--- End Section Table ---\n \n");

  char* text_buffer = malloc(text_size);
  if (!text_buffer){
    printf("Error allocating memory: %s\n", strerror(errno));
    fclose(stream);
    free(sh_table);
    free(sh_strtab);
    return 12;
  }
  char* data_buffer = malloc(data_size);
  if (!data_buffer){
    printf("Error allocating memory: %s\n", strerror(errno));
    fclose(stream);
    free(sh_table);
    free(sh_strtab);
    free(text_buffer);
    return 13;
  }

  // Read the .text section
  if (text_size != 0){
    if (fseek(stream, text_start, SEEK_SET) != 0){
      printf("Error seeking section: %s\n", strerror(errno));
      fclose(stream);
      free(sh_table);
      free(sh_strtab);
      free(text_buffer);
      free(data_buffer);
      return 14;
    }
    if (fread(text_buffer, text_size, 1, stream) != 1){
      printf("Error reading section: %s\n", strerror(errno));
      fclose(stream);
      free(sh_table);
      free(sh_strtab);
      free(text_buffer);
      free(data_buffer);
      return 15;
    }
  }

  // Read the .data section 
  if (data_size != 0){
    if (fseek(stream, data_start, SEEK_SET) != 0){
      printf("Error seeking section: %s\n", strerror(errno));
      fclose(stream);
      free(sh_table);
      free(sh_strtab);
      free(text_buffer);
      free(data_buffer);
      return 16;
    }
  
    if (fread(data_buffer, data_size, 1, stream) != 1){
      printf("Error reading section: %s\n", strerror(errno));
      fclose(stream);
      free(sh_table);
      free(sh_strtab);
      free(text_buffer);
      free(data_buffer);
      return 17;
    }
  }

  printf("Debug: Section: .text (size: %d)\n", text_size);
  printf("Debug: Section: .data (size %d)\n", data_size);

  *out_prog = malloc(text_size + data_size);
  if (!out_prog){
    printf("Error allocating memory: %s\n", strerror(errno));
    fclose(stream);
    free(sh_table);
    free(sh_strtab);
    free(text_buffer);
    free(data_buffer);
    return 18;
  }

  // Storing in output buffer 
  int i = 0;
  for (; i < text_size; i++){
    (*out_prog)[i] = text_buffer[i];
  }
  for (; i < text_size + data_size; i++){
    (*out_prog)[i] = data_buffer[i-text_size];
  }
  *out_size = text_size + data_size;

  // Hex dump
  if (hex_dump){
    printf("\nDebug: .text content (Hex dump):\n");
    for (int i = 0; i < text_size; i++) {
        printf("%02X ", (unsigned char)text_buffer[i]);

        if ((i + 1) % 16 == 0) {
            printf("\n");
        }
    }
    printf("\n\n");

    printf("\nDebug: .data content (Hex dump):\n");
    for (int i = 0; i < data_size; i++) {
        printf("%02X ", (unsigned char)data_buffer[i]);

        if ((i + 1) % 16 == 0) {
            printf("\n");
        }
    }
    printf("\n");
  }

  printf("======   Done reading %s   ======\n", path);
  
  // Clean up
  fclose(stream);
  free(sh_table);
  free(sh_strtab);
  free(text_buffer);
  free(data_buffer);

  return 0; 
}

/// @brief Write the data from the ELF files to an image file
/// @param filename Name of output file  
/// @param bootblock_code The bootblock code as a string
/// @param bootblock_size The length of the bootblock code 
/// @param kernel_code The kernel code as a string 
/// @param kernel_size The length of the kernel code 
/// @return Return code 
int writeFile(char* filename, char* bootblock_code, int bootblock_size, char* kernel_code, int kernel_size){
  int prog_size = SECTOR_SIZE + kernel_size; // The total size of the final image file 
  int calloc_size = (prog_size % SECTOR_SIZE == 0) ? prog_size : prog_size - (prog_size % SECTOR_SIZE) + SECTOR_SIZE;
  char* full_prog = calloc(calloc_size, sizeof(char));
  if (!full_prog){
    printf("Error allocating memory: %s\n", strerror(errno));
    return 30;
  }

  unsigned char bytes[4];

  int ks = kernel_size / SECTOR_SIZE + 1;
  printf("\n %d \n", calloc_size);

  bytes[0] = (ks >> 0)  & 0xFF;
  bytes[1] = (ks >> 8)  & 0xFF;
  bytes[2] = (ks >> 16) & 0xFF;
  bytes[3] = (ks >> 24) & 0xFF;

  // Writing bootblock 
  int i = OS_SIZE_LOC;

  bootblock_code[i++] = bytes[0];
  bootblock_code[i++] = bytes[1];
  bootblock_code[i++] = bytes[2];
  bootblock_code[i++] = bytes[3];  

  i = 0;

  char* iterator = bootblock_code; 
  while (iterator - bootblock_code < bootblock_size){
    full_prog[i++] = (unsigned char)*iterator;
    iterator++;
  }
  // Adding padding 
  while (i < 510) i++;

  // Adding final characters
  full_prog[i++] = 0x55;
  full_prog[i++] = 0xAA;

  // Writing kernel 
  iterator = kernel_code; 
  while (iterator - kernel_code < kernel_size){
    full_prog[i++] = (unsigned char)*iterator;
    iterator++;
  }


  // Write to the file
  FILE *fp = fopen(filename, "wb");
  if (!fp){
    printf("Error opening image file: %s\n", strerror(errno));
    free(full_prog);
    return 31;
  }

  size_t written = fwrite(full_prog, sizeof(char), calloc_size, fp);
  if (written != calloc_size){
    printf("Writing all bytes failed, wrote only %zu out of %d: %s\n", written, calloc_size, strerror(errno));
    free(full_prog);
    free(fp);
    return 32;
  }
  printf("Debug - Wrote %zu bytes to output file %s\n", written, filename);


  fclose(fp);
  free(full_prog);
  return 0;
}

int main(int argc, char **argv)
{
  if (argc < 4) {
    printf("Error: Too few arguments, only %d passed ", argc);
    return 1;
  }

  // Get the name of bootblock file and kernel file 
  char* bootblock = argv[2];
  char* kernel = argv[3];

  char* bootblock_out;
  char* kernel_out;

  int bootblock_size;
  int kernel_size;

  bool debug_info = false;
  bool section_tables = false;

  // Read and parse the bootblock file
  int retVal = readFile(bootblock, &bootblock_out, &bootblock_size, debug_info, section_tables);
  if (retVal != 0){
    return retVal;
  }
  if (!bootblock_out){
    printf("Error loading bootblock: %s\n", strerror(errno));
    return 40;
  }

  // Read and parse the kernel file 
  retVal = readFile(kernel, &kernel_out, &kernel_size, debug_info, section_tables);
  if (retVal != 0){
    return retVal;
  }
  if (!kernel_out){
    printf("Error loading kernel: %s\n", strerror(errno));
    free(bootblock_out);
    return 41;
  }

  // Print the two files (for debugging)
  if (debug_info){
    char* iterator = bootblock_out; 
    while (iterator - bootblock_out < bootblock_size) {
        printf("%02X\n", (unsigned char)*iterator);
        iterator++;
    }

    printf("------\n");

    iterator = kernel_out; 
    while (iterator - kernel_out < kernel_size) {
        printf("%02X\n", (unsigned char)*iterator);
        iterator++;
    }
  }

  printf("\nDone reading files! \n");

  printf("Debug - Bootblock size: %d\n", bootblock_size);
  printf("Debug - Kernel size: %d\n", kernel_size);
  printf("Debug - Total size: %d\n", SECTOR_SIZE+kernel_size);

  // Write file
  int ret_val = writeFile(IMAGE_FILE, bootblock_out, bootblock_size, kernel_out, kernel_size);
  if (ret_val != 0){
    return ret_val;
  }

  printf("Done writing image file! \n");

  free(bootblock_out);
  free(kernel_out);
 
  return 0;
}
