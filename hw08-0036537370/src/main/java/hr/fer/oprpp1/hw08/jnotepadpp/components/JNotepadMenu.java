package hr.fer.oprpp1.hw08.jnotepadpp.components;

import javax.swing.*;
import java.util.List;
import java.util.Map;

public class JNotepadMenu extends JMenuBar {

    public JNotepadMenu(Map<String, List<Action>> actions) {
        for (Map.Entry<String, List<Action>> entry : actions.entrySet()) {
            this.initMenu(entry.getKey(), entry.getValue());
        }
    }

    private void initMenu(String title, List<Action> actions) {
        JMenu menu = new JMenu(title);

        this.add(menu);

        for (Action action : actions) {
            menu.add(action);
        }
    }

}
