import codecs
file = codecs.open("FYS1100_Dataset.txt","r",encoding='utf-8')

Allowed_Characters = [
    "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
    "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
    "1","2","3","4","5","6","7","8","9","0",
    "!","?",".",","," "
]
Conversion_Character = "|"

s = ""

for l in file:
    s += l.replace("\n","").replace("\r"," ").strip()

Delims = ["!","?"]
for d in Delims:
    s.replace(d,d + ".")

Lines = s.split(".")

i = 0
for l in Lines:

    Spaced = True
    for c in l:
        #if(c not in Allowed_Characters):
            #l = l.replace(c,Conversion_Character)
        if c != " ":
            Spaced = False

    if (l != " " and l != '""' and not Spaced):
        Lines[i] = [str(l) ]

    i += 1

#The issue is "", the bastard.

import csv

with open("FYS1100_Dataset_C.csv","w",newline="",encoding="utf-8") as cFile:
    writer = csv.writer(cFile)
    writer.writerows(Lines)