class MainTest
{

    public static void main(String[] args)
    {
        Stabel<String> s = new Stabel();
        s.leggTil("Piss");
        s.leggTil("Hello");
        s.leggTil("Yahoo");

        s.Printer();
    }

}