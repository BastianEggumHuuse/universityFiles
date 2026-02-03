import  numpy              as  np
import  matplotlib.pyplot  as  plt  # type: ignore

# --------------------- [ To Run ] ------------------------
# cd "C:\Users\bastian\Desktop\SEM 1\IN1900\Uke 40 (uke 7)"
# python "Forelesning 03.10.2024.py"
# --------------------- [ ------ ] ------------------------

L = [1,2,3,4,1,2]
S = "Min Fil.txt"

print(S.split)                # prints ["Min", "Fil.txt"]
print(S.index("F"))           # prints 4

# Strings are lists of characters. They work the exact same.
print(S[1:])                  # List slicing
print(S[1:-1])                # List slicing (final char not included)

# List slicing syntax is crazy bro i'm going insane

print(S[S.index("Fil")])      # List slicing again, but we begin at "Fil"s index (Insane)
print(S[1::2])                # List slicing but we move in steps of 2

# Blanks 
S_2 = "  Yahoo   " 
print(S_2.strip())            # Removes the spaces
print(S_2.lstrip())           # Removes spaces on the left
print(S_2.rstrip())           # Removes spaces on the right

# Lists
S_List = ["Word1", "Word2", "Word3"]
S_3 = "".join(S_List)         # Connecting words from lists
S_3 = " ".join(S_List)        # Connecting words from lists with a separator (here whitespace)
S_3 = "--".join(S_List)       # Connecting words from lists with a separator (here --)

# Replace
print(S_3.replace("--", " ")) # Replacing all "--"s with " "s
print(S_3.replace(S_3[:S_3.index("Word2")],"WordOne ")) # List slicing bro.
# This last one replaces all characters before "Word2" with "WordOne"

# Control
print("Yuh \r\n Yuh")         # Professor said we have to use \r\n, but \n works just fine for me...
print(S_3.lower())            # All Lowercase
print(S_3.upper())            # All Uppercase

# Checks
print(S_3.isdigit())          # False
print("123".isdigit())        # True
print(S.startswith("Min"))    # True
print(S.endswith("txt"))      # True
print("a".isalpha())          # True (In the alphabet)
print(".".isalpha())          # False

# A fun list thing (That i couldn't make work......)
Words = [1,5,2]
a = Words.sort(key=lambda x : int(x / 5))
print(a)