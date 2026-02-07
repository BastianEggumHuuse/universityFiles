import  numpy              as  np
import  matplotlib.pyplot  as  plt  # type: ignore

from ODESolver import *
# a) 

class SEIR:
    def __init__(self, beta=0.33, r_ia=0.1,
                 r_e2=1.25, lmbda_1=0.33,
                 lmbda_2=0.5, p_a=0.4, mu=0.2):

        if isinstance(beta, (float, int)): # Check if beta is a number

            self.beta = lambda t: beta # wrap as function
        elif callable(beta): # This returns true if beta is callable.

            self.beta = beta
        else:

            raise ValueError("Beta must be a scalar or a function")
        
        self.r_ia = r_ia
        self.r_e2 = r_e2
        self.lmbda_1 = lmbda_1
        self.lmbda_2 = lmbda_2
        self.p_a = p_a
        self.mu = mu

    def __call__(self, t, u):
        beta = self.beta(t)
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

# b)

def test_SEIR_beta_const():
    
    test = SEIR()

    t = 0
    u = [1,1,1,1,1,1]

    expected_result = [-0.12925, -0.20075, -0.302, 0.3, -0.068, 0.4]
    current_test = test(t,u)
    tol = 1e-10

    for i in range(len(expected_result)):
        assert abs(expected_result[i] - current_test[i]) < tol, "Values are not the same"

def test_SEIR_beta_var():

    u = [1,1,1,1,1,1]
    beta = lambda t : t * 0.33

    test = SEIR(beta)

    expected_result_1 = [-0.2585, -0.07150000000000001, -0.302, 0.3, -0.068, 0.4]
    expected_result_2 = [-0.517, 0.187, -0.302, 0.3, -0.068, 0.4]
    current_test_1 = test(2,u) # t = 2
    current_test_2 = test(4,u) # t = 4
    tol = 1e-10

    for i in range(len(expected_result_1)):
        assert abs(expected_result_1[i] - current_test_1[i]) < tol, "Values are not the same"
        assert abs(expected_result_2[i] - current_test_2[i]) < tol, "Values are not the same"

test_SEIR_beta_var()
test_SEIR_beta_const()

r"""

===================================================================================== test session starts =====================================================================================
platform win32 -- Python 3.12.4, pytest-7.4.4, pluggy-1.0.0
rootdir: C:\Users\bastian\Desktop\SEM 1\IN1900\Oblig 2 Uke 45 (Uke 12)
plugins: anyio-4.2.0
collected 3 items

SEIR.py ...                                                                                                                                                                        [100%]

====================================================================================== 3 passed in 1.25s ======================================================================================

"""

# c)

# The task doesn't specify what to do with dt, so i have here assumed that it is always supposed to be 1.
# This removes dt from the function entirely, so that it matches the one illustrated in the task.

def solve_SEIR(T,beta,S_0,E2_0):

    model = SEIR(beta)
    u = [S_0, 0, E2_0, 0, 0, 0]

    solver = RungeKutta4(model)
    solver.set_initial_condition(u)

    t_span = (0,T)
    N = T

    values = solver.solve(t_span,N)
    return(values[0],values[1])

# d)

def plot_SEIR(t,u,components=['S','I','Ia','R']):

    indexes = {
        "S" : 0,
        "E1" : 1,
        "E2" : 2,
        "I" : 3,
        "Ia" : 4,
        "R" : 5
    }

    current_indexes = []
    lists = []
    for c in components:
        current_indexes.append(indexes[c])
        lists.append(np.zeros(len(u)))

    for j in range(len(u)):
        for i in range(len(lists)):
            lists[i][j] = u[j][current_indexes[i]]

    for n in range(len(lists)):
        plt.plot(t,lists[n],label = components[n])

    plt.legend()
    plt.show()

    return(lists)


# e)

if __name__ == "__main__":
    t, u = solve_SEIR(T=300, S_0=5.5e6, E2_0=100,beta=0.4)
    L = plot_SEIR(t, u)
    print(f"There are ca {max(L[1]):.0f} infected cases, where {(max(L[1]) * 0.05):.0f} need ventilators.")

r"""

There are ca 258544 infected cases, where 12927 need ventilators.

"""

"""

If there are only 700 ventilators in Norway, the demand for them is vastly higher than their supply.

"""