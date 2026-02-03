data = [7.45,7.54,7.58]

# Getting the mean
t_line = 0
for d in data:
    t_line += d
t_line = t_line/len(data)

# Estimating s_t
s_t = 0
for d in data:
    s_t += (d - t_line)**2
s_t *= (1/len(data))
s_t = s_t**(1/2)

# Getting SEM
SEM_t = s_t/((len(data))**(1/2))
print(SEM_t)