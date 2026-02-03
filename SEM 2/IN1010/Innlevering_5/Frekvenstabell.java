import java.util.*;
import java.io.PrintWriter;

class Frekvenstabell extends TreeMap<String, Integer> 
{

    @Override
    public String toString()
    {   
        //Henter ut alle noeklene
        Set<String> keys = keySet();
        String outLine = "";

        // Samler opp noekkel, verdi-par i en streng.
        for(String s : keys)
        {
            outLine += s + " " + get(s);
            if(s != lastKey())
            {
                outLine += "\n";
            }
        }

        // Returner denne strengen
        return(outLine);
    } 

    // Metode som fletter sammen to frekvenstabeller
    public static Frekvenstabell flett(Frekvenstabell f1, Frekvenstabell f2) 
    {
        Frekvenstabell flettet = new Frekvenstabell();
        
        //System.out.println(f1);

        // Henter noekler for begge tabellene
        Set<String> keys1 = f1.keySet();
        Set<String> keys2 = f2.keySet();

        // Legger til noekkel,verdi-par fra foerste tabell
        for(String s : keys1)
        {
            flettet.put(s,f1.get(s));
        }

        // Legger til unike noekkel,verdi-par for andre tabell, og oeker frekvensen på duplikater
        for(String s : keys2)
        {
            if(flettet.containsKey(s))
            {
                flettet.put(s,flettet.get(s) + f2.get(s));
            }
            else
            {
                flettet.put(s,f2.get(s));
            }
        }

        return flettet;
    }

    // Metode som skriver frekvenstabellen til fil.
    public void skrivTilFil(String filnavn)
    {
        PrintWriter f = null;
        try 
        {
            f = new PrintWriter(filnavn);
        } catch (Exception e) {
            System.out.println("Kan ikke lage fil.");
            System.exit(1);
        }

        f.print(toString());
        f.close();
    }

}