# Imports
import numpy as np
import matplotlib.pyplot as plt

# Constants :
e_0 = 8.854 * 10**(-12)
pi = np.pi

# Function that takes in a position r = [x_r,y_r,_z_r], a list of charges Q = [q1, q2, ...], and a list of corresponding positions R = [[x1,y1,z1], [x2,y2,z2], ...]
# The function then returns the electric potential in this point.
def epotlist(r,Q,R):
    V=0

    for i in range(len(R)):
        Ri = r - R[i]
        qi = Q[i]
        Rinorm = np.linalg.norm(Ri)

        V = V + qi/(4*pi*e_0*Rinorm)

    return V

# Function that does the same as the one above, but for the electric field.
def elist(r,Q,R):

    E = np.array([0,0,0])

    for i in range(len(R)):

        Ri = r - R[i]
        qi = Q[i]
        Rinorm = np.linalg.norm(Ri)

        E = E + qi/(4*pi*e_0*Rinorm**3) * Ri

    return E

# Defining parameters
a = 1 # Length of half a side
N_R = 100 # Number of charges
N = 30 # Number of reference points
Q_Total = 10 # Total charge (of entire square)

# Defining a list of points
R_x = np.concatenate((np.linspace(-a,a,N_R),-a * np.ones(N_R),np.linspace(-a,a,N_R),a * np.ones(N_R)))
R_y = np.concatenate((a * np.ones(N_R),np.linspace(-a ,a ,N_R),-a * np.ones(N_R),np.linspace(-a ,a  ,N_R)))
R_z = np.zeros(N_R * 4)
R = np.array(list(zip(*[R_x,R_y,R_z])))

# Defining a list of charges 
Q = np.ones(len(R)) * (Q_Total/(len(R)))

# Defining the length of the space we wish to survey
Lx = 3
Ly = 3

# Defining lists of x and y positions, and creating a grid from these
x = np.linspace(-Lx,Lx,N)
y = np.linspace(-Ly,Ly,N)
rx,ry = np.meshgrid(x,y)

# Defining an empty array of potentials
V = np.zeros((N,N),float)

# Looping through all positions in r, and getting the potential in that point.
# This is added to a list
for i in range(len(rx.flat)):
    r = np.array([rx.flat[i],ry.flat[i],0.0])
    V.flat[i] = epotlist(r,Q,R)

# plotting the potential.
plt.contourf(rx,ry,V)
plt.title("Potensial langs x- og y-aksen")
plt.xlabel("x [m]")
plt.ylabel("y [m]")
plt.show()

# Finding the electric field in all positions
Ex = np.zeros((N,N),float)
Ey = np.zeros((N,N),float)
for i in range(len(rx.flat)):
    r = np.array([rx.flat[i],ry.flat[i],0.0])
    Ev = elist(r,Q,R)
    Ex.flat[i] = Ev[0]
    Ey.flat[i] = Ev[1]

# Plotting electric field
plt.quiver(rx,ry,Ex,Ey)
plt.title("Elektrisk felt langs x- og y-aksen")
plt.xlabel("x [m]")
plt.ylabel("y [m]")
plt.show()

# Finding the potential along the z axis
Z = np.linspace(-3,3,100)
V_z = []
for i in range(len(Z)):
    r = np.array([0,0,Z[i]])
    V_z.append(epotlist(r,Q,R))

# Defining the analytical function
V_z_analytical = lambda z : (Q_Total/(4*pi*e_0*a)) * np.arcsinh(a/((z**2 + a**2)**(1/2)))

# Plotting analytical and numerical Potentials
plt.plot(Z,V_z,color = "royalblue")
plt.plot(Z,V_z_analytical(Z),"Red")
plt.title("Elektrisk potensial langs z \nNumerisk (Blått) og Analytisk (Rødt)")
plt.xlabel("z [m]")
plt.ylabel("V [V]")
plt.show()