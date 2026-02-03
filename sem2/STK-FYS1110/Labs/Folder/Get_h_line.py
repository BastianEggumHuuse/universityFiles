import numpy as np
from scipy.stats import norm
import matplotlib.pyplot as plt

# Maaler diverse stats for prosjektil-Lab

g = 9.81
theta = np.pi/6
h_0 = 0.214

# Collecting data from file
data = []
filename = "Heights.txt"
# Data is in cm!!!
with open(filename) as file:
    file.readline()

    for l in file:
        data.append(float(l.replace(",",""))/100 - h_0)

N = len(data)
h_line = sum(data)/N

print(h_line)
v_0 = np.sqrt(2*g*h_line)
t = (np.sin(theta)*v_0 + np.sqrt((np.sin(theta)**2)*v_0**2 + 2*g*h_0))/g
x = v_0*np.cos(theta)*t

print(f"v_0 = {v_0}")
print(f"t = {t}")
print(f"x = {x}")