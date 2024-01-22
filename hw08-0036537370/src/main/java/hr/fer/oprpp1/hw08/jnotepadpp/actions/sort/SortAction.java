package hr.fer.oprpp1.hw08.jnotepadpp.actions.sort;

import hr.fer.oprpp1.hw08.jnotepadpp.actions.TextAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;

import javax.swing.text.JTextComponent;
import java.awt.event.ActionEvent;
import java.text.Collator;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

/**
 * Action for sorting the selected lines.
 */
public class SortAction extends TextAction {

    /**
     * Boolean representing whether the sorting function is ascending
     */
    private final boolean ascending;

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
     * @param ascending Boolean representing whether the sorting function is ascending
     */
    public SortAction(MultipleDocumentModel model, String key, int keyEvent,
                      String nameKey, ILocalizationProvider provider, boolean ascending) {
        super(model, key, keyEvent, nameKey, provider);

        this.ascending = ascending;
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

    /**
     * Setter for the text component.
     * @param textComponent Text component to perform action on
     */
    @Override
    public void setTextComponent(JTextComponent textComponent) {
        this.textComponent = textComponent;
    }

}
