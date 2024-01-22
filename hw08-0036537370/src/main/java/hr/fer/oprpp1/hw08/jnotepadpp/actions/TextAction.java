package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;

import javax.swing.text.JTextComponent;

/**
 * Class representing a single action that may need a text component.
 */
public abstract class TextAction extends JNotepadAction {

    /**
     * Creates a single action instance.
     * @param model Logic model
     * @param key Keyboard key ("control S", for example)
     * @param keyEvent Key event for mnemonic usage
     * @param nameKey Localization string key
     * @param provider Localization provider
     */
    public TextAction(MultipleDocumentModel model, String key, int keyEvent,
                      String nameKey, ILocalizationProvider provider) {
        super(model, key, keyEvent, nameKey, provider);
    }

    /**
     * Setter for the text component.
     * @param textComponent Text component to perform action on
     */
    public abstract void setTextComponent(JTextComponent textComponent);

}
