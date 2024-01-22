package hr.fer.oprpp1.hw08.jnotepadpp.actions.tools.changecase;

import hr.fer.oprpp1.hw08.jnotepadpp.actions.TextAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;

import javax.swing.text.JTextComponent;
import java.awt.event.ActionEvent;

/**
 * Action for setting selected text to the uppercase.
 */
public class UppercaseAction extends TextAction {

    /**
     * A text component to perform action on.
     */
    private JTextComponent textComponent;

    /**
     * Creates a single action instance.
     * @param model Logic model
     * @param key Keyboard key ("control S", for example)
     * @param keyEvent Key event for mnemonic usage
     * @param nameKey Localization string key
     * @param provider Localization provider
     */
    public UppercaseAction(MultipleDocumentModel model, String key, int keyEvent, String nameKey,
                             ILocalizationProvider provider, JTextComponent textComponent) {
        super(model, key, keyEvent, nameKey, provider);
        this.textComponent = textComponent;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (textComponent != null) {
            int start = textComponent.getSelectionStart();
            int end = textComponent.getSelectionEnd();

            if (start != end) {
                String selectedText = textComponent.getSelectedText();
                textComponent.replaceSelection(selectedText.toUpperCase());
            }
        }
    }

    /**
     * Setter for the text component.
     * @param textComponent Text component to perform action on
     */
    @Override
    public void setTextComponent(JTextComponent textComponent) {
        this.textComponent = textComponent;
    }

}
