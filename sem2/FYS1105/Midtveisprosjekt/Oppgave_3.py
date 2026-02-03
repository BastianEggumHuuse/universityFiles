import numpy as np

def Get_Numeric_Solution(theta_1_0 = np.pi,theta_2_0 = np.pi,m_1 = 1,m_2 = 1,L_1 = 2,L_2 = 2):
    # Stats
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

    Dictionary = {
        "t_1" : [theta_1,tv_1,ta_1],
        "t_2" : [theta_2,tv_2,ta_2],
        "T" : T
    }

    return Dictionary

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