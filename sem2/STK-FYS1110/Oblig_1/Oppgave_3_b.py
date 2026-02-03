import numpy as np
import matplotlib.pyplot as plt
import pandas as pd

# Leser fra fil
doed=pd.read_csv("https://www.uio.no/studier/emner/matnat/math/STK1100/data/doedelighet.txt",sep="\t")
alder=doed["alder"].values
menn=doed["menn"].values
kvinner = doed["kvinner"].values

# Sannsynligheten i datasettet er gitt per 1000 individer.
# Finner sannsynligheten for en person
qx=menn/1000
qy=kvinner/1000

Sx=np.cumprod(1-qx)
Sy=np.cumprod(1-qy)

# Finner Kumulativ Fordeling.
Fx=1-Sx
Fy=1-Sy

# Finner alle punktsannsynlighetene
tmp=np.zeros(107)
tmp[1:107] = Fx[0:106]
px=Fx-tmp
tmp[1:107] = Fy[0:106]
py=Fy-tmp

# Finner forventning (E(X) = sum(x*p(x)))
x=alder
E_x = np.sum(x*px)
y=alder
E_y = np.sum(y*py)

# Finner forventet resterende alder ved gitt alder a
A = [25,50,85]

print(f"\nForventet levealder for menn ved foedsel er {E_x:.2f}\n")

for a in A:
    x = alder[a:107]
    Fx = np.cumsum(px)
    E_x_a = np.sum((alder[a:107]-a)*px[a:107])/(1-Fx[a-1])

    print(f"Forventet levealder for menn, gitt at man har levd {a} aar, er {(E_x_a):.2f}")

print()

print(f"\nForventet levealder for kvinner ved foedsel er {E_y:.2f}\n")

for a in A:
    y = alder[a:107]
    Fy = np.cumsum(py)
    E_y_a = np.sum((alder[a:107]-a)*py[a:107])/(1-Fy[a-1])

    print(f"Forventet levealder for kvinner, gitt at man har levd {a} aar, er {(E_y_a):.2f}")

print()