import matplotlib.pyplot as plt
import numpy as np

class Arrow:
    
    def __init__(self,start,diff,color = "k"):

        self.start = start
        self.diff = diff
        self.length = np.linalg.norm(self.diff)
        self.color = color
        self.fadeColor = self.color

        self.head_width = 0.1
        self.unit = 0.1

    def DrawLength(self,axes):
        axes.arrow(self.start[0],self.start[1],self.diff[0],self.diff[1],color = self.color,head_width = self.head_width)

    def DrawColor(self,axes):
        trueDiff = (self.diff/np.linalg.norm(self.diff)) * self.unit
        axes.arrow(self.start[0],self.start[1],trueDiff[0],trueDiff[1],color = self.fadeColor,head_width = self.head_width)

class ArrowSet:

    RED = np.array([1,0,0])
    BLUE = np.array([0,0,1])

    def __init__(self,axes,start,end,N,field = lambda x : x):
        self.axes = axes
        self.start = start
        self.end = end
        self.N = N
        self.field = field

        # Creating grid
        self.grid = []
        spaces = np.linspace(self.start,self.end,self.N)
        for i in spaces:

            self.grid.append([])

            for j in spaces:

                self.grid[-1].append(np.array([i,j]))

        # Creating arrows
        self.arrows = []
        for l in self.grid:
            for p in l:
                self.arrows.append(Arrow(p,self.field(p)))

        # Setting arrow Colors
        min_arrow = self.arrows[0]
        max_arrow = self.arrows[0]
        for arrow in self.arrows:
            if arrow.length < min_arrow.length:
                min_arrow = arrow
            elif arrow.length > max_arrow.length:
                max_arrow = arrow

        for arrow in self.arrows:
            t = (arrow.length-min_arrow.length)/(max_arrow.length-min_arrow.length)
            color = self.ColorLerp(ArrowSet.RED,ArrowSet.BLUE,t)
            arrow.fadeColor = color

    def ColorLerp(self,ColorA,ColorB,t):
        ColorC = ColorA + (ColorB-ColorA)*t
        return(ColorC)

    def DrawLength(self):
        for arrow in self.arrows:
            arrow.DrawLength(self.axes)

    def DrawColor(self):
        for arrow in self.arrows:
            arrow.DrawColor(self.axes)


# Runtime code
if __name__ == "__main__":

    def VectorField(r):
        #vec = r/(np.linalg.norm(r)**3)
        p = np.array([1,0])
        vec = ((3*(r*p)*r)/(np.linalg.norm(r)**5)) - (p/(np.linalg.norm(r)**3))
        return(vec)

    ax = plt.axes()
    Set = ArrowSet(ax,-5,5,30,VectorField)
    Set.DrawColor()
    plt.show()