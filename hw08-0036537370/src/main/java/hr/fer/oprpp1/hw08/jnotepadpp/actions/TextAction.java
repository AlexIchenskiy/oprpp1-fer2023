package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;

import javax.swing.text.JTextComponent;

public abstract class TextAction extends JNotepadAction {

    public TextAction(MultipleDocumentModel model, String key, int keyEvent) {
        super(model, key, keyEvent);
    }

    public abstract void setTextComponent(JTextComponent textComponent);

}
