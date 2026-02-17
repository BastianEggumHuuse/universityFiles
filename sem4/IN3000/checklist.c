// A list of stuff to check

/* The C programming language:

	-
 
*/

/* Assembly:
	
	- $ Is required both for macros (.equ MACRONAME) and memory shortcuts (shortcut:)
	- Short jumps cannot be too far away in the code (like physically)
	- When reading memory adresses, shortcuts give you the adress locally, as in 
	  relative to the program being run. When reading from memory, we need a global
	  memory adress, and we need to add the place the program is loaded. (see bootblock.s) 
	- When writing to a segment register, remember that its multiplied by 16! This means that
	  if we have ES:BC = 0x0800:0x0000, that translates to memory adress 0x8000!
	- At the end of memory, specifically after segment 0x9000, there is mysterious BIOS data.
	  Don't touch! And don't create the stack there!!!
	-
*/

/* Links:

   	- Memory map: https://wiki.osdev.org/Memory_Map_(x86)
	- Printing: https://wiki.osdev.org/Printing_To_Screen
	- ASCII: https://www.ascii-code.com/
	- GAS (AT&T) Manual: https://docs.oracle.com/cd/E53394_01/pdf/E54851.pdf
	-

*/
