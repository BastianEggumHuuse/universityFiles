import pylab as pl

g = 9.81
v = 0
T = [0]
Y = [0]

for t in range(1,11):

    v -= 9.81

    T.append(t)
    Y.append(Y[-1] + v)

pl.plot(T,Y,"o",c="royalblue")
pl.title("Posisjonsgraf for Vanndraape")
pl.xlabel("t [s]")
pl.ylabel("y [m]")
pl.axvline(c = "black")
pl.axhline(c = "black")
pl.grid()
pl.show()