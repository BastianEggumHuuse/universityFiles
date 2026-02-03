import  numpy              as  np
import  matplotlib.pyplot  as  plt  # type: ignore
import  math               as  mt
import  random             as  rd

# --------------------- [ To Run ] ------------------------
# cd "C:\Users\bastian\Desktop\SEM 1\IN1900\Uke 39 (Uke 6)"
# python "Forelesning 23.09.2024.py"
# --------------------- [ ------ ] ------------------------


'''
a = (np.ones(10) * 0)
b = np.zeros(10)
print(a == b)               #Compares all elements individually (returns array of length len(a))
print(np.array_equal(a,b))  #Compares arrays as a whole
'''

'''
#Taylor-oppgave
f_ex = lambda x, : np.exp(-x)
f_k = lambda x,k : ((-x)**k)/mt.factorial(k)

def AnimateSeries(fk,M,N,xmin,ymin,xmax,ymax,n,exact):

    X = np.linspace(xmin,xmax,n)
    s = np.zeros_like(X)

    plt.axis([xmin,xmax,ymin,ymax])
    plt.plot(X,exact(X))

    lines = plt.plot(X,s)

    plt.draw()

    for k in range(M,N+1,1):
        s = s + fk(X,k) #Adds to entire set of s values Because this isn't a list !!!!!!!!!!
        lines[0].set_ydata(s)
        plt.draw()
        plt.pause(0.01)

AnimateSeries(f_k,M = 0,N = 30,xmin = 0, xmax =15, ymin = -0.5, ymax = 1.4,n = 200,exact = f_ex)
'''

#Intresting List comprehension :)
L = [1 + 0.1*rd.randint(1,4) for i in range(0,10)]
print(L)