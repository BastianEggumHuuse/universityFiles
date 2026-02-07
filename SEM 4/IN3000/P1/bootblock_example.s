# OBS:  denne erstatter ikke bootblock.s som dere har fått
# utlevert med oppgaven, den er kun ment som et hint, samt
# for å vise et par eksempler på noen nyttige 
# kodekonstruksjoner. 
#
# Flere av tingene jeg viser kan kanskje gjøres på andre måter
# (mere kompakt, mere elegant, ++), men dette er i hvert fall
# et utgangspunkt dere kan bruke.
#
# jmb

  
  
  
  .equ  BOOT_SEGMENT,0x07c0
  .equ  DISPLAY_SEGMENT,0xb800
  .equ  STACK_SEGMENT, 0x9000
  .equ  STACK_POINTER, 0xfffe

.text
.globl  _start
.code16

_start:
  jmp  over
os_size:    	
  .word  0
  .word  0
over:
  # setup stack
  movw  $STACK_SEGMENT,%ax
  movw  %ax,%ss
  movw  $STACK_POINTER,%sp

  # setup data segment
  movw  $BOOT_SEGMENT,%ax
  movw  %ax,%ds

  
  # -------------------
  #   Eksempel på enkel if-konstruksjon,
  #   if (a == 2)
  #      a  = 3; 
  #   	
  
  movw  $3, %ax			# sett testverdi for a
  cmpw  $2, %ax
  jne  neida
  movw  $3,  %ax
neida:  


  # -------------------
  #    Eksempel på 
  #  for (i = 0; i < 5; i++) 
  #      a = i;
  # 
  movw  $0,  %cx	# Bruker CX som i
loop1:  
  cmpw  $5, %cx		
  jge  loop1done	# Jump if greater than or equal
  movw  %cx, %ax	# Bruker AX som a
  incw  %cx
  jmp  loop1
loop1done:
  # Legg merke til at cmpw $5, %cx ville vært
  # cmp %cx, $5 i intel syntax. jge i dette tilfellet
  # betyr altså, "jump til loop1done if %cx is greater
  # than or equal to 5"

  # ------------------
  #  Her har jeg lagt inn et kall til print, slik at
  #  koden ligner på:   
  #  for (i = 0; i < 5; i++) {
  #      a = i;
  #      print(mystring);   /* Mystring is a char/string pointer*/
  #  }

  movw  $0,  %cx	# Bruker CX som i
loop1b:  
  cmpw  $5, %cx
  jge  loop1bdone	# Jump if greater than or equal
  movw  %cx, %ax	# Bruker AX som a
  
  movl  $mystring,%esi	# teststring for debug
  call  print		# kall utskriftsrutine
  
  incw  %cx
  jmp  loop1b
loop1bdone:  

  
  # ---------------------
  #  a = 0;
  #  do {
  #     a = a + 1; 
  #  } while (a < 10);

  movw  $0, %ax
loop2:
  incw  %ax
  cmpw  $10, %ax         
  jl  loop2	



  # -------------------
  # Lagre verdi før funksjonskall

  pushw  %ax		# Jeg har noe i ax jeg ikke vil ha overskrevet
  movw  $mystring, %si
  call  print
  pop  %ax
  
  
  # say hello to user
  movl  $hellostring,%esi
  call  print
  
forever:
  jmp  forever

# routine to print a zero terminated string pointed to by esi
# Overwrites:   AX, DS, BX
print:
  movw  $BOOT_SEGMENT,%ax
  movw  %ax,%ds			# Obs denne er korrigert
print_loop:
  lodsb
  cmpb  $0,%al
  je  print_done		# Brukte jz i løsningsforslag
  movb  $14,%ah
  movl  $0x0002,%ebx
  int  $0x10
  jmp  print_loop
print_done:
  retw

# messages

mystring:  
  .asciz  "test.\n\r"
hellostring:  
  .asciz  "Hi there.\n\r"

