/* kernel.c
 * Best viewed with tabs set to 4 spaces.
 */
#include "common.h"
#include "kernel.h"
#include "th.h"
#include "util.h"
#include "scheduler.h"

/* Statically allocate some storage for the pcb's */
pcb_t    pcb[NUM_TOTAL];
/* Ready queue and pointer to currently running process */
pcb_t    *current_running;


/* This is the entry point for the kernel.
 * - Initialize pcb entries
 * - set up stacks
 * - start first thread/process
 */
FUNCTION_ALIAS(kernel_start, _start);
void _start(void) {
  int    i,
      next_stack = STACK_MIN;

  /* Declare entry_point as pointer to pointer to function returning void
   * ENTRY_POINT is defined in kernel h as (void(**)()0xf00)
   */
  void (**entry_point) ()    = ENTRY_POINT;

  /* load address of kernel_entry into memory location 0xf00 */
  *entry_point = kernel_entry;

  clear_screen(0, 0, 80, 25);

  /* RS232 serial print example
   * Using provided bochs configuration you will find the printout
   * in the serial.out file
	*/

  rsprintf("Starting up kernel\n");

  while (1);
}

/* Helper function for kernel_entry, in entry.S. Does the actual work
 * of executing the specified syscall.
 */
void kernel_entry_helper(int fn) {
}

