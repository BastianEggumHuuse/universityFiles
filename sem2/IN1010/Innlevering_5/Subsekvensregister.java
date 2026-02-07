import java.util.*;
import java.io.File;

class Subsekvensregister
{
    // Lengden på hver subsekvens
    private static final int SUBSEKVENSLENGDE = 3;

    private ArrayList<Frekvenstabell> register = new ArrayList<Frekvenstabell>();

    public void settInn(Frekvenstabell f)
    {
        register.add(f);
    }

    public Frekvenstabell taUt()
    {
        // Vi forutsetter her at det finnes ett eller fler 
        // elementer i listen. Vi sjekker for dette i klasser som
        // anvender denne metoden.
        Frekvenstabell r = register.remove(register.size()-1);

        return(r);
    }

    public int antall()
    {
        return(register.size());
    }

    // Metode som leser fra fil
    public static Frekvenstabell les(String filnavn)
     {
        Frekvenstabell f = new Frekvenstabell();

        Scanner fil = null;
        try 
        {
            fil = new Scanner(new File(filnavn));
        } catch (Exception e) {
            System.out.println("Kunne ikke lese fil.");
            System.out.println(e.getMessage());
            System.exit(1);
        }

        String currentString = null;
        char[] chars = null;
        while (fil.hasNextLine()) 
        {
            // Gjør om den nåværende linjen til en liste med karakterer
            currentString = fil.nextLine();
            chars = currentString.toCharArray();

            for(int i = 0; i < chars.length - 2; i += 1)
            {   
                // copyOfRange kommer fra java.util, og tar ut en subsekvens av char-arrayen
                char[] currentChars = Arrays.copyOfRange(chars, i, i + SUBSEKVENSLENGDE);
                String currentSubSequence = new String(currentChars); // Gjør char-ene tilbake til en liste.
                f.put(currentSubSequence,1); // put() overskriver duplikater, slik at vi bare får unike subsekvenser. 
            } 
        }
        fil.close();
        
        return(f);
    }
}