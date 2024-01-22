package hr.fer.oprpp1.hw08.jnotepadpp.actions.language;

import hr.fer.oprpp1.hw08.jnotepadpp.actions.JNotepadAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;

import java.awt.event.ActionEvent;
import java.util.Locale;

/**
 * Action for language change.
 */
public class ChangeLanguageAction extends JNotepadAction {

    /**
     * Localization provider.
     */
    private final FormLocalizationProvider provider;

    /**
     * Language tag.
     */
    private final String language;

    /**
     * Creates a single action instance.
     * @param model Logic model
     * @param key Keyboard key ("control S", for example)
     * @param keyEvent Key event for mnemonic usage
     * @param nameKey Localization string key
     * @param provider Localization provider
     */
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
