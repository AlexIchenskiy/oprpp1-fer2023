package hr.fer.oprpp1.hw07.gui.calc.layout;

import javax.swing.*;

/**
 * Class representing a calculator invert checkbox.
 */
public class CalcCheckbox extends JCheckBox {

    /**
     * Creates an initially unselected check box with text.
     *
     * @param text the text of the check box.
     */
    public CalcCheckbox(String text) {
        super(text);
        this.setHorizontalAlignment(SwingConstants.LEFT);
        this.setVerticalAlignment(SwingConstants.CENTER);
    }

}
