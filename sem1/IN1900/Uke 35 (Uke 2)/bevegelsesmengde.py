m = 1200
c = 3*1e8
v_0 = 0
v = c*0.9

# a)

def p(M,V):
    return(M*V)

'''
for i in range(0,9):
    print(f"| v = {(c * (0.1 * i)):2.1e} | p = {p(m,v):2.1e} |")
'''

# b)

def p_rel(M,V):
    b = 1 / ((1-((V**2)/(c**2)))**0.5)
    return(M*V*b)

for i in range(0,9):
    v_i = c * (0.1 * i)
    print(f"| v = {v_i:2.1e} m/s | p = {p(m,v_i):2.1e} kgm/s | p_rel = {p_rel(m,v_i):2.1e} kgm/s |")
