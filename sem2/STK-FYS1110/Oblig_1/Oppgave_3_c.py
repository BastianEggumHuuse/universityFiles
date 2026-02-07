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
A = range(1,107,1)
plot_m = np.zeros(107)
plot_k = np.zeros(107)
plot_m[0] = E_x
plot_k[0] = E_y

for a in A:
    x = alder[a:107]
    y = alder[a:107]
    Fx = np.cumsum(px)
    Fy = np.cumsum(py)
    E_x_a = np.sum((alder[a:107]-a)*px[a:107])/(1-Fx[a-1])
    E_y_a = np.sum((alder[a:107]-a)*py[a:107])/(1-Fy[a-1])

    plot_m[a] = E_x_a
    plot_k[a] = E_y_a

width=1
edge = "black"
color_k = "orange"
color_m = "red"

fig = plt.figure()
ax = fig.add_subplot(111)    # The big subplot
ax1 = fig.add_subplot(211)
ax2 = fig.add_subplot(212)

ax.spines['top'].set_color('none')
ax.spines['bottom'].set_color('none')
ax.spines['left'].set_color('none')
ax.spines['right'].set_color('none')
ax.tick_params(labelcolor='w', top=False, bottom=False, left=False, right=False)

ax.set_xlabel('Alder')
ax.set_ylabel('Forventet Gjenstaaende Levealder')
ax.set_title("Forventet Gjenstaaende levealder for\n Kvinner (oransje) og Menn (roed)")

ax1.bar(alder,plot_k,width,color=color_k,edgecolor=edge)
ax1.bar(alder,plot_m,width,color=color_m,edgecolor=edge)

ax2.plot(alder,plot_k,color_k)
ax2.plot(alder,plot_m,color_m)
plt.show()
