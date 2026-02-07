import java.io.File;         // Import the File class
import java.io.FileWriter;   // Import the FileWriter class
import java.util.Scanner;    // Import the Scanner class to read text files

class TestA1
{
    public static void main(String[] args) {
        
        try {

            String Path1 = "1A_output_100000";
            String Path2 = "ExpectedOutputs/output_100000";

            // Reading
            Scanner reader1 = new Scanner(new File(Path1));
            Scanner reader2 = new Scanner(new File(Path2));

            System.out.println("Reading file " + Path1 + "...");

            // Collecting all commands;
            int c = 0;
            while (reader1.hasNextLine()) 
            {
                String Value1 = reader1.nextLine();
                String Value2 = reader2.nextLine();

                if(Value1.equals(Value2) == false)
                {
                    System.out.println("Something is wrong in line " + c + "!!");
                    System.out.println(Value1 + " | " + Value2);
                    return;
                }
                c += 1;
            }

            // Finished reading
            reader1.close();
            reader1.close();

            System.out.println("\nGone through all files, no discrepancies");
        } 
        catch (Exception e) {

            System.out.println(e);

        }
        
    }
}