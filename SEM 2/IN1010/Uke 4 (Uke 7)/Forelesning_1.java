class Forelesning_1
{
    public static Box[] Boxes = new Box[3];

    public static void main(String[] Args)
    {
        Boxes[0] = new Box();
        Boxes[1] = new Box_A();
        Boxes[2] = new Box_S();

        // For-each Loop!!!
        for(Box b : Boxes)
        {
            // GetAmount() is different for all types of box!!!
            // Therefore, this will print different amounts depending on class.
            System.out.println(b.GetAmount());
        }

        //Polymorphic Methods don't care if you are using a superclass pointer!!
        //So:
        Box b = new Box_S();
        System.out.println(b.GetAmount());
        // will still print out 50!!

        SUB s = new SUB(1,"s");
        s.Printer(2.0);
    }
}