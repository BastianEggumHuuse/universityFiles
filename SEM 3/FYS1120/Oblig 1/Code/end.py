# Imports
import numpy as np
import matplotlib.pyplot as plt

# Constants :
e_0 = 8.854 * 10**(-12)
pi = np.pi

# Function that takes in a position r = [x_r,y_r,_z_r], a list of charges Q = [q1, q2, ...], and a list of corresponding positions R = [[x1,y1,z1], [x2,y2,z2], ...]
# The function then returns the electric potential in this point.
def epotlist(r,Q,R,mod = 1):
    V=0

    for i in range(len(R)):
        Ri = r - R[i]
        qi = Q[i] * mod
        Rinorm = np.linalg.norm(Ri)

        V = V + qi/(4*pi*e_0*Rinorm)

    return V

# Defining parameters
a = 1 # Length of half a side
N_R = 100 # Number of charges
Q_Total = 1 # Total charge (of entire square)

# Defining a list of points
R_x = np.concatenate((np.linspace(-a,a,N_R),-a * np.ones(N_R),np.linspace(-a,a,N_R),a * np.ones(N_R)))
R_y = np.concatenate((a * np.ones(N_R),np.linspace(-a ,a ,N_R),-a * np.ones(N_R),np.linspace(-a ,a  ,N_R)))
R_z = np.zeros(N_R * 4)
R = np.array(list(zip(*[R_x,R_y,R_z])))

# Defining a list of charges 
Q = np.ones(len(R)) * (Q_Total/(len(R)))

#Defining our observation point
r = np.array([-a,0,2*a]) # On the middle of the West line of the negative square

V = 2 * epotlist(r,Q,R)
C = 2*Q_Total/V

V_a = abs((Q_Total/(2*pi*e_0*a)) * np.arcsinh(((2)**(1/2) - (6)**(1/2))/(5)**(1/2)))
C_a = 2*Q_Total/V_a

print(f"\nDifference in potential between the two squares : {V}")
print(f"Capacitance between the two squares : {C}\n")

print(f"Analytical difference in potential between the two squares : {V_a}")
print(f"Analytical capacitance between the two squares : {C_a}\n")

# Computing Difference in V for an entire line
N = 100 # Number of reference points
r_y = np.linspace(-a,a,N)
mod = 1/(len(r_y)*4)
V = []

for i in range(len(r_y)):

    V.append(epotlist(np.array([-a,r_y[i],2*a]),Q,R,mod))
for i in range(len(r_y)):

    V.append(epotlist(np.array([r_y[i],a,2*a]),Q,R,mod))
for i in range(len(r_y)):

    V.append(epotlist(np.array([a,-r_y[i],2*a]),Q,R,mod))
for i in range(len(r_y)):

    V.append(epotlist(np.array([-r_y[i],-a,2*a]),Q,R,mod))

# Plotting the potential
#plt.plot(r_y,V)
plt.plot(np.linspace(0,1,len(V)),V)
plt.title("Elektrisk potensial langs den vestlige linjen i\ndet negativt ladde kvadratet")
plt.xlabel("y [m]")
plt.ylabel("dV [V]")
plt.show()

print(f"Integrated difference in potential {sum(V) * 2}")