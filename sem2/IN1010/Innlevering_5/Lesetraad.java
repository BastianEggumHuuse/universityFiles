import java.util.concurrent.CountDownLatch;

class Lesetraad implements Runnable
{
    private String filNavn;
    private Monitor monitor;
    CountDownLatch leseSignal;

    public Lesetraad(String filNavn_, Monitor monitor_,CountDownLatch leseSignal_)
    {
        filNavn = filNavn_;
        monitor = monitor_;
        leseSignal = leseSignal_;
    }

    @Override
    public void run()
    {
        // Bruker les()-metoden
        Frekvenstabell f = Subsekvensregister.les(filNavn);

        //Legger inn i monitor
        monitor.settInn(f);
        leseSignal.countDown(); //Teller ned slik at vi til slutt starter flettingen
    }
}