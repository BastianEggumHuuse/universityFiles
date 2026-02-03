
'''
#Det originale programmet.
s = 0
M = 3 #Når man summerer med summasjonstegn, skal den siste indeksen, her m = 3, være med. 
      #Slik fungerer ikke range() funksjonen, som bare går til, ikke til og med (Her fram til i = 2).
      #range() begynner også på 0, noe vi ikke ønsker her.

for i in range(M):
    #Vi bruker i for å representere indeks, men både i formelen under og i det originale utrykket, brukes k.
    s += 1/2*k**2 #Her regner programmet feil, siden det ikke er inført riktige paranteser.
print(s)
'''

#Revidert kode:
s = 0
M = 3

for k in range(1,M + 1):
    s += 1/((2*k)**2) 
print(s)


r'''
python sum_for.py

0.3402777777777778
'''