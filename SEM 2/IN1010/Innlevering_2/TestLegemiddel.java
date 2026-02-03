class TestLegemiddel 
{

    public static void main(String[] args)
    {

        Legemiddel v = new Vanlig("Stoffet",100,10);
        Legemiddel vane = new Vanedannende("pille",236,10,7);
        Legemiddel n = new Narkotisk("Meth",1000,10,3);

        Tester.test(v.id == 0);
        Tester.test(vane.id == 1);
        Tester.test(n.id == 2);

        Tester.test(vane.hentPris() == 236);

        System.out.println(n);

    }

}

class Tester
{

    static int testCount;

    public static void test(Boolean b)
    {
        if(b)
        {
            System.out.println("Test nr." + testCount + " completed with no errors!!");
        }
        else
        {
            System.out.println("Test nr." + testCount + " had an error...");
        }

        testCount += 1;
    }


}