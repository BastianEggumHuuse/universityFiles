import  numpy              as  np
import  matplotlib.pyplot  as  plt  # type: ignore

from SEIR0 import SEIR0
from ODESolver import *

# Copied from lecture notes:
class SEIR0:
    def __init__(self, beta=0.33, r_ia=0.1,
                 r_e2=1.25, lmbda_1=0.33,
                 lmbda_2=0.5, p_a=0.4, mu=0.2):

        self.beta = beta
        self.r_ia = r_ia
        self.r_e2 = r_e2
        self.lmbda_1 = lmbda_1
        self.lmbda_2 = lmbda_2
        self.p_a = p_a
        self.mu = mu

    def __call__(self, t, u):
        beta = self.beta
        r_ia = self.r_ia
        r_e2 = self.r_e2
        lmbda_1 = self.lmbda_1
        lmbda_2 = self.lmbda_2
        p_a = self.p_a
        mu = self.mu

        S, E1, E2, I, Ia, R = u
        N = sum(u)
        dS = -beta * S * I / N - r_ia * beta * S * Ia / N \
            - r_e2 * beta * S * E2 / N
        dE1 = beta * S * I / N + r_ia * beta * S * Ia / N \
            + r_e2 * beta * S * E2 / N - lmbda_1 * E1
        dE2 = lmbda_1 * (1 - p_a) * E1 - lmbda_2 * E2
        dI = lmbda_2 * E2 - mu * I
        dIa = lmbda_1 * p_a * E1 - mu * Ia
        dR = mu * (I + Ia)
        return [dS, dE1, dE2, dI, dIa, dR]

# a)
def test_SEIR0():
    
    test = SEIR0()

    t = 0
    u = [1,1,1,1,1,1]

    expected_result = [-0.12925, -0.20075, -0.302, 0.3, -0.068, 0.4]
    current_test = test(t,u)
    tol = 1e-10

    for i in range(len(expected_result)):
        assert abs(expected_result[i] - current_test[i]) < tol, "Values are not the same"

test_SEIR0()

r"""

===================================================================================== test session starts =====================================================================================
platform win32 -- Python 3.12.4, pytest-7.4.4, pluggy-1.0.0
rootdir: C:\Users\bastian\Desktop\SEM 1\IN1900\Oblig 2 Uke 45 (Uke 12)
plugins: anyio-4.2.0
collected 2 items

SEIR0.py ..                                                                                                                                                                         [100%]

====================================================================================== 2 passed in 3.24s ======================================================================================

"""

# b)

def solve_SEIR(T,dt,S_0,E2_0,beta):

    # The SEIR Model defines the function that gives you the derivatives in a single point
    # It gives you the derivatives for all the values.
    SEIR = SEIR0(beta)
    u = [S_0, 0, E2_0, 0, 0, 0]

    solver = RungeKutta4(SEIR)
    solver.set_initial_condition(u)

    t_span = (0,T)
    N = int(T/dt)

    values = solver.solve(t_span,N)
    return(values[0],values[1])

# Oppgave c)

def plot_SEIR(t,u):

    labels = ["S","I","Ia","R"]
    indexes = [0,3,4,5]

    #Conversion
    S = np.zeros(len(u))
    I = np.zeros(len(u))
    Ia = np.zeros(len(u))
    R = np.zeros(len(u))
    lists = [S,I,Ia,R]

    for j in range(len(u)):
        for i in range(len(indexes)):
            lists[i][j] = u[j][indexes[i]]

    for n in range(len(labels)):
        plt.plot(t,lists[n],label = labels[n])

    plt.legend()
    plt.show()

t,u = solve_SEIR(150,1.0,5.5e6,100,0.4)
plot_SEIR(t,u)
