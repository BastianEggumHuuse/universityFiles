import java.io.File;                 
import java.io.FileNotFoundException;
import java.util.Scanner;

class Test
{
    public static void main(String[] Args)
    {
        File Directory = new File("Outputs");
        try
        {
            int i = 0;
            for(File f : Directory.listFiles())
            {
                String Number = f.getName().split("_")[1];
                if(Number.equals(Args[0]))
                {
                    i += 1;
                }
            }

            int[][] ArrayValues = new int[i][Integer.parseInt(Args[0])];

            i = 0;
            for(File f : Directory.listFiles())
            {
                String Number = f.getName().split("_")[1];
                if(Number.equals(Args[0]))
                {
                    int j = 0;
                    Scanner s = new Scanner(f.getAbsolutePath());
                    while(s.hasNextInt())
                    {
                        ArrayValues[i][j] = s.nextInt();
                        j += 1;
                    }
                    s.close();
                    i += 1;
                }
            }

            // Now we have files!!!

            double t = System.nanoTime();

            for(int l = 0; l < ArrayValues[0].length - 1; l += 1)
            {
                int ArrayValue = ArrayValues[0][l];
                for(int a = 1; a < ArrayValues.length; a += 1)
                {
                    if(ArrayValue != ArrayValues[a][l])
                    {
                        System.out.println("Equality error in line " + l);
                        return;
                    }
                }

                if(ArrayValue > ArrayValues[0][l + 1])
                {
                    System.out.println("Sorting error in line " + l);
                    return;
                }
            }

            // Everything is good!

            t = (System.nanoTime() - t)/1000;
            System.out.println("All good!! Tests completed after " + t + " milliseconds!");
        }
        catch (Exception e)
        {

        }

    }
}