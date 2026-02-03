class Forelesning_2
{

    public static void main(String[] Args)
    {

        // Du kan deklarere variabler på 2 måter.
        int Num1; // 1
        Num1 = 1;
        int Num2 = 10; // 2
        // I Java bruker vi Statisk typing, slik at dette vil og er en int.

        // Java har 8 primitive typer (Primitives). Disse er:
        // Boolean, byte, char, short, int, long, float, og double.
        // Disse er _Ikke_ Klasser, men primitive objekter.

        // I Java deklareres en array slik:
        Boolean[] ListOfBools = {true,false,true,true};
        // Du kan også lage en tom array med lengde n:
        int n = 10;
        Boolean[] ListOfBools2 = new Boolean[n];
        // Du aksesserer dem akkurat som i python
        ListOfBools2[0] = true;
        ListOfBools2[5] = true;

        // I Java kan du lage matrise-style arrays!!!
        int[][] matrix = new int[5][10]; // 5 x 10 matrise

        //pring
        //TestClass NewTest = new TestClass(15);
        //NewTest.Print();

        System.out.println("pring");

    }

}