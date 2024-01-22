package hr.fer.oprpp1.hw08.jnotepadpp.model;

import javax.swing.*;
import java.nio.file.Path;

/**
 * Interface for the single document logic model.
 */
public interface SingleDocumentModel {

    /**
     * Getter for the model text component.
     * @return Text area of the model
     */
    JTextArea getTextComponent();

    /**
     * Getter for the file path of the document.
     * @return Document file path.
     */
    Path getFilePath();

    /**
     * Setter for the document file path.
     * @param path New document file path
     */
    void setFilePath(Path path);

    /**
     * Getter for the modified state of the document.
     * @return Boolean representing whether this document was modified
     */
    boolean isModified();

    /**
     * Setter for the modified flag of the document.
     * @param modified New modified flag value
     */
    void setModified(boolean modified);

    /**
     * Adds a document listener to the current document.
     * @param l Document listener
     */
    void addSingleDocumentListener(SingleDocumentListener l);

    /**
     * Removes a document listener from the current document.
     * @param l Document listener
     */
    void removeSingleDocumentListener(SingleDocumentListener l);

}

