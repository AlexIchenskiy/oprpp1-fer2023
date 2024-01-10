package hr.fer.oprpp1.hw07.gui.calc.layout;

import javax.swing.*;
import java.awt.*;

/**
 * Class representing a calculator display.
 */
public class CalcDisplay extends JLabel {

    /**
     * Creates a <code>JLabel</code> instance with the specified text.
     * The label is aligned against the leading edge of its display area,
     * and centered vertically.
     *
     * @param text The text to be displayed by the label.
     */
    public CalcDisplay(String text) {
        super(text);
        this.setHorizontalAlignment(SwingConstants.RIGHT);
        this.setVerticalAlignment(SwingConstants.CENTER);
        this.setOpaque(true);
        setBackground(Color.decode("#FFFF00"));
    }

}
