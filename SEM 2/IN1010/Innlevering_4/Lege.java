 import java.util.*;

class Lege implements Comparable<Lege>
{
    private final String navn;

    public Lege(String _navn)
    {
        navn = _navn;
    }

    public String hentNavn()
    {
        return(navn);
    }

    @Override
    public String toString()
    {
        return("Navn : " + navn + "\n");
    }

    @Override
    public Lege compareTo(Lege c) {
        
        if(navn.compareTo(c.hentNavn()))
        {
            
        }
        return; 
    }
}