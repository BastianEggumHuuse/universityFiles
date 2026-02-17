import numpy as np
import matplotlib.pyplot as plt
        
# Couldn't be bothered to do this twice
class thetas:

    def __init__(self,gamma,omega_0,kappa,F_0,m,L,omega,g):
        self.gamma = gamma
        self.omega_0 = omega_0
        self.kappa   = kappa
        self.F_0 = F_0
        self.m = m
        self.L = L
        self.omega = omega
        self.g = g

    def set_initial_conditions(self,t0,td0,tdd0,t = 0):
        self.theta         = [t0]
        self.theta_dot     = [td0]
        self.theta_dot_dot = [tdd0]
        self.t = [t]

        self.E_pot = []
        self.E_kin = []
        self.E_tot = []
        self.appendEnergy()

    def appendEnergy(self):
        self.E_pot.append(self.m * self.g * self.L * (1 - np.cos(self.theta[-1])))
        self.E_kin.append(0.5 * self.m * (self.L * self.theta_dot[-1])**2)
        self.E_tot.append(self.E_pot[-1] + self.E_kin[-1])

    def acceleration(self,theta_i_dot,theta_i,theta_k,t):
        return -2*self.gamma*theta_i_dot - self.omega_0**2 * theta_i - self.kappa * (theta_i - theta_k) + (self.F_0/(self.m*self.L)) * np.cos(self.omega * t)

    def integrate(self,tdd,dt ):

        self.theta.append(self.theta[-1] + self.theta_dot[-1] * dt + self.theta_dot_dot[-1] * dt**2)
        self.theta_dot_dot.append(tdd)
        self.theta_dot.append(self.theta_dot[-1] + 0.5 * (self.theta_dot_dot[-1] + self.theta_dot_dot[-2]) * dt)

        self.t.append(self.t[-1] + dt)
    
        self.appendEnergy()
    
    def toArrays(self):

        self.theta = np.array(self.theta)
        self.theta_dot = np.array(self.theta_dot)
        self.theta_dot_dot = np.array(self.theta_dot_dot)
        self.t = np.array(self.t)
        self.E_pot = np.array(self.E_pot)
        self.E_kin = np.array(self.E_kin)
        self.E_tot = np.array(self.E_tot)

# Parameters
gamma = 0.15
kappa = 0.8
F_0   = 15
m     = 30
g     = 9.81
L     = 2.5
T     = 15

# Frequencies
omega_0 = (g/L)**0.5
print(f"omega_0 = {omega_0}")
omega   = 1

# Setting up acceleration functions
theta_1 = thetas(gamma,omega_0,kappa,F_0,m,L,omega,g)
theta_2 = thetas(gamma,omega_0,kappa,0,m,L,omega,g) # Simply set F_0 = 0 to remove the driving :)

# Initial conditions
t = 0
dt = 1e-5
theta_1_0 = 0.3
theta_1_dot_0 = 0
theta_2_0 = 0
theta_2_dot_0 = 0
theta_1_dot_dot_0 = theta_1.acceleration(theta_1_dot_0,theta_1_0,theta_2_0,t)
theta_2_dot_dot_0 = theta_2.acceleration(theta_2_dot_0,theta_2_0,theta_1_0,t)

# Setting up variables
theta_1.set_initial_conditions(theta_1_0,theta_1_dot_0,theta_1_dot_dot_0)
theta_2.set_initial_conditions(theta_2_0,theta_2_dot_0,theta_2_dot_dot_0)

# Running integration loop
while t < T:

    theta_1_dd = theta_1.acceleration(theta_1.theta_dot[-1],theta_1.theta[-1],theta_2.theta[-1],t)
    theta_2_dd = theta_2.acceleration(theta_2.theta_dot[-1],theta_2.theta[-1],theta_1.theta[-1],t)

    theta_1.integrate(theta_1_dd,dt)
    theta_2.integrate(theta_2_dd,dt)
    t += dt

theta_1.toArrays()
theta_2.toArrays()

# Plotting displacements
plt.plot(theta_1.t,theta_1.theta,color = "firebrick",label = r"$\theta_1$")
plt.plot(theta_2.t,theta_2.theta,color = "darkviolet",label = r"$\theta_2$")
plt.grid()
plt.legend()
plt.xlabel("time [s]")
plt.ylabel("displacement [rad]")
plt.title(f"coupled oscillators with driving\nfrequence $\\omega = {omega}$")
plt.show()

# Plotting energies
plt.plot(theta_1.t,theta_1.E_tot,color = "tomato",label = r"$E_1$")
plt.plot(theta_2.t,theta_2.E_tot,color = "violet",label = r"$E_2$")
plt.grid()
plt.legend()
plt.xlabel("time [s]")
plt.ylabel("energy [j]")
plt.title(f"Energy of coupled oscillators\nwith driving frequence $\\omega = {omega}$")
plt.show()

plt.plot(theta_1.t,theta_1.E_tot + theta_2.E_tot,color = "green",label = r"$E_1$")
plt.grid()
plt.show()