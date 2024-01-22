package hr.fer.oprpp1.hw08.jnotepadpp.actions.unique;

import hr.fer.oprpp1.hw08.jnotepadpp.actions.TextAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;

import javax.swing.text.JTextComponent;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.StringJoiner;

public class UniqueLinesAction extends TextAction {

    private JTextComponent textComponent;

    public UniqueLinesAction(MultipleDocumentModel model, String key, int keyEvent, String nameKey,
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
                String selectedText = this.textComponent.getSelectedText();
                String[] lines = selectedText.split("\\r?\\n");

                Set<String> uniqueLinesSet = new LinkedHashSet<>(Arrays.asList(lines));

                StringJoiner resultText = new StringJoiner(System.lineSeparator());
                for (String line : uniqueLinesSet) {
                    resultText.add(line);
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
