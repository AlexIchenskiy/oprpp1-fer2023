package hr.fer.oprpp1.hw08.jnotepadpp.actions.tools.changecase;

import hr.fer.oprpp1.hw08.jnotepadpp.actions.TextAction;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;

import javax.swing.text.JTextComponent;
import java.awt.event.ActionEvent;

public class LowercaseAction extends TextAction {

    private JTextComponent textComponent;

    public LowercaseAction(MultipleDocumentModel model, String key, int keyEvent, JTextComponent textComponent) {
        super(model, key, keyEvent);

        this.textComponent = textComponent;

        this.putValue(NAME, "To lowercase");
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
                textComponent.replaceSelection(selectedText.toLowerCase());
            }
        }
    }

    @Override
    public void setTextComponent(JTextComponent textComponent) {
        this.textComponent = textComponent;
    }

}
