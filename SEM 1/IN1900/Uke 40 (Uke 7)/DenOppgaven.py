import  numpy              as  np
import  matplotlib.pyplot  as  plt  # type: ignore
import  math               as  mt
import  random             as  rd

# --------------------- [ To Run ] ------------------------
# cd "C:\Users\bastian\Desktop\SEM 1\IN1900\Uke 40 (Uke 7)"
# python "DenOppgaven.py"
# --------------------- [ ------ ] ------------------------

def f(r,theta):
    a = (mt.e**((1/r)*np.cos(theta)) * np.cos(-(1/r)*np.sin(theta)))
    b = (-mt.e**(1/r) * np.sin(-(1/r)*np.sin(theta)))
    return(a,b)

W = []

N = [10,50,100,500]
for n in N:
    
    R = np.linspace(0.01,1,n)
    Theta = np.linspace(0,2*mt.pi,n)
    R, Theta = np.meshgrid(R,Theta)
    W.append(f(R,Theta))

plt.plot(W[3][0],W[3][1],c = "green")
plt.xlim(-0.5,0.5)
plt.ylim(-0.5,0.5)
plt.show()