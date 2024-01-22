package hr.fer.oprpp1.hw08.jnotepadpp.actions.tools;

import hr.fer.oprpp1.hw08.jnotepadpp.actions.JNotepadAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.awt.event.ActionEvent;

/**
 * Action for copying text.
 */
public class CopyTextAction extends JNotepadAction {

    /**
     * Creates a single action instance.
     * @param model Logic model
     * @param key Keyboard key ("control S", for example)
     * @param keyEvent Key event for mnemonic usage
     * @param nameKey Localization string key
     * @param provider Localization provider
     */
    public CopyTextAction(MultipleDocumentModel model, String key, int keyEvent,
                          String nameKey, ILocalizationProvider provider) {
        super(model, key, keyEvent, nameKey, provider);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Action action = new DefaultEditorKit.CopyAction();
        action.actionPerformed(e);
    }
}
