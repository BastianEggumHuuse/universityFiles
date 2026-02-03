class Lege
{
    private final String navn;

    public Lege(String _navn)
    {
        navn = _navn;
    }

    public String hentNavn()
    {
        return(navn);
    }

    @Override
    public String toString()
    {
        return("Navn : " + navn + "\n");
    }
}