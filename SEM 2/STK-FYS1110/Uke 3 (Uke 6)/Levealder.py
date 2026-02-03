# Program stjålet direkte fra jupyter notebook lol

import numpy as np
import matplotlib.pyplot as plt
import pandas as pd

doed=pd.read_csv("Levealder.txt",sep="\t")
alder=doed["alder"].values
menn=doed["menn"].values
kvinner=doed["kvinner"].values

plt.semilogy(alder,menn) # Plots with a logarithmic y-axis :)
plt.semilogy(alder,kvinner)
plt.xlabel("Alder")
plt.ylabel("Dødssannsynlighet per 1000")
plt.show()

qx=menn/1000
qy=kvinner/1000
Sx=np.cumprod(1-qx)
Sy=np.cumprod(1-qy)
Fx=1-Sx
Gy=1-Sy
plt.step(alder,Fx,where="post")
plt.step(alder,Gy,where="post")
plt.xlabel("Alder")
plt.ylabel("F(x)")
plt.show()

Nums = [60,70,80,90]
for n in Nums:
    print(f"Sannsynligheten for å bli minst {n} år:")
    print(f"Menn : {1-Fx[n]:.2f}, Kvinner : {1-Gy[n]:.2f}")

# Unødvendig for oppgaven
'''
tmp=np.zeros(107)
tmp[1:107] = Fx[0:106]
px=Fx-tmp

width=1
plt.bar(alder,px,width,edgecolor="black")
plt.xlabel("Alder")
plt.ylabel("p(x)") 
plt.show()
'''


