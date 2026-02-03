import java.util.*;

class Forelesning{

    public static void main(String[] Args)
    {
        /*

        Beholdere!!!
        En datastruktur som oppbevarer en samling elementer
        Lister, ordbøker, osv.
        Du kan hente, fjerne, og sette inn elementer.        

        Beholdere er generiske, altså de kan inneholde forskjellige typer.

        Noen beholdere, gitt for Strenger:

        */

       String[] s                      = new String[100];
       List<String>       ArrayList_s  = new ArrayList<>();
       List<String>       LinkedList_s = new LinkedList<>();
       Set<String>        Set_s        = new HashSet<>();
       Set<String>        Tree_Set_s   = new TreeSet<>();
       Map<String,String> Dict_s       = new HashMap<>(); // Synonymt med Dictionary i python
       Map<String,String> Dict_Tree_s  = new TreeMap<>();

        /*
        
        Grensesnitt for beholdere:
        
        Det finnes som regel flere måter å implementere det samme grensesnittet.
        Noen eksempler:
        
        Iterable   --- Arver til: Collection og Map.

        Map        --- Arver til: HashMap og TreeMap (og flere men vi bryr oss ikke)

        Collection --- Arver til: List og Set

        List       --- Implementeres som ArrayList og LinkedList

        Set        --- Implementeres som HashSet og TreeSet.



        Litt om Lister:

        En endelig samling av elementer i en bestemt ordning (Rekkefølgen betyr noe).
        I en liste kan vi hente ut størrelse, sette inn element ved gitt posisjon, og fjerne element ved gitt posisjon
        Vi kan også ofte fjerne basert på verdi, hente element ved indeks, og finne indeks til element.
        Vi forventer at vi kan iterere gjennom lista, og at vi da får elementene i rekkefølge.

        Vi ser på to grensesnitt som avgrenser operasjoner:

        Stack - En liste hvor alle innsettinger og slettinger ser på samme ende av listen.
        Når noe legges til, kommer det på slutten av listen, og når noe fjernes, tar du ut det siste elementet.
        Å legge noe til, kalles å pushe, og å fjerne, kalles å poppe.

        Kø (queue) - En liste der alle innsettinger kommer inn på den ene enden, og alle slettinger tas ut av den andre enden.
        Syntax er offer og poll, der offer er å legge til, og poll er å ta ut (eller enqueue/dequeue).

        

        Dynamiske arrayer:

        ArrayList er en ikke fast Array, altså du kan endre lengden osv.

        */
    }
}