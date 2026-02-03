import numpy as np
import matplotlib.pyplot as plt
import pandas as pd

# Leser fra fil
doed=pd.read_csv("https://www.uio.no/studier/emner/matnat/math/STK1100/data/doedelighet.txt",sep="\t")
alder=doed["alder"].values
menn=doed["menn"].values

plt.semilogy(alder,menn)
plt.xlabel("Alder")
plt.ylabel("Dødssannsynlighet per 1000")
plt.show()

# Sannsynligheten i datasettet er gitt per 1000 individer.
# Finner sannsynligheten for en person
qx=menn/1000

Sx=np.cumprod(1-qx)

# Finner Kumulativ Fordeling.
Fx=1-Sx

#Plotter kumulativ fordeling
plt.step(alder,Fx,where="post")
plt.xlabel("Alder")
plt.ylabel("F(x)")
plt.show()

# Finner alle punktsannsynlighetene
tmp=np.zeros(107)
tmp[1:107] = Fx[0:106]
px=Fx-tmp

# Plotter punktsannsynlighetene
width=1
plt.bar(alder,px,width,edgecolor="black")
plt.xlabel("Alder")
plt.ylabel("p(x)") 
plt.show()

# Finner forventning (E(X) = sum(x*p(x)))
x=alder
np.sum(x*px)

# Finner forventet resterende alder ved gitt alder a
a = 30
x = alder[a:107]
Fx = np.cumsum(px)
np.sum((alder[a:107]-a)*px[a:107])/(1-Fx[a-1])