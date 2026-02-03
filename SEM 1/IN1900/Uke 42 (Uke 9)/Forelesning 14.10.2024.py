import  numpy              as  np
import  matplotlib.pyplot  as  plt  # type: ignore

# --------------------- [ To Run ] ------------------------
# cd "C:\Users\bastian\Desktop\SEM 1\IN1900\Uke 42 (uke 9)"
# python "Forelesning 14.10.2024.py"
# --------------------- [ ------ ] ------------------------
  


for i in range(0,10):
    if i % 2 == 0:
        continue # Causes the loop to skip to the next iteration (I genuinely did not know this)

if __name__ == "__main__":
    pass # Runs if this program is run on its own
else:
    pass # Runs if this program is imported to another program


# Some testing with callable classes
class FuncClass: # This class is callable!!!!

    def __init__(self,func):
        self.func = func

    def __call__(self,x): # Here is the calling function
        return(self.func(x))
    
func = FuncClass(lambda x : x**2)
func(2) # returns 4

# Ok classes!!!!!


# def DicAddition(Dic1,Dic2):

#     NewDic = Dic1.copy()

#     for k in Dic2.keys():
#         if k in NewDic.keys():
#             NewDic[k] += Dic2[k]
#         else:
#             NewDic[k] = Dic2[k]

#     return(NewDic)

# d1 = {1 : 4, 3 : 5, 2 : 6}
# d2 = {1 : 3, 4 : 2, 3 : 2,7 : 100}

# print(DicAddition(d1,d2))

# heallo