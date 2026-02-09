
	.equ	BOOT_SEGMENT,    0x07c0
	.equ	DISPLAY_SEGMENT, 0xb800
	.equ    STACK_POINTER,   0xfffe

.text                  # Code segment
.globl	_start	       # The entry point must be global
.code16                # Real mode

_start:
	jmp	over # Jumping to the actual start of the code
os_size:
	# area reserved for createimage to write the OS size
	.word	0
	.word	0
over:

	# 1) Loading the kernel

	# Preparing for INT 0x13
	mov $0x20, %ah # which 0x13 instruction
	mov $127, %al  # Number of sectors to read
	mov $0, %ch    # Cylinder
	mov $2, %cl    # Sector
	mov $0, %dh    # Drive
	mov DISPLAY_SEGMENT, %es # target memory segment
	mov $0, %bx     	 # target memory offset
	mov os_size, %sp	 # Storing OS size

checkIfFinished:
	
	cmp 127, %sp
	jo  maxRead
	jmp smallRead

maxRead:

	int $0x13 # Calling the interrupt
	jc  crisis

	mov $0, %ah
	mul $0x20    # Doesn't work??
	add %ax, %es # Doesn't work??
	add $2, %dh

	mov $0x20, %ah
	mov 127, %al
	sub 127, %sp
	jmp checkIfFinished:

smallRead:
	
	int $0x13 # Calling the interrupt
	jc  crisis

	mov $0, %ah
	mul $0x20    # Doesn't work??
	add %ax, %es # Doesn't work??
	add $2, %dh
	
createStack:

	mov %es, %ss	
	mov STACK_POINTER, %sp

jumpToKernel:
	
	goto DISPLAY_SEGMENT

crisis:
	jmp crisis
