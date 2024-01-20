package hr.fer.oprpp1.hw08.jnotepadpp.components;

import javax.swing.*;
import java.util.List;

public class JNotepadMenu extends JMenuBar {

    public JNotepadMenu(List<Action> fileActions, List<Action> toolsActions) {
        this.initMenu("File", fileActions);
        this.initMenu("Tools", toolsActions);
    }

    private void initMenu(String title, List<Action> actions) {
        JMenu menu = new JMenu(title);

        this.add(menu);

        for (Action action : actions) {
            menu.add(action);
        }
    }

}
