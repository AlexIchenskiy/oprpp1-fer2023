package hr.fer.oprpp1.hw07.gui.calc.layout;

import hr.fer.oprpp1.hw07.gui.calc.model.CalcModel;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

/**
 * Class representing a calculator button that can be inverted.
 */
public class CalcInvertibleButton extends JButton {

    /**
     * Boolean representing whether the button is inverted.
     */
    private boolean inverted = false;

    /**
     * Regular button label.
     */
    private final String text;

    /**
     * Inverted button label.
     */
    private final String invertedText;

    /**
     * Regular button action.
     */
    private final ActionListener actionListener;

    /**
     * Inverted button action.
     */
    private final ActionListener invertedActionListener;

    /**
     * Creates an invertible button with regular and inverted name and action.
     * @param text Regular button label
     * @param actionListener Regular button action
     * @param invertedText Inverted button label
     * @param invertedActionListener Inverted button action
     */
    private CalcInvertibleButton(String text, ActionListener actionListener, String invertedText,
                                 ActionListener invertedActionListener) {
        super(text);

        this.text = text;
        this.invertedText = invertedText;
        this.actionListener = actionListener;
        this.invertedActionListener = invertedActionListener;

        this.addActionListener(actionListener);
    }

    /**
     * Getter for the inverted property.
     * @return Boolean representing whether the button is inverted
     */
    public boolean isInverted() {
        return inverted;
    }

    /**
     * Setter for the inverted property.
     * @param inverted New inverted property
     */
    public void setInverted(boolean inverted) {
        this.inverted = inverted;

        if (this.isInverted()) {
            this.removeActionListener(actionListener);
            this.addActionListener(invertedActionListener);
            this.setText(invertedText);
            return;
        }

        this.removeActionListener(invertedActionListener);
        this.addActionListener(actionListener);
        this.setText(text);
    }

    public static CalcInvertibleButton createInvertibleButton(String text, DoubleUnaryOperator operation,
                                                              String invertedText,
                                                              DoubleUnaryOperator invertedOperation,
                                                              CalcModel calcModel) {
        return new CalcInvertibleButton(text, e -> {
            if (calcModel.hasFrozenValue()) return;
            calcModel.setValue(operation.applyAsDouble(calcModel.getValue()));
        }, invertedText, e -> {
            if (calcModel.hasFrozenValue()) return;
            calcModel.setValue(invertedOperation.applyAsDouble(calcModel.getValue()));
        });
    }

    public static CalcInvertibleButton createInvertibleButton(String text, DoubleBinaryOperator operation,
                                                              String invertedText,
                                                              DoubleBinaryOperator invertedOperation,
                                                              CalcModel calcModel) {
        return new CalcInvertibleButton(text, e -> {
            CalcInvertibleButton.performBinaryOperation(operation, calcModel);
        }, invertedText, e -> {
            CalcInvertibleButton.performBinaryOperation(invertedOperation, calcModel);
        });
    }

    private static void performBinaryOperation(DoubleBinaryOperator operation, CalcModel calcModel) {
        if (calcModel.hasFrozenValue()) {
            calcModel.setPendingBinaryOperation(operation);
            return;
        }

        if (calcModel.getPendingBinaryOperation() != null) {
            double temp = calcModel.getPendingBinaryOperation().applyAsDouble(
                    calcModel.getActiveOperand(), calcModel.getValue()
            );
            calcModel.setValue(temp);
            calcModel.setActiveOperand(temp);
            calcModel.setPendingBinaryOperation(operation);
            calcModel.freezeValue("" + calcModel.getValue());
            return;
        }

        calcModel.setPendingBinaryOperation(operation);
        calcModel.setActiveOperand(calcModel.getValue());
        calcModel.freezeValue("" + calcModel.getValue());
    }

}
