#include <assert.h>
#include <elf.h>
#include <errno.h>
#include <stdarg.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/*
	This file is an alternative solution to createimage.c that uses the program headers instead of the section headers.
	We were told by TAs that we should use the program headers, but when we assembled our bootblock, it had no program headers.
	It's fully possible that this is a mistake on our part, but we haven't found a solution. Instead, we've chosen to write two
	image creators. This one is unused in our project.
*/

// Macros included by default
#define IMAGE_FILE "image"
#define SECTOR_SIZE 512       /* USB sector size in bytes */
#define OS_SIZE_LOC 2         /* OS position within bootblock */  

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
  // Checking if file opened correctly
  if(!file)
  {
	  printf("Couldn't open file %s\n",filePath);
  }

  // Reading the ELF header
  Elf32_Ehdr elfHeader;
  if(!fread(&elfHeader, sizeof(Elf32_Ehdr),1,file))
  {
	printf("Couldn't read ELF header for file %s\n",filePath);
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
  if(programHeaders == NULL){printf("Couldn't allocate memory for program header array\n");} // Error handling

  for(int i = 0; i < elfHeader.e_phnum; i++)
  {
	// Reading the current header
	Elf32_Phdr* currentHeader = malloc(sizeof(Elf32_Phdr));
        if(programHeaders == NULL){printf("Couldn't allocate memory for program header\n");} // Error handling

	int successfulRead = fread(currentHeader,sizeof(Elf32_Phdr),1,file); // Reading
        if(!successfulRead){printf("Couldn't read program header\n");}    	  	     // Error handling
	
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
  if(loadableHeaders == NULL){printf("Couldn't allocate memory for loadable program header array\n");} // Error handling
 
  // Actually getting loadable segments
  int j = 0;
  for(int i = 0; i < elfHeader.e_phnum; i++)
  {
	// Is 1 if loadable
	if (programHeaders[i]->p_type == 1) {loadableHeaders[j++] = programHeaders[i];}
	// if not loadable, we free the memory
	else {free(programHeaders[i]);}
  }

  // Defining the size of the array that will hold our loadable segments
  int arraySize = 0;
  for(int i = 0; i < numSegments; i++) {arraySize += loadableHeaders[i]->p_filesz;}
  // We want the program to have the size corresponding to the closest multiple of 512 bytes (n sectors)
  arraySize = (arraySize % 512 == 0) ? arraySize : arraySize - (arraySize % 512) + 512;
  // Declaring the array
  char* program = malloc(arraySize);
  if(program == NULL){printf("Couldn't allocate memory for program array\n");} // Error handling

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
	// Reading data from a program header
  	Elf32_Phdr* programHeader = loadableHeaders[i];
	int programSize       	  = programHeader->p_filesz;
	// Allocating memory for the segment
	char* programSegment = malloc(programSize);
        if(programSegment == NULL){printf("Couldn't allocate memory for program segment array\n");} // Error handling

	// Reading the segment
	fseek(file,programHeader->p_offset,0);
	int successfulRead = fread(programSegment,programSize,1,file);
	if(!successfulRead){printf("Couldn't allocate memory for program segment array\n");} // Error handling

	// Inserting the text section into the program
	for(int i = 0; i < programSize; i++)
	{
		program[programPointer++] = programSegment[i];
	}

	// Freeing memory
	free(programHeader);
	free(programSegment);
  }

  // Filling the rest of the array with zeroes (padding)
  while(programPointer < arraySize)
  {
	program[programPointer++] = 0;
  }

  // Closing the file
  fclose(file);

  // Freeing all leftover memory
  free(programHeaders);
  free(loadableHeaders);

  // Finally, returning the program data
  *programSize = arraySize;
  return program;
}
