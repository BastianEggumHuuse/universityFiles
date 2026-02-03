from Oppgave_3 import Get_Numeric_Solution,get_limits

import numpy as np
import matplotlib.pyplot as plt

def Plot_Position(N,L_1,L_2):

    Nineties = np.ones(len(N["t_1"][0])) * (np.pi/2)

    X_1 = np.cos(N["t_1"][0] - Nineties) * L_1;
    Y_1 = np.sin(N["t_1"][0] - Nineties) * L_1;
    X_2 = X_1 + np.cos(N["t_2"][0] - Nineties) * L_2;
    Y_2 = Y_1 + np.sin(N["t_2"][0] - Nineties) * L_2;

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


s_1 = np.pi/3
s_2 = np.pi/6
l_1 = 2
l_2 = 2
Numeric = Get_Numeric_Solution(s_1,s_2,1,1,l_1,l_2)
Plot_Position(Numeric,l_1,l_2)
plt.show()

s_1 = np.pi/3 + np.pi/30
s_2 = np.pi/6
l_1 = 2
l_2 = 2
Numeric = Get_Numeric_Solution(s_1,s_2,1,1,l_1,l_2)
Plot_Position(Numeric,l_1,l_2)
plt.show()

s_1 = 0
s_2 = np.pi/2
l_1 = 2
l_2 = 2
Numeric = Get_Numeric_Solution(s_1,s_2,1,1,l_1,l_2)
Plot_Position(Numeric,l_1,l_2)
plt.show()