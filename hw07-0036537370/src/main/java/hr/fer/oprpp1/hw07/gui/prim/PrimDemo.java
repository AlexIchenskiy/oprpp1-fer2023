package hr.fer.oprpp1.hw07.gui.prim;

import javax.swing.*;
import java.awt.*;

/**
 * Prime numbers GUI component.
 */
public class PrimDemo extends JFrame {

    /**
     * Creates a prime numbers GUI component.
     */
    public PrimDemo() {
        super();
        this.setTitle("PrimDemo");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.initGUI();
        this.pack();
        this.setVisible(true);
    }

    /**
     * Initializes a prime number lists GUI.
     */
    private void initGUI() {
        Container cp = this.getContentPane();
        this.setLayout(new BorderLayout());

        PrimListModel primListModel = new PrimListModel();

        JPanel panel = new JPanel(new BorderLayout());

        panel.add(new JScrollPane(new JList<>(primListModel)), BorderLayout.NORTH);
        panel.add(new JScrollPane(new JList<>(primListModel)), BorderLayout.SOUTH);

        JButton button = new JButton("Sljedeći!");

        cp.add(panel, BorderLayout.NORTH);
        cp.add(button, BorderLayout.SOUTH);

        button.addActionListener(e -> primListModel.next());
    }

    /**
     * Main function for prime numbers lists GUI initialization.
     * @param args No arguments needed
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(PrimDemo::new);
    }

}
