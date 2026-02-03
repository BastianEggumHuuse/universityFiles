
#   Generate 16 bit compatible code.
.code16gcc
#   These symbols are declared global and will be visible
#   to the linker.
.globl get_char
.globl write_char
.globl set_marker
.globl get_vmode
#   Put the following assembly code in .text section (see
#   readelf).
.text

# ###################################################### #
#   Notes on calling convention in C, i386 (cdecl)
#
#   void caller(void){
#       callee(args)
#   }
#
#   void callee(args){
#       // Do stuff
#       ret
#   }
#
#   1) Arguments / Return values
#   Arguments are passed on the stack. The caller will 
#   allocate and clean up stack itself. Return values are
#   passed in %ax from the callee. Inspect the assembly
#   generated code when in doubt of how arguments and 
#   return values are passed on the stack.
#
#   The return address is implicitly pushed on the stack
#   when the call instruction is encountered. Likewise, it
#   is implicitly popped off the stack when the ret
#   instruction is encountered. 
#
#   2) Register preservation
#   The caller will preserve the registers %ax, %cx and
#   %dx. The callee must take care to preserve the rest
#   (part of your job in these functions.)
#
#   3) gcc
#   These functions are declared in shell.h with the 
#   cdecl attribute. It means that the compiler will push
#   any function call arguments on the stack, from right 
#   to left. 
#
#   !!!
#   Even though this project is running in real mode (16
#   bit mode) it seems that the compiler generates code
#   as if running in 32 bit mode. This means that all 
#   arguments are aligned to a 4-byte boundary instead of
#   the more intuitive 2-byte boundary. You need to watch
#   how you implement the functions so that you get the
#   correct arguments to work on. 
#
#   Try to do: 
#       gcc -S -march=i386 -m32 shell.c
#   and inspect the assembly code calling these functions.
#
#   4) Stack frame
#   In these functions it is probably a good idea to
#   reserve some stack space for local variables. Normally
#   a function will always create a stack frame for this
#   purpose.
#
#   The following steps are just an example how it can be 
#   done (on callee entry):
#    - push old %bp value (preservation)
#    - mov %sp value to %bp
#    - subtract x bytes from %sp, where x is the number of
#      bytes to reserve
#    - <Perform function call work>
#   
#   On exit
#    - add x bytes to %sp
#    - pop stack into &bp (preservation)
#    - return
#
#   Example:
#    
#       set_marker(uint16_t x, uint16_t y)
#
#   Stack space after setup:
#   10(%bp): y
#   6(%bp): x
#   2(%bp): return address
#   (%bp): old %bp
#
# ###################################################### #

# ###################################################### #
#   Stack on entry
#
#   8(%sp) : y
#   4(%sp) : x
#   (%sp)  : return address
#
# ###################################################### #
set_marker:

    pushw %bp           # %sp = %sp - 2
    movw %sp, %bp       # Strictly speaking, we don't need
                        # to reserve stack space

    pushw %bx           # Preservation

    movb $2, %ah        # ah -> 2 (set marker)
    movb $0, %bh        # bh -> 0 (page 0)
    movb 6(%bp), %dl    # dl -> x coordinate
    movb 10(%bp), %dh   # dh -> y coordinate

    int $0x10

    popw %bx            # Preservation
    
    movw %bp, %sp       
    popw %bp

    ret

# ###################################################### #
#
#   BIOS int 0x16
#
#   INPUT
#    - %ah: 0x0
#   OUTPUT
#    - %ah: scan code (ignore it)
#    - %al: ASCII character read
#
#   Stack on entry
#    (%sp)  : return address
#
#   No arguments (void)
#   Return value should be in %al on ret
#
# ###################################################### #

get_char:

    # Preserving the base stack pointer and allocating stack space
    pushw %bp
    movw %sp, %bp

    sub $4, %sp
    
    pusha

    # Setting the input parameters for INT 0x16
    movb $0x0, %ah     # 0x0 is the function number for getting keyboard input

    int $0x16        # Call INT 0x16, the keyboard interfacing interrupt
    # The read character is now stored in al, which is the register output is read from

    movb $0, %ah        # We want to ignore the upper bits of ax
    movw %ax, -4(%bp) # Storing this value

    popa

    movw -4(%bp), %ax  # Restoring this value to ax (which is returned)

    movw %bp, %sp      # Deallocating stack space
    popw %bp         # Restoring %bp

    ret             # Returning

# ###################################################### #
#
#   BIOS int 0x10
#
#   INPUT
#    - %ah: 0x0A
#    - %al: ASCII character to write
#    - %bh: page number (use 0)
#    - %cx: repeat write this number of times
#    OUTPUT
#    - none
#
#   Stack on entry
#
#   4(%sp)  : char
#   (%sp)   : return address
#
# ###################################################### #

write_char:

    # Preserving the base stack pointer
    pushw %bp
    movw %sp, %bp
    
    pusha

    # Setting the input parameters for INT 0x10
    movb $0x0A, %ah # ah is used for the function number. 0x0A is writing at cursor
    movb 6(%bp), %al  # We use al for the character. Here we print !
    movb $0,    %bh    # We don't care about page number
    movw $1,    %cx    # We don't want to repeat the action

    int $0x10     # Finally, call INT 10h, which is the video interrupt

    popa

    movw %bp, %sp
    popw %bp      # Restoring bp

    ret          # Returning

# ###################################################### #
#   You can figure this one out yourself
# ###################################################### #
get_vmode:

    pushw %bp

    int $0x11

    andw $0x0030, %ax
    shrb $4, %al

    popw %bp
    ret


