class Box
{
    public int Amount = 0;

    public int GetAmount()
    {
        Amount = 0;
        return(Amount);
    }

    public void PrintName()
    {
        System.out.println("Base Box");
    }

    public void Open()
    {
        System.out.println("The box is opened...");
    }
}

class Box_A extends Box
{
    // This is an annotation, and signifies that we are going to override a method with the same signature.
    // This isn't neccesary, but is very nice for debugging and such (The compiler now knows that this is an overridden method).

    @Override 
    public int GetAmount()
    {
        Amount = 10;
        return(Amount);
    }

    public void PrintName()
    {
        System.out.println("Rank A Box");
    }

    // This is not overriding, this is overloading. It essentially just replaces the method with a new one.
    // Notice how this one has a parameter, unlike the original.
    // Also!!! You can't access this method using a Box pointer. It will just call the method Open() from Box instead.
    public void Open(String Key)
    {
        if(Key.equals("A"))
        {
            System.out.println("The box is opened...");
        }
    }
}

class Box_S extends Box
{
    @Override
    public int GetAmount()
    {
        Amount = 50;
        return(Amount);
    }

    public void PrintName()
    {
        super.PrintName(); // Runs the method from the superclass, printing "Base Box"

        System.out.println("Rank S Box!!!");
    }

    public void Open(String Key)
    {
        if(Key.equals("S"))
        {
            System.out.println("The box is opened...");
        }
    }
}