import numpy as np


s = int(input())
v0 = input().split()
ab = np.array([int(v0[0]), int(v0[1])])
ba = np.array([int(v0[1]), int(v0[0])])
v0 = input().split()
cd = np.array([int(v0[0]), int(v0[1])])
dc = np.array([int(v0[1]), int(v0[0])])

def InGrid(gridsize,vector):
    return(vector[0] >= 0 and vector[0] < gridsize and vector[1] >= 0 and vector[1] < gridsize)

def InList(a,v):
    for v0 in v:
        if np.array_equal(v0, v):
            return True
        
    return False

Vectors = []
units = [np.array([1,1]),np.array([-1,1]),np.array([1,-1]),np.array([-1,-1])]
for u in units:

    Vectors.append(ab * u)
    Vectors.append(ba * u)
    Vectors.append(dc * u)
    Vectors.append(cd * u)

#print (Vectors)

start = [0,0]#np.array()

AllOptions = [start]
CurrentOptions = [start]

# Ikke ferdig :(((

while len(CurrentOptions) != 0:
    NextOptions = []

    for o in CurrentOptions:
        for v in Vectors:
            NewPos = list(np.array(o) + v)
            
            if InGrid(s,NewPos):
                #b = InList(AllOptions,NewPos) == False
                b = NewPos not in AllOptions
                
                if b:
                    print(NewPos)
                    AllOptions.append(NewPos)
                    NextOptions.append(NewPos)
    
    CurrentOptions = NextOptions

print(AllOptions)
print(len(AllOptions))