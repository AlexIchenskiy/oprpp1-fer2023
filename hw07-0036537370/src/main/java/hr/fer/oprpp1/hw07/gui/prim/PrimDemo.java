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

        Box verticalBox = Box.createVerticalBox();

        verticalBox.add(new JScrollPane(new JList<>(primListModel)), BorderLayout.PAGE_START);
        verticalBox.add(new JScrollPane(new JList<>(primListModel)), BorderLayout.PAGE_END);

        JButton button = new JButton("Sljedeći!");

        cp.add(verticalBox, BorderLayout.CENTER);
        cp.add(button, BorderLayout.PAGE_END);

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
