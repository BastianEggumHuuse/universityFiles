import numpy as np

printTask = lambda a : print(f"\nOppgave {a})\n")

printTask("3b")

h_ev = 4.135667 * 10**(-15)
c    = 3*10**(8)

p_ev = lambda e,em : np.sqrt(2*(em)*e)
l_ev = lambda e,em : (h_ev*c)/p_ev(e,em) * 10**9

#a)
m = 939.56542194 * 10**(6)
e = (1/40)
print(f"a) {l_ev(e,m)} nm")

#b)
m_e = m = 0.51099895069 * 10**6
e = 10
print(f"b) {l_ev(e,m)} nm")
e = 1 * 10**3
print(f"b) {l_ev(e,m)} nm")
e = 1 * 10**6
print(f"b) {l_ev(e,m)} nm")

#c)
m = 938.27208943 * 10**(6)
e = 270 * 10**9
print(f"c) {(h_ev*c)/(np.sqrt((e**2 +2*e*m))) * 10**9} nm")

#d)
m = 670 * 10**(9)
e = 100
print(f"d) {l_ev(e,m)} nm")

printTask("4")

print((h_ev*c)/np.sqrt(2*m_e))

print((0.95**2+1)*(2*m_e))
