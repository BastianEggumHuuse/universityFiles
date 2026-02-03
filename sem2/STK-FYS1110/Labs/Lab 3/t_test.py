import scipy as sci

t_hat = 2.09
u_t_hat = 0.0866

t_s = 2.50
u_t_s = 0.05

print(sci.stats.ttest_ind(t_hat,t_s))