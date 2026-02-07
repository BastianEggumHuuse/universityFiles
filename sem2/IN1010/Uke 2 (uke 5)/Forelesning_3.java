import java.util.Scanner;
import java.io.File;

class Forelesning_3
{
    public static void main(String[] Args)
    {
        if(Args.length != 1)
        {
            System.exit(1); // Stopper hele programmet.
            // parameteren brukes som statuskode for programmet.
            // I enkelte operativsystem (les linux) kan du lese denne koden etter programmet har kjørt.
        }

        // Lenke til java API-en!!!
        // https://docs.oracle.com/javase/8/docs/api/

        try 
        {
            Scanner fil = new Scanner(new File(Args[0]));
            LesFil(fil);
        }
        catch (Exception e)
        {
            System.out.println("Fil " + Args[0] + " eksisterer ikke.");
            System.exit(2);
        }

    }

    private static void LesFil(Scanner fil)
    {
        int AntTegn = 0;
        int AntOrd = 0;
        while(fil.hasNextLine()) // Sjekker om det er flere linjer igjen.
        {
            String s = fil.nextLine(); // Henter ut nåværende linje, og går til neste (i scanneren).  
            AntTegn += s.length(); 
        }
        System.out.println(AntTegn);
    }
}