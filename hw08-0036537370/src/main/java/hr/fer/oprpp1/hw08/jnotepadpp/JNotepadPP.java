package hr.fer.oprpp1.hw08.jnotepadpp;

import hr.fer.oprpp1.hw08.jnotepadpp.actions.file.*;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.language.ChangeLanguageAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.sort.SortAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.tools.CopyTextAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.tools.CutTextAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.tools.PasteTextAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.tools.StatisticsAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.TextAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.tools.changecase.InvertcaseAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.tools.changecase.LowercaseAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.tools.changecase.UppercaseAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.unique.UniqueLinesAction;
import hr.fer.oprpp1.hw08.jnotepadpp.components.JNotepadMenu;
import hr.fer.oprpp1.hw08.jnotepadpp.components.JNotepadStatusbar;
import hr.fer.oprpp1.hw08.jnotepadpp.components.JNotepadToolbar;
import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.model.impl.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.model.impl.MultipleDocumentListenerImpl;
import hr.fer.oprpp1.hw08.jnotepadpp.util.JNotepadUserInteraction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Implementation of the custom text editor GUI.
 */
public class JNotepadPP extends JFrame {

    private final DefaultMultipleDocumentModel multipleDocumentModel;

    private Map<String, Object> actions = new TreeMap<>();

    private List<TextAction> changeCaseActions = new ArrayList<>();

    private List<TextAction> uniqueActions = new ArrayList<>();

    private List<TextAction> sortActions = new ArrayList<>();

    private final String title = "JNotepad++";

    private JNotepadStatusbar statusbar;

    private final FormLocalizationProvider provider;

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
        this.multipleDocumentModel = new DefaultMultipleDocumentModel(this);

        this.provider = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);

        this.initActions();
        this.initGUI();
        this.initListeners();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleClose();
            }
        });
    }

    private void initActions() {
        this.actions = new TreeMap<>();
        this.changeCaseActions = new ArrayList<>();
        this.uniqueActions = new ArrayList<>();
        this.sortActions = new ArrayList<>();

        JTextArea textArea = this.multipleDocumentModel.getCurrentDocument() == null ?
                null : this.multipleDocumentModel.getCurrentDocument().getTextComponent();

        this.actions.put("file", List.of(
                new CreateFileAction(this.multipleDocumentModel, "control N", KeyEvent.VK_N, "create_file",
                        this.provider),
                new OpenFileAction(this.multipleDocumentModel, "control O", KeyEvent.VK_O, "open_file",
                        this.provider),
                new SaveFileAction(this.multipleDocumentModel, "control S", KeyEvent.VK_S, "save_file",
                        this.provider),
                new SaveFileAsAction(this.multipleDocumentModel, "control A", KeyEvent.VK_A, "save_file_as",
                        this.provider),
                new CloseFileAction(this.multipleDocumentModel, "control W", KeyEvent.VK_W, "close_file",
                        this.provider)
        ));

        this.changeCaseActions.addAll(List.of(
                new UppercaseAction(this.multipleDocumentModel, "control U", KeyEvent.VK_U,
                        "to_uppercase", this.provider, textArea),
                new LowercaseAction(this.multipleDocumentModel, "control L", KeyEvent.VK_L,
                        "to_lowercase", this.provider, textArea),
                new InvertcaseAction(this.multipleDocumentModel, "control K", KeyEvent.VK_K,
                        "invert_case", this.provider, textArea)
        ));

        this.actions.put("tools", List.of(
                new CopyTextAction(this.multipleDocumentModel, "control C", KeyEvent.VK_C,
                        "copy_text", this.provider),
                new PasteTextAction(this.multipleDocumentModel, "control V", KeyEvent.VK_V,
                        "paste_text", this.provider),
                new CutTextAction(this.multipleDocumentModel, "control X", KeyEvent.VK_X,
                        "cut_text", this.provider),
                new StatisticsAction(this.multipleDocumentModel, "control I", KeyEvent.VK_I,
                        "statistics", this.provider),
                Map.of("case_change", this.changeCaseActions)
        ));

        uniqueActions.addAll(List.of(
                new UniqueLinesAction(this.multipleDocumentModel, "control P", KeyEvent.VK_P,
                        "unique_lines", this.provider, textArea)
        ));

        this.actions.put("unique", uniqueActions);

        sortActions.addAll(List.of(
                new SortAction(this.multipleDocumentModel, "control N",
                        KeyEvent.VK_N, "lines_ascending", this.provider, true),
                new SortAction(this.multipleDocumentModel, "control M",
                        KeyEvent.VK_N, "lines_descending", this.provider, false)
        ));

        this.actions.put("sort", sortActions);

        this.actions.put("language", List.of(
                new ChangeLanguageAction(this.multipleDocumentModel, "", -1, null, this.provider,
                        "HR"),
                new ChangeLanguageAction(this.multipleDocumentModel, "", -1, null, this.provider,
                        "EN"),
                new ChangeLanguageAction(this.multipleDocumentModel, "", -1, null, this.provider,
                        "UA"),
                new ChangeLanguageAction(this.multipleDocumentModel, "", -1, null, this.provider,
                        "DE")
        ));
    }

    private void initGUI() {
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setLocation(0, 0);
        this.setSize(600, 600);
        this.setTitle(title);

        Container cp = this.getContentPane();

        cp.setLayout(new BorderLayout());
        cp.add(this.multipleDocumentModel, BorderLayout.CENTER);

        this.statusbar = new JNotepadStatusbar(this.provider);

        this.setJMenuBar(new JNotepadMenu(actions, this.provider));
        this.getContentPane().add(new JNotepadToolbar(actions), BorderLayout.PAGE_START);
        this.getContentPane().add(statusbar, BorderLayout.PAGE_END);
    }

    private void initListeners() {
        this.multipleDocumentModel.addMultipleDocumentListener(new MultipleDocumentListenerImpl(this));
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
                        "save_changes",
                        new String[]{"save", "no_save", "cancel"},
                        provider,
                        (model.getFilePath() == null ? "(unnamed)" : model.getFilePath().toString()) + "?"
                );

                switch (result) {
                    case JOptionPane.YES_OPTION:
                        if (model.getFilePath() == null) {
                            SaveFileAsAction.saveAsAction(this.multipleDocumentModel, model, provider);
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
            } else {
                this.multipleDocumentModel.closeDocument(model);
            }
        }
    }

    @Override
    public String getTitle() {
        return title;
    }

    public JNotepadStatusbar getStatusbar() {
        return statusbar;
    }

    public List<TextAction> getChangeCaseActions() {
        return changeCaseActions;
    }

    public List<TextAction> getUniqueActions() {
        return uniqueActions;
    }

    public List<TextAction> getSortActions() {
        return sortActions;
    }

    public DefaultMultipleDocumentModel getMultipleDocumentModel() {
        return multipleDocumentModel;
    }

    public static void main(String[] args) {
        System.setProperty("file.encoding", "UTF-8");
        SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));
    }

}
