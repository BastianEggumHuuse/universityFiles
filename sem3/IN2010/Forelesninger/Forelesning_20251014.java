class Forelesning_20251014
{
    /*

    Grafer! Vi jobber med det de neste 3 forelesningene.
    Vi vil i dag dekke
        Hva en graf er?
        Hvordan grafer representeres
        Hvordan grafer traverseres
        Hvordan grafer kan ordnes.
     
    Videre vil vi se på grafer med vekter,
    og senere sammenhengende grafer og komponenter
    */

    public static void Main(String[] Args)
    {

    }
}

class Graph
{
    /**
    Et tre er en graf! Grafer er er abstraksjon av trær.

    Definisjon:

    En graf er en mengde med noder og kanter.
    En kant binder to noder sammen: Eks Node A, B, kant {A,B}.

    Vi definerer da en graf G som to mengder <V,E>, der
    V er vertices, og E er edges.

    Dersom det er to kanter mellom to noder, kalles disse kantene parallelle.
    Dersom en kant går fra en node til den samme noden, kalles dette en enkelt løkke.
    En kant kan ha retning, altså at rekkefølge på noder i kanten er relevant
    En kant kan også være vektet, altså at den har en verdi.

    Dersom en graf ikke inneholder parallelle kanter, løkker, retning, eller vekt,
    kalles det for en enkel graf (simple graph)

    Retning:
    Som nevnt kan en graf ha retning, altså at man bare kan forflytte seg en vei langs en kant.
    Vi kan ikke lenger bruke mengdenotasjon, og bruker istedet par (A,B), fra A til B.
    En hver urettet graf kan representeres som en rettet graf, ved å bytte hver kant med to parallelle.

    Stier og Veier:
    En sti er en sekvens av noder i grafen, forbundet av kanter, der ingen noder gjentas.
    En vei er en sekvens av noder i grafen, forbundet av kanter der ingen kanter gjentas.
    Ofte ønsker vi å finne korteste sti mellom to noder.

    Sammenhengende grafer og komponenter:
    En graf er sammenhengende dersom det finnes en sti mellom alle par av noder.
    Dersom en graf ikke er sammenhengende, kan vi danne komponenter, som er undergrafer
    Dersom det er retning på grafen, kan de også være sterkt sammenhengende, som betyr
    at vi kan gå hvor vi vil fra alle noder.

    Sykler
    En Sykel er en sti som forbinder minst tre noder, der første node forbindes med den siste.
    I en rettet graf må sykelen også peke riktig vei, slik at vi kan gå rundt.
    En graf som ikke inneholder minst en sykel kalles for asyklisk.

    Et tre er en urettet, sammenhengende, asyklisk graf!
    En rettet, asyklsik graf er en veldig anvendelig type graf, og kalles en DAG

    Størrelse:
    En gitt node har en grad deg(v), gitt ved antall kanter koblet til v.
    I rettede grafer snakker vi om inngrad og utgrad.

    En enkel komplett graf er en graf der alle noder er koblet sammen.
        Disse har (|V|(|V|-1))/2 kanter (kvadratisk),
        eller O(|V|^2)
    Kjøretidskompleksitet avhenger av både |V| og |E|
    Dersom vi har mange kanter relativt til noder kalles disse tette (dense),
    og motsatt har vi tynne (sparse).
    */

    /*
    Representasjon:

    En graf representeres ofte som en nabomatrise eller en naboliste.
    Vi ønsker enkelt å kunne avgjøre om det er en kant mellom u og v.
    Vi antar at vi har tilgang til raske mappinger og mengder (Hashing),
    og dermed konstant tid på oppslag.
        Dersom man har tette grafer, kan vi bruke en nabomatrise,
        dersom vi har tynne grafer kan vi bruke en naboliste (dette er det vi vil gjøre)
    */

    /*
    Traversering:

    Vi ønsker å gå gjennom hele grafen, altså være innom alle noder 1 gang.
    Vi kan lett svare på
        Hvilke noder vi kan nå fra en gitt node s
        Hvor mange komponenter en graf har.

    Vi vil se på to traverseringer:
        Dybde-først søk (med stack)
        Bredde-først søk (med kø)

    Dybde-først søk:
        Vi blir gitt en start-node, og så går vi en vilkårlig sti fra denne noden.
        For hver node vi besøker, markeres den som besøkt. For hver besøkt node, prøver den å følge alle stier som går
        til andre ubesøkte noder.
        Dette implementeres som regel med rekursjon, og har lavt minnebruk.

    Bredde-først søk:
        Vi besøker først alle direkte naboer, så de direkte naboene deres, osv.
        Implementeres ved en kø.

    Topologisk sortering:
        Dersom vi har en DAG (rettet asyklisk graf), kan vi ordne nodene topologisk
        En slik sortering gir en "gjennomføringsplan" for å anvende nodene.
        Vi starter ved en inn-node, og velger den

    */

    class Node
    {
        boolean equals(Node u)
        {
            return true;
        }
    }

    class Edge
    {
        Node start;
        Node end;
    }

    class Set
    {
        void add(Node u)
        {

        }

        boolean in(Node u)
        {
            return true;
        }
    }

    Edge[] Edges;
    Node[] Nodes;

    void DepthFirstVisit(Graph G, Node u, Set visited)
    {

        // Adding to set of visited 
        visited.add(u);

        // Looping through all edges
        for(Edge e : G.Edges)
        {
            // Checking if we are at an edge between u and an unvisited node
            if(e.start.equals(u) && visited.in(e.end) == false)
            {
                // visiting said node
                DepthFirstVisit(G,e.end,visited);
            }
        }
    }

    void DepthFirstSearch(Graph G)
    {
        Set visited = new Set();

        for(Node n : G.Nodes)
        {
            if(visited.in(n) == false)
            {
                DepthFirstVisit(G,n,visited);
            }
        }
    }

    class Queue{
            
        void push(Node n){

        }

        void pop()
        {

        }

        boolean empty()
        {
            return false;
        }

        boolean in(Node n)
        {
            return false;
        }
    }

    void WidthFirstVisit(Graph G){

        // This code is straight up wrong, but it would work if programmed correctly

        Queue q = new Queue;
        Set visited = new Set;

        visited.add(n);
        Queue.push(G.Nodes[0])

        while (Queue.empty() == false)
        {
            Node n = Queue.pop;

            for(Edge e : n.edges)
            {
                if (visited.in(e.end))
                {
                    visited.add(n);
                    Queue.push(e)
                }
            }
        }

    }

    WidthFirstSearch(Graph G)
    {
        Set visited = new Set();

        for(Node n : G.Nodes)
        {
            if(visited.in(n) == false)
            {
                WidthFirstVisit(G,n,visited);
            }
        }
    }
        
}