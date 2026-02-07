# Utrykket vi fant i oppgave f)
def p(N,n,q):
    return(1/(1+(N-n)/4**q))

# Variabler fra oppgaven
N = 5500000
n = 30000
Q = [5,10,20]

print(f"\nVi har N = {N}, og n = {n}\n")
for q in Q:
        print(f"Naar q = {q:2}, har vi at P(C| X = 1) = {p(N,n,q):.6f}")
print()