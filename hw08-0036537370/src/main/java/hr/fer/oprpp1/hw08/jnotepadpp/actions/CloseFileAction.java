package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;

import java.awt.event.ActionEvent;

public class CloseFileAction extends JNotepadAction {

    public CloseFileAction(MultipleDocumentModel model, String key, int keyEvent) {
        super(model, key, keyEvent);

        this.putValue(NAME, "Close file");
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.model.closeDocument(this.model.getCurrentDocument());
    }
}
