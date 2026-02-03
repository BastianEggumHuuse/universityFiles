import math as mt

C = 9
k = 0.2
B = 50000

def Population(t):
    return B / (1 + (C*(mt.e)**(-k * t)))

n = 12
d_t = 48/(n)
T = []
N = []

# Her har vi n som øvre grense, siden vi ønsker å ha med elementet n
for i in range(0,n+1):
    T.append(d_t * i)
    N.append(Population(T[-1]))

for i in range(0,len(T)):
    print(f"Ved tiden t ={T[i]:5.1f}, har vi N ={N[i]:8.1f}")

r'''
python population_table.py

Ved tiden t =  0.0, har vi N =  5000.0
Ved tiden t =  4.0, har vi N =  9912.8
Ved tiden t =  8.0, har vi N = 17748.9
Ved tiden t = 12.0, har vi N = 27526.0
Ved tiden t = 16.0, har vi N = 36580.2
Ved tiden t = 20.0, har vi N = 42924.3
Ved tiden t = 24.0, har vi N = 46552.0
Ved tiden t = 28.0, har vi N = 48389.6
Ved tiden t = 32.0, har vi N = 49263.3
Ved tiden t = 36.0, har vi N = 49666.3
Ved tiden t = 40.0, har vi N = 49849.5
Ved tiden t = 44.0, har vi N = 49932.3
Ved tiden t = 48.0, har vi N = 49969.5
'''