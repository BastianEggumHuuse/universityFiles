/*
 * Simple counter program, to check the functionality of yield().
 * Each process prints their invocation counter on a separate line
 */

#include "common.h"
#include "syslib.h"
#include "util.h"

#define ROWS 4
#define COLUMNS 18

static char picture[ROWS][COLUMNS + 1] =
{
  "    ___       _  ",
  "| __\\_\\_o____/_| ",
  "<[___\\_\\_-----<  ",
  "|  o'            "
};

int main(void);
static void draw(int locx, int locy);
static void print_counter(void);

int main(void)
{
  int locx = 80, locy = 1;

  while (1)
  {
    print_counter();
    locx -= 1;
    if (locx < -20)
    {
      locx = 80;
    }
    /* draw plane */
    draw(locx, locy);
    print_counter();
    delay(DELAY_VAL);
    yield();
  }

  return 0;
}

/* print counter */
static void print_counter(void)
{
  static int counter = 0;

  scrprintf(23, 0, "Process 1 (Plane)     : %d", counter++);
}

/* draw plane */
static void draw(int locx, int locy)
{
  int i, j;

  for (i = 0; i < COLUMNS; i++)
    for (j = 0; j < ROWS; j++)
      scrprintf(locy + j, locx + i, "%c", picture[j][i]);
}

