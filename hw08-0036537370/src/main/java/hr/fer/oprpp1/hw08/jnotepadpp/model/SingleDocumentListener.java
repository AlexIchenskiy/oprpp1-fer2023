package hr.fer.oprpp1.hw08.jnotepadpp.model;

/**
 * Interface for the single document listener.
 */
public interface SingleDocumentListener {

    /**
     * Action on the update of the modified status of the document.
     * @param model Document to be watched
     */
    void documentModifyStatusUpdated(SingleDocumentModel model);

    /**
     * Action on the update of the path of the document.
     * @param model Document to be watched
     */
    void documentFilePathUpdated(SingleDocumentModel model);

}

