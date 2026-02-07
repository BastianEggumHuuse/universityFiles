import  numpy              as  np
import  matplotlib.pyplot  as  plt  # type: ignore

# --------------------- [ To Run ] ------------------------
# cd "C:\Users\bastian\Desktop\SEM 1\FYS1100\Oblig 5"
# python "Oppgave_k.py"
# --------------------- [ ------ ] ------------------------

rho = 1.293
A_0 = 0.45
C_D = 1.2
w = 0.0
F = 400
m = 80
f_v = 25.8
f_c = 488
t_c = 0.67
dt = 1e-5

D = lambda v,t : (1/2) * rho * C_D * A_0 * (1-0.25*np.exp(-(t/t_c)**2)) * (v-w)**2
F_C = lambda t : f_c * np.exp(-(t/t_c)**2)
F_V = lambda v : - f_v*v
F_net = lambda v,t : F + F_C(t) + F_V(v) - D(v,t)

X = [0]
V = [0]
A = [F/m]
T = [0]

F_L = [F]
F_C_L = [F_C(T[-1])]
F_V_L = [F_V(V[-1])]
F_D_L = [-D(V[-1],T[-1])]

while X[-1] < 100:

    A.append(F_net(V[-1],X[-1]) / m)
    V.append(V[-1] + A[-1] * dt)
    X.append(X[-1] + V[-1] * dt)
    T.append(T[-1] + dt) 

    F_L.append(F)
    F_C_L.append(F_C(T[-1]))
    F_V_L.append(F_V(V[-1]))
    F_D_L.append(-D(V[-1],T[-1]))


plt.plot(T,F_L,label = "F")
plt.plot(T,F_C_L,label = "F_C")
plt.plot(T,F_V_L,label = "F_V")
plt.plot(T,F_D_L,label = "D")
plt.legend(loc = 'upper right')

plt.show()