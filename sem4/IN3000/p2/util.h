/* util.h
 *
 * Various utility functions that can be
 * linked with both the kernel and user code.
 * Best viewed with tabs set to 4 spaces.
 */

#ifndef UTIL_H
  #define UTIL_H

/* Includes */
  #include  "common.h"

/* See util.c for commments */

void    clear_screen( int minx, int miny, int maxx, int maxy);
void    scroll( int minx, int miny, int maxx, int maxy);
int      peek_screen( int x, int y);

void    delay(int val);
uint64_t  get_timer( void);

uint32_t  atoi ( char *s );
void    dtoa(double dbl, char *s, int s_len);
void    itoa(uint32_t n, char *s);
void    itohex(uint32_t n, char *s);

int scrprintf(int line, int col, char *in, ...);
int rsprintf(char *in, ...);

void    reverse( char *s);
int      strlen( char *s);

int      same_string( char *s1, char *s2);

void    bcopy( char* source, char* destin, int size);
void    bzero( char* a, int size);

uint8_t    inb(int port);
void    outb(int port, uint8_t data);

void    srand(uint32_t seed);
uint32_t  rand(void);

#endif

