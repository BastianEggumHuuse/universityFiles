// Imports : Bare ha med alle liksom :)
import java.util.Iterator;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Interface : "Regler" som en gitt klasse må følge
interface Motordrevet {
    
    int trekkKraft(); // Denne interfacen krever at klassen har en metode trekkKraft(); 
    // og at denne metoden skal returnere en int. 
}

// Helt vanlig klasse. Merk at den er abstract, og du kan dermed ikke lage objekter av den.
// Den implementerer også interfacet Motordrevet!
abstract class Fly implements Motordrevet {

    // final betyr at disse ikke kan endres utenfor denne klassen.
    public final int MTOW, motorAntall, kraft;
    public String id;
    
    // Denne klassen er del av en enkelt-lenket liste, og 
    // har derfor en referanse til det neste objektet i lista.
    public Fly Neste;
    
    // Konstruktør. Denne kalles når et objekt av klassen dannes
    public Fly(String nId, int nMTOW,int nMotorAntall,int nTtrekkKraft){
        
        id = nId;
        MTOW = nMTOW;
        motorAntall = nMotorAntall;
        kraft = nTrekkKraft;
    }
    
    public int trekkKraft()
    {
        return(kraft)
    }
    
    public String hentID()
    {
        return id;
    }
    
    public int hentMotorAntall()
    {
        return motorAntall;
    }
    
    public int hentMTOW()
    {
        return MTOW;
    }

    // Rekursjon : Denne metoden henter den største maks-vekten (MTOW) i lista rekursivt
    public int hentVektRekursivt()
    {
        // dersom neste == null, er vi ved det siste flyet.
        if (neste == null)
        {
            return MTOW;
        }

        // Sender oss til neste fly
        int rest = neste.hentVektRekursivt()

        // Vi kommer altså ikke hit før vi har vært gjennom alle flyene
        // Så returnerer vi den største. Dette sender oss opp til int rest = neste.hentVektRekursivt()
        // Fram til alle er hentet ut.
        if (rest > MTOW)
        {
            return rest;    
        }
        else
        {
            return MTOW;
        }
    }


    
}

// Arv : en klasse som arver fra Fly.
class SeilFly extends Fly{
    
    // En ny instans-variabel
    public final int minSynk;
    
    public SeilFly(String nId, int nMTOW,int nMotorAntall,int nTrekkKraft, int nMinSynk){
        
        // her kaller vi på konstruktøren til Fly.
        super(nId,nMTOW,nMotorAntall,nTrekkKraft);
        minSynk = nMinSynk;
    }
}



abstract class MotorFly extends Fly{
    
    public MotorFly(String nId, int nMTOW,int nMotorAntall,int nTrekkKraft){
        
        super(nId,nMTOW,nMotorAntall,nTrekkKraft);
    }
}

class LasteFly extends MotorFly{
    
    public final int maxVekt;
    
    public LasteFly(String nId, int nMTOW,int nMotorAntall,int nTrekkKraft, int nMaxVekt){
        
        super(nId,nMTOW,nMotorAntall,nTrekkKraft);
        maxVekt = nMaxVekt;
    }
}

class PassasjerFly extends MotorFly{
    
    public final int maxPass;
    
    public LasteFly(String nId, int nMTOW,int nMotorAntall,int nTrekkKraft, int nMaxPass){
        
        super(nId,nMTOW,nMotorAntall,nTrekkKraft);
        maxPass = nMaxPass;
    }
}

// Oppgave 3
class Flyformasjon{
    
    // 3a
    public final Fly foerste = null;
    
    public Fyformasjon()
    {
        
    }
    
    // 3b
    public void leggtil(Fly nFly)
    {
        nFly.neste = foerste;
        foerste = nFly
    }
    
    // 3c
    public boolean erMed(String id)
    {
        private Fly iter = foerste;
        while(iter != null)
        {
            if(iter.hentID().equals(id)):
            {
                return true;
            }
            iter = iter.neste;
        }
        
        return false;
    }
    
    // 3d
    public Fly taUt(String id)
    {
        private Fly iter = foerste;
        
        // Edge-case der det første elementet er den vi vil ha
        if(iter.hentID().equals(id)):
        {
            foerste = iter.neste;
            return iter;
        }
        
        
        while(iter.neste != null)
        {
            if(iter.neste.hentID().equals(id)):
            {
                Fly temp = iter.neste;
                iter.neste = iter.neste.neste;
                
                return temp;
            }
            
            iter = iter.neste;
        }
        
        return null;
    }
    
    // 3e
    
    
    // her danner vi en iterator. Dette er slik at vi kan bruke
    // en for-each loop til å iterere gjennom alle Flyene.
    // Denne metoden gir java iteratoren. Den er nødvendig.
    @Override
    public FlyIterator iterator(){
        return new FlyIterator();
    }
    // Her danner vi selve iteratoren.
    public FlyIterator implements Iterator<Fly>{
        
        Fly iter;
        // Starter med første element i lista
        FlyIterator () {
            iter = foerste
        }
        // sjekker om vi er ferdig med å iterere
        @Override 
        public boolean hasNext() {
            return iter = null;
        }
        // henter neste, og returnerer nåværende fly
        @Override 
        public Fly next() {
            private Fly dette = iter;
            iter = iter.neste;
            return dette;
        }    
    }


    // 3f)
    
    public PassasjerFly[] hentPassasjerFly()
    {
        int count = 0;
        // en for-each loop!
        // Først sier vi hvilken type vi ønsker å iterer gjennom (Fly)
        // og så fra hvilken liste (i dette tilfellet er vi allerede i den)
        for(Fly f : this)
        {
            if(f instanceof PassasjerFly)
            {
                count += 1;
            }
        }

        PassasjerFly[] a = new PassasjerFly[count];
        count = 0;

        for(Fly f : this)
        {
            if(f instanceof PassasjerFly)
            {
                PassasjerFly[count] = (PassasjerFly)f;
                count += 1;
            }
        }

        return a;
    }

    // 4a)

    public int totalVekt()
    {
        int total = 0;

        for(Fly f : this)
        {
            total += f.MTOW;
        }

        return(totalVekt)
    }
    

    // 4b
    public int maksVekt()
    {
        if(foerste != null)
        {
            return foerste.hentVektRekursivt();
        }

        return 0;
    }
}

// 5 Traader

// Dette er en monitor!! En klasse som holder styr på mange forskjellige tråder, og hvordan de opererer sammen.
class Rullebane
{
    int antVentendeFly = 0; // Holder styr på antall fly som er tilgjengelige
    Lock laas = new ReentrantLock(); // En lås! Disse hindrer tråder fra å gå inn til steder de ikke skal :)
    Condition avventStartTillatelse = laas.newCondition(); // En condition! En tråd vil vente ved den
    // Fram til den får en beskjed om å fortsette.

    public void sjekkAvganger() {
        laas.lock(); // Låser, slik at ingen tråder kan gå inn mens denne tråden er her.
        try {
            if (antVentendeFly == 0) {
                return;
            }
            avventStartTillatelse.signal(); // Sier ifra til første tråd som venter at den kan gå.
            antVentendeFly -= 1;
        } finally {
            laas.unlock(); // Låser opp igjen. Alt dette må være i en try-except block!!
        }
    }

    // Metode som henter tillatelse
    public void hentStartTillatelse(Fly startFly) {
        laas.lock()
        try {
            antVentendeFly++;
            avventStartTillatelse.await(); // Venter ved en condition!!
        } catch (InterruptedException e) { // Dette kan interruptes, og må derfor tas med :)
            return;
        } finally {
            laas.unlock();
        }
    }

    // Alternativer til metodene ovenfor, som bruker "Busy waiting"
    Condition avventFly = laas.newCondition();
    void sjekkAvganger5d () {
        laas.lock();
        try {
            while (antVentendeFly == 0) avventFly.await(); // Venter så lenge det ikke er noen ledige fly
            avventStartTillatelse.signal();
            antVentendeFly--;
        } catch (InterruptedException e) {
            return;
        } finally {
            laas.unlock();
        }
    }

    void hentStartTillatelse5d (Fly f) 
    {
        laas.lock();
        try {
            antVentendeFly++;
            avventFly.signalAll(); // Sender signal til ALLE som venter. Dette er grunnen til while-loopen over !
            avventStartTillatelse.await();
        } catch (InterruptedException e) {
            return;
        } finally {
            laas.unlock();
        }
    }
}

// En tråd!! Denne kjører parallelt med main-tråden.
class Flygeleder implements Runnable // Må implementere Runnable!
{
    Rullebane bane; // Referanse til monitor
    Flygeleder (Rullebane r) { // Konstruktøren setter monitor
        Rullebane = r;
    }   

    @Override // Denne metoden kommer fra Runnable interfacen.
    public void run () {

        while (true) { // Evig loop!
            // prøver å gjennomføre en handling
            bane.sjekkAvganger();

            // Uavhengig av om handlingen gjennomføres, venter tråden ett minutt.
            try {
                Thread.sleep(60 * 1000); /* = 1 minutt */
            } catch (InterruptedException e) { 
                return; 
            }
        }
    }    
}


class Pilot implements Runnable {
    Fly mittFly;
    Rullebane bane;

    Pilot (Rullebane r, Fly f) 
    {
        minRullebane = r; mittFly = f;
    }

    @Override
    public void run () 
    {
        minRullebane.hentStartTillatelse(mittFly);
    }
}

// En exception! Denne kan man kalle på i et tilfelle der noe feil skjer.
class FeilSporvidde extends RuntimeException {}

// Exeption og RuntimeExeption er 2 forskjellige ting :)
class FeilSporvidde2 extends Exception {}
// Exception er feil i programmering, Runtimeexception er feil under kjøring.

// Metode som legger til en vogn til et tog, men som sjekker om sporvidden er riktig først 
void leggTilSikker (Skinnegående s) {
    if (første != null) {
        sjekkSporvidde(); // Denne metoden er ikke skrevet her :)

        if (første.hentSporvidde() != s.hentSporvidde()) {
            throw new FeilSporvidde(); // Kaster FeilSporvidde Exceptionen :)
        }
    }
    leggTil(s);
}

// Metode som sjekker om sporvidden på en rekke vogner er lik
void sjekkDenneSporviddenR () { //Rekursivt!!
    if (neste == null) return; // Ferdig
    if (sporvidde != neste.sporvidde)
        throw new FeilSporvidde(); // Feilmelding.
    neste.sjekkDenneSporviddenR(); // merk at sjekken skjer før vi går til neste!
} // Denne metoden sjekker altså ikke på veien tilbake!

// ArrayList, which works a lot like a python list
import java.util.ArrayList; // Importing
private ArrayList<int> list = new ArrayList<>(); // Constructing
list.add(10); // Adding to list
System.out.println(list.size()) // printing list size
buffer.remove(10) // Removing from list


//Countdownlatch! En teller man kan vente på
import java.util.concurrent.CountDownLatch; // Importing
CountDownLatch latch = new CountDownLatch(4); // Constructing
// I Main:
latch.await(); // Sier at man skal vente til latchen er ferdig tellt ned.
// I Tråd:
latch.countDown(); // Teller ned latchen 1 gang. I dette tilfellet trengs det 4 av disse.


// Diverse! 
public int[] a = new int[10]; // new int array.
System.out.println(a.length) // The length of an array

@Override // Overriding the base toString() Method.
public String toString () { // When printing this is used.
	return "And then something interesting goes here."
}

// Checking if two strings are the same
if ("Hello!".equals("Goodbye!")){
    return "Something interesting i guess?"
}

// Manually Interrupting a thread
thread.interrupt(); // Maybe you need a thread to stop.
// While sleeping perhaps?

// Casting!!
int i = 100;
float j = (float)i;


// Input

// The scanner! Java's løsning på bruker input.
import java.util.Scanner; // Importing

class Main {
    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in); //Constructing.
        System.out.println("Enter username");

        String userName = myObj.nextLine();  // Leser en linje fra kommandolinjen.
        System.out.println("Username is: " + userName);  // Skriver dette ut igjen.
    }
}

