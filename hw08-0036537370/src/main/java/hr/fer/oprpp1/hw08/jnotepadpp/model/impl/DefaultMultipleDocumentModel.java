package hr.fer.oprpp1.hw08.jnotepadpp.model.impl;

import hr.fer.oprpp1.hw08.jnotepadpp.components.JNotepadTextArea;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

    private final List<SingleDocumentModel> documents;

    private SingleDocumentModel document;

    private final List<MultipleDocumentListener> listeners;

    /**
     * Creates an empty <code>TabbedPane</code> with a default
     * tab placement of <code>JTabbedPane.TOP</code>.
     *
     * @see #addTab
     */
    public DefaultMultipleDocumentModel() {
        super();

        this.documents = new ArrayList<>();
        this.document = null;
        this.listeners = new ArrayList<>();

        this.addChangeListener(e -> {
            try {
                int index = this.getSelectedIndex();
                if (index < 0 || index >= this.documents.size()) return;
                this.document = this.documents.get(this.getSelectedIndex());
                this.notifyListeners();
            } catch (ArrayIndexOutOfBoundsException ignored) {}
        });
    }

    @Override
    public JComponent getVisualComponent() {
        return this;
    }

    @Override
    public SingleDocumentModel createNewDocument() {
        SingleDocumentModel model = new DefaultSingleDocumentModel(null, "");

        this.documents.add(model);
        this.addTab("(unnamed)", new JNotepadTextArea(model.getTextComponent()));
        this.setSelectedIndex(this.documents.size() - 1);

        return model;
    }

    @Override
    public SingleDocumentModel getCurrentDocument() {
        return this.document;
    }

    @Override
    public SingleDocumentModel loadDocument(Path path) {
        SingleDocumentModel existingModel = findForPath(path);

        if (existingModel != null) {
            int index = this.documents.indexOf(existingModel);
            this.setSelectedIndex(index);
            return existingModel;
        }

        try {
            byte[] bytes = Files.readAllBytes(path);
            String data = new String(bytes, StandardCharsets.UTF_8);

            DefaultSingleDocumentModel model = new DefaultSingleDocumentModel(path, data);

            this.documents.add(model);
            this.addTab(path.getFileName().toString(), new JNotepadTextArea(model.getTextComponent()));
            this.setSelectedIndex(this.documents.size() - 1);

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
            this.setTitleAt(this.documents.indexOf(model), model.getFilePath().getFileName().toString());

            this.notifyListeners();
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
        this.documents.remove(model);

        this.document = this.documents.isEmpty() ? null : this.documents.get(this.documents.size() - 1);

        this.notifyListeners();
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

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<SingleDocumentModel> iterator() {
        return this.documents.iterator();
    }

    private void notifyListeners() {
        for (MultipleDocumentListener listener : this.listeners) {
            listener.notify();
        }
    }

}
