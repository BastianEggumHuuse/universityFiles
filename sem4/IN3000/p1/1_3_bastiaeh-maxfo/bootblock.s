# bootblock.s

	.equ	KERNEL_SEGMENT, 0x0800
	.equ	DISPLAY_SEGMENT, 0xb800
	.equ    STACK_SEGMENT, 0x7000
	.equ    STACK_POINTER, 0xfffe

.text                  # Code segment
.globl	_start	       # The entry point must be global
.code16                # Real mode

_start:
	jmp	createStack

os_size:
	# area reserved for createimage to write the OS size
	.word	0
	.word	0

createStack:
	# Set SS to the stack segment address
	movw $STACK_SEGMENT, %bx
	movw %bx, %ss

	# Set stackpointer to the beginning of the stack
	movw $0xfffe, %bx
	movw %bx, %sp

	# --- Set registers for INT 0x13 ---
	movb $0x02, %ah      # Do a Read Sectors From Drive
	movb $1, %al         # Read 1 sector at a time
	movb $0, %ch         # Cylinder 0
	movb $2, %cl   		 # Start from second sector (bootblock is in first) 
	movb $0, %dh         # Drive 0

	# Set ES to kernel segment
	movw $KERNEL_SEGMENT, %bx
	movw %bx, %es

	# Set DI to the first 2 bytes of the OS size
	movw (os_size + 0x7c00), %bx
	movw %bx, %di

	# Set SI to the last 2 bytes of the OS size
	movw (os_size + 0x7c00 + 2), %bx
	movw %bx, %si

	jmp checkIfDone

# Checks if we are done reading
checkIfDone:
	# if DI != 0
	movw %di, %bx
	cmpw $0, %bx
	jnz decDI

	# if SI == 0
	movw %si, %bx
	cmpw $0, %bx
	jz toKernel

	# --- DI is now 0 and SI is not 0, so gotta reset DI and decrease SI ---
	# SI--
	movw %si, %bx
	decw %bx
	movw %bx, %si

	# DI = 0xFFFF
	movw $0xffff, %bx 
	movw %bx, %di

	jmp read

# Only decrease DI if DI != 0
decDI:
	# DI--
	movw %di, %bx
	decw %bx
	movw %bx, %di
	jmp read

read:
	movw $0, %bx        # Read into the start of the ES segment

	int $0x13
	

	# If CF is set, throw error 
	jc error

	# --- Make registers ready for another INT 0x13 ---

	# Make AX contain the number of sectors read (0x0020 is the size of 1 sector)
	movb $0x0000, %ah
	imulw $0x0020, %ax

	# ES += 0x0020 * AX
	movw %es, %bx
	addw %ax, %bx
	movw %bx, %es

	movb $0x02, %ah     # Do a Read Sectors From Drive
	movb $1, %al        # Number of sectors to read 

	incb %cl            # We want to read the next segment

	# if CL != 64
	cmpb $64, %cl
	jnz checkIfDone

	# --- There are 63 sectors per drive, gotta reset CL and increase DH ---
	# DH++ and CL = 1
	incb %dh
	movb $1, %cl

	jmp checkIfDone


error:
	# Print exit code
	movw	$DISPLAY_SEGMENT,%bx	
	movw	%bx,%es	

	movb    %ah, %al
	movb    $0x00, %ah

	# If error code under 10 
	cmpw $9, %ax
	jng smallErrorCode

	# If larger error code, print as letter 
	movb $0x04, %ah     # black background with red text

	addb    $0x37, %al
	movw	%ax,%es:(0x0)	# Write error kode to top left corner

# If error code between 0 and 9, print as number
smallErrorCode:
	movb $0x04, %ah			# black background with red text

	addb    $0x30, %al
	movw	%ax,%es:(0x0)	# Write error kode to top left corner
	
	jmp forever

forever:
	jmp	forever                 # Loop forever


toKernel:
	# Do a long jump to 0x0800:0000
	ljmp $0x0800, $0x0000
