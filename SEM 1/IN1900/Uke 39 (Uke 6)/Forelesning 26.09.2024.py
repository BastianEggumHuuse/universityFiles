import  numpy              as  np
import  matplotlib.pyplot  as  plt  # type: ignore
import  math

# ---------------------- [ To Run ] -------------------------
#  cd "C:\Users\bastian\Desktop\SEM 1\IN1900\Uke 39 (uke 6)"
#  python "Forelesning 26.09.2024.py"
# ---------------------- [ ------ ] -------------------------

'''
#Newtons metode !!!!!!!
def Newtons(x,f,d_f,Eps,n_max):

    n = 0
    while(abs(f(x)) > Eps and n < 100):
        x = x - f(x)/d_f(x)
        n += 1

    return(x,n,f(x))

Ans = Newtons(x = 10, f = lambda x : x**2, d_f = lambda x : 2*x,Eps = 1e-10, n_max = 1000)
print(f"x = {Ans[0]}, f(x) = {Ans[2]}")
'''

'''
# Sekvensoppgave
def seq_a(N):
    Indexes = range(0,N+1,2)
    a = np.zeros(len(Indexes))
    for n in Indexes:
        a[int(n/2)] = (7 + 1.0/(int(n/2)+1))/(3 - 1.0/((int(n/2) + 1)**2))

    return(a)

print(seq_a(100))
'''

'''
# Vectorization
x = np.linspace(0.1,1,10)

# Non Vectorized
y = np.zeros(len(x))
for i in range(len(x)):
    y[i] = math.log(x[i])

# Vectorized
y = np.log(x)
'''

'''
# Class stuff
class Vector2:

    def __init__(self,x,y):
        self.x = x
        self.y = y

    def __str__(self):
        return(f"[{self.x}, {self.y}]")
    
Vectors = [Vector2(x,x*2) for x in range(0,4)]

print(Vectors[2])

for v in Vectors:
    # since Vector2 is a class, you can edit it via a for loop
    v.x = 0

print(Vectors[2])
'''

'''
# Derivasjon
def D(f,x,N):
    seq = []

    for n in range(N):
        h = 2**(-n)
        seq.append((f(x + h)-f(x))/h)

    return(np.array(seq))

d_f = lambda f,x: (f(x + 1e-8) - f(x)) / 1e-8

x = np.linspace(-np.pi * 3,np.pi * 3,1000)
x2 = np.array(list(x)[0:int(len(x)/2)])
y = np.sin(x)
y2 = d_f(np.sin,x)
y3 = np.cos(x2)

plt.plot(x,y,label = "sin(x)")
plt.plot(x,y2,label = "sin(x)'")
plt.plot(x2,y3,label = "cos(x)")
plt.axvline(c = "black")
plt.axhline(c = "black")
plt.grid()
plt.legend()
plt.show()
'''