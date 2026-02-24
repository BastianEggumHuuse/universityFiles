/* util.c
 *
 * Various utility functions that can be linked with both the kernel
 * and user code.
 *
 * K&R = "The C Programming Language", Kernighan, Ritchie
 *
 * Best viewed with tabs set to 4 spaces.
 */

#include "util.h"
#include "print.h"

static short *screen = SCREEN_ADDR;



/*
 * Convert a double to a string with the specified precision
 * (ignores sign; double is always treated as positive)
 */
void dtoa(double dbl, char *s, int precision) {

    int i;
    long unsigned int integer, multiplier;

    if(dbl < 0)
        dbl *= (-1);

    i = 0;
    integer = (long unsigned int) dbl;
    multiplier = 10;

    do{
        s[i++] = integer % 10 + '0';
        integer /= 10;

    }while(integer > 0);

    s[i] = '\0';
    reverse(s);

    if(!precision)
        return;

    s[i++] = ',';
    s = &s[i];

    multiplier = 1;
    for(i = 0; i < precision; i++)
        multiplier *= 10;

    dbl -= (long int) dbl;
    integer = dbl * multiplier;

    multiplier = 10;
    i = 0;

    do{
        s[i++] = integer % multiplier + '0';
        integer /= 10;

    }while(integer > 0);

    s[i] = '\0';
    reverse(s);

}

/*
 * Print to screen
 */
struct location {
  int line;
  int col;
};

int scrwrite(void *data, char c) {
  struct location *loc = (struct location *)data;

  if ((loc->line < 0) || (loc->line > 24))
    return 0;
  if ((loc->col < 0) || (loc->col > 79))
    return 0;

  /* Tabulating */
  if (c == '\t') {
    /* If at tabulator position then tabulate to the next */
    if (loc->col % 5 == 0)
      loc->col++;

    while ((loc->col % 5) && (loc->col < 80))
      loc->col++;
  } else {
    /* text screen is 80x25 characters */
    screen[loc->line * 80 + loc->col] = 0x07 << 8 | c;
    loc->col++;
  }
  return 1;
}


int scrprintf(int line, int col, char *in, ...) {
  va_list args;

  struct location loc = {
    .line = line,
    .col = col
  };

  struct output out = {
    .write = scrwrite,      /* write function                */
    .data = &loc          /* where to print on the screen  */
  };

  va_start(args, in);

  return uprintf(&out, in, args);
}

/*
 * Print to serial port 0x3F8
 */
int rswrite(void *drop, char c) {
  /* Wait for the previous character to be sent */
  while ((inb(0x3FD) & 0x20) != 0x20);

  /* Send the character */
  outb(0x3F8, c);

  return 1;
}

int rsprintf(char *in, ...) {
  va_list args;
  struct output out = {
    .write = rswrite,     /* write function     */
    .data = NULL          /* optional data      */
  };

  outb(0x3FB, 3);         /* word is 8bits long */
  va_start(args, in);

  return uprintf(&out, in, args);
}

/* Read the character stored at location (x,y) on the screen */
int peek_screen(int x, int y) {
  return screen[y*80+x] & 0xff;
}

void clear_screen(int minx, int miny, int maxx, int maxy) {
  int i, j, k;

  for (j = miny; j < maxy; j++) {
    for (i = minx; i < maxx; i++) {
      k = j * 80 + i;
      screen[k] = 0x0700;
    }
  }
}

/* scroll screen */
void scroll(int minx, int miny, int maxx, int maxy) {
  int i, j, k;

  for (j = miny; j < maxy; j++) {                /* all lines */
    for (i = minx; i < maxx; i++) {        /* all characters */
      k = j * 80 + i;
      if (j < maxy-1)                /* if not in first
      				* line */
           screen[k] = screen[k+80]; /* move character
      			        * one line up */
      else
           screen[k] = 0x0700;       /* 0x0700 = blank */
    }
  }
}

/* Simple delay loop to slow things down */
void delay(int n) {
        /* volatile so it will not be optimized away */
  volatile int i,j;

  for (i = 0; i < n; i++) {
    j = i * 11;
  }
}


/* Read the pentium time stamp counter */
uint64_t  get_timer(void) {
  uint64_t  x = 0LL;

  /* Load the time stamp counter into edx:eax (x) */
  __asm__ volatile ("rdtsc":"=A" (x));

  return x;
}

/* Convert an ASCII string (like "234") to an integer */
uint32_t atoi (char *s ) {
  int n;
  for (n = 0; *s >= '0' && *s <= '9'; n = n * 10 + *s++ - '0' )
  ;
  return n;
}

/*
 * Functions from Kerninghan/Ritchie - The C Programming Language
 */

/* Convert an integer to an ASCII string, Page 64 */
void itoa(uint32_t n, char *s) {
  int i;

  i = 0;
  do {
    s[i++] = n %10 + '0';
  } while((n /= 10) >0) ;
  s[i++] = 0;
  reverse(s);
}

/* Convert an integer to an ASCII string, base 16 */
void itohex(uint32_t n, char *s) {
  int i,d;

  i = 0;
  do {
    d = n % 16;
    if (d < 10)
      s[i++] = d + '0';
    else
      s[i++] = d - 10 + 'a';
  } while((n /= 16) >0) ;
  s[i++] = 0;
  reverse(s);
}

/* Reverse a string, Page 62 */
void reverse(char* s) {
  int c,i,j;

  for(i = 0, j = strlen(s) - 1; i < j; i++, j--) {
    c  	= s[i];
    s[i]  = s[j];
    s[j]  = c;
  }
}

/* Get the length of a null-terminated string, Page 99 */
int strlen(char* s) {
  int n;
  for (n=0; *s != '\0'; s++)
    n++;
  return n;
}

/* return TRUE if string s1 and string s2 are equal */
int      same_string(char *s1, char *s2) {
  while ((*s1 != 0) && (*s2 != 0)) {
    if (*s1 != *s2)
      return FALSE;
    s1++;
    s2++;
  }
  return (*s1 == *s2);
}

/* Block copy: copy size bytes from source to dest.
 * The arrays can overlap */
void    bcopy(char* source, char* destin, int size) {
  int i;

  if (size == 0)
    return;

  if (source < destin) {
    for (i = size-1; i >= 0; i--)
      destin[i] = source[i];
  }
  else {
    for (i = 0; i < size; i++)
      destin[i] = source[i];
  }
}

/* Zero out size bytes starting at area */
void    bzero(char* area, int size) {
  int i;

  for (i = 0; i < size; i++)
  area[i] = 0;
}

/* Read byte from I/O address space */
uint8_t    inb(int port) {
  int ret;

  __asm__ volatile ("xorl %eax,%eax");
  __asm__ volatile ("inb %%dx,%%al":"=a" (ret):"d"(port));

  return ret;
}

/* Write byte to I/O address space */
void    outb(int port, uint8_t data) {
  __asm__ volatile ("outb %%al,%%dx"::"a" (data), "d"(port));
}


static uint32_t rand_val;

void    srand(uint32_t seed) {
  rand_val = seed;
}

/* Return a pseudo random number */
uint32_t  rand(void) {
    rand_val = rand_val*1103515245 + 12345;
    return (rand_val / 65536) % 32768;
}
