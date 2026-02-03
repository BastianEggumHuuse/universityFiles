import  numpy              as  np
import  matplotlib.pyplot  as  plt  # type: ignore
import  math               as  mt

# --------------------- [ To Run ] ------------------------
# cd "C:\Users\bastian\Desktop\SEM 1\FYS1100\Oblig 4"
# python "Oblig_4_Oppgave_m.py"
# --------------------- [ ------ ] ------------------------

# Variabler
m   = 5.0  # kg
k   = 500  # N/m
l_0 = 0.50 # m
h   = 0.30 # m
x_1 = 0.75 # m
dt  = 1e-4 # s
g   = 9.81 # m/s^2
mu  = 0.05 # konstant
n   = 1000 # enhetslos

# Funksjoner
P_X = lambda x : -k*((x**2 + h**2)**(1/2) - l_0)**2

#Plotting
X = np.linspace(-0.75,0.75,n)
P = P_X(X)

plt.plot(X,abs(P),"crimson")
plt.ylabel("Potensial i joule")
plt.xlabel("Posisjon i meter")

print(X[6],P[6])

plt.show()