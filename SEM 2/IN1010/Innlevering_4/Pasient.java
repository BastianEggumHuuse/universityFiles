class Pasient
{

    private final int id;
    private static int totalId = 0;

    private final String navn;
    private final String foedselsnr;
    private Resept[] resepter;

    Pasient(String _navn, String _foedselsnr)
    {
        navn = _navn;
        foedselsnr = _foedselsnr;
        resepter = new Resept[0];

        id = totalId;
        totalId += 1;
    }

    @Override
    public String toString()
    {
        return("Yahoo");
    }

}