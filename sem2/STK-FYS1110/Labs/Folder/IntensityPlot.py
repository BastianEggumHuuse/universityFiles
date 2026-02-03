import numpy as np
import matplotlib.pyplot as plt
import math as mt

I_b = 16.31

# Collecting data from file
X_data = []
data = []
logData = []
with open("TrueIntensity.txt") as file:
    for l in file:
        points = l.split(",")
        X_data.append(float(points[1]))
        data.append(float(points[0]) - I_b)
        logData.append(mt.log(float(points[0]) - I_b))

plt.title("log-transformert Straaling mot blytykkelse")
plt.ylabel("ln(Straaling)")
plt.xlabel("Blytykkelse [cm]")

#plt.plot(data.keys(),data.values(),"o")
#plt.plot(logData.keys(),logData.values(),"o",color = "green")
#plt.show()

# Regresjon
X=np.array(X_data)
I=np.array(logData)

beta1_hat=np.sum((I-np.mean(I))*(X-np.mean(X)))/np.sum((X-np.mean(X))**2)
beta0_hat=np.mean(I)-beta1_hat*np.mean(X)
print('betahat =%f %f' % (beta0_hat, beta1_hat))

def LinReg(x):
    return(beta0_hat + beta1_hat*x)

import statsmodels.api as sm
X_int = sm.add_constant(X)
mod_ols = sm.OLS(I,X_int)
mod_fit = mod_ols.fit()
print(mod_fit.summary())

plt.plot(X,I,"o",color="green")
#plt.plot(X, mod_fit.fittedvalues, color='red', label='Fitted Line')
plt.plot(X, LinReg(X), color='blue', label='Fitted Line')
plt.show()

# Finner blytykkelse for 90% absorpsjon
Target = beta0_hat * 0.1
x_approx = X[-1]
d_x = 0.001

lim = 0
while LinReg(x_approx) > Target:
    x_approx += d_x

    #lim += 1
    if(lim > 10000):
        print("Buged")
        break

print(f"Blytykkelse slik at 90% absorberes: {x_approx}")

# Finner for 99% Absorpsjon
Target = beta0_hat * 0.01
while LinReg(x_approx) >Target:
    x_approx += d_x

print(f"Blytykkelse slik at 99% absorberes: {x_approx}")

#   Beta_0_Hat = 7.075217
#   Beta_1_Hat = -1.247014
#   90% = 5.10700
#   99% = 5.61700