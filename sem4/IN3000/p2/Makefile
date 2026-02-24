# Makefile for the OS projects.
# Best viewed with tabs set to 4 spaces.

CC = gcc
LD = ld
AS = as
CPP = cpp

# Address of the kernel, process 1 and process 2
KERNEL_ADDR		= 0x8000
PROC1_ADDR		= 0x10000
PROC2_ADDR		= 0x12000

# Compiler flags
#-fno-builtin:		Don't recognize builtin functions that do not begin with
#			'__builtin_' as prefix.
#
#-fno-defer-pop:	Always pop the arguments to each function call as soon
#			as that function returns.
#
#-fomit-frame-pointer:	Don't keep the frame pointer in a register for
#			functions that don't need one.
#			(not used by default)
#
#-fno-stack-protector:	Prevents mangling of stack which happened on some
#			Linux distributions (do not use with a very old GCC)
#
#-make-program-do-what-i-want-it-to-do:
#			Turn on all friendly compiler flags.
#
#-O2:			Turn on all optional optimizations
#			except for loop unrolling and function inlining.
#
#-c:			Compile or assemble the source files, but do not link.
#
#-Wall:			All of the `-W' options combined (all warnings on)
#
#-DKERNEL_ADDR=$(KERNEL_ADDR), -DPROC1_ADDR, etc:
#			Define macro KERNEL_ADDR as $(KERNEL_ADDR). The macro is
#			used by the C preprocessor.
#
#-m32:			Make sure compiled code is 32 bit
#
#-march=i386:


CCOPTS	= -O2 -Wall -c -m32 -fno-stack-protector -fno-defer-pop \
		-fno-builtin -march=i386 \
		-DKERNEL_ADDR=$(KERNEL_ADDR) \
		-DPROC1_ADDR=$(PROC1_ADDR) \
		-DPROC2_ADDR=$(PROC2_ADDR)

# Linker flags
#-nostartfiles:	Do not use the standard system startup files when linking.
#
#-nostdlib:	Don't use the standard system libraries and startup files when
#			linking. Only the files you specify
#			will be passed to the linker.
#

LDOPTS = -n -nostdlib -melf_i386 -N

# Common objects used by both the kernel and user processes
COMMON = util.o print.o

# Objects needed by the processes we'll create.
# Note that the order in which the objects
# appear, is extremely important --
# the object with the process' entry point needs to
# come first.
PROCESS1_OBJS	= proc_start.o process1.o syslib.o $(COMMON)
PROCESS2_OBJS	= proc_start.o process2.o syslib.o $(COMMON)

# Objects needed by the kernel
# Extremely important to have
# entry.o linked in first
KERNEL_OBJS = entry.o kernel.o scheduler.o lock.o th1.o th2.o th3.o $(COMMON)

# Makefile targets
all: createimage bootblock kernel process1 process2 image

# Link kernel and make all but the kernel_start symbols local
# Later we use the kernel_start symbol to resove the kernel start address
# in bootblock
kernel: $(KERNEL_OBJS)
	$(LD) $(LDOPTS) -Ttext $(KERNEL_ADDR) -o kernel $^
	objcopy $@ $@ -G kernel_start

entry.o: entry.S
	$(CC) $(CCOPTS) -x assembler-with-cpp -o $@ $<

process1: $(PROCESS1_OBJS)
	$(LD) $(LDOPTS) -Ttext $(PROC1_ADDR) -o process1 $^

process2: $(PROCESS2_OBJS)
	$(LD) $(LDOPTS) -Ttext $(PROC2_ADDR) -o process2 $^

bootblock: bootblock.o kernel
	$(LD) $(LDOPTS) -Ttext 0x0 -Rkernel -o bootblock $<

createimage: createimage.o
	$(CC) -o createimage $<

# Create an image to put on the USB stick
image: createimage bootblock kernel process1 process2
	./createimage ./bootblock ./kernel ./process1 ./process2

# Put the image on the usb (these two stages are independent, as both
# vmware and bochs can run using only the image file stored on the harddisk)	
boot: image
	dd if=./image of=/dev/sdb bs=512

# Figure out dependencies, and store them in the hidden file .depend
depend: .depend
.depend:
	$(CC) $(CCOPTS) -MM *.c > $@

# Clean up!
clean:
	rm -f *.o
	rm -f bootblock createimage image image.lock kernel process1 process2
	rm -f .depend
	rm -f entry-pp.s

# No, really, clean up!
distclean: clean
	rm -f *~
	rm -f \#*
	rm -f *.bak
	rm -f bochsout.txt
	rm -f serial.out

# How to compile a createimage
createimage.o:createimage.c
	$(CC) -c -o $@ $<

# How to compile a C file
%.o:%.c
	$(CC) $(CCOPTS) $<

# How to assemble
%.o:%.s
	$(CC) $(CCOPTS) $<

# How to produce assembler input from a C file
%.s:%.c
	$(CC) $(CCOPTS) -S $<

# Include dependencies (the leading dash prevents
# warnings if the file doesn't exist)
-include .depend
