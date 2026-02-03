class Rutenett
{
    // Definerer Variabler i toppen av klassen.
    int antRader;
    int antKolonner;
    Celle[][] rutene;

    // Konstruktør for klassen Rutenett, som kjøres når et nytt Rutenett-Objekt dannes.
    public Rutenett(int Rader,int Kolonner)
    {
        antRader = Rader;
        antKolonner = Kolonner;
        // Dette er en dobbel Array, som eventuelt kan tolkes som en matrise.
        rutene = new Celle[Rader][Kolonner];
    }

    // Metode som lager en enkelt Celle, og så bestemmer om Cellen skal leve eller ikke ved starten av spillet.
    public void lagCelle(int Rad,int Kol)
    {
        // Bruker Celle-konstruktøren.
        Celle NyCelle = new Celle();
        if (Math.random() <=0.3333) // Hver Celle har en 1/3 sjanse for å leve ved starten av spillet
        {
            NyCelle.settLevende();
        }

        // Du får tilgang til en dobbel array ved å først indeksere Rad, og så indeksere Kolonne.
        // Her får Cellen sin plass i rutenettet.
        rutene[Rad][Kol] = NyCelle;
    }

    // Metode som fyller Rutenettet med tilfeldige Celler.
    public void fyllMedTilfeldigeCeller()
    {
        // Her bruker vi en dobbel for-løkke, et triks som dukker opp mange ganger i denne oppgaven, for å iterere gjennom hele rutenettet.
        for(int i = 0; i < antRader; i ++)
        {
            for (int j = 0; j < antKolonner; j ++)
            {
                // Bruker LagCelle-metoden skrevet over, sammen med koordinatene funnet fra for-løkkene.
                lagCelle(i,j);
            }
        }
    }

    // Metode som returnerer Celle-objektet for Cellen som ligger ved gitt koordinat.
    public Celle hentCelle(int Rad,int Kol)
    {
        // Her sjekker vi om posisjonen gitt av Rad og Kol er gyldig, altså at den er innenfor rutenettet.
        if (Rad < 0 || Rad >= antRader || Kol < 0 || Kol >= antKolonner)
        {
            // Vi returnerer null dersom posisjonen er ugyldig.
            return null;
        }

        return rutene[Rad][Kol];
    }

    // Metode som tegner rutenetett for den nåverende generasjonen.
    public void tegnRutenett()
    {
        // Jeg har valgt å dele opp tegningen i en og en Rad, for å gjøre ting litt mer ryddig (Dette var ikke gitt fra oppgaven).
        for(int Rad = 0; Rad < antRader; Rad ++)
        {
            // Bruker metoder for å tegne linjer og rader med Celler.
            // Hver rad med Celler har en linje over og under seg.
            tegnLinje();
            tegnRadCeller(Rad);
        }

        // Tegner en siste linje for å fullføre brettet.
        tegnLinje();
    }

    // Metode for å tegne linjer.
    public void tegnLinje()
    {
        //Linjene består av "+" og "-", som beskrevet i oppgaveteksten.
        String Linje = "";
        for(int Kol = 0; Kol < antKolonner; Kol ++)
        {
            //Vi legger til et "segment" av linjen for hver kolonne i rutenettet.
            Linje += "+---";
        }
        Linje += "+";

        System.out.println(Linje);
    }

    // Metode for å tegne rader med celler.
    public void tegnRadCeller(int Rad)
    {
        // Radene består av "|" og Cellenes Statustegn, som vi kan hente via metoden Celle.HentStatusTegn()
        String CelleRad = "";
        for(int Kol = 0; Kol < antKolonner; Kol ++)
        {
            // Her henter vi ut den cellen vi er på, og henter statustegn fra den.
            // Dette er grunnen til at denne metoden krever Rad som parameter.
            CelleRad  += ("| " + hentCelle(Rad,Kol).hentStatusTegn() + " ");
        }

        CelleRad += "|";
        System.out.println(CelleRad);
    }

    // Metode som setter naboer for en Celle ved gitte koordinater
    public void settNaboer(int Rad, int Kol)
    {
        // Henter Cellen-objektet vi skal sette naboer for
        Celle HovedCelle = hentCelle(Rad,Kol);

        
        for(int i = Rad-1; i <= Rad + 1; i ++)// igjen en dobbel for-løkke, her for å iterere gjennom alle 8 Cellene rundt HovedCellen.
        // Vi itererer altså først over de tre cellene over hovedcellen, så de to på hver sin side, og så de tre under.
        {
            for(int j = Kol-1; j <= Kol + 1; j ++)
            {
                // Henter ut nabocelle for gitte koordinater
                Celle NaboCelle = hentCelle(i,j);
                // Sjekker om NaboCellen fins (At koordinatene er gyldige) og at NaboCellen ikke er HovedCellen, slik at Hovedcellen ikke får seg selv som nabo.
                if (NaboCelle != null && NaboCelle != HovedCelle)
                {
                    HovedCelle.leggTilNabo(NaboCelle);
                } 
            }
        }
    }

    // Metode som kjører SettNaboer() for alle Cellene i rutenettet
    public void kobleAlleCeller()
    {
        for(int i = 0; i < antRader; i ++)
        {
            for(int j = 0; j < antKolonner; j ++)
            {
                settNaboer(i,j);
            }
        }
    }

    // Metode som teller totale antall levende celler.
    public int antallLevende()
    {
        // Bruker en integer for å holde styr på antallet.
        int Teller = 0;

        for(int i = 0; i < antRader; i ++)
        {
            for(int j = 0; j < antKolonner; j ++)
            {
                Celle NyCelle = hentCelle(i,j);
                if (NyCelle.erLevende()) // Telleren inkrementeres kun dersom Cellen lever.
                {
                    Teller += 1;
                }
            }
        }

        return(Teller);
    }

    //Disse metodene er ikke fra oppgaven, men jeg valgte å inkludere dem for leselighet

    // Metode som får hver Celle til å telle sine egne levende naboer
    public void tellLevendePerCelle()
    {
        for(int i = 0; i < antRader; i ++)
        {
            for(int j = 0; j < antKolonner; j ++)
            {
                Celle NyCelle = hentCelle(i,j);
                NyCelle.tellLevendeNaboer();
            }
        }
    }

    // Metode som oppdaterer hver Celle i rutenettet, til neste generasjon.
    public void oppdaterAlleCeller()
    {
        for(int i = 0; i < antRader; i ++)
        {
            for(int j = 0; j < antKolonner; j ++)
            {
                Celle NyCelle = hentCelle(i,j);
                NyCelle.oppdaterStatus();
            }
        }
    }

}