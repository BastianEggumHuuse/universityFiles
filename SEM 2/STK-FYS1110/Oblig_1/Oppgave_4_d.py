from scipy.stats import binom
import numpy as np
import matplotlib.pyplot as plt
# Vi har 4 ulike valg, og q antall valg vi skal gjoere:

def n(q):
    return 4**q

# Vi skal finne sannsynligheten for akkurat en kombinasjon

def P_x_s(n):
    # n er antall kombinasjoner
    return(1/n)

Q = [5,10,20]
N = 30000

print()


print()

fig = plt.figure()
ax = fig.add_subplot(111)    # The big subplot

ax.spines['top'].set_color('none')
ax.spines['bottom'].set_color('none')
ax.spines['left'].set_color('none')
ax.spines['right'].set_color('none')
ax.tick_params(labelcolor='w', top=False, bottom=False, left=False, right=False)

ax.set_xlabel('x')
ax.set_ylabel('P(X = x)',labelpad = 4)
ax.set_title("Binomisk fordeling for sannsynligheten \n for at X = x, for q = 5, q = 10 og q = 20")

i = 1
for q in Q:
    E = int(N*(1/4**(q)))
    ax = plt.subplot(3,1,i)
    X = np.arange(E - 20,E + 20)
    ax.bar(X,binom.pmf(X,N,1/(4**q)),width = 1,edgecolor="black",color = "lightgreen")
    i += 1
    #pass

plt.show()