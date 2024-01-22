package hr.fer.oprpp1.hw08.jnotepadpp.components;

import javax.swing.*;
import java.util.List;
import java.util.Map;

public class JNotepadMenu extends JMenuBar {

    public JNotepadMenu(Map<String, Object> actions) {
        for (Map.Entry<String, Object> entry : actions.entrySet()) {
            JMenu menu = new JMenu(entry.getKey());
            List<Object> menuContent = (List<Object>)entry.getValue();

            for (Object action : menuContent) {
                if (action instanceof Action) {
                    menu.add((Action)action);
                } else if (action instanceof Map) {
                    Map<String, List<Action>> map = (Map<String, List<Action>>) action;

                    for (Map.Entry<String, List<Action>> entry1 : map.entrySet()) {
                        JMenu subMenu = new JMenu(entry1.getKey());
                        for (Action actionItem : entry1.getValue()) {
                            subMenu.add(actionItem);
                        }

                        menu.add(subMenu);
                    }
                }
            }

            this.add(menu);
        }
    }

}
