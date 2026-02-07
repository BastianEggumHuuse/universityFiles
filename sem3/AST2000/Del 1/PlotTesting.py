import numpy as np
import matplotlib.pyplot as plt

Points = np.array([
    np.array([0,0,0]),
    np.array([1,0,0]),
    np.array([0,1,0]),
    np.array([0,0,1]),
    np.array([1,0,1]),
    np.array([1,1,0])
])

fig, ax = plt.subplots(subplot_kw={"projection": "3d"})

ax.scatter(Points[:,0],Points[:,1],Points[:,2])
plt.show()