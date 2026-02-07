import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;


class Monitor
{
    private Subsekvensregister sRegister;

    private final ReentrantLock lock = new ReentrantLock();
    private Condition ikkeTom = lock.newCondition();

    // Variabler vi bruker for å bryte ut av flette-sekvensen
    private int threadCount = 0;
    private int waitCounter = 0;
    private String filNavn = "fil.txt";

    public Monitor(int threadCount_, String filNavn_)
    {
        sRegister = new Subsekvensregister();
        threadCount = threadCount_;
        filNavn = filNavn_;
    }



    public void settInn(Frekvenstabell f)
    {
        lock.lock();
        try
        {
            sRegister.settInn(f); // Legger inn ny Frekvenstabell
            ikkeTom.signalAll();  // Sier ifra til alle ventende traader om at listen ikke er tom
        }
        finally
        {
            lock.unlock();
        }
    }

    public Frekvenstabell taUt()
    {
        return(sRegister.taUt());  
    }

    public int antall()
    {
        return(sRegister.antall());
    }



    public Frekvenstabell[] taUtTo()
    {
        lock.lock();

        Frekvenstabell[] f = new Frekvenstabell[2];

        try
        {   
            // Venter på at det ligger 2 elementer i listen
            while (antall() <= 1)
            {
                // For å bryte ut av flette-sekvensen, sjekker vi om alle traadene venter på at registeret skal fylles.
                // Vi vet at alle venter dersom waitCounter == threadCount
                waitCounter += 1;
                if(waitCounter == threadCount)
                {
                    // når vi vet at alle traadene venter, ber vi alle om å gå videre, slik at de går ut av sekvensen og termineres.
                    ikkeTom.signalAll();

                    // Den siste traaden skriver ut den siste tabellen. (Vi er kun i denne if-setningen dersom vi er på den siste traaden)
                    Frekvenstabell siste = taUt();
                    siste.skrivTilFil(filNavn);

                    // Returnerer null for å signalisere at vi skal bryte ut av sekvensen.
                    return(null);
                }

                // Dersom det ikke er to ledige frekvenstabeller i subsekvensregisteret, legger traaden seg og venter.
                ikkeTom.await();

                // Alle traader som skal bryte ut av sekvensen vil gå inn her.
                if(waitCounter == threadCount)
                {
                    return(null);
                }

                waitCounter -= 1;
            }

            f[0] = taUt();
            f[1] = taUt();
        }
        catch(Exception e)
        {
            // traad ender opp her hvis den blir interrupted (Java krever at jeg har med catch-blokken)
        }
        finally
        {
            lock.unlock();
        }

        return(f);
    }
}