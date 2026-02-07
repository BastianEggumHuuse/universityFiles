# bootblock.s

# .equ symbol, expression. 
# This directive sets the value of symbol to expression
	.equ	BOOT_SEGMENT,0x07c0
	.equ	DISPLAY_SEGMENT,0xb800

.text                  # Code segment
.globl	_start	       # The entry point must be global
.code16                # Real mode

#
# The first instruction to execute in a program is called the entry
# point. The linker expects to find the entry point in the "symbol" _start
# (with underscore).
#
_start:

#
# Do not add any instructions before or in os_size.
#
	
	jmp	over
os_size:
	# area reserved for createimage to write the OS size
	.word	0
	.word	0
over:
	movw	$DISPLAY_SEGMENT,%bx	
	movw	%bx,%es			
	movw	$0x074b,%es:(0x0)	# Write 'K' in the upper left corner
					#  of the screen
forever:
	jmp	forever                 # Loop forever

