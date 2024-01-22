package hr.fer.oprpp1.hw08.jnotepadpp.actions.tools;

import hr.fer.oprpp1.hw08.jnotepadpp.actions.JNotepadAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.model.impl.DefaultSingleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.util.JNotepadUserInteraction;

import java.awt.event.ActionEvent;

public class StatisticsAction extends JNotepadAction {

    public StatisticsAction(MultipleDocumentModel model, String key, int keyEvent,
                          String nameKey, ILocalizationProvider provider) {
        super(model, key, keyEvent, nameKey, provider);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        DefaultSingleDocumentModel current = (DefaultSingleDocumentModel) this.model.getCurrentDocument();
        if (current != null) {
            JNotepadUserInteraction.infoWindow(
                    "stats_data",
                    this.provider,
                    current.getFilePath() == null ? "Document" :
                                    current.getFilePath().getFileName().toString(),
                            current.getNumberOfAllChars(), current.getNumberOfNonBlankChars(),
                            current.getNumberOfLines()
            );
        }
    }

}
