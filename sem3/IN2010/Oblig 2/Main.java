import java.io.File;                 
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;

class Main
{

    public static void main(String[] Args) 
    {
        String FileName = Args[0];

        // Defining algorithms we want to use
        SortingAlgorithm[] Algs = new SortingAlgorithm[] {
            //new Insertion(),
            //new Quick(),
            new Heap(),
            new Merge()};

        // Deciding which program to run
        if(Args[1].equals("1"))
        {
            // Writing each sorted array to a list, one per algorithm
            Case1(FileName,Algs);
        }
        else if(Args[1].equals("2"))
        {
            // Writing various data about num comparisons, num swaps, and runtime for all algorithms
            Case2(FileName,Algs);
        }
    }

    public static void Case1(String FileName,SortingAlgorithm[] Algs)
    {
        // Getting the unsorted array
        int[] A = GetFileArray(FileName);

        // Writing to file for each array
        for (SortingAlgorithm Alg : Algs)
        {
            WriteArrayToFile(FileName + "_" + Alg.GetName(), Alg.Sort(A.clone()));
        }
    }

    public static void Case2(String FileName,SortingAlgorithm[] Algs)
    {
        // Getting the unsorted array
        int[] A = GetFileArray(FileName);

        try
        {
            FileWriter Writer = new FileWriter(FileName.replace("Inputs","Outputs") + "_results.csv");

            // The method we have chosen is an incredibly bad way of formatting these strings, but it gives a suitable result for viewing quickly.
            String FormatString = "%1$13s";

            // Printing the first line
            String OutLine = String.format(FormatString,"n");
            for(SortingAlgorithm Alg : Algs)
            {
                OutLine += ", " + String.format(FormatString,Alg.GetName() + "_cmp");
                OutLine += ", " + String.format(FormatString,Alg.GetName() + "_swaps");
                OutLine += ", " + String.format(FormatString,Alg.GetName() + "_time");
            }
            Writer.write(OutLine + "\n");

            // Printing all remaining lines
            for(int i = 1; i < A.length; i += 1)
            {
                // Getting a copy of all elements in a up until element i
                int[] A_i = CopyArray(A,0,i);

                // Printing information for this copy
                OutLine = Integer.toString(i);
                for(SortingAlgorithm Alg : Algs)
                {
                    Alg.Sort(A_i.clone()); // actually sorting
                    OutLine += ", " + String.format(FormatString,Alg.NumComps);
                    OutLine += ", " + String.format(FormatString,Alg.NumSwaps);
                    OutLine += ", " + String.format(FormatString,Alg.NumMicroSeconds);
                }
                Writer.write(OutLine + "\n");
            }

            Writer.close();
        }
        catch(Exception e)
        {

        }
    }

    static int[] CopyArray(int[] A, int i0, int i1)
    {
        // Support method that simply copies an array, from index to index.

        int Diff = i1 - i0;
        int[] NewArray = new int[Diff];

        for(int i = i0; i < i1; i += 1)
        {
            NewArray[i - i0] = A[i];
        }

        return NewArray;
    }

    public static int[] GetFileArray(String FileName)
    {
        File InFile = new File(FileName);
        int[] Input = new int[0];

        try
        {   
            // First we use a scanner to find out how many elements that are in this file
            Scanner Scanner1 = new Scanner(InFile);
            int i = 0; 
            while(Scanner1.hasNextInt())
            {
                Scanner1.nextInt();
                i += 1;
            }
            Input = new int[i];
            Scanner1.close();

            // Then we use another scanner to go through and get our values
            Scanner Scanner2 = new Scanner(InFile);
            i = 0;
            while(Scanner2.hasNextInt())
            {
                Input[i] = Scanner2.nextInt();
                i += 1;
            }
            Scanner2.close();
        }  
        catch(Exception e)
        {
            System.out.println("Something is wrong");
        }

        return Input;
    }

    public static void WriteArrayToFile(String FileName,int[] A)
    {
        FileName = FileName.replace("Inputs","Outputs") + ".out";

        try
        {
            FileWriter Writer = new FileWriter(FileName);

            for (int i : A)
            {
                Writer.write(i + "\n");
            }

            Writer.close();
        }
        catch(Exception e)
        {
            System.out.println("Something is wrong");
        }
    }

}