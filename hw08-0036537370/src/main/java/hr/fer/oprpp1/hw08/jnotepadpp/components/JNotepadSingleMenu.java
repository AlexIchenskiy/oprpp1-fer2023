package hr.fer.oprpp1.hw08.jnotepadpp.components;

import javax.swing.*;

public class JNotepadSingleMenu extends JMenu {

    private final String nameKey;

    /**
     * Constructs a new <code>JMenu</code> with the supplied string
     * as its text.
     *
     * @param s the text for the menu label
     */
    public JNotepadSingleMenu(String s, String nameKey) {
        super(s);

        this.nameKey = nameKey;
    }

    public String getNameKey() {
        return nameKey;
    }

}
