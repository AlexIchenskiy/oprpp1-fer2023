package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Class representing a single action.
 */
public abstract class JNotepadAction extends AbstractAction {

    /**
     * Notepad logic model.
     */
    protected final MultipleDocumentModel model;

    /**
     * Localization provider.
     */
    protected final ILocalizationProvider provider;

    /**
     * Action localization string key.
     */
    protected String nameKey;

    /**
     * Creates a single action instance.
     * @param model Logic model
     * @param key Keyboard key ("control S", for example)
     * @param keyEvent Key event for mnemonic usage
     * @param nameKey Localization string key
     * @param provider Localization provider
     */
    public JNotepadAction(MultipleDocumentModel model, String key, int keyEvent,
                          String nameKey, ILocalizationProvider provider) {
        this.model = model;

        this.nameKey = nameKey;
        this.provider = provider;
        this.provider.addLocalizationListener(this::handleLanguageChange);
        this.handleLanguageChange();

        this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(key));
        this.putValue(Action.MNEMONIC_KEY, keyEvent);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public abstract void actionPerformed(ActionEvent e);

    /**
     * Handler function for the action language change.
     */
    private void handleLanguageChange() {
        if (this.nameKey != null) {
            this.putValue(NAME, this.provider.getString(this.nameKey));
        }
    }

}
