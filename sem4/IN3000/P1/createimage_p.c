#include <assert.h>
#include <elf.h>
#include <errno.h>
#include <stdarg.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define IMAGE_FILE "image"

#define SECTOR_SIZE 512       /* USB sector size in bytes */
#define OS_SIZE_LOC 2         /* OS position within bootblock */  
#define BOOT_MEM_LOC 0x7c00   /* bootblock memory location */
#define OS_MEM_LOC 0x8000     /* kernel memory location */

// Defining macros for the arguments we want to read
#define EXTENDED_ARG "--extended"
#define VM_ARG       "--vm"

// Defining macros for the boot characters
#define BOOT_CHAR_1 0x55
#define BOOT_CHAR_2 0xAA

// Declaring the function we wish to use later on
// It takes in the array we want to write the file to, it's filepath, and if we want extended output
// It returns the size of program after writing.
char* readFile(int* programSize, char* filePath, char extended);

int main(int argc, char **argv)
{
  // There should be three arguments. We can loop through them and get their values

  // These are the values we want to read
  char extended = 0;          // If we should print extended information
  char*  bootFilepath = NULL;   // Filepath of the bootblock
  char*  kernelFilepath = NULL; // Filepath of the kernel

  // Looping over arguments, skipping the first one (the first one is always the name of this file)
  for(int i = 1; i < argc; i++)
  {
    // Getting the current argument
    char* arg = *(argv+i);

    if(!strcmp(EXTENDED_ARG,arg))
    {
      extended = 1;
    }
    else if (!strcmp(VM_ARG,arg))
    {
      // We ignore this
    }
    else
    {
      if(bootFilepath == NULL)
      {
        bootFilepath = arg;
      }
      else
      {
        kernelFilepath = arg;
      }
    }
  }

  // We've now read all arguments. We can open the files:

  // First let's handle the bootBlock:
  int* bootBlockSize = malloc(sizeof(int));
  char* bootBlock = readFile(bootBlockSize,bootFilepath,extended);
  // Then the kernel
  int* kernelSize = malloc(sizeof(int));;
  char* kernel = readFile(kernelSize,kernelFilepath,extended);

  // We want to modify the bootBlock array, by adding the boot codes to the end
  bootBlock[*bootBlockSize - 2] = BOOT_CHAR_1;
  bootBlock[*bootBlockSize - 1] = BOOT_CHAR_2;
  // We also want to add the size of the kernel (we assume this number is no greater than 16 bits)
  char kernelSectors = (*kernelSize / 512);
  char kernelSizeL = (char)(kernelSectors);        // Getting the 8 low bits of kernelSize
  char kernelSizeH = (char)((kernelSectors) >> 8); // Getting the 8 high bits of kernelSize
  // Inserting into the bootBlock array (note that we assume little-endian here. This can be figured out in the bootBlock)
  bootBlock[OS_SIZE_LOC] = kernelSizeL;
  bootBlock[OS_SIZE_LOC + 1] = kernelSizeH;
  
  // Now we write the arrays to a new file (the image file)
  FILE* imageFile = fopen("./image","wb");
  fwrite(bootBlock,1,*bootBlockSize,imageFile);
  fwrite(kernel,1,*kernelSize,imageFile);
  fclose(imageFile);

  // We're done! :)
  printf("\nImage successfully created!\n");
}

char* readFile(int* programSize,char* filePath,char extended)
{
  // Opening the file in read mode
  FILE* file = fopen(filePath,"rb");
  if(!file)
  {
	  printf("NEI!!!");
  }

  // Reading the ELF header
  //Elf32_Ehdr* elfHeader = malloc(sizeof(Elf32_Ehdr));
  Elf32_Ehdr elfHeader;
  if(fread(&elfHeader, sizeof(Elf32_Ehdr),1,file) == 0)
  {
	printf("Oh no!!");
  }

  // Printing extended information for the ELF header:
  if (extended) 
  {
	printf("\nPrinting ELF header data for     %s",filePath);
  	printf("\n Program header offset:          %5d b",elfHeader.e_phoff);
  	printf("\n Entries in program header:      %5d b",elfHeader.e_phnum);
  	printf("\n Size of program header entries: %5d b",elfHeader.e_phentsize);
  	printf("\n Section header offset:          %5d b",elfHeader.e_shoff);
  	printf("\n Entries in section header:      %5d b",elfHeader.e_shnum);
  	printf("\n Size of section header entries: %5d b",elfHeader.e_shentsize);
  	printf("\n Section header name index       %5d ",elfHeader.e_shstrndx);
	printf("\n");
  }

  // Moving to the program header offset, and reading all section headers
  fseek(file,elfHeader.e_phoff,0);

  Elf32_Phdr** programHeaders = malloc(sizeof(Elf32_Phdr*) * elfHeader.e_phnum);
  for(int i = 0; i < elfHeader.e_phnum; i++)
  {
	// Reading the current header
	Elf32_Phdr* currentHeader = malloc(sizeof(Elf32_Phdr));
	fread(currentHeader,sizeof(Elf32_Phdr),1,file);

	// Adding header to array
	programHeaders[i] = currentHeader;
  }

  // Isolating loadable segments:
  int numSegments = 0;
  for(int i = 0; i < elfHeader.e_phnum; i++)
  {
	// Is 1 if loadable
	if (programHeaders[i]->p_type == 1) {numSegments++;}
  }
  Elf32_Phdr** loadableHeaders = malloc(sizeof(Elf32_Phdr*) * numSegments);
  int j = 0;
  for(int i = 0; i < elfHeader.e_phnum; i++)
  {
	// Is 1 if loadable
	if (programHeaders[i]->p_type == 1) {loadableHeaders[j++] = programHeaders[i];}
  }

  // Defining the array that will hold our .text and .data files
  int arraySize = 0;
  for(int i = 0; i < numSegments; i++) {arraySize += loadableHeaders[i]->p_filesz;}
  // We want the program to have the size corresponding to the closest multiple of 512 bytes (n sectors)
  arraySize = (arraySize % 512 == 0) ? arraySize : arraySize - (arraySize % 512) + 512;
  char* program = malloc(arraySize);

  // Printing extended information about the program size
  if (extended)
  {
	printf("\nPrinting program size for %s",filePath);
	printf("\n Size:                    %d",arraySize);
	printf("\n");
  }

  // Defining an int to point to the current spot in the program array we are writing to
  int programPointer = 0;

  for(int i = 0; i < numSegments; i++)
  {
  	Elf32_Phdr* programHeader = loadableHeaders[i];
	int programSize       	  = programHeader->p_filesz;
	char* programSegment = malloc(programSize);
	fseek(file,programHeader->p_offset,0);
	fread(programSegment,programSize,1,file);
	// Inserting the text section into the program
	for(int i = 0; i < programSize; i++)
	{
		program[programPointer++] = programSegment[i];
	}
  }

  // Filling the rest of the array with zeroes
  while(programPointer < arraySize)
  {
	program[programPointer++] = 0;
  }

  // Closing the file
  fclose(file);

  // Finally, returning the program data
  *programSize = arraySize;
  return program;
}
