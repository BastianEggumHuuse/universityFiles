#include <assert.h>
#include <elf.h>
#include <errno.h>
#include <stdarg.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define IMAGE_FILE "image"
#define ARGS "[--extended] [--vm] <bootblock> <executable-file> ..."

#define SECTOR_SIZE 512
#define OS_SIZE_LOC 2
#define BOOT_MEM_LOC 0x7c00
#define OS_MEM_LOC 0x8000

/* variable to store pointer to program name */
char *progname;

/* variable to store pointer to the filename for the file being
   read. This is not an example of good program design */
char *elfname;

/* structure to store command line options */
static struct {
    int vm;
    int extended;
} options;

/* prototypes of local functions */
static void create_image(int nfiles, char *files[]);
static void error(char *fmt,...);
static void read_ehdr(Elf32_Ehdr *ehdr, FILE *fp);
static void read_phdr(Elf32_Phdr *phdr, FILE *fp, int ph, Elf32_Ehdr ehdr);
static void write_segment(Elf32_Ehdr ehdr, Elf32_Phdr phdr, FILE *fp,
    FILE *img, int *nbytes, int *first);
static void write_os_size(int nbytes, FILE *img);

int main(int argc, char **argv)
{
    /* process command line options */
    progname = argv[0];
    options.vm = 0;
    options.extended = 0;
    while ((argc > 1) && (argv[1][0] == '-') && (argv[1][1] == '-')) {
  char *option = &argv[1][2];

  if (strcmp(option, "vm") == 0) {
      options.vm = 1;
  } else if (strcmp(option, "extended") == 0) {
      options.extended = 1;
  } else {
      error("%s: invalid option\nusage: %s %s\n", progname,
    progname, ARGS);
  }
  argc--;
  argv++;
    }
    if (options.vm == 1) {
  error("%s: option --vm not implemented\n", progname);
    }
    if (argc < 3) {
  /* at least 3 args (createimage bootblock kernel) */
  error("usage: %s %s\n", progname, ARGS);
    }
    create_image(argc - 1, argv + 1);
    return 0;
}

static void create_image(int nfiles, char *files[])
{
    int ph, nbytes = 0, first = 1;
    FILE *fp, *img;
    Elf32_Ehdr ehdr;
    Elf32_Phdr phdr;

    /* open the image file */
    img = fopen(IMAGE_FILE, "w");
    if (img == NULL)
  error("%s: %s: ", progname, IMAGE_FILE);

    /* for each input file */
    while (nfiles-- > 0) {

  /* open input file */
        elfname = *files;
  fp = fopen(*files, "r");
  if (fp == NULL)
      error("%s: %s: ", progname, elfname);

  /* read ELF header */
  read_ehdr(&ehdr, fp);
  printf("0x%04x: %s\n", ehdr.e_entry, *files);

  /* for each program header */
  for (ph = 0; ph < ehdr.e_phnum; ph++) {

      /* read program header */
      read_phdr(&phdr, fp, ph, ehdr);

      /* write segment to the image */
      write_segment(ehdr, phdr, fp, img, &nbytes, &first);
  }
  fclose(fp);
  files++;
    }
    write_os_size(nbytes, img);
    fclose(img);
}

static void read_ehdr(Elf32_Ehdr *ehdr, FILE *fp)
{
    if (fread(ehdr, sizeof(*ehdr), 1, fp)  != 1)
  error ("%s: %s: Reading ELF header failed\n", progname, elfname);

    if (ehdr->e_ident[EI_MAG1] != 'E' ||
        ehdr->e_ident[EI_MAG2] != 'L' ||
        ehdr->e_ident[EI_MAG3] != 'F' ||
  ehdr->e_type != ET_EXEC)
  error ("%s: %s: Not an ELF executable file\n", progname, elfname);
}

static void read_phdr(Elf32_Phdr *phdr, FILE *fp, int ph, Elf32_Ehdr ehdr)
{
    fseek(fp, ehdr.e_phoff + ph * ehdr.e_phentsize, SEEK_SET);
    if (fread(phdr, sizeof(*phdr), 1, fp) != 1)
  error ("%s: %s: Reading program header failed\n", progname, elfname);

    if (options.extended == 1) {
  printf("\tsegment %d\n", ph);
  printf("\t\toffset 0x%04x", phdr->p_offset);
  printf("\t\tvaddr 0x%04x\n", phdr->p_vaddr);
  printf("\t\tfilesz 0x%04x", phdr->p_filesz);
  printf("\t\tmemsz 0x%04x\n", phdr->p_memsz);
    }
}

static void write_segment(Elf32_Ehdr ehdr, Elf32_Phdr phdr, FILE *fp,
    FILE *img, int *nbytes, int *first)
{
    int phyaddr;

    if (phdr.p_memsz != 0) {
  /* find physical address in image */
  if (*first == 1) {
      phyaddr = 0;
      *first = 0;
  } else {
      phyaddr = phdr.p_vaddr - OS_MEM_LOC + SECTOR_SIZE;
  }
  if (phyaddr < *nbytes) {
      error("%s: Memory conflict\n", progname);
  }

  /* write padding before the segment */
  if (*nbytes < phyaddr) {
      if (options.extended == 1) {
    printf("\t\tpadding up to 0x%04x\n", phyaddr);
      }
      while (*nbytes < phyaddr) {
    fputc(0, img);
    (*nbytes)++;
      }
  }

  /* write the segment itself */
  if (options.extended == 1) {
      printf("\t\twriting 0x%04x bytes\n", phdr.p_memsz);
  }
  fseek(fp, phdr.p_offset, SEEK_SET);
  while (phdr.p_filesz-- > 0) {
      fputc(fgetc(fp), img);
      (*nbytes)++;
      phdr.p_memsz--;
  }
  while (phdr.p_memsz-- > 0) {
      fputc(0, img);
      (*nbytes)++;
  }

  /* write padding after the segment */
  if (*nbytes % SECTOR_SIZE != 0) {
      while (*nbytes % SECTOR_SIZE != 0) {
    fputc(0, img);
    (*nbytes)++;
      }
      if (options.extended == 1) {
    printf("\t\tpadding up to 0x%04x\n", *nbytes);
      }
  }
    }
}

static void write_os_size(int nbytes, FILE *img)
{
    short os_size;

    os_size = nbytes / SECTOR_SIZE - 1;
    fseek(img, OS_SIZE_LOC, SEEK_SET);
    fwrite(&os_size, sizeof(os_size), 1, img);
    if (options.extended == 1) {
  printf("os_size: %d sectors\n", os_size);
    }
}

/* print an error message and exit */
static void error(char *fmt, ...)
{
    va_list args;

    va_start(args, fmt);
    vfprintf(stderr, fmt, args);
    va_end(args);
    if (errno != 0) {
        perror(NULL);
    }
    exit(EXIT_FAILURE);
}
