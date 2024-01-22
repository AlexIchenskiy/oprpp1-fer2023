package hr.fer.oprpp1.hw08.jnotepadpp.components;

import javax.swing.*;
import java.util.List;
import java.util.Map;

/**
 * Notepad toolbar.
 */
public class JNotepadToolbar extends JToolBar {

    /**
     * Creates a new toolbar; orientation defaults to <code>HORIZONTAL</code>.
     */
    public JNotepadToolbar(Map<String, Object> actions) {
        this.setFloatable(true);

        int counter = 0;
        for (Map.Entry<String, Object> entry : actions.entrySet()) {
            this.initSection(entry.getValue());
            if (counter < actions.size() - 1) {
                this.addSeparator();
            }
            counter++;
        }
    }

    /**
     * Function for single section initialization.
     * @param actions Map of actions and other maps (aka submenus) that are flattened into single toolbar
     */
    private void initSection(Object actions) {
        List<Object> actionList = (List<Object>) actions;

        for (Object action : actionList) {
            if (action instanceof Action) {
                this.add((Action) action);
            } else if (action instanceof Map) {
                for (Map.Entry<String, List<Action>> entry : ((Map<String, List<Action>>) action).entrySet()) {
                    for (Action action1 : entry.getValue()) {
                        this.add(action1);
                    }
                }
            }
        }
    }

}
