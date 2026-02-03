abstract class Legemiddel
{

    private int pris;
    public final String navn;
    public final double mengdeVirkestoff;
    
    public final int id;
    private static int totalId = 0;

    Legemiddel(String _navn, int _pris, double _mengdeVirkestoff)
    {
        navn = _navn;
        pris = _pris;
        mengdeVirkestoff = _mengdeVirkestoff;

        id = totalId;
        totalId += 1;
    }

    public int hentPris()
    {
        return(pris);
    }

    public void settNyPris(int nyPris)
    {
        pris = nyPris;
    }

    @Override
    public String toString()
    {
        return("ID : " + id + ", Navn : " + navn + ", Pris : " + pris + ", Mengde Virkestoff : " + mengdeVirkestoff);
    }

}