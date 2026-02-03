import java.io.File;         // Import the File class
import java.io.FileWriter;   // Import the FileWriter class
import java.util.Scanner;    // Import the Scanner class to read text files

class A1
{
    public static void main(String[] args) {

        if(args.length == 0)
        {
            System.out.println("No arguments inserted!!");
            return;
        }

        try {

            String InputPath = args[0];
            File   data      = new File(InputPath);

            // Reading
            Scanner reader = new Scanner(data);

            // Initializing a new string array with the length of the amount of commands
            String[] commands = new String[reader.nextInt() + 1];
            int i = 0;

            // Collecting all commands;
            while (reader.hasNextLine()) 
            {
                commands[i] = reader.nextLine();
                i += 1;
            }

            // Finished reading
            reader.close();

            // Writing
            String FilePathOut = "1A_" + data.getName().replace("input", "output");
            File outFile = new File(FilePathOut);
            FileWriter Writer = new FileWriter(outFile);  

            // Creating our set
            BinarySearchTree set = new BinarySearchTree();

            for(String command : commands)
            {
                String[] items = command.split(" ");

                if(items[0].equals("insert"))
                {
                    set.insert(Integer.parseInt(items[1]));
                }
                else if(items[0].equals("contains"))
                {
                    Boolean truth = set.contains(Integer.parseInt(items[1]));
                    Writer.write(truth.toString() + "\n");
                }
                else if(items[0].equals("remove"))
                {
                    set.remove(Integer.parseInt(items[1]));
                }
                else if(items[0].equals("size"))
                {
                    int value = set.size();
                    Writer.write(Integer.toString(value) + "\n");
                }
            }

            System.out.println("Finished with file: " + data.getName());
            System.out.println("Final tree height : " + set.height() + "\n");

            // Finished writing
            Writer.close();
        } 
        catch (Exception e) {

            System.out.println(e);

        }

    }
}