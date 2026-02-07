Line = input()

def pol_1(current,wanted):

    num = 0

    if current != wanted:
        current = wanted
        num += 1

    if(current == "D"):
        current = "U"
        num += 1

    return(current, num)

def pol_2(current,wanted):

    num = 0

    if current != wanted:
        current = wanted
        num += 1

    if(current == "U"):
        current = "D"
        num += 1

    return(current, num)

def pol_3(current,wanted):

    num = 0

    if current != wanted:
        current = wanted
        num += 1

    return(current, num)

now_1 = Line[0]
now_2 = Line[0]
now_3 = Line[0]
num_1 = 0
num_2 = 0
num_3 = 0

for c in Line[1:]:

    now_1,num = pol_1(now_1,c)
    num_1 += num
    now_2,num = pol_2(now_2,c)
    num_2 += num
    now_3,num = pol_3(now_3,c)
    num_3 += num

print(num_1)
print(num_2)
print(num_3)