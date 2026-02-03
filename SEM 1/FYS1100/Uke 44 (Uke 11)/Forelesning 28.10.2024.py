import  numpy              as  np
import  matplotlib.pyplot  as  plt  # type: ignore

# --------------------- [ To Run ] ------------------------
# cd "C:\Users\bastian\Desktop\SEM 1\FYS1100\Uke 44 (uke 11)"
# python "Forelesning 28.10.2024.py"
# --------------------- [ ------ ] ------------------------

# Diff-likninger Numerisk



# Likninger av første orden -------------------

# Finne x(t), der x'(t) = f(x,t) (Farten er bestemt av distansen)
# Dette krever også en initialbetingelse (fks. x(0) = 1)

# Vi løser disse med en variant av Euler-Cromer metoden,
# altså at vi deler opp i veldig mange små ledd, og antar
# at hastigheten er konstant i denne perioden.
# Dette kalles å velge diskre tidsledd.

# t_i = i*dt
# x_(i+1) + f(x_i,t_i) * dt

# Eulers metode: 

# Initial variables
x_0 = 1
dt = 1e-4
tMax = 20

# Lists
T = [0]
V = [0]
X = [x_0]

# Functions
dx = lambda x,t : x*np.cos(t)

# Loop
# Hadde vært MYE raskere å bruke arrays, men er hakke mer komplisert å skrive :)
while T[-1] < tMax:

    # Appending to lists
    V.append(dx(X[-1],T[-1]))
    X.append(X[-1] + V[-1]*dt)
    T.append(T[-1] + dt)

plt.plot(T,X)
plt.plot(T,x_0 * np.e**(np.sin(np.array(T))))
plt.xlabel("Tid [s]")
plt.ylabel("Strekning [m]")
plt.axvline(0,c="k")
plt.axhline(0,c="k")
# Tegner effektivt kun en funksjon, så vi antar at det funker bra :)
plt.show()

# Implisitt Eulers metode:

# Skriver ikke kode her, men er akkurat det samme,
# men her er x_(i+1) = x_i + f(x_(i+1),t_(i+1)), altså
# at du regner med neste verdi i stedet for den nåværende.

# Dette kan fort bli stygt, så anbefales ikke (utenom spesielle tilfeller).

# Eulers midtpunktmetode:
 
# Her bruker vi ett punkt mellom det for den vanlige og implisitte metoden.
# Vi finner midtpunktet med den vanlige metoden, og så finner vi dx i dette punktet.
# x_(i + 1/2) = x_i + f(x_i,t_i)*(dt/2)

# Fullt mulig at vi bruker dette i IN1900
# ---------------------------------------------



# Likninger av andre orden -------------------

# Vi bruker et eksempel (Harmonisk Oscillator)

# x'' = (-o**2) * x

# Igjen kan vi bruke Eulers metode:

# v_(i+1) = v_i + a_i*dt
#         = v_i - (o**2)*x_i*dt
# x_(i+1) = x_i + v_i*dt

# Analytisk:
# Tipper x = e**(lt)
# x' = l*e**(lt)
# x'' = (l**2)*e**(lt)
# l**2 = (-o**2)
# l = +- o
# x(t) = A*e**(i*o*t) + B*e**(-i*o*t) = C*cos(o*t) + D*sin(o*t)

# Vi kan også løse dette ved Euler-Cromer

# v_(i+1) = v_i - (o**2)*x_i*dt
# x_(i+1) = x_i + v_i*dt
# Blir til
# v_(i+1) = v_i - (o**2)*x_i*dt
# x_(i+1) = x_i + v_(i+1)*dt (Det er her forskjellen er)

# Dette blir betydelig bedre.

# ---------------------------------------------

