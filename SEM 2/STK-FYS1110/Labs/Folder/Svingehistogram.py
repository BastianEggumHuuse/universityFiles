import numpy as np
from scipy.stats import norm
import matplotlib.pyplot as plt

# Collecting data from file
data = []
with open("Svingetid.txt") as file:
    for l in file:
        data.append(float(l.replace(",","")))

# Fit a normal distribution to
# the data:
# mean and standard deviation
mu, std = norm.fit(data)

fig, ax1 = plt.subplots()


# Plot the histogram and norm
ax1.hist(data,edgecolor = "k",color = "orange")
ax2 = ax1.twinx()

# Creating norm pdf
xmin, xmax = plt.xlim()
x = np.linspace(xmin, xmax, 100)
p = norm.pdf(x, mu, std)

ax2.plot(x, p, 'k', linewidth=2)

# Getting the mean
t_line = 0
for d in data:
    t_line += d
t_line = t_line/len(data)

# Estimating s_t
s_t = 0
for d in data:
    s_t += (d - t_line)**2
s_t *= (1/len(data))
s_t = s_t**(1/2)

# Getting SEM
SEM_t = s_t/((len(data))**(1/2))

print(f"t_line = {t_line:.3f}\ns_t = {s_t:.3f}\nSEM_t = {SEM_t}")

plt.title("Svingetider")
plt.xlabel("Tid [s]")
plt.ylabel("Antall målinger")

#plt.axvline(1.59,lw = 3,color = "red")

plt.show()