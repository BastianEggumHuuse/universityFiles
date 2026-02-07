import  numpy              as  np
import  matplotlib.pyplot  as  plt  # type: ignore

# --------------------- [ To Run ] ------------------------
# cd "C:\Users\bastian\Desktop\SEM 1\FYS1100\Oblig 5"
# python "Oppgave_e.py"
# --------------------- [ ------ ] ------------------------

rho = 1.293
A_0 = 0.45
C_D = 1.2
w = 0.0
F = 400
m = 80
dt = 1e-5

D = lambda v : (1/2) * rho * C_D * A_0 * (v-w)**2

X = [0]
V = [0]
A = [F/m]
T = [0]

while X[-1] < 100:

    A.append( (F - D(V[-1])) / m )
    V.append( V[-1] + A[-1] * dt)
    X.append( X[-1] + V[-1] * dt)
    T.append(T[-1] + dt) 

plt.subplot(3,1,1)
plt.plot(T,A,c="red")
plt.ylabel("Akselerasjon i m/s^2")

plt.subplot(3,1,2)
plt.plot(T,V,c="orange")
plt.ylabel("Hastighet i m/s")

plt.subplot(3,1,3)
plt.plot(T,X,c = "firebrick")
plt.ylabel("Posisjon i m")
plt.xlabel("Tid i s")
plt.axvline(T[-1],c = "k",linestyle='dashed')
plt.text(T[-1] - 0.7,X[-1] - 35,f"{T[-1]:.3}s")

plt.show()