import java.io.File;  // Import the File class
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class Badge
{

    String Name;
    String FileName;
    String Description;
    String Lore;
    int Slots;

    public Badge(String NewName,String NewDesc, String NewLore, int NewSlots)
    {
        Name = NewName;
        FileName = NewName + ".txt";
        Description = NewDesc;
        Lore = NewLore;
        Slots = NewSlots;
    }

    public Badge(String NewFileName)
    {
        //Reading from file instead of setting manually
        try 
        {
            File f = new File(NewFileName + ".txt");
            Scanner CountReader = new Scanner(f);
            int Count = 0;

            while(CountReader.hasNextLine())
            {
                String data = CountReader.nextLine();
                Count += 1;
            }
            CountReader.close();

            Scanner StatReader = new Scanner(f);
            String[] DataLines = new String[Count];
            Count = 0;

            while (StatReader.hasNextLine()) 
            {
                String data = StatReader.nextLine();
                String[] datas = data.split(";");
                DataLines[Count] = datas[1];
                Count ++;
            }
            StatReader.close();

            Name = DataLines[0];
            FileName = Name + ".txt";
            Description = DataLines[1];
            Lore = DataLines[2];
            Slots = Integer.parseInt(DataLines[3]);

        } 
        catch (FileNotFoundException e) 
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        catch (NumberFormatException e) 
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void Write()
    {
        try {

            File BadgeFile = new File(FileName);
            FileWriter Writer = new FileWriter(BadgeFile);

            Writer.write("Name;" + Name + "\n");
            Writer.write("Desc;" + Description + "\n");
            Writer.write("Lore;" + Lore + "\n");
            Writer.write("Slots;" + Slots + "\n");

            Writer.close();

            System.out.println("Succesfully wrote to " + Name + ".txt");
        } 
        catch (IOException e) 
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public void Run()
    {
        //Do nothing
        System.out.println(Slots);
    }

}