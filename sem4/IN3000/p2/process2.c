/*
 * Simple counter program, to check the functionality of yield().
 * Each process prints their invocation counter on a separate line
 */

#include "common.h"
#include "syslib.h"
#include "util.h"

static void print_counter(int done);
static int rec(int n);

int main(void)
{
  int i;

  for (i = 0; i <= 100; i++)
    {
      scrprintf(10, 0, "Did you know that 1 + ... + %d = %d", i, rec(i));
      print_counter(FALSE);
      delay(DELAY_VAL);
      yield();
    }
  print_counter(TRUE);
  exit();

  return 0;
}

static void print_counter(int done)
{
  static int counter = 0;

  scrprintf(24, 0, "Process 2 (Math)      : ");
  if (done)
    {
      scrprintf(24, 24, "Exited");
    }
  else
    {
      scrprintf(24, 24, "%d", counter++);
    }
}

/* calculate 1 + ... + n */
static int rec(int n)
{
  if (n % 37 == 0)
    {
      yield();
    }
  if (n == 0)
    {
      return 0;
    }
  else
    {
      return n + rec(n - 1);
    }
}

