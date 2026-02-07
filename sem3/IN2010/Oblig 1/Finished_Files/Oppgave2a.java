import java.util.ArrayList;
import java.util.Scanner;

public class Oppgave2a {

    public static void skrivBalansertRekkefølge(int[] array) {
        
        // Getting lenght of array, returning if empty
        int upper = array.length;
        if (upper == 0) {
            return;
        }

        // Finding the element in the middle of the array, and printing it
        int mid = (int) Math.floor(upper / 2.0);
        System.out.println(array[mid]);

        // Creating two new arrays, to store the remaining numbers
        int[] array1 = new int[mid];
        int[] array2 = new int[mid];

        // If the array has an even length, the lengths of the new arrays will be uneven
        if(upper % 2 == 0)
        {
            array2 = new int[mid-1];
        }

        // Filling the new arrays with halves from the original array
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

        // Repeating the process until all numbers have been written
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
