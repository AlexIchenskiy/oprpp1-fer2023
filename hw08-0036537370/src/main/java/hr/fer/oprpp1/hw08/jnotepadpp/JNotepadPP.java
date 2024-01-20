package hr.fer.oprpp1.hw08.jnotepadpp;

import hr.fer.oprpp1.hw08.jnotepadpp.actions.*;
import hr.fer.oprpp1.hw08.jnotepadpp.components.JNotepadMenu;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.model.impl.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.util.JNotepadUserInteraction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

/**
 * Implementation of the custom text editor GUI.
 */
public class JNotepadPP extends JFrame {

    private final DefaultMultipleDocumentModel multipleDocumentModel;

    private List<Action> fileActions;

    private List<Action> toolsActions;

    /**
     * Constructs a new frame that is initially invisible.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * @throws HeadlessException if GraphicsEnvironment.isHeadless()
     *                           returns true.
     * @see GraphicsEnvironment#isHeadless
     * @see Component#setSize
     * @see Component#setVisible
     * @see JComponent#getDefaultLocale
     */
    public JNotepadPP() throws HeadlessException {
        this.multipleDocumentModel = new DefaultMultipleDocumentModel();

        this.initActions();
        this.initGUI();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleClose();
            }
        });
    }

    private void initActions() {
        this.fileActions = List.of(
                new CreateFileAction(this.multipleDocumentModel, "control N", KeyEvent.VK_N),
                new OpenFileAction(this.multipleDocumentModel, "control O", KeyEvent.VK_O),
                new SaveFileAction(this.multipleDocumentModel, "control S", KeyEvent.VK_S),
                new SaveFileAsAction(this.multipleDocumentModel, "control A", KeyEvent.VK_A),
                new CloseFileAction(this.multipleDocumentModel, "control W", KeyEvent.VK_W)
        );

        this.toolsActions = List.of(
                new CopyTextAction(this.multipleDocumentModel, "control C", KeyEvent.VK_C),
                new PasteTextAction(this.multipleDocumentModel, "control V", KeyEvent.VK_V),
                new CutTextAction(this.multipleDocumentModel, "control X", KeyEvent.VK_X),
                new StatisticsAction(this.multipleDocumentModel, "control I", KeyEvent.VK_I)
        );
    }

    private void initGUI() {
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setLocation(0, 0);
        this.setSize(600, 600);
        this.setTitle("JNotepad++");

        Container cp = this.getContentPane();

        cp.setLayout(new BorderLayout());
        cp.add(this.multipleDocumentModel, BorderLayout.CENTER);

        this.setJMenuBar(new JNotepadMenu(fileActions, toolsActions));
    }

    private void handleClose() {
        while (true) {
            SingleDocumentModel model = this.multipleDocumentModel.getCurrentDocument();

            if (model == null) {
                this.dispose();
                return;
            }

            if (model.isModified()) {
                int result = JNotepadUserInteraction.optionConfirmationWindow(
                        "Do you want to save changes to " +
                                (model.getFilePath() == null ? "(unnamed)" : model.getFilePath().toString()) + "?",
                        new String[]{"Save", "Don't save", "Cancel"}
                );

                switch (result) {
                    case JOptionPane.YES_OPTION:
                        if (model.getFilePath() == null) {
                            SaveFileAsAction.saveAsAction(this.multipleDocumentModel, model);
                        } else {
                            this.multipleDocumentModel.saveDocument(model, model.getFilePath());
                        }

                        this.multipleDocumentModel.closeDocument(model);
                        continue;
                    case JOptionPane.NO_OPTION:
                        this.multipleDocumentModel.closeDocument(model);
                        continue;
                    case JOptionPane.CANCEL_OPTION:
                        return;
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));
    }

}
