package hr.fer.oprpp1.hw07.gui.calc.layout;

import hr.fer.oprpp1.hw07.gui.calc.model.CalcModel;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

/**
 * Class representing a calculator button.
 */
public class CalcButton extends JButton {

    /**
     * Creates a button with text and an action listener.
     *
     * @param text the text of the button
     * @param actionListener the button action listener
     */
    public CalcButton(String text, ActionListener actionListener) {
        super(text);

        this.addActionListener(actionListener);
    }

    public static CalcButton createBinaryOperationButton(String buttonText, DoubleBinaryOperator operation,
                                                          CalcModel calcModel) {
        return new CalcButton(buttonText, e -> {
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
        });
    }

    public static CalcButton createUnaryOperationButton(String buttonText, DoubleUnaryOperator action,
                                                        CalcModel calcModel) {
        return new CalcButton(buttonText, e -> {
            if (calcModel.hasFrozenValue()) return;
            calcModel.setValue(action.applyAsDouble(calcModel.getValue()));
        });
    }

    public static CalcButton createDigitButton(String buttonText, int digit, CalcModel calcModel) {
        return new CalcButton(buttonText, e -> {
            if (calcModel.isActiveOperandSet()) calcModel.clear();
            calcModel.insertDigit(digit);
        });
    }

}
