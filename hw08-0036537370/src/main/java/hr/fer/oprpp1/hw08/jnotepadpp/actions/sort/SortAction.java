package hr.fer.oprpp1.hw08.jnotepadpp.actions.sort;

import hr.fer.oprpp1.hw08.jnotepadpp.actions.TextAction;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;

import javax.swing.text.JTextComponent;
import java.awt.event.ActionEvent;
import java.text.Collator;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

public class SortAction extends TextAction {

    private final boolean ascending;

    private JTextComponent textComponent;

    public SortAction(MultipleDocumentModel model, String key, int keyEvent, String name, boolean ascending) {
        super(model, key, keyEvent);

        this.ascending = ascending;

        this.putValue(NAME, name);
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
                String selectedText = this.textComponent.getSelectedText();
                String[] lines = selectedText.split("\\r?\\n");

                Collator collator = Collator.getInstance(Locale.getDefault());

                Arrays.sort(lines, collator);

                if (!this.ascending) {
                    Collections.reverse(Arrays.asList(lines));
                }

                StringBuilder resultText = new StringBuilder();
                for (String line : lines) {
                    resultText.append(line).append(System.lineSeparator());
                }

                this.textComponent.replaceSelection(resultText.toString());
            }
        }
    }

    @Override
    public void setTextComponent(JTextComponent textComponent) {
        this.textComponent = textComponent;
    }

}
