import math as mt

C = 9
k = 0.2
B = 50000

def Population(t):
    return B / (1 + (C*(mt.e)**(-k * t)))

n = 12
d_t = 48/(n+1)
T = []
N = []

# Her har vi n+2 som øvre grense, siden vi ønsker å ha med elementet n+1
for i in range(0,n+2):
    T.append(d_t * i)
    N.append(Population(T[-1]))

# a)
TN = [T,N]

for i in range(0,len(T)):
    print(f"Ved tiden t ={TN[0][i]:3.0f}, har vi N ={TN[1][i]:6.0f}")

print("\n ------------------------ \n")

# b)
TN2 = []
for i in range(0,len(T)):
    TN2.append([TN[0][i],TN[1][i]])

for tn in TN2:

    print(f"Ved tiden t ={tn[0]:3.0f}, har vi N ={tn[1]:6.0f}")

r'''
python population_table2.py

Ved tiden t =  0, har vi N =  5000
Ved tiden t =  4, har vi N =  9433
Ved tiden t =  7, har vi N = 16366
Ved tiden t = 11, har vi N = 25227
Ved tiden t = 15, har vi N = 34031
Ved tiden t = 18, har vi N = 40842
Ved tiden t = 22, har vi N = 45161
Ved tiden t = 26, har vi N = 47565
Ved tiden t = 30, har vi N = 48806
Ved tiden t = 33, har vi N = 49422
Ved tiden t = 37, har vi N = 49722
Ved tiden t = 41, har vi N = 49867
Ved tiden t = 44, har vi N = 49936
Ved tiden t = 48, har vi N = 49970

 ------------------------

Ved tiden t =  0, har vi N =  5000
Ved tiden t =  4, har vi N =  9433
Ved tiden t =  7, har vi N = 16366
Ved tiden t = 11, har vi N = 25227
Ved tiden t = 15, har vi N = 34031
Ved tiden t = 18, har vi N = 40842
Ved tiden t = 22, har vi N = 45161
Ved tiden t = 26, har vi N = 47565
Ved tiden t = 30, har vi N = 48806
Ved tiden t = 33, har vi N = 49422
Ved tiden t = 37, har vi N = 49722
Ved tiden t = 41, har vi N = 49867
Ved tiden t = 44, har vi N = 49936
Ved tiden t = 48, har vi N = 49970
'''