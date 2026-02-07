/*
First lecture!

Why do we learn this?
We want to learn how an OS works, and what the central abstractions are.
This helps us with all forms of development, writing larger design documents, etc.

We're going to build an entire operating system! (within reason of course)
There are 7 projects, P0-P6. All are mandatory except for P0

Lectures will teach OS abstractions and theory, and we get graded
on OS implementation.

Go visit [freecodecamp.org how to write a good software design document]



Technical intro start!!

Can you run code without an OS? Yes! But it's very difficult.

The OS provides abstractions for the hardware! It let's us programmers for example work with files,
instead of looking at memory directly on the disk.
The OS also manages resources! It decides which processes get resources, and which threads get to run.
The OS is also a security manager. It protects the hardware from direct access.

There exists many different types of OS, and they are often customized for the device it runs on. The classical
operating system is what's called a kernel. This contains a lot of fundamental abstractions, but only a few services 
(no GUI for example.) We will implement such a kernel!

We are gonna learn so much holy shit

We will implement the OS on a virtual machine, using Bochs (you should probably download this)

Dealing with complexity:
    
    Do early - fail early.

A very simplified computer:

    The CPU is connected to a chipset, which is connected to memory and the I/O bus.
    The I/O bus is connected to all other machines.

A list of what we will learn:

    OS Architechture
    Syscalls
    Process abstraction
    Synchronization abstractions (syncinc threads)
    Message passing abstraction
    Processor abstraction
    Memory abstraction
    I/O abstraction
    Storage abstraction

    and loads of practical skills!!!

We need:

    A pc with Linux
    The C programming language (gcc)
    The assembler (gas from gnu)
    Bochs PC emulator for Linux

Books can be found at AnnasArchive but remember that piracy is illegal (and awesome)

*/