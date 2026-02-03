import  numpy              as  np
import  matplotlib.pyplot  as  plt  # type: ignore

from SEIR import SEIR, solve_SEIR, plot_SEIR
from ODESolver import *
from datetime import date, timedelta

# --------------------- [ To Run ] ------------------------
# cd "C:\Users\bastian\Desktop\SEM 1\IN1900\Oblig 2 Uke 45 (Uke 12)"
# python "lockdown.py"
# --------------------- [ ------ ] ------------------------

class Beta:

    def __init__(self,filename):

        with open(filename,"r") as file:

            data_list = []

            for l in (list(file)[2:]):
                values = l.split()

                if(values[1] == "---"):
                    self.final_value = float(values[2])
                    break

                L = [self.str2date(values[0]),self.str2date(values[1]),float(values[2])]
                data_list.append(L)

            self.day_values = []
            self.starting_day = data_list[0][0]

            for d in data_list:
                L = [(d[0]-self.starting_day).days,(d[1]-self.starting_day).days,d[2]]
                self.day_values.append(L)


    def str2date(self, date_str):
        #convert a date string on the format dd.mm.yyyy
        #to a datetime object  
        day, month, year = (int(n) for n in date_str.split('.'))
        return date(year,month,day)
    
    def __call__(self,t):
        for d in self.day_values:
            if t <= d[1]:
                return d[2]
            
        return self.final_value
    
    def plot(self,T):
        T_Values = np.linspace(0,T,T)
        B_Values = [self(t) for t in T_Values]
        plt.plot(T_Values,B_Values)
        plt.show()

if __name__ == "__main__":
    beta = Beta('beta_values.txt')
    beta.plot(1000)
    t, u = solve_SEIR(T=1000, S_0=5.5e6, E2_0=100, beta=beta)
    D = plot_SEIR(t, u)



"""

The graph of the infections stays more or less the same (Visually at least, it has in fact grown). The change in R and S 
is more visible here, while still visually flattening out over time.
This matches the values of beta, which starts out somewhat higher, before falling.

"""    