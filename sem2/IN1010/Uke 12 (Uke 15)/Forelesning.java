public class Forelesning {
    
    public static void main(String[] args)
    {
    
        // Rekursjon!!!!
        // Putte funksjon inni fuksjon hahaaa

        /*
        Fakultet
        Vi ønsker å beregne n!
        Vi kan bruke en metode for dette!!!

        Metoden Factorial_1 regner ut n! uten rekursjon,
        Metoden Factorial_2 regner ut n! med rekursjon.
        */

        /*
        Hvorfor Rekursiv kode??

        Enkelte problemer er rekursive av natur.
        Vi skal se på Hanois tårn senere.
        Å finne løsninger med trær er også en god applikasjon.
        (Husk det du gjorde på code for drinks dette semesteret!!!)

        "The key to the design of a recursive method is to not think about it."
            -Big java
        */

        /*
        Reglene!!

        Et rekursivt kall må løse et enklere problem.
        Vi kan altså ikke spre oss utover.

        Vi må også sørge for at grunntilfellene (typ fib(1),fib(2))
        er løst ordentlig.
        */
    }

    public static long Factorial_1(int n)
    {
        long res = 1;
        for(int i = 1; i <= n; i += 1)
        {
            res *= i;
        }
        return(res);
    }

    public static long Factorial_2(int n)
    {
        long res = 1;

        if(n == 1)
        {
            res = 1;
        }
        else
        {
            res = n*Factorial_2(n-1);
        }

        return(res);
    }

    public static int Fibonacci(int n)
    {
        if(n <= 2)
        {
            return(1);
        }
        else
        {
            return(Fibonacci(n-1) + Fibonacci(n-2));
        }

    }

}
