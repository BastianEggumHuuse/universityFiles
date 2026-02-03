import  numpy              as  np
import  matplotlib.pyplot  as  plt  # type: ignore

from SEIR import SEIR, solve_SEIR, plot_SEIR
from ODESolver import *

def beta_func(t):
    if t < 30:
        return(0.4)
    return(0.083)
    
if __name__ == "__main__":
    t, u = solve_SEIR(T=300, S_0=5.5e6, E2_0=100,beta=beta_func)
    L = plot_SEIR(t, u)
    print(f"At it's highest, the number of infected, symptomatic individuals, was {max(L[1]):.0f}")

r"""

At it's highest, the number of infected, symptomatic individuals, was 771.

"""

"""

The plot with this piecewise constant beta is a lot flatter. On the scale with which we see it in this plot,
it is essentially constant. The highest total infected also reflects this, being significally lower,
which would significally slow the growth of all graphs.

"""