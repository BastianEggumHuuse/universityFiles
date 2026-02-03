import java.io.File;
import java.util.*;

class test
{
    public static void main(String[] args)
    {

        String name = "smittet";

        String filNavn = name + ".txt";
        String filNavn2 = "Data/" + name;

        Scanner scanner = null;
        try 
        {
            scanner = new Scanner(new File(filNavn));
        } catch (Exception e) {
            System.out.println("Kunne ikke lese fil.");
            System.out.println(e.getMessage());
            System.exit(1);
        }

        Scanner scanner2 = null;
        try 
        {
            scanner2 = new Scanner(new File(filNavn2));
        } catch (Exception e) {
            System.out.println("Kunne ikke lese fil.");
            System.out.println(e.getMessage());
            System.exit(1);
        }

        int Errors = 0;

        while(scanner.hasNextLine())
        {
            String s1 = scanner.nextLine();
            String s2 = scanner2.nextLine();

            if(s1.equals(s2) == false)
            {
                System.out.println("ERROR!!!" + s1 + "is not equal to" + s2 + "!!!");
                Errors += 1;
            }

            if(Errors >= 10)
            {
                break;
            }
        }

        System.out.println("Completed test with " + Errors + " errors.");
    }
}