import  numpy              as  np
import  matplotlib.pyplot  as  plt  # type: ignore
import  math               as  mt

# --------------------- [ To Run ] ------------------------
# cd "C:\Users\bastian\Desktop\SEM 1\FYS1100\Oblig 4"
# python "Oblig_4_Oppgave_e.py"
# --------------------- [ ------ ] ------------------------

# Variabler
m   = 5.0  # kg
k   = 500  # N/m
l_0 = 0.50 # m
h   = 0.30 # m
n   = 1000 # enhetslos

# Funksjoner
F_x = lambda x : -k * x * ( 1 - l_0/( ( x**2 + h**2 )**( 1 / 2 ) ) )

# Array'er
X_L = np.linspace(-0.75,0.75,n)
F_L = F_x(X_L)

#Plotting
plt.plot(X_L,F_L,"purple")
plt.axhline(0,c = "black")
plt.xlabel("Strekning i meter")
plt.ylabel("Kraft i newton")
plt.show()

plt.plot(X_L,abs(F_L),"mediumorchid")
plt.axhline(0,c ="black")
plt.xlabel("Strekning i meter")
plt.ylabel("Kraft i newton")
plt.show()
