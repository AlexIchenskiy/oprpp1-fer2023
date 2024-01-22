package hr.fer.oprpp1.hw08.jnotepadpp.actions.tools.changecase;

import hr.fer.oprpp1.hw08.jnotepadpp.actions.TextAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;

import javax.swing.text.JTextComponent;
import java.awt.event.ActionEvent;

public class InvertcaseAction extends TextAction {

    private JTextComponent textComponent;

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

    public void setTextComponent(JTextComponent textComponent) {
        this.textComponent = textComponent;
    }

}
