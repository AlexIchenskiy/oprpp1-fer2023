package hr.fer.oprpp1.hw07.gui.calc.layout;

import hr.fer.oprpp1.hw07.gui.calc.model.CalcModel;

import javax.swing.*;
import java.awt.*;
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

        this.setOpaque(true);
        this.setBackground(Color.decode("#ff6e20"));
        this.setFont(this.getFont().deriveFont(12f));
        this.setBorder(BorderFactory.createLineBorder(Color.decode("#231f20"), 2));

        this.addActionListener(actionListener);
    }

    public CalcButton(String text, ActionListener actionListener, float fontSize) {
        this(text, actionListener);
        this.setFont(this.getFont().deriveFont(fontSize));
    }

    /**
     * Generates a single binary operation button.
     * @param buttonText Button text
     * @param operation Button binary operation
     * @param calcModel Calculator logic model
     * @return A single binary operation button
     */
    public static CalcButton createBinaryOperationButton(String buttonText, DoubleBinaryOperator operation,
                                                          CalcModel calcModel) {
        return new CalcButton(buttonText, e -> {
            CalcInvertibleButton.performBinaryOperation(operation, calcModel);
        });
    }

    /**
     * Generates a single unary operation button.
     * @param buttonText Button text
     * @param action Button unary operation
     * @param calcModel Calculator logic model
     * @return A single unary operation button
     */
    public static CalcButton createUnaryOperationButton(String buttonText, DoubleUnaryOperator action,
                                                        CalcModel calcModel) {
        return new CalcButton(buttonText, e -> {
            if (calcModel.hasFrozenValue()) return;
            calcModel.setValue(action.applyAsDouble(calcModel.getValue()));
        });
    }

    /**
     * Generates a single digit button.
     * @param buttonText Button text
     * @param digit Corresponding digit
     * @param calcModel Calculator logic model
     * @return A single digit button
     */
    public static CalcButton createDigitButton(String buttonText, int digit, CalcModel calcModel) {
        return new CalcButton(buttonText, e -> {
            if (!calcModel.isEditable()) calcModel.clear();
            calcModel.insertDigit(digit);
        }, 24f);
    }

}
