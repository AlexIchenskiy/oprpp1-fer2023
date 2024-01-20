package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;

import java.awt.event.ActionEvent;

public class CreateFileAction extends JNotepadAction {

    public CreateFileAction(MultipleDocumentModel model, String key, int keyEvent) {
        super(model, key, keyEvent);

        this.putValue(NAME, "New file");
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.model.createNewDocument();
    }

}
