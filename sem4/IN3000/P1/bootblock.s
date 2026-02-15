# bootblock.s

# .equ symbol, expression. 
# This directive sets the value of symbol to expression
	.equ	KERNEL_SEGMENT,0x0800
	.equ	DISPLAY_SEGMENT,0xb800
	.equ    STACK_SEGMENT, 0x7000
	.equ    STACK_POINTER, 0xfffe

.text                  # Code segment
.globl	_start	       # The entry point must be global
.code16                # Real mode

#
# The first instruction to execute in a program is called the entry
# point. The linker expects to find the entry point in the "symbol" _start
# (with underscore).
#
_start:
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

	jmp createStack

createStack:
	# Now the ES register is pointing to the end of the kernel, 
	#   copying ES to SS sets the correct stack segment
	movw $STACK_SEGMENT, %bx
	# movw %es, %bx 
	movw %bx, %ss

	# Set stackpointer to the beginning of the stack
	movw $0xfffe, %bx
	movw %bx, %sp

	# xchgw %bx, %bx
	
	# jmp toKernel

	# Set registers for INT 0x13 
	movb $0x02, %ah
	movb $1, %al
	movw $2, %cx   # second sector 
	movb $0, %dh
	movw $KERNEL_SEGMENT, %bx
	movw %bx, %es

	movw (os_size + 0x7c00), %bx
	movw %bx, %di

	movw (os_size + 0x7c00 + 2), %bx
	movw %bx, %si

	jmp checkIfDone

	# Path - /mnt/c/Users/maxfo/Documents/UiO/vår26/In3000/In3000-precode-P1/1_pre

checkIfDone:
	# if SP == 0
	movw %di, %bx
	cmpw $0, %bx
	jnz decSP

	# if SS == 0
	movw %si, %bx
	cmpw $0, %bx
	jz toKernel

	# SS--
	movw %si, %bx
	decw %bx
	movw %bx, %si

	# SP = 0xFFFF
	movw $0xffff, %bx 
	movw %bx, %di
	jg read

decSP:
	# SP--
	movw %di, %bx
	decw %bx
	movw %bx, %di
	jmp read

read:
	# xchgw %bx, %bx   # add 337 and then it fails at test al, al
	movw $0, %bx

	int $0x13
	

	# If CF is set, throw error 
	jc error

	# --- Make registers ready for another INT 0x13 ---

	# Make AX contain the number of sectors read
	movb $0x0000, %ah
	imulw $0x0020, %ax



	# ES += 0x0020 * AX
	movw %es, %bx
	addw %ax, %bx
	movw %bx, %es

	movb $0x02, %ah
	movb $1, %al      # number of sectors to read 

	incb %cl

	# if cl == 64
	cmpb $64, %cl
	jnz checkIfDone

	# else DH++ and CL = 1
	incb %dh
	movb $1, %cl

	jmp checkIfDone


error:
	# Print exit code
	movw	$DISPLAY_SEGMENT,%bx	
	movw	%bx,%es	

	movb    %ah, %al
	movb    $0x4e, %ah
	addb    $65, %al
	movw	%ax,%es:(0x0)	# Write error kode to top left corner
	
	jmp forever

forever:
	jmp	forever                 # Loop forever

	# Reboot 
	# ljmp $0xffff, $0x000

toKernel:
	# Do a long jump to 0x0800:0000
	ljmp $0x0800, $0x0000
