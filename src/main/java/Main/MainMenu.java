package Main;

import javax.swing.*;

public class MainMenu extends JPanel {

    MainClass main;

    public JButton color1 = new JButton();
    public JButton color2 = new JButton();
    public JButton color3 = new JButton();

    public MainMenu() {

        //set main menu options
        setSize(300, 50);
        setLocation(10, 320);
        setOpaque(false);

        //set button sizes
        color1.setSize(25, 25);
        color2.setSize(25, 25);
        color3.setSize(25, 25);

        //set button positions
        color1.setLocation(15, 250);
        color3.setLocation(35, 250);
        color2.setLocation(65, 250);

        color1.setBackground(Colors.DEEP_BLUE);
        color2.setBackground(Colors.BLOOD_RED);
        color3.setBackground(Colors.CUTE_PINK);

        color1.setName("DEEP_BLUE");
        color2.setName("BLOOD_RED");
        color3.setName("CUTE_PINK");

        //add buttons to jPanel
        add(color1);
        add(color2);
        add(color3);

        color1.addActionListener(new ButtonClick(main));
        color2.addActionListener(new ButtonClick(main));
        color3.addActionListener(new ButtonClick(main));

    }

}
