package hr.fer.oprpp1.hw08.jnotepadpp.components;

import javax.swing.*;
import java.util.List;
import java.util.Map;

public class JNotepadToolbar extends JToolBar {

    /**
     * Creates a new tool bar; orientation defaults to <code>HORIZONTAL</code>.
     */
    public JNotepadToolbar(Map<String, List<Action>> actions) {
        this.setFloatable(true);

        int counter = 0;
        for (Map.Entry<String, List<Action>> entry : actions.entrySet()) {
            this.initSection(entry.getValue());
            if (counter < actions.size() - 1) {
                this.addSeparator();
            }
            counter++;
        }
    }

    private void initSection(List<Action> actions) {
        for (Action action : actions) {
            this.add(action);
        }
    }
}
