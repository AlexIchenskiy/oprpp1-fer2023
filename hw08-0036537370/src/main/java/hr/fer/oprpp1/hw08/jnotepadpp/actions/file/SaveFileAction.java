package hr.fer.oprpp1.hw08.jnotepadpp.actions.file;

import hr.fer.oprpp1.hw08.jnotepadpp.actions.JNotepadAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;

import java.awt.event.ActionEvent;

/**
 * Action for saving file.
 */
public class SaveFileAction extends JNotepadAction {

    /**
     * Creates a single action instance.
     * @param model Logic model
     * @param key Keyboard key ("control S", for example)
     * @param keyEvent Key event for mnemonic usage
     * @param nameKey Localization string key
     * @param provider Localization provider
     */
    public SaveFileAction(MultipleDocumentModel model, String key, int keyEvent,
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
        SingleDocumentModel current = this.model.getCurrentDocument();

        if (current != null && current.getFilePath() != null) {
            this.model.saveDocument(current, current.getFilePath());
            return;
        }

        SaveFileAsAction.saveAsAction(this.model, current, this.provider);
    }

}
