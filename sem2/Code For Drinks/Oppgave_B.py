#Names = Line.split(" ")[1:]

count = int(input())
Names = []

for i in range(count):
    n = input()
    Names.append(n)

Chars = ["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"]

Increasing = True;
Decreasing = True;

# Increasing
prev = Names[0]
for n in Names[1:]:

    for i in range(len(prev)):

        previndex = Chars.index(prev[i])
        
        if(len(n) < i-1):
            Increasing = False
            #print("Broken Length")
            break

        currentindex = Chars.index(n[i])

        if (currentindex > previndex):
            break

        if (currentindex < previndex) :
            Increasing = False
            #print("Broken order")
            break;
    
    prev = n

    if(Increasing == False):
        break;

# Decreasing

prev = Names[-1]
for n in list(reversed(Names))[1:]:

    for i in range(len(prev)):

        previndex = Chars.index(prev[i])
        
        if(len(n) < i-1):
            Decreasing = False
            #print("Broken Length")
            break

        currentindex = Chars.index(n[i])

        if (currentindex > previndex):
            break

        if (currentindex < previndex) :
            Decreasing = False
            #print("Broken order")
            break;
    
    prev = n

    if(Decreasing == False):
        break;

if Increasing:
    print("INCREASING")
elif Decreasing:
    print("DECREASING")
else:
    print("NEITHER")
