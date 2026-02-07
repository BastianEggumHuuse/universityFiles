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

// Declaring the function we wish to use later on
char* readFile(char* filePath);

int main(int argc, char **argv)
{
  // There should be three arguments. We can loop through them and get their values

  // These are the values we want to read
  __int8 extended = 0;          // If we should print extended information
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
}

char* readFile(char* filePath)
{
  // Opening the file in read mode
  FILE* file = fopen(filePath,"r");

  Elf32_Ehdr header;
  fread(&header, sizeof(header),1,file);
}