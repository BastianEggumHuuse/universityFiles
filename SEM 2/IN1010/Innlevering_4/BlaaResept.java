class BlaaResept extends Resept
{
    public BlaaResept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient, int reit)
    {
        super(legemiddel,utskrivendeLege,pasient,reit);
    }

    @Override
    public String farge()
    {
        return("Blaa");
    }

    @Override
    public int prisAABetale()
    {
        int nyPris = (int)Math.round(hentLegemiddel().hentPris() * 0.25);

        return(nyPris);
    }
}