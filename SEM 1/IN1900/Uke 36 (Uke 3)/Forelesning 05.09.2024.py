#Vi skal ha lambda-funksjoner!!!!

# "C:\Users\bastian\Desktop\SEM 1\IN1900\Uke 36 (Uke 3)""
# "Forelesning 05.09.2024.py"

def funksjon(f,x):
    return(f(x))

print(funksjon(lambda x: x**2,2)) 

def floatRange(EndExcluded = 1,StartIncluded = 0, Diff = 1):
    l = []
    i = StartIncluded
    while i < EndExcluded:
        l.append(StartIncluded + i)
        i += Diff
    return(l)
#print(floatRange(1,0,0.1))

#List comprehension

#lamba
l = [(lambda x,y: x/y)(i,10) for i in range(10)]
print(l)

import numpy