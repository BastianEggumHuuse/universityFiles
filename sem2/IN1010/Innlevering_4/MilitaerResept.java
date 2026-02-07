class MilitaerResept extends HvitResept
{
    public MilitaerResept(Legemiddel legemiddel, Lege utskrivendeLege,Pasient pasient)
    {
        super(legemiddel,utskrivendeLege,pasient,3);
    }

    @Override
    public int prisAABetale()
    {
        return(0);
    }
}