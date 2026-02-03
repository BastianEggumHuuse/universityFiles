class Flettetraad implements Runnable
{
    private Monitor monitor;

    public Flettetraad(Monitor monitor_)
    {
        monitor = monitor_;
    }

    @Override
    public void run()
    {
        while(true)
        {
            // Tar ut to frekvenstabeller
            Frekvenstabell[] f = monitor.taUtTo();

            // Dersom f er null, skal vi bryte ut av flettingen
            if(f == null)
            {
                break;
            }

            // Fletter sammen tabellene
            Frekvenstabell flettet = Frekvenstabell.flett(f[0],f[1]);

            // Setter inn i monitor
            monitor.settInn(flettet);
        }
    }
}