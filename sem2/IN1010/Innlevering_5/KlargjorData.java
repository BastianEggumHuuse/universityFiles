import java.io.File;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.CountDownLatch;

class KlargjorData
{

    public static final int ANTALL_TRÅDER = 8;

    public static void main(String[] args)
    {
        
        String filNavn = args[0];
        File fil = new File(filNavn);
        String mappe = fil.getParent() + "/";

        Monitor smittet = new Monitor(ANTALL_TRÅDER,"smittet.txt");
        Monitor ikkeSmittet = new Monitor(ANTALL_TRÅDER,"ikke_smittet.txt");



        Scanner scanner = null;
        try 
        {
            scanner = new Scanner(new File(filNavn));
        } catch (Exception e) {
            System.out.println("Kunne ikke lese fil.");
            System.out.println(e.getMessage());
            System.exit(1);
        }

        int antallLestraader = 0;
        while(scanner.hasNextLine())
        {
            antallLestraader += 1;
            scanner.nextLine();
        }



        scanner = null;
        try 
        {
            scanner = new Scanner(new File(filNavn));
        } catch (Exception e) {
            System.out.println("Kunne ikke lese fil.");
            System.out.println(e.getMessage());
            System.exit(1);
        }

        CountDownLatch leseSignal = new CountDownLatch(antallLestraader);

        while(scanner.hasNextLine())
        {
            String[] strings = scanner.nextLine().split(",");
            String currentFilNavn = mappe + strings[0];
            String currentSmittet = strings[1];

            Runnable leser = null;
            if(currentSmittet.equals("True"))
            {
                leser = new Lesetraad(currentFilNavn,smittet,leseSignal);
            }
            else
            {
                leser = new Lesetraad(currentFilNavn,ikkeSmittet,leseSignal);
            }
            
            Thread traad = new Thread(leser);
            traad.start();
        }
        scanner.close();

        // Venter på at alle lesetraadene blir ferdige
        try
        {
            leseSignal.await();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

        

        for(int i = 0; i < ANTALL_TRÅDER; i += 1)
        {
            Runnable fletter1 = new Flettetraad(smittet);
            Runnable fletter2 = new Flettetraad(ikkeSmittet);

            Thread traad1 = new Thread(fletter1);
            Thread traad2 = new Thread(fletter2);

            traad1.start();
            traad2.start();
        }
    }
}