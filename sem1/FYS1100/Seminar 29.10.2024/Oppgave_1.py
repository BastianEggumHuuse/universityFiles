import numpy       as  np
import matplotlib.pyplot  as  plt

class ThetaClass:

    def __init__(self,l,theta,v):

        self.l = l
        self.theta = theta
        self.v = v
        self.g = 9.81

    def get_delta_theta(self,theta):
        return(-(self.g/self.l) *np.sin(theta))

    def __call__(self,t):

        T = np.linspace(0,t,10000)
        Theta = np.zeros(len(T))
        V = np.zeros(len(T))
        
        Theta[0] = self.theta
        V[0] = self.v

        for i in range(1, len(T)):
            dt = T[i] - T[i-1]

            a = self.get_delta_theta(Theta[i-1])
            V[i] = V[i-1] + a*dt
            Theta[i] = Theta[i-1] + V[i] * dt

        return(T,Theta)
    
class TaylorThetaClass:

    def __init__(self,l,theta,v):

        self.l = l
        self.theta = theta
        self.v = v
        self.g = 9.81

    def get_value(self,t):
        return self.theta * np.cos(((self.g/self.l)**(1/2)) * t)

    def __call__(self,t):

        T = np.linspace(0,t,10000)
        Theta = self.get_value(T)

        return(T,Theta)
    
angles = [np.pi/12, np.pi/6, np.pi/4, np.pi/2]
for i in range(len(angles)):

    theta = ThetaClass(2,angles[i],0)
    theta_values = theta(30)

    taylor_theta = TaylorThetaClass(2,angles[i],0)
    taylor_theta_values = taylor_theta(30)

    plt.subplot(4,1,i+1)
    plt.plot(theta_values[0],theta_values[1])
    plt.plot(taylor_theta_values[0],taylor_theta_values[1])
    plt.ylabel(f"{angles[i]:.2f}")


plt.show()