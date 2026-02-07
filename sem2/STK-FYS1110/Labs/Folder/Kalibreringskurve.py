import numpy as np
import scipy.stats as stats
import math
import matplotlib.pyplot as plt
import statsmodels.api as sm
from statsmodels.sandbox.regression.predstd import wls_prediction_std

length = []
Mass = []

with open("KalibreringsMasser.txt") as file:
    for l in file:
        ls = l.split(",")
        length.append(float(ls[0]))
        Mass.append(float(ls[1]))

Length=np.array(length)
Mass=np.array(Mass)
plt.xlabel("Lengde lest av måleuret [mm]")
plt.ylabel("Masse [g]")
plt.title("Lineær regresjonsmodell av \nsammenhengen mellom lengde\n og masse")
#plt.plot(Length,Mass,'o')

beta1_hat=np.sum((Mass-np.mean(Mass))*(Length-np.mean(Length)))/np.sum((Length-np.mean(Length))**2)
beta0_hat=np.mean(Mass)-beta1_hat*np.mean(Length)
print('betahat =%f %f' % (beta0_hat, beta1_hat))

res = Mass-beta0_hat-beta1_hat*Length
SSE = sum(res**2)
SST = sum((Mass-np.mean(Mass))**2)
R2 = 1-SSE/SST

print('R2=%f' % R2)
sigma2hat = SSE/(len(Mass)-2)

print('sigma2hat=%f' % sigma2hat)

import statsmodels.api as sm
Length_int = sm.add_constant(Length)
print(Length_int)
mod_ols = sm.OLS(Mass,Length_int)
mod_fit = mod_ols.fit()
print(mod_fit.summary())

print('sigma2hat=%f' % mod_fit.mse_resid)

y_hat=mod_fit.get_prediction(Length_int)
#plt.plot(Length,Mass,'o')
#plt.plot(Length,y_hat)

# Get prediction results
pred_summary = y_hat.summary_frame(alpha=0.05)  # 95% confidence intervals

# Extract prediction intervals
pi_lower = pred_summary['obs_ci_lower']
pi_upper = pred_summary['obs_ci_upper']

print("Avvik for små verdier",abs(pi_lower[0] - pi_upper[0]))
print("Avvik for store verdier",abs(pi_lower[len(pi_lower)-1] - pi_upper[len(pi_upper)-1]))
print("Dynamisk område", abs(math.log10((Mass[-1])/abs(pi_lower[0] - pi_upper[0]))))


plt.plot(length, mod_fit.fittedvalues, color='red', label='Fitted Line')
plt.fill_between(Length, pi_lower, pi_upper, color='blue', alpha=0.2, label='95% PI')
plt.show()

#plt.hist(mod_fit.resid)
plt.xlabel("Lengde lest av måleuret [mm]")
plt.ylabel("Masse [g]")
plt.title("Residualer for lineær regresjonsmodell")
plt.plot(Length,mod_fit.resid,".")
plt.axhline(0,color="k")
plt.show()

s = 0
for r in mod_fit.resid:
    s += r**2
s = s**(1/2)
print(s)

#sm.qqplot(mod_fit.resid, line='45')
#plt.show()
