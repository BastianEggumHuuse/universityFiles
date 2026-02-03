import matplotlib.pyplot as plt
import numpy as np

# Defining theta dot dot
def theta_ddot(theta,theta_dot,gamma,beta,omega):
    if(theta_dot == 0):
        theta_dot_hat = 0
    else:
        theta_dot_hat = (theta_dot/abs(theta_dot))

    return -1 * (2 * gamma * theta_dot + beta * theta_dot_hat * theta + omega**2 * theta)

# Setting oscillation parameters
theta_0 = np.pi/4
omega_0 = 1
gamma   = 0.1
beta    = 1.0

# Setting time parameters
num_periods = 3
t_max       = ((2*np.pi)/omega_0) * num_periods # Since the oscillation is damped, this is not exactly 3 periods
dt          = 10e-4

# Setting initial parameters
T             = [0]
theta         = [theta_0]
theta_dot     = [0]
theta_dot_dot = [theta_ddot(theta[-1],theta_dot[-1],gamma,beta,omega_0)]

# Performing euler cromer integration
t = 0
while t < t_max:

    # Performing one integration step
    theta_dot_dot.append(theta_ddot(theta[-1],theta_dot[-1],gamma,beta,omega_0))
    theta_dot.append(theta_dot[-1] + theta_dot_dot[-1] * dt)
    theta.append(theta[-1] + theta_dot[-1] * dt)

    t += dt
    T.append(t)

# Plotting
plt.plot(T,theta)
plt.xlabel("time")
plt.ylabel("displacement")
plt.grid()
plt.show()

# Computation
omega_d = (omega_0**2 - gamma**2)
print("Omega_d: ",omega_d)

# Plotting phase space
plt.plot(theta,theta_dot,color = "teal")
plt.xlabel("displacement")
plt.ylabel("velocity")
plt.axis("equal")
plt.grid()
plt.show()