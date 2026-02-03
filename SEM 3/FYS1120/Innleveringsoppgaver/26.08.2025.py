import numpy as np
import scipy as sp
import matplotlib.pyplot as plt
from PlotVectorField import Arrow, ArrowSet

class Field:

    def __init__(self,Q,L):

        self.Q = Q
        self.L = L
        self.eps_0 = sp.constants.epsilon_0

    def Get_Field(self,x,y):

        if(x == 0):
            Q_x = 0
        else:
            Q_x = ((self.Q/self.L)/(2*np.pi*self.eps_0*abs(x))) * (x/abs(x))
        if(y == 0):
            Q_y = 0
        else:
            Q_y = ((-self.Q/self.L)/(2*np.pi*self.eps_0*abs(y))) * (y/abs(y))

        #print(Q_x,Q_y)
        return(Q_x,Q_y)
    
VectorField = Field(1,1)

Line = np.linspace(-10,10,21)
Grid = np.meshgrid(Line,Line)[0]

Lines = []
for i in range(len(Grid)):
    for j in Grid[i]:
        #print(Line[i],j)
        Lines.append(VectorField.Get_Field(Line[i],j))
Lines = np.array(Lines)

plt.axhline(0)
plt.axvline(0)
plt.quiver(Grid ,Lines[:,0],Lines[:,1])
plt.show()