import numpy as np
import matplotlib.pyplot as plt

# Stats
m_1        = 1
m_2        = 1
L_1        = 2
L_2        = 2
g          = 9.81

# Tid og intervaller
t_max      = 10
N          = 100000
dt         = (t_max/N)

# Arrays vi lagrer data i
T          = np.zeros(N)
theta_1    = np.zeros(N)
theta_2    = np.zeros(N)
tv_1       = np.zeros(N)
tv_2       = np.zeros(N)
ta_1       = np.zeros(N)
ta_2       = np.zeros(N)

# Initialbetingelser
theta_1_0  = np.pi/6#1/1000
theta_2_0  = np.pi/6#((2)**(1/2))/1000
tv_1_0     = 0
tv_2_0     = 0
ta_1_0     = 0
ta_2_0     = 0

# Legger initialbetingelsene inn in arrayene
theta_1[0] = theta_1_0
theta_2[0] = theta_2_0
tv_1[0]    = tv_1_0
tv_2[0]    = tv_2_0
ta_1[0]    = ta_1_0
ta_2[0]    = ta_2_0

# Funksjoner som finner M og a
def get_M(q_1,q_2):
    M_1 = (m_1 + m_2)*(L_1**2)
    M_2 = m_2*L_1*L_2*np.cos(q_2-q_1)
    M_3 = m_2*L_1*L_2*np.cos(q_2-q_1)
    M_4 = m_2*(L_2**2)
    M = np.matrix([[M_1,M_2],[M_3,M_4]])
    return M

def get_a(q_1,q_2,v_1,v_2):
    #a_1 = 2*L_1*L_2*v_1*v_2*np.sin(q_2-q_1)-(m_1+m_2)*g*L_1*(np.sin(q_1))+2*L_1*L_2*v_2*np.sin(q_2-q_1)*(v_2-v_1)
    #a_2 = 2*L_1*L_2*v_1*v_2*np.sin(q_1-q_2)-(m_2)*g*L_1*(np.sin(q_2))+2*L_1*L_2*v_1*np.sin(q_2-q_1)*(v_2-v_1)
    a_1 = m_2*L_1*L_2*v_2*v_2*np.sin(q_2-q_1)-(m_1+m_2)*g*L_1*(np.sin(q_1))
    a_2 = m_2*L_1*L_2*v_1*v_1*np.sin(q_2-q_1)-(m_2)*g*L_2*(np.sin(q_2))
    a = np.matrix([[a_1],[a_2]])
    return a

# Funksjon som genererer liste over ticks som skal brukes langs y-aksen
def get_ticks(n,l_min,l_max):
    pi_min = round(l_min/np.pi,2)
    pi_max = round(l_max/np.pi,2)

    diff = int((pi_max - pi_min)*n)
    ticks = [round(pi_min*n,2)]
    for i in range(1,diff):
        ticks.append(int(int(pi_min*n) + i))
    ticks.append(round(pi_max*n,2))
    tickvalues = np.array(ticks) * (np.pi/n)

    return(tickvalues,ticks)

def get_limits(l):
    return(max([abs(max(l)),abs(min(l))]))

# Loekke :)
for i in range(1,N):
    M_i = get_M(theta_1[i-1],theta_2[i-1])
    a_i = get_a(theta_1[i-1],theta_2[i-1],tv_1[i-1],tv_2[i-1])
    M_i_inv = np.linalg.inv(M_i)

    t_a_vector = M_i_inv * a_i

    #print(t_a_vector)

    ta_1[i] = t_a_vector[0]
    ta_2[i] = t_a_vector[1]
    tv_1[i] = tv_1[i-1] + ta_1[i] * dt
    tv_2[i] = tv_2[i-1] + ta_2[i] * dt
    theta_1[i] = theta_1[i-1] + tv_1[i] * dt
    theta_2[i] = theta_2[i-1] + tv_2[i] * dt
    T[i] = T[i-1] + dt


Plot_Time = False
if Plot_Time:

    plt.plot(T,theta_1)
    plt.plot(T,theta_2)

    def x_1(t):
        return((1/1000)            *   np.cos(t*(((2-(2)**(1/2))*9.81)/2)**(1/2)))
    def x_2(t):
        return(((2**(1/2))/1000)   *   np.cos(t*(((2-(2)**(1/2))*9.81)/2)**(1/2)))

    #plt.plot(T,x_1(T))
    #plt.plot(T,x_2(T))

    # Changing the ticks of the y-axis
    Ticks = get_ticks(2,min([min(theta_1),min(theta_2)]),max([max(theta_1),max(theta_2)]))
    if(Ticks[1] != [-0.0,0.0]):
        plt.yticks(Ticks[0],Ticks[1])
else:

    Nineties = np.ones(len(theta_1)) * (np.pi/2)

    X_1 = np.cos(theta_1 - Nineties) * L_1;
    Y_1 = np.sin(theta_1 - Nineties) * L_1;
    X_2 = X_1 + np.cos(theta_2 - Nineties) * L_2;
    Y_2 = Y_1 + np.sin(theta_2 - Nineties) * L_2;

    fig, ax = plt.subplots()
    r_1, = ax.plot(X_1,Y_1)
    r_2, = ax.plot(X_2,Y_2)
    lims = get_limits([get_limits(X_2),get_limits(Y_2)]) *1.1

    ax.plot(0,0,".",color = "k")
    ax.plot(X_1[0],Y_1[0],".",color = "green")
    ax.plot(X_2[0],Y_2[0],".",color = "red")
    plt.axis('square')
    plt.xlim(-lims,lims)
    plt.ylim(-lims,lims)

    print(theta_1[-1],theta_2[-1])

plt.show()
