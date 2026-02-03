import numpy as np
import matplotlib.pyplot as plt
import math as mt

# Some data:
x_hat = 133.36052176299642 # Out estimate

# Collecting data from file
data = []
filename = "Lengths.txt"
# Data is in cm!!!
with open(filename) as file:
    file.readline()

    for l in file:
        data.append(x_hat + float(l))


N = len(data)
x_line = sum(data)/N

# Estimating s_t
s_x = 0
for d in data:
    s_x += (d - x_line)**2
s_x *= (1/N)
s_x = s_x**(1/2)

## Getting SEM
sem_x = s_x/((N)**(1/2))

print(f"x_line = {x_line}, sem_x = {sem_x}")

plt.title("Histogram av mål av rekkevidder")
plt.xlabel("Rekkevidde [cm]")

plt.hist(data,color="orange",edgecolor = "k")
plt.axvline(x_hat,color="red",linewidth = 2)
plt.axvline(x_line,color="blue",linewidth = 2)
plt.show()