def Separations(string):
    words = string.split()

    for i in range(len(words)-1):
        s = "   "
        for j in range(len(words)):
            s += words[j] + " "
            if j == i:
                s += "| "
        
        print(s)

print("\n")
Separations("the big book of poems with the blue cover")
print("\n")
Separations("drove the red car in the garage")
print("\n")