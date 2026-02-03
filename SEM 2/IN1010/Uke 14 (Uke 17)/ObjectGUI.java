// Neccesary GUI imports
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class ObjectGUI
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

        JFrame frame = new JFrame("Object_GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);


        // Getting user name
        String Name = System.getProperty("user.name");
        // Creating a text label
        JLabel Hello = new JLabel("Hello " + Name);
        panel.add(Hello);


        // Creating exit button
        JButton Exit = new JButton("Exit");
        // We're doing events now!!!
        // We require an ActionListener to listen for events. 
        // An object of this class is connected to the JButton's pressed event.
        class ExitListener implements ActionListener
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                System.out.println("Window terminated.");
                System.exit(0);
            }
        }
        // Adding a new ExitListener object to our Exit Button's event.
        Exit.addActionListener(new ExitListener());
        panel.add(Exit);


        JButton Print1 = new JButton("Yahoo");
        JButton Print2 = new JButton("Wahoo");
        JButton Print3 = new JButton("Mario");
        class PrinterListener implements ActionListener
        {
            String Output = "";

            public PrinterListener(String output)
            {
                Output = output;
            }

            @Override
            public void actionPerformed(ActionEvent event)
            {
                System.out.println(Output);
            }
        }
        Print1.addActionListener(new PrinterListener("Yahoo"));
        Print2.addActionListener(new PrinterListener("Wahoo"));
        Print3.addActionListener(new PrinterListener("Paper Mario is a video game series and part of the Mario franchise, developed by Intelligent Systems and published by Nintendo. It combines elements from the role-playing, action-adventure, and puzzle genres. Players control a paper cutout version of Mario, usually with allies, on a quest to defeat the antagonist. The series consists of six games and one spin-off; the first, Paper Mario (2000), was released for the Nintendo 64, and the most recent, a 2024 remake of 2004's Paper Mario: The Thousand-Year Door, for the Nintendo Switch. The original Paper Mario began as a sequel to Super Mario RPG (1996), developed by Square for the Super Nintendo Entertainment System. Changes in development resulted in the game becoming a standalone game titled Mario Story in Japan. Although the early games in the series were well-received, Kensuke Tanabe wanted each one to have different genre and core gameplay elements. This led the series to slowly move genres from role-playing to action-adventure, though some role-playing elements are still present later in the series. The first two games in the series, Paper Mario and The Thousand-Year Door, received critical acclaim, and were praised for their story, characters, and unique gameplay. When Paper Mario: Sticker Star was released in 2012, the series began to receive many complaints about its change in genre, removal of original fictional races, and less unique character designs, but continued to garner praise for its writing, characters, music, and enhanced paper-inspired visuals. Super Paper Mario is the best-selling game in the series, with 4.3 million sales as of 2019. The series has collectively sold 12.54 million copies. Several Paper Mario games were nominated for at least one award; The Thousand-Year Door won 'Role Playing Game of the Year' at the 2005 Interactive Achievement Awards, Super Paper Mario won 'Outstanding Role Playing Game' at the 12th Satellite Awards in 2007, and Sticker Star won 'Handheld Game of the Year' at the 16th Annual D.I.C.E. Awards in 2012. The Origami King was nominated for 3, the most at once for the series. The games, mainly the first two titles, have inspired various indie games including Bug Fables: The Everlasting Sapling. Numerous Paper Mario elements have also been included in the Super Smash Bros. series"));
        panel.add(Print1);
        panel.add(Print2);
        panel.add(Print3);


        //Yahoo


        // Standard stuff
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }   
}