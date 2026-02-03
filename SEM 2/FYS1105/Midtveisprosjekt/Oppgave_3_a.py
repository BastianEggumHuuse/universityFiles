from Oppgave_3 import Get_Numeric_Solution,get_ticks

import numpy as np
import matplotlib.pyplot as plt

def Plot_Analytic(N,s_1,s_2):

    def t_1(t):
        return(s_1   *   np.cos(t*(((2-(2)**(1/2))*9.81)/2)**(1/2)))
    def t_2(t):
        return(s_2   *   np.cos(t*(((2-(2)**(1/2))*9.81)/2)**(1/2)))
    
    plt.plot(N["T"],t_1(N["T"]),color = "coral")
    plt.plot(N["T"],t_2(N["T"]),color = "tomato")

    print(f"Analytisk løsning gir t_1(10) = {t_1(N['T'])[-1]:.4f}, t_2(10) = {t_2(N['T'])[-1]:.4f}")

def Plot_Numeric(N):

    plt.plot(N["T"],N["t_1"][0],color = "powderblue")
    plt.plot(N["T"],N["t_2"][0],color = "skyblue")

    print(f"Numerisk løsning gir t_1(10) = {N['t_1'][0][-1]:.4f}, t_2(10) = {N['t_2'][0][-1]:.4f}")

def Set_Ticks(N):
    Ticks = get_ticks(2,min([min(N["t_1"][0]),min(N["t_2"][0])]),max([max(N["t_1"][0]),max(N["t_2"][0])]))
    if(Ticks[1] != [-0.0,0.0]):
        plt.yticks(Ticks[0],Ticks[1])
        plt.ylabel("Vinkel med steglengde 1pi")
    plt.xlabel("Tid i sekunder")


s_1 = np.pi/100
s_2 = (np.pi*2**(1/2))/100
Numeric = Get_Numeric_Solution(s_1,s_2)

Plot_Analytic(Numeric,s_1,s_2)
Plot_Numeric(Numeric)
Set_Ticks(Numeric)
plt.show()