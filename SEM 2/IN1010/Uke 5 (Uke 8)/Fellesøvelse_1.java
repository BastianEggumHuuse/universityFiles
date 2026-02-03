class Fellesøvelse_1
{

    int Num;
    String Text;

    public Fellesøvelse_1(int i)
    {
        Num = i;
    }

    public Fellesøvelse_1(int i, String s)
    {
        this(i); // Kaller på egen konstruktør!!
        Text = s;
    }

}