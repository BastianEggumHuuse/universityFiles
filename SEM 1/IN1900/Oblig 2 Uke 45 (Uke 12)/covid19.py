from SEIR import SEIR, plot_SEIR
from lockdown import Beta
from ODESolver import *

class SEIRimport(SEIR):

    def __init__(self, beta=0.33, r_ia=0.1,
                 r_e2=1.25, lmbda_1=0.33,
                 lmbda_2=0.5, p_a=0.4, mu=0.2,sigma = 10):

        super().__init__(beta,r_ia,r_e2,lmbda_1,lmbda_2,p_a,mu)

        self.sigma = sigma

    def __call__(self,t,u):

        L = super().__call__(t,u)

        L[2] += self.sigma

        return(L)
    
def solve_SEIR(T,beta,S_0,E2_0):

    model = SEIRimport(beta = beta, sigma = 10)
    u = [S_0, 0, E2_0, 0, 0, 0]

    solver = RungeKutta4(model)
    solver.set_initial_condition(u)

    t_span = (0,T)
    N = T

    values = solver.solve(t_span,N)
    return(values[0],values[1])

if __name__ == "__main__":
    beta = Beta('beta_values.txt')
    t, u = solve_SEIR(T=1000, S_0=5.5e6, E2_0=100, beta=beta)
    plot_SEIR(t, u,components=["S","I","Ia","R"])
    plot_SEIR(t, u,components=["R"])
    D = plot_SEIR(t, u,components=["I","Ia"])

    print(f"At it's highest, the number of infected, symptomatic individuals, was {max(D[0]):.0f}.")


r"""

At it's highest, the number of infected, symptomatic individuals, was 7786.

"""


"""

This plot begins a lot like problem 4's, but is different crucially in that people keep getting infected even after
restrictions hit. You can see this by looking at how susceptible people keep falling and recovered people keep rising,
but also by viewing the graphs for only the infected, which while significally lower than in the beginning, stay above 0.

"""