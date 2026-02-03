/*
Kompleksitet!

Vi prater om ressursbruk, i våre tilfeller tid og minne.
I IN2010 vektlegger vi tid over minne.

Tid måles i antall steg i forhold til størrelsen på input.
Vi måler antall steg i verste tilfelle, i de fleste tilfeller vil algoritmen være raskere

Kjøretidskompleksitet handler om hvor mye kjøretiden vokser når input vokser.
Vi definerer da kjøretidskompleksiteten som en funksjon av størrelsen på input.
Vi bruker store O notasjon! Vekst er viktigere enn konstantfaktorer

Ofte ser vi på Avgjørelsesproblemer, altså problemer med boolske output.
Veldig ofte kan rikere output gjøres om til boolske, altså kan vi først skrive en algoritme 
som løser et avgjørelsesproblem, og så gjøre det om.

Kompleksitetsklasser:
Vi deler disse i 2:

    P (Polynomial time) :
    Et avgjørelsesproblem er i P dersom den kan løses i polynomiell tid (O(n^k))
    Dette gjelder O(log(n)), siden O(log(n)) er i O(n^2)

    NP (Nondeterministic polynomial time) :
    Et avgjørelsesproblem er i NP dersom en løsning kan verifiseres i polynomiell tid.
    Alle problemer i P er også i NP

Sertifikat:
Et verktøy for å verifisere løsningen på et avgjørelsesproblem

Et sertifikat tar inn problemet og løsningen, og returnerer en sannhetsverdi.

Polynomtidsreduksjon:

En polynomtidsreduksjon fra problem A til problem B transformerer instanser av A til instanser av B
der B har polynomiell tid.
En korrekt reduksjon er en som opprettholder alle output fra A til B.
*/