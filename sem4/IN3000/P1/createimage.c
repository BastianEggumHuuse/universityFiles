#include <assert.h>
#include <elf.h>
#include <errno.h>
#include <stdarg.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define IMAGE_FILE "image"
#define ARGS "[--extended] [--vm] <bootblock> <executable-file> ..."

#define SECTOR_SIZE 512       /* USB sector size in bytes */
#define OS_SIZE_LOC 2         /* OS position within bootblock */  
#define BOOT_MEM_LOC 0x7c00   /* bootblock memory location */
#define OS_MEM_LOC 0x8000     /* kernel memory location */

// Defining macros for the arguments we want to read
#define EXTENDED_ARG "--extended"
#define VM_ARG       "--vm"

// Definign macros for the characters the bootblock needs to end with
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
  char kernelSizeL = (char)(*kernelSize);        // Getting the 8 low bits of kernelSize
  char kernelSizeH = (char)((*kernelSize) >> 8); // Getting the 8 high bits of kernelSize
  // Inserting into the bootBlock array (note that we assume big-endian here. This can be figured out in the bootBlock)
  // This is probably wrong tho...
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

  // Reading the ELF header
  Elf32_Ehdr* elfHeader = malloc(sizeof(Elf32_Ehdr));
  fread(elfHeader, sizeof(Elf32_Ehdr),1,file);

  // Printing extended information for the ELF header:
  if (extended) 
  {
	printf("\nPrinting ELF header data for     %s",filePath);
  	printf("\n Program header offset:          %5d b",elfHeader->e_phoff);
  	printf("\n Entries in program header:      %5d b",elfHeader->e_phnum);
  	printf("\n Size of program header entries: %5d b",elfHeader->e_phentsize);
  	printf("\n Section header offset:          %5d b",elfHeader->e_shoff);
  	printf("\n Entries in section header:      %5d b",elfHeader->e_shnum);
  	printf("\n Size of section header entries: %5d b",elfHeader->e_shentsize);
  	printf("\n Section header name index       %5d ",elfHeader->e_shstrndx);
	printf("\n");
  }

  // Moving to the section header offset, and reading all section headers
  fseek(file,elfHeader->e_shoff,0);

  Elf32_Shdr** sectionHeaders = malloc(sizeof(Elf32_Shdr*) * elfHeader->e_shnum);
  for(int i = 0; i < elfHeader->e_shnum; i++)
  {
	// Reading the current header
	Elf32_Shdr* currentHeader = malloc(sizeof(Elf32_Shdr));
	fread(currentHeader,sizeof(Elf32_Shdr),1,file);

	// Adding header to array
	sectionHeaders[i] = currentHeader;
  }

  // Isolating the section header containing the names of the sections
  Elf32_Shdr* nameSectionHeader = sectionHeaders[elfHeader->e_shstrndx];
  int nameSectionSize = nameSectionHeader->sh_size;

  // Jumping to the name section and reading it
  char* nameSection = malloc(nameSectionSize);
  fseek(file,nameSectionHeader->sh_offset,0);
  fread(nameSection,nameSectionSize,1,file);

  // Defining an array to hold all the names
  char** names = malloc(sizeof(char*) * elfHeader->e_shnum);
  
  // Instead of simply looping through the name header, we have to get the name indexes from the individual section headers
  // This is because the name header stores the names in a DIFFERENT ORDER than the sections are stored (for some reason)
  
  // Looping over all sections
  for(int i = 0; i < elfHeader->e_shnum;i++)
  {
	// Declaring a new string
	names[i] = malloc(sectionHeaders[i]->sh_size);
	// Getting the offset of the name of the current section
	int nameOffset = sectionHeaders[i]->sh_name;
	int j; char c;
	// Looping until we find a null terminator, and copying the name over
	for(j = nameOffset; (c = nameSection[j]) != '\0'; j++)
	{
		names[i][j - nameOffset] = c;
	}
	names[i][j - nameOffset] = c;
  }

  // Printing extended info about the names
  if (extended)
  {
	printf("\nPrinting section names and sizes for %s",filePath);
	int totalSize = 0;
	for(int i = 0; i < elfHeader->e_shnum; i++)
	{
		printf("\n %2d] %20s",i,names[i]);
		printf(" Size: %5d",sectionHeaders[i]->sh_size);
		totalSize += sectionHeaders[i]->sh_size;
	}
	printf("\n total size: %24d",totalSize);
	printf("\n");
  }

  // We want to get only the .text and .data sections. (if they exist) Isolating these sections:
  char* textName = ".text";
  char* dataName = ".data";
  int textIndex  = -1;
  int dataIndex  = -1;
  for(int i = 0; i < elfHeader->e_shnum; i++)
  {
	  if(strcmp(textName,names[i]) == 0)
	  {
		textIndex = i;
		break;
	  }
	  else if(strcmp(dataName,names[i]) == 0)
	  {
		dataIndex = i;
		break;
	  }
  }

  // Defining the array that will hold our .text and .data files
  int arraySize = 0;
  if(textIndex >= 0){arraySize += sectionHeaders[textIndex]->sh_size;}
  if(dataIndex >= 0){arraySize += sectionHeaders[dataIndex]->sh_size;}
  // We want the program to have the size corresponding to the closest multiple of 512 bytes (n sectors)
  arraySize = (arraySize % 512 == 0) ? arraySize : arraySize - (arraySize % 512) + 512;
  char* program = malloc(arraySize);
  // Defining an int to point to the current spot in the program array we are writing to
  int programPointer = 0;

  // Printing extended information about the program size
  if (extended)
  {
	printf("\nPrinting program size for %s",filePath);
	printf("\n Size:                    %d",arraySize);
	printf("\n");
  }

  // Reading the text section into the program array (if it exists)
  if(textIndex >= 0)
  {
	// Getting the text section header
  	Elf32_Shdr* textSectionHeader = sectionHeaders[textIndex];
  	int textSectionSize = textSectionHeader->sh_size;
	// Getting and reading the text section
	char* textSection = malloc(textSectionSize);
	fseek(file,textSectionHeader->sh_offset,0);
	fread(textSection,textSectionSize,1,file);
	// Inserting the text section into the program
	for(int i = 0; i < textSectionSize; i++)
	{
		program[programPointer++] = textSection[i];
	}
  }

  // Reading the data section into the program array (if it exists)
  if(dataIndex >= 0)
  {
	// Getting the data section
  	Elf32_Shdr* dataSectionHeader = sectionHeaders[dataIndex];
  	int dataSectionSize = dataSectionHeader->sh_size;
	// We now have the text section. We want to place the text section into a char array with a minimum size of 512 bytes (a sector
	char* dataSection = malloc(dataSectionSize);
	fseek(file,dataSectionHeader->sh_offset,0);
	fread(dataSection,dataSectionSize,1,file);

	for(int i = 0; i < dataSectionSize; i++)
	{
		program[programPointer++] = dataSection[i];
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
