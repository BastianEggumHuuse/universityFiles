import numpy as np
import matplotlib.pyplot as plt

class Tokamak:

    def __init__(self,r,I,num_points):

        # Storing parameters
        self.r = r
        self.I = I
        self.num_points = num_points

        # Assigning constants
        self.mu_0 = 4*np.pi*10**(-7)

        # Constructing circuit
        self.construct()

    def construct(self):

        self.circuit_points = np.zeros((self.num_points,3))

        for i in range(self.num_points):

            theta_i = (2*np.pi) * (i/self.num_points)
            self.circuit_points[i] = self.r * np.array([np.cos(theta_i),np.sin(theta_i),0])

    def magnetic_field(self,R,i):

        R_circuit = 0.5 * (self.circuit_points[i] + self.circuit_points[i+1])
        dl = self.circuit_points[i+1] - self.circuit_points[i]
        
        R_observation = R - R_circuit
        R_observation_norm = np.linalg.norm(R_observation,keepdims=True,axis=1) 
        R_observation_hat = R_observation / R_observation_norm

        dB = (self.mu_0 / (4*np.pi)) * (np.cross((self.I * dl),R_observation_hat)/(R_observation_norm**2))

        return dB
    
    def total_magnetic_field(self,R,N = 0):

        B = np.zeros(R.shape)

        if(N == 0):
            N = self.num_points - 1

        for i in range(N):

            dB = self.magnetic_field(R,i)
            B += dB

        print(B)

        return B

"""
Defining the system
"""

# Initial conditions
r_0 = np.array([2,0,0])
v_0 = np.array([0,1,0])

# Particle Parameters
m = 1.0
q = 1.0

# System parameters
B_0 = 1.0

r_1_torus = 1.0
r_2_torus = 0.2
num_circles = 50
num_points = num_circles * 7
num_points_per_circle = int(num_points/num_circles)

# Creating system
torus_points = np.zeros((num_circles * num_points_per_circle,3))

for i in range(num_circles):

    theta_i = (2*np.pi) * (i/num_circles)
    r_vec_i = r_1_torus * np.array([np.cos(theta_i),np.sin(theta_i),0])
    r_hat_i = r_vec_i / np.linalg.norm(r_vec_i)

    for j in range(num_points_per_circle):

        phi_i = (2*np.pi) * (j/num_points_per_circle)
        r_vec_j = r_vec_i + r_2_torus * r_hat_i * np.cos(phi_i) + r_2_torus * np.array([0,0,np.sin(phi_i)])
        
        index = i * num_points_per_circle + j
        torus_points[index] = r_vec_j

# Simulation Parameters
dt = 0.01
total_time = 100
num_timesteps = int(total_time / dt)

# Defining Arrays
r = np.zeros((num_timesteps,3))
v = np.zeros((num_timesteps,3))
r[0] = r_0
v[0] = v_0

for i in range(1,num_timesteps):

    #B_i = B_theta(B_0,r[i-1])
    #F_B = np.cross(q*v[i-1],B_i)
    #a = F_B / m

    #v[i] = v[i-1] + a * dt
    #r[i] = r[i-1] + v[i] * dt
    pass


"""
Defining Torus
"""

toka = Tokamak(1,1,100)
field_points = np.zeros((len(torus_points[:num_points_per_circle]),3))
field_points = toka.total_magnetic_field(torus_points[:num_points_per_circle])
scaled_points = field_points * 10e4

ax = plt.axes(projection='3d')
ax.quiver(torus_points[:num_points_per_circle][:,0],torus_points[:num_points_per_circle][:,1],torus_points[:num_points_per_circle][:,2],scaled_points[:,0],scaled_points[:,1],scaled_points[:,2])
#ax.plot(toka.circuit_points[:,0][0],toka.circuit_points[:,1][0],toka.circuit_points[:,2][0],".",color = "red")

print(np.linalg.norm(np.array([ 0.30098664, -0.03139526,  0.        ])))
print(np.linalg.norm(np.array([ 0.30492929, -0.09406188,  0.        ])))

plt.axis("equal")
plt.show()