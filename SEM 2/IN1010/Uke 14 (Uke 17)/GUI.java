// Neccesary GUI imports
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// Ok we create a class now
class GUI
{
    public static void main(String[] args)
    {
        // GUI!!!!!
        try
        {
            // Setting universal Look and Feel
            UIManager.setLookAndFeel(
                UIManager.getCrossPlatformLookAndFeelClassName()
            );
        }
        catch(Exception e)
        {
            System.out.println("Can't do that boss. [LookAndFeel]");
            System.exit(1);
        }


        // Creating a window with name :)
        JFrame vindu = new JFrame(":)");
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Creating essentially a "canvas" to put things on.
        // Window is empty, remember.
        JPanel panel = new JPanel();

        // Adding said panel to window:
        vindu.add(panel);

        // "Packing" the window. We have to do this i guess.
        vindu.pack();

        // Moving out window to the center.
        vindu.setLocationRelativeTo(null);

        // Making our window visible
        vindu.setVisible(true);
    }
}