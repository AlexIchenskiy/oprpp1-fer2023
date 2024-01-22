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

/**
 * Single document model logic implementation.
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

    /**
     * Document path.
     */
    private Path filePath;

    /**
     * Document text content.
     */
    private final String textContent;

    /**
     * Document text area component.
     */
    private final JTextArea textComponent;

    /**
     * Boolean representing whether the file was modified.
     */
    private boolean modified;

    /**
     * Listeners.
     */
    private final List<SingleDocumentListener> listeners;

    /**
     * Creates a single document logic model.
     * @param filePath Document path
     * @param textContent Document content
     * @param notepad Parent notepad
     */
    public DefaultSingleDocumentModel(Path filePath, String textContent, JNotepadPP notepad) {
        this.filePath = filePath;
        this.textContent = textContent;

        this.textComponent = new JTextArea(textContent == null ? "" : textContent);
        this.modified = false;
        this.listeners = new ArrayList<>();

        this.textComponent.getDocument().addDocumentListener(new DocumentListener() {
            /**
             * @inheritDoc
             */
            @Override
            public void insertUpdate(DocumentEvent e) {
                this.handleChange();
            }

            /**
             * @inheritDoc
             */
            @Override
            public void removeUpdate(DocumentEvent e) {
                this.handleChange();
            }

            /**
             * @inheritDoc
             */
            @Override
            public void changedUpdate(DocumentEvent e) {
                this.handleChange();
            }

            /**
             * Function for handling any updates.
             */
            private void handleChange() {
                notepad.getStatusbar().setLength(
                        getTextComponent() == null ? 0 : getTextComponent().getText().length()
                );
                setModified(true);
            }
        });
    }

    /**
     * @inheritDoc
     */
    @Override
    public JTextArea getTextComponent() {
        return this.textComponent;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Path getFilePath() {
        return this.filePath;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setFilePath(Path path) {
        this.filePath = path;

        this.notifyListeners(listener -> listener.documentFilePathUpdated(this));
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isModified() {
        return this.modified;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setModified(boolean modified) {
        if (this.modified != modified) {
            this.modified = modified;

            this.notifyListeners(listener -> listener.documentModifyStatusUpdated(this));
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void addSingleDocumentListener(SingleDocumentListener l) {
        listeners.add(Objects.requireNonNull(l, "Listener cant be null!"));
    }

    /**
     * @inheritDoc
     */
    @Override
    public void removeSingleDocumentListener(SingleDocumentListener l) {
        listeners.remove(Objects.requireNonNull(l, "Listener cant be null!"));
    }

    /**
     * Getter for the line number.
     * @return Number of document lines
     */
    public int getNumberOfLines() {
        return textComponent.getText().isEmpty() ? 0 : textComponent.getLineCount();
    }

    /**
     * Getter for the all chars number.
     * @return Number of all chars in the document
     */
    public int getNumberOfAllChars() {
        return textComponent.getText().length();
    }

    /**
     * Getter for the all non-blank chars number.
     * @return Number of all non-blank chars in the document
     */
    public int getNumberOfNonBlankChars() {
        return textComponent.getText().replaceAll("\\s", "").length();
    }

    /**
     * Notify all listeners.
     * @param consumer Function to notify
     */
    private void notifyListeners(Consumer<SingleDocumentListener> consumer) {
        this.listeners.forEach(consumer);
    }

}
