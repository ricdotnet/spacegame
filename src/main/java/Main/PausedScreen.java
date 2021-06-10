package Main;

import javax.swing.*;
import java.awt.*;

public class PausedScreen extends JPanel {

    public PausedScreen() {
        init();
    }

    private void init() {
//        setBackground(Colors.BLOOD_RED);
        setPreferredSize(new Dimension(350, 350));
        setSize(350, 350);
        setVisible(false);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Colors.BLOOD_RED);

        g.drawRect(0, 0, 300, 300);
        g.fillRect(0, 0, 300, 300);
    }

}
