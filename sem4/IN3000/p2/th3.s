  .file  "th3.c"
  .local  init
  .comm  init,4,4
  .section  .rodata.cst4,"aM",@progbits,4
  .align 4
.LC0:
  .long  1233125376
  .align 4
.LC1:
  .long  897581056
  .text
  .p2align 2,,3
.globl my_rand
  .type  my_rand, @function
my_rand:
  subl  $8, %esp
  fnstcw  6(%esp)
  flds  .LC0
  movw  6(%esp), %ax
  movb  $12, %ah
  movw  %ax, 4(%esp)
  fmull  12(%esp)
  fldcw  4(%esp)
  fistpl  (%esp)
  fldcw  6(%esp)
  movl  (%esp), %edx
  leal  (%edx,%edx,4), %eax
  leal  (%edx,%eax,2), %eax
  leal  (%edx,%eax,4), %eax
  leal  (%edx,%eax,4), %eax
  leal  (%eax,%eax,8), %ecx
  leal  1(%ecx), %eax
  testl  %eax, %eax
  movl  %eax, %edx
  js  .L3
.L2:
  andl  $-1048576, %edx
  subl  %edx, %eax
  flds  .LC1
  pushl  %eax
  fimull  (%esp)
  addl  $12, %esp
  ret
  .p2align 2,,3
.L3:
  leal  1048576(%ecx), %edx
  jmp  .L2
  .size  my_rand, .-my_rand
  .p2align 2,,3
.globl addsum
  .type  addsum, @function
addsum:
  pushl  %ebx
  subl  $20, %esp
  movl  28(%esp), %ebx
  pushl  $mutex
  call  lock_acquire
  addl  $16, %esp
  pushl  %ebx
  fldl  ghit
  fiaddl  (%esp)
  addl  $4, %esp
  fstpl  ghit
  call  yield
  movl  $mutex, 16(%esp)
  addl  $8, %esp
  popl  %ebx
  jmp  lock_release
  .size  addsum, .-addsum
  .section  .rodata.str1.1,"aMS",@progbits,1
.LC4:
  .string  "pi=       "
  .section  .rodata.cst4
  .align 4
.LC5:
  .long  1148846080
  .text
  .p2align 2,,3
.globl print_res
  .type  print_res, @function
print_res:
  pushl  %esi
  pushl  %ebx
  subl  $24, %esp
  fldl  44(%esp)
  movl  36(%esp), %esi
  movl  40(%esp), %ebx
  fstpl  4(%esp)
  pushl  $.LC4
  pushl  %ebx
  pushl  %esi
  call  print_str
  fnstcw  30(%esp)
  fldl  16(%esp)
  movw  30(%esp), %ax
  movb  $12, %ah
  fmuls  .LC5
  movw  %ax, 28(%esp)
  addl  $3, %ebx
  movl  %ebx, 52(%esp)
  movl  %esi, 48(%esp)
  fldcw  28(%esp)
  fistpl  56(%esp)
  fldcw  30(%esp)
  addl  $36, %esp
  popl  %ebx
  popl  %esi
  jmp  print_int
  .size  print_res, .-print_res
  .p2align 2,,3
.globl is_hit
  .type  is_hit, @function
is_hit:
  fldl  4(%esp)
  fldl  12(%esp)
  fxch  %st(1)
  fmul  %st(0), %st
  fxch  %st(1)
#APP
  pushal ; call yield ; popal
#NO_APP
  fmul  %st(0), %st
  faddp  %st, %st(1)
  fld1
  fucompp
  fnstsw  %ax
  testb  $1, %ah
  jne  .L7
  movl  $1, %eax
  ret
  .p2align 2,,3
.L7:
  xorl  %eax, %eax
  ret
  .size  is_hit, .-is_hit
  .p2align 2,,3
.globl do_mod
  .type  do_mod, @function
do_mod:
  movl  4(%esp), %eax
  testl  %eax, %eax
  movl  %eax, %edx
  js  .L12
.L11:
  andl  $-16, %edx
  subl  %edx, %eax
  ret
  .p2align 2,,3
.L12:
  leal  15(%eax), %edx
  jmp  .L11
  .size  do_mod, .-do_mod
  .section  .rodata.str1.1
.LC13:
  .string  "i%10:     "
  .section  .rodata.cst8,"aM",@progbits,8
  .align 8
.LC10:
  .long  2061584302
  .long  1091817180
  .section  .rodata.cst4
  .align 4
.LC12:
  .long  1073741824
  .text
  .p2align 2,,3
.globl mcpi
  .type  mcpi, @function
mcpi:
  pushl  %ebp
  pushl  %edi
  pushl  %esi
  pushl  %ebx
  subl  $44, %esp
  movl  64(%esp), %eax
  movl  %eax, 36(%esp)
  xorl  %ebp, %ebp
  movl  68(%esp), %ecx
  movl  72(%esp), %eax
  fldl  .LC10
  xorl  %ebx, %ebx
  cmpl  36(%esp), %ebp
  movl  %ecx, 32(%esp)
  movl  %eax, 28(%esp)
  fstpl  16(%esp)
  movl  $-2130303779, %esi
  movl  $1088408733, %edi
  jl  .L20
.L22:
  movl  %ebp, 64(%esp)
  addl  $44, %esp
  popl  %ebx
  popl  %esi
  popl  %edi
  popl  %ebp
  jmp  addsum
  .p2align 2,,3
.L20:
  subl  $8, %esp
  pushl  28(%esp)
  pushl  28(%esp)
  call  my_rand
  popl  %edx
  popl  %ecx
  fstpl  24(%esp)
  pushl  %edi
  pushl  %esi
  call  my_rand
  addl  $16, %esp
  fstpl  8(%esp)
  movl  12(%esp), %edi
  movl  8(%esp), %esi
  pushl  %edi
  pushl  %esi
  pushl  28(%esp)
  pushl  28(%esp)
  call  is_hit
  addl  $16, %esp
  testl  %eax, %eax
  je  .L18
  incl  %ebp
.L18:
  subl  $16, %esp
  flds  .LC12
  fstl  8(%esp)
  fstpl  (%esp)
  call  is_hit
  movl  $10, %edx
  movl  %ebx, %eax
  movl  %edx, %ecx
  cltd
  idivl  %ecx
  addl  $16, %esp
  testl  %edx, %edx
  je  .L23
.L16:
  incl  %ebx
  cmpl  36(%esp), %ebx
  jl  .L20
  jmp  .L22
  .p2align 2,,3
.L23:
  pushl  %eax
  pushl  $.LC13
  pushl  36(%esp)
  pushl  44(%esp)
  call  print_str
  movl  $1717986919, %eax
  imull  %ebx
  movl  %edx, %ecx
  movl  %ebx, %eax
  cltd
  sarl  $2, %ecx
  addl  $12, %esp
  subl  %edx, %ecx
  pushl  %ecx
  movl  36(%esp), %eax
  addl  $6, %eax
  pushl  %eax
  pushl  44(%esp)
  call  print_int
  addl  $16, %esp
  jmp  .L16
  .size  mcpi, .-mcpi
  .section  .rodata.cst4
  .align 4
.LC15:
  .long  1082130432
  .align 4
.LC16:
  .long  1148846080
  .text
  .p2align 2,,3
.globl run_mcpi_threadN
  .type  run_mcpi_threadN, @function
run_mcpi_threadN:
  subl  $12, %esp
  movl  init, %eax
  testl  %eax, %eax
  je  .L28
.L30:
  pushl  %eax
  pushl  24(%esp)
  pushl  24(%esp)
  pushl  $250
  call  mcpi
  movl  $done_mutex, (%esp)
  call  lock_acquire
  movl  threads_done, %eax
  incl  %eax
  movl  $done_mutex, (%esp)
  movl  %eax, threads_done
  call  lock_release
  fldl  ghit
  fmuls  .LC15
  fdivs  .LC16
  addl  $28, %esp
  ret
  .p2align 2,,3
.L28:
  call  yield
  movl  init, %eax
  testl  %eax, %eax
  je  .L28
  jmp  .L30
  .size  run_mcpi_threadN, .-run_mcpi_threadN
  .section  .rodata.str1.1
.LC18:
  .string  "Thread  4-7 (MCPi)    : "
.LC19:
  .string  "running"
.LC23:
  .string  "final"
  .section  .rodata.cst4
  .align 4
.LC21:
  .long  1082130432
  .align 4
.LC22:
  .long  1148846080
  .text
  .p2align 2,,3
.globl mcpi_thread0
  .type  mcpi_thread0, @function
mcpi_thread0:
  subl  $32, %esp
  pushl  $.LC18
  pushl  $0
  pushl  $22
  call  print_str
  addl  $12, %esp
  pushl  $.LC19
  pushl  $25
  pushl  $22
  call  print_str
  movl  init, %eax
  addl  $16, %esp
  testl  %eax, %eax
  je  .L39
.L32:
  pushl  %ecx
  pushl  $25
  pushl  $22
  pushl  $250
  call  mcpi
  fldl  ghit
  popl  %eax
  fmuls  .LC21
  popl  %edx
  fdivs  .LC22
  fstpl  (%esp)
  pushl  $25
  pushl  $22
  call  print_res
  movl  $done_mutex, (%esp)
  call  lock_acquire
  movl  threads_done, %eax
  incl  %eax
  movl  %eax, threads_done
  movl  $done_mutex, (%esp)
  call  lock_release
  movl  threads_done, %eax
  addl  $16, %esp
  cmpl  $4, %eax
  je  .L38
  .p2align 2,,3
.L36:
  call  yield
  movl  threads_done, %eax
  cmpl  $4, %eax
  jne  .L36
.L38:
  pushl  %ecx
  pushl  $.LC23
  fldl  ghit
  pushl  $65
  fmuls  .LC21
  fdivs  .LC22
  pushl  $22
  fstpl  16(%esp)
  call  print_str
  popl  %eax
  popl  %edx
  fldl  8(%esp)
  fstpl  (%esp)
  pushl  $71
  pushl  $22
  call  print_res
  addl  $44, %esp
  jmp  exit
.L39:
  subl  $12, %esp
  pushl  $mutex
  call  lock_init
  movl  $done_mutex, (%esp)
  call  lock_init
  xorl  %eax, %eax
  xorl  %edx, %edx
  movl  %eax, ghit
  addl  $16, %esp
  movl  %edx, ghit+4
  movl  $0, threads_done
  movl  $1, init
  jmp  .L32
  .size  mcpi_thread0, .-mcpi_thread0
  .p2align 2,,3
.globl mcpi_thread1
  .type  mcpi_thread1, @function
mcpi_thread1:
  subl  $16, %esp
  pushl  $.LC19
  pushl  $35
  pushl  $22
  call  print_str
  popl  %ecx
  popl  %eax
  pushl  $35
  pushl  $22
  call  run_mcpi_threadN
  popl  %eax
  popl  %edx
  fstpl  (%esp)
  pushl  $35
  pushl  $22
  call  print_res
  addl  $28, %esp
  jmp  exit
  .size  mcpi_thread1, .-mcpi_thread1
  .p2align 2,,3
.globl mcpi_thread2
  .type  mcpi_thread2, @function
mcpi_thread2:
  subl  $16, %esp
  pushl  $.LC19
  pushl  $45
  pushl  $22
  call  print_str
  popl  %ecx
  popl  %eax
  pushl  $45
  pushl  $22
  call  run_mcpi_threadN
  popl  %eax
  popl  %edx
  fstpl  (%esp)
  pushl  $45
  pushl  $22
  call  print_res
  addl  $28, %esp
  jmp  exit
  .size  mcpi_thread2, .-mcpi_thread2
  .p2align 2,,3
.globl mcpi_thread3
  .type  mcpi_thread3, @function
mcpi_thread3:
  subl  $16, %esp
  pushl  $.LC19
  pushl  $55
  pushl  $22
  call  print_str
  popl  %ecx
  popl  %eax
  pushl  $55
  pushl  $22
  call  run_mcpi_threadN
  popl  %eax
  popl  %edx
  fstpl  (%esp)
  pushl  $55
  pushl  $22
  call  print_res
  addl  $28, %esp
  jmp  exit
  .size  mcpi_thread3, .-mcpi_thread3
  .comm  ghit,8,8
  .comm  mutex,8,4
  .comm  done_mutex,8,4
  .local  threads_done
  .comm  threads_done,4,4
  .section  .note.GNU-stack,"",@progbits
  .ident  "GCC: (GNU) 3.3.3 20040412 (Red Hat Linux 3.3.3-7)"
