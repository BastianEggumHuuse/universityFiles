import numpy as np
import matplotlib.pyplot as plt

eps_0 = 8.854e-12

def field(x,y):

    r = np.array([x,y])
    r_hat = r / np.linalg.norm(r)
    E     = 0.5/(4*np.pi*eps_0*(np.linalg.norm(r)**2))
    E_vec = E * r_hat

    return E_vec[0],E_vec[1]

R_x = np.linspace(-5,5,10)
R_y = np.linspace(-5,5,10)
r_x,r_y = np.meshgrid(R_x,R_y,indexing="ij")

E_x = np.zeros_like(r_x)
E_y = np.zeros_like(r_y)
for i in range(len(r_x)):
    for j in range(len(r_y)):

        E_x[i][j],E_y[i][j] = field(r_x[i][j],r_y[i][j])


for l in r_x:
    print(len(l))

#plt.quiver(r_array[:,0],r_array[:,1],field_array[:,0],field_array[:,1])
plt.streamplot(r_x.T,r_y.T,E_x.T,E_y.T,density= 5,color = "limegreen")
plt.show()