import  numpy              as  np
import  matplotlib.pyplot  as  plt  # type: ignore
import  datetime           as  dt   # Make sure not to also have delta time!!!

# --------------------- [ To Run ] ------------------------
# cd "C:\Users\bastian\Desktop\SEM 1\IN1900\Uke 43 (uke 10)"
# python "Forelesning 21.10.2024.py"
# --------------------- [ ------ ] ------------------------

print(f"current time: {dt.datetime.now()}")


class TestClass:

    def __init__(self):
        self.ConstructorName = self.__init__.__name__ # returns __init__ (hell yeah)
        
    def PrintMethodName(self):

        print(self.ConstructorName)

TestClass().PrintMethodName()

# You technically knew this, but

def func_1():
    def func2(): # Local function!!!!!!!
        return(2)
    
    return(func2())

Lis = [1,2,3,4]
# We want to add 4 zeros
Lis += [0 for i in range(0,4)]