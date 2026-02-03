abstract class Resept
{

    private final int id;
    private static int totalId = 0;

    private final Legemiddel legemiddel;
    private final Lege utskrivendeLege;
    private final int pasientId;
    private int reit;

    Resept(Legemiddel _legemiddel, Lege _utskrivendeLege, int _pasientId, int _reit)
    {
        legemiddel = _legemiddel;
        utskrivendeLege = _utskrivendeLege;
        pasientId = _pasientId;
        reit = _reit;

        id = totalId;
        totalId += 1;
    }

    // Metoder som henter ting
    public int hentId()
    {
        return(id);
    }

    public Legemiddel hentLegemiddel()
    {
        return(legemiddel);
    }

    public Lege hentLege()
    {
        return(utskrivendeLege);
    }

    public int hentPasientId()
    {
        return(pasientId);
    }

    public int hentReit()
    {
        return(reit);
    }

    // Metoder som gjør ting
    public boolean bruk()
    {
        if(reit > 0)
        {
            reit -= 1;
            return(true);
        }

        return(false);
    }

    abstract public String farge();

    abstract public int prisAABetale();

    @Override
    public String toString()
    {
        return("ID : " + id + ", Lege : " + utskrivendeLege + ", PasientId : " + pasientId + ", reit : " + reit + "\n");
    }

}