class Celle
{
    // Definerer Variabler i toppen av klassen.
    boolean levende;
    Celle[] naboer;
    int antNaboer;
    int antLevendeNaboer;

    // Konstruktør for klassen Celle, som kjøres når et nytt Celle-Objekt dannes.
    public Celle()
    {
        levende = false;
        naboer = new Celle[8];
        antNaboer = 0;
        antLevendeNaboer = 0;
    }

    // Metoder som kontrollerer cellens "levende"-verdi.
    public void settDod()
    {
        levende = false;
    }

    public void settLevende()
    {
        levende = true;
    }

    // Metode som brukes til å sjekke om en Celle er levende eller ikke.
    public boolean erLevende()
    {
        return(levende);
    }

    // Metode som returnerer riktig tegn for denne cellen.
    // Dette tegnet er '0' dersom cellen lever, og '.' dersom cellen er død.
    public char hentStatusTegn()
    {
        if (levende)
        {   
            // I java brukes "" til å definere Strings, og '' til å definere Chars, altså enkeltkarakterer.
            return('O');
        }
        return('.');
    }

    // Metode som legger til en nabo til denne Cellen.
    // Disse naboene brukes til å sjekke om cellen skal leve neste generasjon
    // Legg merke til at Cellen ikke vet noe om hvor den selv er, eller hvor naboene er.
    // Den har bare tilgang til Naboenes Celle-objekter.
    public void leggTilNabo(Celle NyNabo)
    {
        // Her forutsetter vi at vi ikke prøver å legge til mer enn 8 naboer 
        naboer[antNaboer] = NyNabo;
        antNaboer ++;
    }

    // Metode som teller antall levende naboer.
    // Det er denne metoden vi bruker til å finne ut om Cellen skal leve. 
    public void tellLevendeNaboer()
    {
        antLevendeNaboer = 0;

        for(int i = 0; i < antNaboer; i++)
        {
            // Her bruker vi metoden ErLevende, skrevet over, til å finne levende-verdien til Nabocellen.
            if (naboer[i].erLevende())
            {
                antLevendeNaboer ++;
            }
        }
    }

    // Metode som oppdaterer Cellens status, basert på spillets regler.
    public void oppdaterStatus()
    {
        if (levende)
        {
            if(antLevendeNaboer == 2 || antLevendeNaboer == 3)
            {
                return;
            }
            settDod();
        }
        else
        {
            if (antLevendeNaboer == 3)
            {
                settLevende();
            }
        }
    }
}