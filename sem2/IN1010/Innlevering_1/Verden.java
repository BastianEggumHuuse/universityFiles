class Verden
{
    // Definerer Variabler i toppen av klassen.
    Rutenett nett;
    public int genNr;

    // Konstruktør for klassen Verden, som kjøres når et nytt Verden-Objekt dannes.
    public Verden(int Rad, int Kol)
    {
        // Denne variabelen holder styr på hvilken generasjon vi er på
        genNr = 0;

        // Skaper et Rutenett-objekt, fyller det med Celler, og Kobler disse Cellene sammen.
        nett = new Rutenett(Rad,Kol);
        nett.fyllMedTilfeldigeCeller();
        nett.kobleAlleCeller();
    }

    // Metode som tegner hele rutenettet, og printer informasjon om den nåværende generasjonen.
    public void tegn()
    {
        nett.tegnRutenett();

        System.out.println("\nDet er nå " + nett.antallLevende() + " Levende Celler");
        System.out.println("Nåverende Generasjon er nr " + genNr + "\n");
    }

    // Metode som oppdaterer hele brettet, og inkrementerer Generasjons-nummeret.
    public void oppdatering()
    {
        nett.tellLevendePerCelle();
        nett.oppdaterAlleCeller();
        genNr ++;
    }
}