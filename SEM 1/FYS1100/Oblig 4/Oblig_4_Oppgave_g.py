import  numpy              as  np
import  matplotlib.pyplot  as  plt  # type: ignore
import  math               as  mt

# --------------------- [ To Run ] ------------------------
# cd "C:\Users\bastian\Desktop\SEM 1\FYS1100\Oblig 4"
# python "Oblig_4_Oppgave_g.py"
# --------------------- [ ------ ] ------------------------

# Variabler
m   = 5.0  # kg
k   = 500  # N/m
l_0 = 0.50 # m
h   = 0.30 # m
x_1 = 0.65  # m
dt  = 1e-4 # s

T   = np.linspace(0,10,int(10/dt)) # s
V   = [0]                  # m/s
X   = [x_1]                # m


# Funksjoner
F_x = lambda x : -k * x * ( 1 - l_0/( ( x**2 + h**2 )**( 1 / 2 ) ) )

# Løkke
for t in T[1:]:
    a = F_x(X[-1])/m
    V.append(V[-1] + a * dt)
    X.append(X[-1] + V[-1] * dt)

#Plotting

plt.subplot(2,1,1)
plt.plot(T,V,"mediumseagreen")
#plt.ylim(-0.001,0.05)
plt.ylabel("Hastighet i m/s")

plt.subplot(2,1,2)
plt.plot(T,X,"springgreen")
#plt.ylim(-0.001,0.05)
plt.ylabel("Strekning i meter")
plt.xlabel("Tid i sekunder")

plt.show()