package hr.fer.oprpp1.hw08.jnotepadpp.actions.tools.changecase;

import hr.fer.oprpp1.hw08.jnotepadpp.actions.TextAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;

import javax.swing.text.JTextComponent;
import java.awt.event.ActionEvent;

/**
 * Action for the selected text case inversion.
 */
public class InvertcaseAction extends TextAction {

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
    public InvertcaseAction(MultipleDocumentModel model, String key, int keyEvent, String nameKey,
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
        if (this.textComponent != null) {
            int start = this.textComponent.getSelectionStart();
            int end = this.textComponent.getSelectionEnd();

            if (start != end) {
                String selectedText = textComponent.getSelectedText();
                StringBuilder invertedText = new StringBuilder(selectedText.length());

                for (char c : selectedText.toCharArray()) {
                    if (Character.isUpperCase(c)) {
                        invertedText.append(Character.toLowerCase(c));
                    } else if (Character.isLowerCase(c)) {
                        invertedText.append(Character.toUpperCase(c));
                    } else {
                        invertedText.append(c);
                    }
                }

                textComponent.replaceSelection(invertedText.toString());
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
