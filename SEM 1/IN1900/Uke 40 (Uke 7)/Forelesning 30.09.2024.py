import  numpy              as  np
import  matplotlib.pyplot  as  plt  # type: ignore
import  math               as  mt
import  random             as  rd

# --------------------- [ To Run ] ------------------------
# cd "C:\Users\bastian\Desktop\SEM 1\IN1900\Uke 40 (Uke 7)"
# python "Forelesning 30.09.2024.py"
# --------------------- [ ------ ] ------------------------



# Dictionaries!!!!!

# Definition
Numbers = {
    1 : "One", 
    2 : "Two",
    3 : "Three"
}

# Adding
Numbers[4] = "Four"

# Doesn't have to be numbers lol

Numbers["Five"] = 5 #Can be a string...

Numbers[(1,2)] = "One, Two" #Can be a tuple...

# It does have to be immutable:

'''
Numbers[[1,2,3]] doesnt work, since lists are mutable.
'''

# Every key-value pair has to be unique:

'''
Numbers2 = {1 : "One", 1 : "Two"} doesnt work, the keys match (The values can however match).
'''

#Adding an entire dictionary

Numbers_2 = {6 : "Six", 7 : "Seven"}
Numbers.update(Numbers_2) # If any keys are shared between the two, they are overwritten.

#Deleting elements

del Numbers[(1,2)]

# Checking for keys

("Five" in Numbers) # = true

try:
    Numbers["Five"]
except:
    #Seems the key doesn't exist..
    pass

(Numbers.get("Five") != None) # = true

# Iterating through Dictionaries

for k,v in Numbers.items():
    print(f"The key of {k} gives you the value of {v}")

for k in sorted(Numbers_2): #Must be numbers
    print(k)

# Ok imagine that you're opening a file:

Dict_3 = {}
with open("blood_test.txt") as file:
    for l in file:
        w = l.split()
        Dict_3[w[0]] = w[1]
