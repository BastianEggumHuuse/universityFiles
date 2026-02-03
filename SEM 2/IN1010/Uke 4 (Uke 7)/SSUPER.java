class SSUPER
{
    public void Printer()
    {
        System.out.println("Yahoo");
    }

    // Working with a Constructor.
    public SSUPER(int i)
    {
        System.out.println(i);
    }
}

class SUPER extends SSUPER
{
    public void Printer(int i)
    {
        System.out.println("Byhaoo");
    }

    public SUPER(int i, String s)
    {
        super(i); // Calling the constructor of the superclass.
        System.out.println(s);
    }
}

class SUB extends SUPER
{
    public void Printer(double d)
    { 
        Printer();
    }

    public SUB(int i, String s)
    {
        super(i,s);
    }
}

