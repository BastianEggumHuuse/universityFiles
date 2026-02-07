import java.util.ArrayList;
import java.util.Scanner;

public class Oppgave2a {

    public static void skrivBalansertRekkefølge(int[] array) {
        
        int upper = array.length;

        if (upper == 0) {
            return;
        }

        int mid = (int) Math.floor(upper / 2.0);

        System.out.println(array[mid]);

        // Creating two new arrays, and writing them balanced

        int[] array1 = new int[mid];
        int[] array2 = new int[mid];

        // upper is an even number
        if(upper % 2 == 0)
        {
            array2 = new int[mid-1];
        }

        for(int i = 0; i < upper; i += 1)
        {
            if(i < mid)
            {
                array1[i] = array[i];
            }
            if(i > mid)
            {
                array2[i-mid-1] = array[i];
            }
        }

        skrivBalansertRekkefølge(array2);
        skrivBalansertRekkefølge(array1);
    }

    public static void main(String[] args) {
        int[] Numbers = new int[args.length];

        // Filling the ArrayList with command line arguments
        int i = 0;
        for(String arg : args)
        {
            Numbers[i] = Integer.valueOf(arg);
            i += 1;
        }

        
        new Oppgave2a().skrivBalansertRekkefølge(Numbers);
    }
}
