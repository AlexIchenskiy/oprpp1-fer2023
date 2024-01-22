package hr.fer.oprpp1.hw08.jnotepadpp.model;

/**
 * Interface for the listener for the multiple document model.
 */
public interface MultipleDocumentListener {

    /**
     * Action to do on the current document change.
     * @param previousModel Previous document
     * @param currentModel New document
     */
    void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);

    /**
     * Action to do on the new document addition.
     * @param model New document
     */
    void documentAdded(SingleDocumentModel model);

    /**
     * Action to do on the document removal.
     * @param model Removed document
     */
    void documentRemoved(SingleDocumentModel model);

}

