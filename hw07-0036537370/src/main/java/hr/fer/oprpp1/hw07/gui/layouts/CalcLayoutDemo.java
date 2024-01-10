package hr.fer.oprpp1.hw07.gui.layouts;

import javax.swing.*;
import java.awt.*;

/**
 * Demo usage example of CalcLayout.
 */
public class CalcLayoutDemo extends JFrame {
    public CalcLayoutDemo() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initGUI();
        pack();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalcLayoutDemo().setVisible(true));
    }

    private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new CalcLayout(3));
        cp.add(l("tekst 1"), new RCPosition(1, 1));
        cp.add(l("tekst 2"), new RCPosition(2, 3));
        cp.add(l("test tekst"), new RCPosition(1, 6));
        cp.add(l("tekst stvarno najdulji"), new RCPosition(2, 7));
        cp.add(l("tekst kraći"), new RCPosition(4, 2));
        cp.add(l("tekst srednji"), new RCPosition(4, 5));
        cp.add(l("tekst"), new RCPosition(4, 7));
    }

    private JLabel l(String text) {
        JLabel l = new JLabel(text);
        l.setBackground(Color.YELLOW);
        l.setOpaque(true);
        return l;
    }

}
