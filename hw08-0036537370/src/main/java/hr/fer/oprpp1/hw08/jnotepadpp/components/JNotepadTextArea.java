package hr.fer.oprpp1.hw08.jnotepadpp.components;

import javax.swing.*;
import java.awt.*;

/**
 * Notepad text area.
 */
public class JNotepadTextArea extends JPanel {

    /**
     * Text area.
     */
    private final JTextArea textArea;

    /**
     * Creates a text area.
     * @param textArea Text area reference
     */
    public JNotepadTextArea(JTextArea textArea) {
        this.textArea = textArea;

        this.initGUI();
    }

    /**
     * Function for GUI initialization.
     */
    private void initGUI() {
        setLayout(new BorderLayout());
        add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

    /**
     * Getter for the text area.
     * @return Text area
     */
    public JTextArea getTextArea() {
        return this.textArea;
    }

}

