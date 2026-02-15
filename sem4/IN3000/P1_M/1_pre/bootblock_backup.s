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
	movb $0x20, %ah
	movb $127, %al
	movw $2, %cx
	movb $0, %dh
	movw $0x0800, %bx
	movw %bx, %es
	movw 0xcc09, %sp  # same as 0x07c0:0509   - getting the os size


	# ----- Add funcitonality to read all 4 bytes
	# ----- Check if the bytes are correct in the image file


	jmp checkIfDone

	# Path - /mnt/c/Users/maxfo/Documents/UiO/vår26/In3000/In3000-precode-P1/1_pre

checkIfDone:
	# If SP >= 127
	movw %sp, %bx
	cmpw $0x007f, %bx   # 127
	jle maxRead

	movw %sp, %bx
	cmpw $0, %bx

	# Set SP to the remaining kernel size
	movw %sp, %dx
	movb %dl, %al
	movb $0, %dh
	jmp smallRead

maxRead:
	movw $0x0000, %bx
	int $0x13

	# If cf is set, throw error 
	jc error

	movb $0x0000, %ah
	imulw $0x0020, %ax

	# ES += 0x0020 * AX
	movw %es, %bx
	addw %ax, %bx
	movw %bx, %es

	addb $2, %dh
	movb $0x20, %ah
	movb $127, %al
	subw $127, %sp
	jmp checkIfDone

smallRead:
	movw $0x0000, %bx
	int $0x13
	# If cf is set, throw error 
	movb $0x0000, %ah
	imulw $0x0020, %ax

	# ES += 0x0020 * AX
	movw %es, %bx
	addw %ax, %bx
	movw %bx, %es

	jmp createStack

error:
	# Reboot 
	ljmp $0xffff, $0x0


createStack:
	# Now the ES register is pointing to the end of the kernel, 
	#   copying ES to SS sets the correct stack segment
	movw %es, %bx 
	movw %bx, %ss

	movw $0xffff, %bx
	movw %bx, %sp



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

