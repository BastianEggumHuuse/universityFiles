
k_e = 9*10**9
e = 1.6*10**-19
G = 6.7*10**-11
m_p = 1.7*10**-27
m_e = 9.1*10**-31
r = 5.3 * 10**-11

def F_C():
    return(k_e * ((e**2)/(r**2)))

def F_G():
    return(G*((m_p * m_e)/ (r**2)))

def Notation(n):
    s = f"{n:.1e}".split("e")
    num = float(s[0])
    t = int(s[1])
    return(f"{num}*10^{t}")

print(f"The Coloumb Force is {Notation(F_C())} N, while  the Gravitational Force is {Notation(F_G())} N.\nThe ratio between the two is {Notation(F_C()/F_G())}.")