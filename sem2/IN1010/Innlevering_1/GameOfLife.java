import java.util.Scanner;

class GameOfLife
{

    // Hovedmetoden til klassen GameOfLife, som kjøres når GameOfLifes klassefil kjøres.
    public static void main(String[] args)
    {

        // Dette er den utvidede, frivillige løsningen. Den ordinære løsningen kommer under.
        int rader = 8;
        int kolonner = 12;
        // Henter ut antall rader og kolonner fra programparameterne.
        try 
        {
            // Vi bruker try i tilfellet der programparameterne ikke er tall.
            if(args.length > 1)
            {
                // Konverterer programparameterne til integerverdier.
                rader = Integer.parseInt(args[0]);
                kolonner = Integer.parseInt(args[1]);
            }
        }
        // Dersom programparameterne ikke er tall, gjør vi ingen ting, og bruker i stedet 8 rader og 12 kolonner.
        catch (NumberFormatException e)
        {
            // Vi gjør ingen ting her :)
        }

        // Danner et Verden-objekt.
        Verden v = new Verden(rader,kolonner);
        
        // Scanner-objektet (Hentet fra java.util.Scanner) brukes for å håndtere bruker-input.
        Scanner s = new Scanner(System.in);
        String input = ""; 

        // Programmet vil fortsette å kjøre nye generasjoner hver gang bruker trykker enter.
        // Dersom brukeren legger inn en string-input, vil programmet stoppe å kjøre generasjoner
        while(input == "")
        {
            v.tegn();
            v.oppdatering();

            input = s.nextLine();
        }

        // Ordinær løsning: 
        /*

        Verden v = new Verden(8,12);

        while(v.GenNR < 3)
        {
            v.tegn();
            v.oppdater();
        }

        */

        // Her er vi i mål :)
        System.out.println("\nSpillet er ferdig :)\n");
        s.close();
    }

    // @Override
    // public double prisABetale()
    // {

    //     double pris = hentlegemiddel().hentpris() * rabatt;
    //     return Math.round(pris);

    // }

}