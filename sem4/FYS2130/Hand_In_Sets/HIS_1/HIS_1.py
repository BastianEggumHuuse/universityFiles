import matplotlib.pyplot as plt
import numpy as np

omega = np.pi * 2

# First variant
def x_1(t):
    return -2 * np.sin(omega * t)

T = np.linspace(0,((2*np.pi)/omega),100)

X1 = x_1(T)

plt.plot(T,X1)
plt.grid()
plt.show()

# Second variant
