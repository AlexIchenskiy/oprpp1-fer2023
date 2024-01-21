package hr.fer.oprpp1.hw08.jnotepadpp.model.impl;

import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.components.JNotepadStatusbar;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class DefaultSingleDocumentModel implements SingleDocumentModel {

    private Path filePath;

    private final String textContent;

    private final JTextArea textComponent;

    private boolean modified;

    private final List<SingleDocumentListener> listeners;

    public DefaultSingleDocumentModel(Path filePath, String textContent, JNotepadPP notepad) {
        this.filePath = filePath;
        this.textContent = textContent;

        this.textComponent = new JTextArea(textContent == null ? "" : textContent);
        this.modified = false;
        this.listeners = new ArrayList<>();

        this.textComponent.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                this.handleChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                this.handleChange();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                this.handleChange();
            }

            private void handleChange() {
                notepad.getStatusbar().setLength(
                        getTextComponent() == null ? 0 : getTextComponent().getText().length()
                );
                setModified(true);
            }
        });
    }

    @Override
    public JTextArea getTextComponent() {
        return this.textComponent;
    }

    @Override
    public Path getFilePath() {
        return this.filePath;
    }

    @Override
    public void setFilePath(Path path) {
        this.filePath = path;

        this.notifyListeners(listener -> listener.documentFilePathUpdated(this));
    }

    @Override
    public boolean isModified() {
        return this.modified;
    }

    @Override
    public void setModified(boolean modified) {
        if (this.modified != modified) {
            this.modified = modified;

            this.notifyListeners(listener -> listener.documentModifyStatusUpdated(this));
        }
    }

    @Override
    public void addSingleDocumentListener(SingleDocumentListener l) {
        listeners.add(Objects.requireNonNull(l, "Listener cant be null!"));
    }

    @Override
    public void removeSingleDocumentListener(SingleDocumentListener l) {
        listeners.remove(Objects.requireNonNull(l, "Listener cant be null!"));
    }

    public int getNumberOfLines() {
        return textComponent.getText().isEmpty() ? 0 : textComponent.getLineCount();
    }

    public int getNumberOfAllChars() {
        return textComponent.getText().length();
    }

    public int getNumberOfNonBlankChars() {
        return textComponent.getText().replaceAll("\\s", "").length();
    }

    private void notifyListeners(Consumer<SingleDocumentListener> consumer) {
        this.listeners.forEach(consumer);
    }

}
