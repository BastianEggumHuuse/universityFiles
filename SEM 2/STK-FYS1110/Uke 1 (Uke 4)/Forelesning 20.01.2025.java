/*

Forelesning 20.01.2025
----------------------

Dersom du er på 4/6 gruppetimer, slipper du en del av første oblig!!! Husk!!!

Innledning og introduksjon til sannsynlighetsbegrepet
-----------------------------------------------------
Svarer til 2.1 og 2.2 i boka
----------------------------

Deterministiske fenomener:

Når det er fullmåne, når det er soloppgang og solnedgang, er fast. De følger visse låver,
og er forutsigbare. Disse er deterministiske fenomener.

Stokastiske Forsøk:

Situasjoner der vi ikke vet hva resultatet vil bli. Kaste terning, osv. Disse er tilfeldige. 

Utfall og Utfallsrom:
---------------------

Resultatet av et stokastisk forsøk er ikke gitt på forhånd, men vi kan angi utfallene. 
Mengdene av alle utfall for et gitt forsøk kaller vi S

vi vet ikke hvor mange øyne vi vil få, men vi vet vi vil få 1,2,3,4,5, eller 6

S = {1,2,3,4,5,6}.

Dersom vi kaster en mynt, har vi S = {0,1}
Skal vi kaste til vi får mynt, har vi S ? {0,01,001,0001,00001,...}

Dersom vi skal liste opp mulige fødselsvekter, kan vi gi et intervall
S = (0,6) (vi antar at en baby kan veie 0 kilo).

Et utfallsrom kan være endelig, tellbar uendelig, eller kontinuerlig.

Begivenheter (events):
----------------------

Vi ser som regel etter et resultat av et forsøk som svarer til flere utfall. 
Dette er en begivenhet. Fks. kan vi være interresert i om vi får > 3 på 

La A og B være begivenheter. 
Unionen av A og B er en begivenhet C som inneholder alle utfall som befinner seg i A eller i B eller i begge
Snittet av A og B er en begivenhet D som inneholder alle utfall som befinner seg i både A og B.

Dersom A snitt B = Ø, er begivenhetene disjunkte.

Komplementære begivenheter er alle utfall som ikke er med i A.
Vi skriver A'. A og A' vil alltid være disjunkte.

vi kan ha flere enn to hendelser, fks. A, B, C.
Vi kan da lage ting som A snitt B snitt C.

deMorgan!!

1:

(A union B)' = A' snitt B'

2:

(A snitt B)' = A' union B'

Det Klassiske Sannsynlighetsbegrepet:
--------------------------------------

Sannsynlighet handlet om problemer i spill. Sannsynligheten for at vi får minst 5 øyne på terningen er 2/6, eller 1/3
Utfallsrommet er S = {1,2,3,4,5,6}, og begivenheten er A = {5,6}.

Et stokastisk forsøk har N utfall. Vi antar at alle utfall har lik sannsynlighet.
Vi har en begivenhet A som inneholder N(A) utfall. Dette er de gunstige utfallene.

Sannsynligheten for A er da P(A) = Antall gunstige utfall / antall mulige utfall = N(A)/N

Sannsynlighet og relativ frekvens:
----------------------------------

Relativ frekvens er den målte sannsynligheten for et utfall.
Dersom du kaster terning 10 ganger og får 1 sekser, får du at den relative frekvensen er 1/10
Den ordentlige sannsynligheten er 1/6.

Dersom du kaster veldig mange ganger, vil r_n(6) gå mot 1/6.

Det forutsettes at forsøke holdes med like betingelser.
relativ frekvens over lange løp kalles noen gange objektiv sannsynlighet

Dersom du bruker sannsynlighet i dagligtale, er det som regel subjektiv sannsynlighet.

Aksiomer for sannsynlighet:
---------------------------

Aksiom 1:
For enhver begivenhet A, er P(A) >= 0.

Aksiom 2:
For utfallsrommet S er P(S) = 1

Aksiom 3:
Dersom A1, A2, A3, ... er en uendelig følge disjunkte begivenheter,
er P(A1 union A2 union A3 union ...) = SUM(i = 1, i < inf) P(A_i)

*/