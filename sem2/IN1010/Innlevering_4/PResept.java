class PResept extends HvitResept
{
    public PResept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient,int reit)
    {
        super(legemiddel,utskrivendeLege,pasient,reit);
    }

    @Override
    public int prisAABetale()
    {
        int nyPris = hentLegemiddel().hentPris() - 108;

        if(nyPris >= 0)
        {
            return(nyPris);
        }

        return(0);
    }
}