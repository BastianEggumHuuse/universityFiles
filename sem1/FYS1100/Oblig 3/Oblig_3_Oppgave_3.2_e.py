import  numpy              as  np
import  matplotlib.pyplot  as  plt  # type: ignore
import  math               as  mt

# --------------------- [ To Run ] ------------------------
# cd "C:\Users\bastian\Desktop\SEM 1\FYS1100\Oblig 3"
# python "Oblig_3_Oppgave_3.2_e.py"
# --------------------- [ ------ ] ------------------------

k   = 0.21    # enhetslos
mu  = 0       # enhetslos
v_0 = 2.0     # m/s
m   = 1.0     # kg
g   = 9.81    # m/s^2
d_t = 1e-4   # s
a   = -mu * g # m/s^2 (Negativ siden den gaar mot fartsretningen)

def s_v(v_n, a_n, dt):
    return(v_n + a_n*dt)

def s_x(x_n, v_n1, dt):
    return(x_n + v_n1*dt)

T = [0]
A = [a]
V = [v_0]
X = [0]

while V[-1] > 0:
    pass

    T.append(T[-1] + d_t)
    
    a = -mu * g

    A.append(a)
    V.append(s_v(V[-1],a,d_t))
    X.append(s_x(X[-1],V[-1],d_t))

    mu = k * X[-1]

plt.subplot(3,1,1)
plt.plot(T,A,c = "blue",label = "Akselerasjon")
plt.ylabel("Akselerasjon i m/s^2")
plt.title(f"Bevegelse langs bord (m = {m}, k = {k}, v_0 = {v_0})")
plt.grid()
plt.legend()

plt.subplot(3,1,2)
plt.plot(T,V,c = "teal",label = "Hastighet")
plt.ylabel("Hastighet i m/s")
plt.grid()
plt.legend()

plt.subplot(3,1,3)
plt.plot(T,X,c = "green",label = "Posisjon")
plt.xlabel("Tid i sekunder")
plt.ylabel("Posisjon fra start i meter")
plt.grid()
plt.legend()

manager = plt.get_current_fig_manager()
manager.full_screen_toggle()
plt.show()

print(X[-1])