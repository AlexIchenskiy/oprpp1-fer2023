package hr.fer.oprpp1.hw08.jnotepadpp.components;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Notepad menu bar.
 */
public class JNotepadMenu extends JMenuBar {

    /**
     * List of references to all the menus, used for localization.
     */
    private final List<JNotepadSingleMenu> menus = new ArrayList<>();

    /**
     * Localization provider.
     */
    private final ILocalizationProvider provider;

    /**
     * Creates a notepad menu bar with provided action
     * @param actions Map of menus and submenus of actions (title, list of actions/map for submenu)
     * @param provider
     */
    public JNotepadMenu(Map<String, Object> actions, ILocalizationProvider provider) {
        this.provider = provider;
        this.provider.addLocalizationListener(this::handleLanguageChange);

        for (Map.Entry<String, Object> entry : actions.entrySet()) {
            JNotepadSingleMenu menu = new JNotepadSingleMenu(this.provider.getString(entry.getKey()), entry.getKey());
            menus.add(menu);

            List<Object> menuContent = (List<Object>)entry.getValue();

            for (Object action : menuContent) {
                if (action instanceof Action) {
                    menu.add((Action)action);
                } else if (action instanceof Map) {
                    Map<String, List<Action>> map = (Map<String, List<Action>>) action;

                    for (Map.Entry<String, List<Action>> entry1 : map.entrySet()) {
                        JNotepadSingleMenu subMenu = new JNotepadSingleMenu(
                                this.provider.getString(entry1.getKey()), entry1.getKey()
                        );
                        for (Action actionItem : entry1.getValue()) {
                            subMenu.add(actionItem);
                        }

                        menu.add(subMenu);
                        menus.add(subMenu);
                    }
                }
            }

            this.add(menu);
        }
    }

    /**
     * Function for handling the language change.
     */
    private void handleLanguageChange() {
        this.menus.forEach(menu -> menu.setText(provider.getString(menu.getNameKey())));
    }

}
