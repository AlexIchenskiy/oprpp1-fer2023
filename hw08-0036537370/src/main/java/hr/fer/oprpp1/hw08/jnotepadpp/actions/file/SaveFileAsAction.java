package hr.fer.oprpp1.hw08.jnotepadpp.actions.file;

import hr.fer.oprpp1.hw08.jnotepadpp.actions.JNotepadAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.util.JNotepadUserInteraction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Path;
import java.util.Locale;

public class SaveFileAsAction extends JNotepadAction {

    public SaveFileAsAction(MultipleDocumentModel model, String key, int keyEvent,
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
        SaveFileAsAction.saveAsAction(this.model, model.getCurrentDocument(), this.provider);
    }

    public static boolean saveAsAction(MultipleDocumentModel model, SingleDocumentModel current,
                                       ILocalizationProvider provider) {
        if (current != null) {
            JFileChooser fileChooser = new JFileChooser();

            fileChooser.setLocale(Locale.KOREA);

            fileChooser.setDialogTitle("Save as");

            int selection = fileChooser.showSaveDialog(null);

            if (selection == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                Path path = file.toPath();

                if (path.toFile().exists()) {
                    int result = JNotepadUserInteraction.warningConfirmationWindow(
                            "overwrite",
                            provider
                    );

                    if (result != JOptionPane.YES_OPTION) {
                        model.saveDocument(current, path);
                        return true;
                    }

                    return false;
                }

                model.saveDocument(current, path);
                return true;
            }
        }

        return false;
    }

}
