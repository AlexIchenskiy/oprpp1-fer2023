package hr.fer.oprpp1.hw08.jnotepadpp.model.impl;

import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.components.JNotepadTextArea;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.util.JNotepadIcon;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.Utilities;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

    private final List<SingleDocumentModel> documents;

    private SingleDocumentModel document;

    private final List<MultipleDocumentListener> listeners;

    private final ImageIcon redSaveIcon;

    private final ImageIcon greenSaveIcon;

    private final JNotepadPP notepad;

    private boolean isSelected = false;

    /**
     * Creates an empty <code>TabbedPane</code> with a default
     * tab placement of <code>JTabbedPane.TOP</code>.
     *
     * @see #addTab
     */
    public DefaultMultipleDocumentModel(JNotepadPP notepad) {
        super();

        this.notepad = notepad;

        this.documents = new ArrayList<>();
        this.document = null;
        this.listeners = new ArrayList<>();

        this.redSaveIcon = JNotepadIcon.createSingleIcon("../../icons/save-red.png", this);
        this.greenSaveIcon = JNotepadIcon.createSingleIcon("../../icons/save-green.png", this);

        this.addChangeListener(e -> {
            try {
                SingleDocumentModel previousModel = this.getCurrentDocument();

                int index = this.getSelectedIndex();
                if (index < 0 || index >= this.documents.size()) return;
                this.document = this.documents.get(this.getSelectedIndex());

                this.document.getTextComponent().addCaretListener(new CaretListener() {
                    @Override
                    public void caretUpdate(CaretEvent e) {
                        handleCaretChange(e.getDot(), e.getMark());
                    }
                });

                this.notifyListeners(listener ->
                        listener.currentDocumentChanged(previousModel, this.getCurrentDocument()));
            } catch (ArrayIndexOutOfBoundsException ignored) {}
        });
    }

    @Override
    public JComponent getVisualComponent() {
        return this;
    }

    @Override
    public SingleDocumentModel createNewDocument() {
        SingleDocumentModel model = new DefaultSingleDocumentModel(null, "", this.notepad);
        SingleDocumentModel previousModel = this.getCurrentDocument();

        this.documents.add(model);
        this.addTab("(unnamed)", new JNotepadTextArea(model.getTextComponent()));

        int index = this.documents.size() - 1;
        this.setSelectedIndex(index);
        this.setToolTipTextAt(index, "(unnamed)");
        this.setIconAt(index, greenSaveIcon);
        this.handleCaretChange(-1, -1);

        this.notifyListeners(listener -> listener.currentDocumentChanged(previousModel, model));
        this.getCurrentDocument().addSingleDocumentListener(this.initDocumentListener());

        return model;
    }

    @Override
    public SingleDocumentModel getCurrentDocument() {
        return this.document;
    }

    @Override
    public SingleDocumentModel loadDocument(Path path) {
        SingleDocumentModel existingModel = findForPath(path);
        SingleDocumentModel previousModel = this.getCurrentDocument();

        if (existingModel != null) {
            int index = this.documents.indexOf(existingModel);
            this.setSelectedIndex(index);
            return existingModel;
        }

        try {
            byte[] bytes = Files.readAllBytes(path);
            String data = new String(bytes, StandardCharsets.UTF_8);

            DefaultSingleDocumentModel model = new DefaultSingleDocumentModel(path, data, this.notepad);

            this.documents.add(model);
            this.addTab(path.getFileName().toString(), new JNotepadTextArea(model.getTextComponent()));

            int index = this.documents.size() - 1;
            this.setSelectedIndex(index);
            this.setToolTipTextAt(index, path.getFileName().toString());
            this.setIconAt(index, greenSaveIcon);
            this.handleCaretChange(-1, -1);

            this.notifyListeners(listener -> listener.documentAdded(this.getCurrentDocument()));
            this.notifyListeners(listener -> listener.currentDocumentChanged(previousModel, this.getCurrentDocument()));

            model.addSingleDocumentListener(this.initDocumentListener());

            return model;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    @Override
    public void saveDocument(SingleDocumentModel model, Path newPath) {
        try {
            String data = model.getTextComponent().getText();
            Files.writeString(newPath, data);

            model.setFilePath(newPath);
            int index = this.documents.indexOf(model);
            String fileName = model.getFilePath().getFileName().toString();

            this.setTitleAt(index, fileName);
            this.setToolTipTextAt(index, fileName);
            this.setIconAt(index, greenSaveIcon);

            model.setModified(false);

            this.notifyListeners(listener -> listener.currentDocumentChanged(model, this.getCurrentDocument()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeDocument(SingleDocumentModel model) {
        if (model == null) {
            return;
        }

        int index = this.documents.indexOf(model);

        this.removeTabAt(index);
        if (!this.documents.remove(model)) return;

        this.document = this.documents.isEmpty() ? null : this.documents.get(this.documents.size() - 1);

        this.notifyListeners(listener -> listener.documentRemoved(model));
        this.notifyListeners(listener -> listener.currentDocumentChanged(model, this.getCurrentDocument()));
    }

    @Override
    public void addMultipleDocumentListener(MultipleDocumentListener l) {
        this.listeners.add(Objects.requireNonNull(l, "Listener cant be null!"));
    }

    @Override
    public void removeMultipleDocumentListener(MultipleDocumentListener l) {
        this.listeners.remove(Objects.requireNonNull(l, "Listener cant be null!"));
    }

    @Override
    public int getNumberOfDocuments() {
        return this.documents.size();
    }

    @Override
    public SingleDocumentModel getDocument(int index) {
        return this.documents.get(index);
    }

    @Override
    public SingleDocumentModel findForPath(Path path) {
        return null;
    }

    @Override
    public int getIndexOfDocument(SingleDocumentModel doc) {
        return this.documents.indexOf(Objects.requireNonNull(doc, "Document cant be null!"));
    }

    public boolean isSelected() {
        return isSelected;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<SingleDocumentModel> iterator() {
        return this.documents.iterator();
    }

    private SingleDocumentListener initDocumentListener() {
        return new SingleDocumentListener() {
            @Override
            public void documentModifyStatusUpdated(SingleDocumentModel model) {
                int index = documents.indexOf(model);
                if (model.isModified()) {
                    setIconAt(index, redSaveIcon);
                } else {
                    setIconAt(index, greenSaveIcon);
                }
            }

            @Override
            public void documentFilePathUpdated(SingleDocumentModel model) {
                int index = documents.indexOf(model);

                setTitleAt(index,
                        model.getFilePath() == null ? "(unnamed)" : model.getFilePath().getFileName().toString());
                setToolTipTextAt(index, model.getFilePath() == null ? "(unnamed)" : model.getFilePath().toString());
            }
        };
    }

    private void handleCaretChange(int dot, int mark) {
        JTextComponent textComponent = this.document.getTextComponent();
        try {
            if (dot > 0 && mark > 0) {
                int pos = textComponent.getCaretPosition();
                Element root = textComponent.getDocument().getDefaultRootElement();
                int line = root.getElementIndex(pos);
                this.notepad.getStatusbar().setLine(line + 1);
                this.notepad.getStatusbar().setColumn(1 + pos - root.getElement(line).getStartOffset());
            } else {
                this.notepad.getStatusbar().setLine(1);
                this.notepad.getStatusbar().setColumn(1);
            }
        } catch (Exception e) {
            this.notepad.getStatusbar().setLine(1);
            this.notepad.getStatusbar().setColumn(1);
        }
        int selection = Math.abs(mark - dot);
        this.notepad.getStatusbar().setSelection(selection);

        this.notepad.getChangeCaseActions().forEach((action) -> action.setEnabled(selection > 0));

        this.isSelected = selection > 0;
    }

    private void notifyListeners(Consumer<MultipleDocumentListener> consumer) {
        this.listeners.forEach(consumer);
    }

}
