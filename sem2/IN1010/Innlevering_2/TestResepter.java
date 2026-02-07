class TestResepter
{

    public static void main(String[] args)
    {

        Legemiddel v = new Vanlig("Stoffet",200,10);
        Legemiddel vane = new Vanedannende("pille",236,10,7);
        Legemiddel n = new Narkotisk("Meth",1000,10,3);

        Resept b = new BlaaResept(vane,null,0,10);
        Resept m = new MilitaerResept(n,null,0);
        Resept p = new PResept(v,null,0,0);

        TestReseptId(b,0);
        TestReseptId(m,1);
        TestReseptId(p,2);

        TestReseptPris(m,0);
        TestReseptPris(p,200-108);

        TestAABruke(m,true);
        TestAABruke(p,false);

        Tester.end();
    }

    public static void TestReseptId(Resept v, int e)
    {
        System.out.println("Tester ID...");
        Tester.test(v.hentId() == e);
    }

    public static void TestReseptPris(Resept v, int e)
    {
        System.out.println("Tester pris...");
        Tester.test(v.prisAABetale() == e);
    }

    public static void TestAABruke(Resept v, Boolean e)
    {
        System.out.println("Tester å bruke...");
        Tester.test(v.bruk() == e);
    }

    class Tester
    {

        static int testCount = 0;
        static int errors = 0;

        public static void test(Boolean b)
        {
            testCount += 1;

            if(b)
            {
                System.out.println("Test nr." + testCount + " completed with no errors!! \n");
            }
            else
            {
                System.out.println("Test nr." + testCount + " had an error... \n");
                errors += 1;
            }
        }

        public static void end()
        {
            System.out.println("Completed " + testCount + " tests with " + errors +" errors! \n");
        }
    }

}