package hr.fer.oprpp1.hw08.jnotepadpp.model;

import javax.swing.*;
import java.nio.file.Path;

/**
 * Interface for the multiple document logic model.
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {

    /**
     * Returns a multiple document visual component.
     * @return Tabbed pane representing a multiple document model
     */
    JComponent getVisualComponent();

    /**
     * Create a new document.
     * @return Created document
     */
    SingleDocumentModel createNewDocument();

    /**
     * Get current document.
     * @return Current document
     */
    SingleDocumentModel getCurrentDocument();

    /**
     * Load document into model.
     * @param path Path of the document
     * @return Loaded document
     */
    SingleDocumentModel loadDocument(Path path);

    /**
     * Save document
     * @param model Document
     * @param newPath Document path
     */
    void saveDocument(SingleDocumentModel model, Path newPath);

    /**
     * Close a single document.
     * @param model Document to be closed
     */
    void closeDocument(SingleDocumentModel model);

    /**
     * Add listener to the model.
     * @param l Listener to be added
     */
    void addMultipleDocumentListener(MultipleDocumentListener l);

    /**
     * Remove listener from the model.
     * @param l Listener to be removed
     */
    void removeMultipleDocumentListener(MultipleDocumentListener l);

    /**
     * Get current documents number.
     * @return Documents number
     */
    int getNumberOfDocuments();

    /**
     * Get document on the given index.
     * @param index Index of the document
     * @return Document
     */
    SingleDocumentModel getDocument(int index);

    /**
     * Find document for path.
     * @param path Path of the document
     * @return Corresponding document
     */
    SingleDocumentModel findForPath(Path path); // null, if no such model exists

    /**
     * Returns document index.
     * @param doc Document
     * @return Documents index
     */
    int getIndexOfDocument(SingleDocumentModel doc); // -1 if not present

}

