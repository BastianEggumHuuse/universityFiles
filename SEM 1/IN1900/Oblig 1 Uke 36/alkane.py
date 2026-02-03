M_C = 12.011 # g/mol
M_H = 1.0079 # g/mol

def M(n):
    m = (2*n + 2)
    return([n,m,n * M_C + m*M_H])

for i in range(2,10):
    Mass_Info = M(i)
    # Nested f-strings!!! :)
    print(f"{f"M(C{Mass_Info[0]}H{Mass_Info[1]})":8} = {Mass_Info[2]:7.3f} g/mol")


r'''
python alkane.py

M(C2H6)  =  30.069 g/mol
M(C3H8)  =  44.096 g/mol
M(C4H10) =  58.123 g/mol
M(C5H12) =  72.150 g/mol
M(C6H14) =  86.177 g/mol
M(C7H16) = 100.203 g/mol
M(C8H18) = 114.230 g/mol
M(C9H20) = 128.257 g/mol
'''