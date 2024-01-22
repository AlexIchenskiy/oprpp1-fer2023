package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public abstract class JNotepadAction extends AbstractAction {

    protected final MultipleDocumentModel model;

    protected final ILocalizationProvider provider;

    protected String nameKey;

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

    private void handleLanguageChange() {
        if (this.nameKey != null) {
            this.putValue(NAME, this.provider.getString(this.nameKey));
        }
    }

}
