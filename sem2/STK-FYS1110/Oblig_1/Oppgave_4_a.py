# Vi har 4 ulike valg, og q antall valg vi skal gjoere:

def n(q):
    return 4**q

# Vi skal finne sannsynligheten for akkurat en kombinasjon

def P_x_s(n):
    # n er antall kombinasjoner
    return(1/n)

Q = [5,10,20]
N = 5500000

print()

for q in Q:
    p = P_x_s(n(q))
    print(f"Sannsynligheten for at en person har en spesifikk DNA-profil S, naar DNA-profilen har {q:2} nukleotider, er {p:.2e}")
    # E_x = sum(x*px)
    E = N * p
    print(f"Dersom vi har {N} personer, kan vi forvente at {E:.2e} personer har profilen S\n")