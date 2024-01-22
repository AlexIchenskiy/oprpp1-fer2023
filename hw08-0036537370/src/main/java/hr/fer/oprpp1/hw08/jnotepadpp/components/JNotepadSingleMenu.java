package hr.fer.oprpp1.hw08.jnotepadpp.components;

import javax.swing.*;

/**
 * Basic swing JMeni with added localization name key.
 */
public class JNotepadSingleMenu extends JMenu {

    /**
     * Localization name key.
     */
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

    /**
     * Getter for the name key.
     * @return Localization name key
     */
    public String getNameKey() {
        return nameKey;
    }

}
