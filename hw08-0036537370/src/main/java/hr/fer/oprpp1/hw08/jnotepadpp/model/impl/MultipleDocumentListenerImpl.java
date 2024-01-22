package hr.fer.oprpp1.hw08.jnotepadpp.model.impl;

import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;

public class MultipleDocumentListenerImpl implements MultipleDocumentListener {

    private final JNotepadPP frame;

    public MultipleDocumentListenerImpl(JNotepadPP frame) {
        this.frame = frame;
    }

    @Override
    public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
        String title = "";

        if (currentModel != null && currentModel.getFilePath() == null) {
            title = "(unnamed) - ";
        }

        if (currentModel != null && currentModel.getFilePath() != null) {
            title = currentModel.getFilePath().toString() + " - ";
        }

        title += this.frame.getTitle();

        this.frame.setTitle(title);
        this.frame.getStatusbar().setLength(
                currentModel == null ? 0 : currentModel.getTextComponent().getText().length()
        );

        if (this.frame.getMultipleDocumentModel().getCurrentDocument() != null) {
            this.frame.getChangeCaseActions().forEach(action -> action.setTextComponent(
                    this.frame.getMultipleDocumentModel().getCurrentDocument().getTextComponent())
            );

            this.frame.getUniqueActions().forEach(action -> action.setTextComponent(
                    this.frame.getMultipleDocumentModel().getCurrentDocument().getTextComponent())
            );

            this.frame.getSortActions().forEach(action -> action.setTextComponent(
                    this.frame.getMultipleDocumentModel().getCurrentDocument().getTextComponent())
            );
        } else {
            this.frame.getChangeCaseActions().forEach(action -> action.setTextComponent(null));

            this.frame.getUniqueActions().forEach(action -> action.setTextComponent(null));

            this.frame.getSortActions().forEach(action -> action.setTextComponent(null));
        }
    }

    @Override
    public void documentAdded(SingleDocumentModel model) {

    }

    @Override
    public void documentRemoved(SingleDocumentModel model) {

    }

}
