package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;

import javax.swing.text.JTextComponent;

public abstract class TextAction extends JNotepadAction {

    public TextAction(MultipleDocumentModel model, String key, int keyEvent,
                      String nameKey, ILocalizationProvider provider) {
        super(model, key, keyEvent, nameKey, provider);
    }

    public abstract void setTextComponent(JTextComponent textComponent);

}
