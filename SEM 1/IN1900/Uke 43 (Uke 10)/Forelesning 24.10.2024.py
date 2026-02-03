import  numpy              as  np
import  matplotlib.pyplot  as  plt  # type: ignore
import  datetime           as  dt   # Make sure not to also have delta time!!!

# --------------------- [ To Run ] ------------------------
# cd "C:\Users\bastian\Desktop\SEM 1\IN1900\Uke 43 (uke 10)"
# python "Forelesning 24.10.2024.py"
# --------------------- [ ------ ] ------------------------

class TestClass:

    # You don't need to call self self!! it can be something else!!!!
    def __init__(belf,v,s):
        belf.v = v
        belf.s = s


# OOP (Object Oriented Programming) betyr strengt ikke kun programmering med klasser og objekter
# Formelt betyr det programmering med klasse hirarkier.

# Repetisjon på arv

class BaseClass:

    def __init__(self, v1, v2):

        self.v1 = v1
        self.v2 = v2

# Ok i didn't know this, but you can ignore __init__ if you just want the same variables!!!!

class SubClass1(BaseClass):

    # This will then have the __init__ method og BaseClass as it's constructor.

    def Func(self):
        print(self.v1)

class SubClass2(SubClass1):
    
    def __init__(self, v1, v2, v3):
        super().__init__(v1, v2)

        self.v3 = v3
        self.v4 = self() #Refers to this class' __call__

    def __str__(self):
        return(f"{self.v1},{self.v2},{self.v3}")
    
    def __call__(self):
        return(self.v3 * 3)

s1 = SubClass1(1,2)
s2 = SubClass2(1,2,3)
t = isinstance(s1,BaseClass) and isinstance(s1,SubClass1) # This returns True !!!!!

# Some testing stuff:

# Generic Polynomial class
class Polynomial:

    def __init__(self,Coeffs):
        self.Coeffs = Coeffs

        self.func = self.Calculate(self.Coeffs)

    def Calculate(self,Coeffs):

        def F(x):
            y = 0

            i = 0
            for c in Coeffs:
                y += c*(x**i)
                i += 1

            return(y)
        
        return(F)

    def __call__(self,x):
        return(self.func(x))
    
# Specializing into line
class Line(Polynomial):

    def __init__(self,a,b):
        super().__init__([b,a])

# Specializing into parabola
class Parabola(Polynomial):

    def __init__(self,a,b,c):
        super().__init__([c,b,a])

l = Line(1,3)
p = Parabola(2,0,3)

plt.plot(np.linspace(-2,2,1000),p(np.linspace(-2,2,1000)))
plt.show()