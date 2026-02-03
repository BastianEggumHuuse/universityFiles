import  numpy  as  np

# --------------------- [ To Run ] ------------------------
# cd "C:\Users\bastian\Desktop\SEM 1\Div"
# python "Code_For_Drinks.py"
# --------------------- [ ------ ] ------------------------

'''
a = int(input("Skriv inn tall 1: "))
b = int(input("Skriv inn tall 2: "))

print(f"Summen av {a} og {b} er {a + b}")
'''

extra_Chars = ["0","1","2","3","4","5","6","7","8","9"]

s = input("")

a, b = s.split()
a = int(a)
b = int(b)

def CountBase2(num,base):

    BaseChars = extra_Chars[:base]

    BaseNum = [["0"] for n in range(100)]
    BaseCount = int(num/base)

    List = []
    while((num - num%base)/base) > 0:
        List.append(extra_Chars[int(num%base)])
        num = (num - num%base)/base

    List.append(extra_Chars[int(num%base)])

    num = ""
    for i in range(len(List)-1,-1,-1):
        num = num + str(List[i])

    return(num)




List = []
for n in range(max([b,2]),b * 1000 + 100):

    if n > 10:
        extra_Chars.append("-" * len(extra_Chars))

    List.append([n,CountBase2(a,n),0])

for l in List:
    count = 0
    for c in l[1]:
        if c == str(b):
            count +=1

    l[2] = count

max = [0,"0"]
for l in List:
    if l[2] > max[0]:
        max[0] = l[2]
        max[1] = l[1]

print(max[0])