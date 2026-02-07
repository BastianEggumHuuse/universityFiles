import numpy as np
import scipy.stats as stats
import math
import matplotlib.pyplot as plt

p = [[],[]]
p_2 = []

p_line = [[],[]]
p_sum = 0
p_len = 0

def periodCalc(l):
    return((float(l)/5)**1)

d_m_0 = 2145
d_m = d_m_0
with open("Perioder.txt") as File:
    for l in File:
        if("-" in l):

            p_line[0].append(p_sum/p_len)
            p_line[1].append(d_m)
            p_sum = p_len = 0

            d_m = d_m_0 + float(l.replace("-",""))
            #plt.axhline(d_m)
        else:
            p[0].append(periodCalc(l))
            p[1].append(d_m)

            p_sum += periodCalc(l)
            p_len += 1

            if(d_m == d_m_0):
                p_2.append((float(l)/5))
        
plt.title("Periodetider mot masse")
plt.xlabel("Masse [g]")
plt.ylabel("tid [s]")

plt.plot(p[1],p[0],".")#,color="green")
plt.plot(p_line[1],p_line[0])#,color="red")
plt.show()

# Estimerer forventning (Gjennomsnitt)

N = len(p_2)
t_line = sum(p_2)/N

# Estimerer standardavvik

s_t = 0
for t in p_2:
    s_t += (t - t_line)**2
s_t *= 1/(N-1)
s_t = (s_t)**(1/2)

print(f"t_line : {t_line:.5f}, s_t : {s_t:.5f}, SEM(t) : {s_t/np.sqrt(N)}")
