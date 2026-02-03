class MilitaerResept extends HvitResept
{
    public MilitaerResept(Legemiddel legemiddel, Lege utskrivendeLege, int pasientId)
    {
        super(legemiddel,utskrivendeLege,pasientId,3);
    }

    @Override
    public int prisAABetale()
    {
        return(0);
    }
}