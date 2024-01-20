package hr.fer.oprpp1.hw08.jnotepadpp.components;

import javax.swing.*;
import java.awt.*;

public class JNotepadTextArea extends JPanel {

    private final JTextArea textArea;

    public JNotepadTextArea(JTextArea textArea) {
        this.textArea = textArea;

        this.initGUI();
    }

    private void initGUI() {
        setLayout(new BorderLayout());
        add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

    public JTextArea getTextArea() {
        return this.textArea;
    }

}

