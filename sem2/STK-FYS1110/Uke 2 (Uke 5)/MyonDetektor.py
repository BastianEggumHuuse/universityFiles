
A = 0.53
B = 0.65
C = 0.84
D = 0.71

P = [A,B,C,D]

NotP = []

for i in range(0,4):
    for (j) in range(0,4):
        Num = P[i] * P[j]
        print (f"{P[i]} * {P[j]} {Num}")
        NotP.append(1 - Num)

P_Detekt = 1
for n in NotP:
    P_Detekt = P_Detekt * n

print(f"P(Detekt') = {P_Detekt}")
print(f"P(Detekt) = 1 - P(Detekt') = {1 - P_Detekt}")