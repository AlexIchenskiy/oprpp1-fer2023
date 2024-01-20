package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public abstract class JNotepadAction extends AbstractAction {

    protected final MultipleDocumentModel model;

    public JNotepadAction(MultipleDocumentModel model, String key, int keyEvent) {
        this.model = model;

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

}
