import  numpy              as  np
import  math               as  mt
import  matplotlib.pyplot  as  plt  # type: ignore
import  inspect            as  ins

# --------------------- [ To Run ] ------------------------
# cd "C:\Users\bastian\Desktop\SEM 1\IN1900\Uke 42 (uke 9)"
# python "Forelesning 16.10.2024.py"
# --------------------- [ ------ ] ------------------------
  
# heallo

def add_kwarg(kwargs,key):

    if key in kwargs:
        return(kwargs[key])

class FuncClass:

    def __init__(self,**kwargs):

        self.number = add_kwarg(kwargs,"num")
        self.func = add_kwarg(kwargs,"func")

    def __call__(self):

        if self.number != None:

            return(self.func(self.number))
    
class Taylor_Expression: #Failed!!!

    def __init__(self,func,N,a):
        self.func = func
        self.N = N
        self.a = a

        self.main_func = self.calculate()

    def calculate(self):

        funcs = [None] * (self.N + 1)
        funcs[0] = lambda x : self.func(self.a)
        for n in range(1,self.N + 1):
            new_func = lambda x : (1/mt.factorial(n)) * (x-self.a)**n * self.chain_derivative(self.func,n,self.a)
            funcs[n] = self.add_funcs(funcs[n-1],new_func)
            print(n)

        return(funcs[-1])

    def derivative(self,func,x,h = 1E-8):

        dx = (func(x + h) - func(x))/h
        return(dx)
    
    def chain_derivative(self,func,N,x):

        dx_func = func
        for n in range(N):
            dx_func = self.derivative(func,x)

        return(dx_func)
    
    def add_funcs(self,func1,func2):
        return(lambda x : func1(x) + func2(x))

    def __call__(self,x):
        return(self.main_func(x))
    
    def __str__(self):
        return(ins.getsource(self.main_func))
    
#T_Square = Taylor_Expression(func = lambda x : x**2, N = 10,a = -0.2)
T_Cube = Taylor_Expression(func = lambda x : x**3, N = 10,a = 0.2)

X = np.linspace(-0.5,0.5,100)

plt.plot(X,X**3)
plt.plot(X,T_Cube(X))
plt.show()