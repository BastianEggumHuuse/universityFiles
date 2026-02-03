
/*
Paralellitet og tråder!!!

Vi kan kjøre flere programmer samtidig!!!

Programmer kjøres som prosesser innad i OS-en. Vi kan kjøre disse i paralell.
En tråd er en paralell eksekvering innad i en prosess!!!! Så vi slipper å kjøre flere prosesser!

I ny arkitektur, har man flere prosessorer, som kobles til register og cache, og så kobles de sammen med
cache 2,3 osv.

Vi ønsker å bruke tråder fordi det er ineffektivt å skifte mellom prosesser.
De opererer som små prosesser innad i en stor prosess, og kommuniserer via felles data.
*/

/*

klassen Thread ligger i java, og er ferdig bygget.
Vi ønsker å implementere Interfacet Runnable,
som kun har metoden void run().


Eksempel på kode som kjører en tråd:
------------------------------------
class Runner implements Runnable {}

Runnable r = new Runner();
Thread t = new Thread(r);
t.start();
------------------------------------

Tenker på å ha en rekursiv metode som kjører tråder?
Med en int-variabel som bestemmer dybde? 
Much to think about

En tråd kan stoppe i et antall millisekunder.
ved hjelp av Thread.Sleep();

Vi kan avbryte en tråd ved Thread.interrupt.

Hvis Threaden blir interrupted mens den sover, så får vi en feilmelding som vi kan Catche
*/

/*

Samarbeid mellom tråder

Vi kan låse metoder som flere tråder skal bruke, slik at de ikke ødelegger for hverandre.
Tråd nr 2 vil da venter rett før låsen fram til Tråd nr 1 er ferdig.

Vi har en klasse som heter ReentrantLock. Vi gjør:

Reentrant lock = new Reentrantlock();

lock.lock();
-Viktige ting her
lock.unlock();

som regel gjør vi dette i en try-finally. (finally kjører uavhengig om try-en går gjennom eller ikke)


Vi kan også legge inn betingelser!!

Condition c = lock.newCondition();

# Thread 1
run()
{
    lock.lock();

    c.await(); // Dette låser opp låsen!!!! Når await stopper, låses vi igjen.
    print("Yahoo");

    lock.unlock();
}

# Thread 2
run()
{
    lock.lock();

    c.signal();

    lock.unlock();
}

*/