class Tester
{

    public static void main(String[] args)
    {
       IndeksertListe<String> l = new IndeksertListe<String>();

       l.leggTil("Hallo!");
       l.leggTil("Yaow");
       l.leggTil("Pille");
       l.leggTil("Pille 2");

       for(String s : l)
       {
            System.out.println(s);
       }
    }
}