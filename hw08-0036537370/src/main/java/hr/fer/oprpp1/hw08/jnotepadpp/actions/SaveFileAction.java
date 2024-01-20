package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;

import java.awt.event.ActionEvent;

public class SaveFileAction extends JNotepadAction {

    public SaveFileAction(MultipleDocumentModel model, String key, int keyEvent) {
        super(model, key, keyEvent);

        this.putValue(NAME, "Save file");
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

        SaveFileAsAction.saveAsAction(this.model, current);
    }

}
