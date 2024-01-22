package hr.fer.oprpp1.hw08.jnotepadpp.actions.language;

import hr.fer.oprpp1.hw08.jnotepadpp.actions.JNotepadAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;

import java.awt.event.ActionEvent;

public class ChangeLanguageAction extends JNotepadAction {

    private final FormLocalizationProvider provider;

    private final String language;

    public ChangeLanguageAction(MultipleDocumentModel model, String key, int keyEvent,
                                String nameKey, FormLocalizationProvider provider, String name) {
        super(model, key, keyEvent, nameKey, provider);

        this.provider = provider;
        this.language = name;

        this.putValue(NAME, name);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.provider.getProvider().setLanguage(this.language.toLowerCase());
    }

}
