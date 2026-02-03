class Spesialist extends Lege implements Godkjenningsfritak
{

    private String kontrollkode;

    public Spesialist(String _navn,String _kontrollkode)
    {
        super(_navn);
        kontrollkode = _kontrollkode;
    }

    public String hentKontrollkode()
    {
        return(kontrollkode);
    }

    @Override
    public String toString()
    {
        return("Navn : " + hentNavn() + " , Kode : " + kontrollkode + "\n");
    }
}