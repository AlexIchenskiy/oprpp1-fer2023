package hr.fer.oprpp1.hw07.gui.calc;

import hr.fer.oprpp1.hw07.gui.calc.layout.CalcButton;
import hr.fer.oprpp1.hw07.gui.calc.layout.CalcCheckbox;
import hr.fer.oprpp1.hw07.gui.calc.layout.CalcDisplay;
import hr.fer.oprpp1.hw07.gui.calc.layout.CalcInvertibleButton;
import hr.fer.oprpp1.hw07.gui.calc.model.CalcModel;
import hr.fer.oprpp1.hw07.gui.calc.model.CalcModelImpl;
import hr.fer.oprpp1.hw07.gui.layouts.CalcLayout;
import hr.fer.oprpp1.hw07.gui.layouts.RCPosition;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Stack;

/**
 * Class representing a calculator GUI.
 */
public class Calculator extends JFrame {

    /**
     * Calculator model used for handling the calculator logic.
     */
    private final CalcModel calcModel;

    /**
     * A custom map representing a binary operation button by its position.
     */
    private final Map<RCPosition, JButton> binaryOperationButtonByPosition;

    /**
     * A custom map representing an action button by its position.
     */
    private final Map<RCPosition, JButton> actionButtonByPosition;

    /**
     * A custom map representing a unary operation button by its position.
     */
    private final Map<RCPosition, CalcInvertibleButton> unaryOperationButtonByPosition;

    /**
     * A stack structure for calculator values saving.
     */
    private final Stack<Double> stack = new Stack<>();

    public Calculator(CalcModel calcModel) {
        this.calcModel = calcModel;

        this.actionButtonByPosition = Map.of(
                new RCPosition(1, 7), new CalcButton("clr", e -> this.calcModel.clear()),
                new RCPosition(2, 7), new CalcButton("reset", e -> this.calcModel.clearAll()),
                new RCPosition(3, 7), new CalcButton("push",
                        e -> this.stack.push(this.calcModel.getValue())),
                new RCPosition(4, 7), new CalcButton("pop",
                        e -> {
                    try {
                        this.stack.peek();
                    } catch (Exception exception) {
                        return;
                    }
                    this.calcModel.setValue(this.stack.pop());
                }),
                new RCPosition(5, 4), new CalcButton("+/-", e -> this.calcModel.swapSign()),
                new RCPosition(5, 5), new CalcButton(".", e -> this.calcModel.insertDecimalPoint())
        );

        this.binaryOperationButtonByPosition = Map.of(
                new RCPosition(1, 6), new CalcButton("=", e -> {
                    if (calcModel.getPendingBinaryOperation() == null) return;
                    calcModel.setValue(calcModel.getPendingBinaryOperation().applyAsDouble(
                            calcModel.getActiveOperand(), calcModel.getValue()
                    ));
                    calcModel.setPendingBinaryOperation(null);
                }),

                new RCPosition(2, 6), CalcButton.createBinaryOperationButton("/", (x , y) -> x / y,
                        this.calcModel),
                new RCPosition(3, 6), CalcButton.createBinaryOperationButton("*", (x, y) -> x * y,
                        this.calcModel),
                new RCPosition(4, 6), CalcButton.createBinaryOperationButton("-", (x, y) -> x - y,
                        this.calcModel),
                new RCPosition(5, 6), CalcButton.createBinaryOperationButton("+", Double::sum,
                        this.calcModel)
        );

        this.unaryOperationButtonByPosition = Map.of(
                new RCPosition(2, 1), CalcInvertibleButton.createInvertibleButton("1/x", (x) -> 1 / x,
                        "1/x", (x) -> 1 / x, this.calcModel),
                new RCPosition(3, 1), CalcInvertibleButton.createInvertibleButton("log", Math::log10,
                        "10^x", (x) -> Math.pow(10, x), this.calcModel),
                new RCPosition(4, 1), CalcInvertibleButton.createInvertibleButton("ln", Math::log,
                        "e^x", (x) -> Math.pow(Math.E, x), this.calcModel),
                new RCPosition(5, 1), CalcInvertibleButton.createInvertibleButton("x^n",
                        Math::pow, "x^(1/n)", (x, n) -> Math.pow(x, 1 / n), this.calcModel),
                new RCPosition(2, 2), CalcInvertibleButton.createInvertibleButton("sin", Math::sin,
                        "arcsin", Math::asin, this.calcModel),
                new RCPosition(3, 2), CalcInvertibleButton.createInvertibleButton("cos", Math::cos,
                        "arccos", Math::acos, this.calcModel),
                new RCPosition(4, 2), CalcInvertibleButton.createInvertibleButton("tan", Math::tan,
                        "arctan", Math::atan, this.calcModel),
                new RCPosition(5, 2), CalcInvertibleButton.createInvertibleButton("ctg",
                        (x) -> 1 / Math.tan(x),
                        "arcctg", (x) -> Math.PI / 2 - Math.atan(x), this.calcModel)
        );

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.initGUI();
        pack();
    }

    /**
     * Function for calculator GUI initialization.
     */
    private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new CalcLayout(7));

        this.initDisplay(cp);
        this.initFromMap(cp, this.binaryOperationButtonByPosition);
        this.initFromMap(cp, this.actionButtonByPosition);
        this.initFromMap(cp, this.unaryOperationButtonByPosition);
        this.initDigitButtons(cp);
        this.initInvertCheckbox(cp);
    }

    /**
     * Function for calculator display initialization.
     * @param cp A content pane for display to be placed on
     */
    private void initDisplay(Container cp) {
        CalcDisplay display = new CalcDisplay(this.calcModel.toString());

        cp.add(display, new RCPosition(1, 1));

        this.calcModel.addCalcValueListener(calcModel -> display.setText(calcModel.toString()));
    }

    /**
     * Function for initialization of elements from the given map.
     * @param cp A content pane for elements to be placed on
     */
    private void initFromMap(Container cp, Map<RCPosition, ? extends JComponent> elements) {
        for (Map.Entry<RCPosition, ? extends JComponent> entry : elements.entrySet()) {
            cp.add(entry.getValue(), entry.getKey());
        }
    }

    /**
     * Function for initialization of digit calculator buttons.
     * @param cp A content pane for buttons to be placed on
     */
    private void initDigitButtons(Container cp) {
        int count = 1;

        for (int i = 4; i >= 2; i--) {
            for (int j = 3; j <= 5; j++) {
                int finalCount = count;
                cp.add(CalcButton.createDigitButton("" + finalCount, finalCount, this.calcModel),
                        new RCPosition(i, j));
                count++;
            }
        }

        cp.add(CalcButton.createDigitButton("0", 0, this.calcModel), new RCPosition(5, 3));
    }

    /**
     * Function for initialization of calculator invert checkbox.
     * @param cp A content pane for buttons to be placed on
     */
    private void initInvertCheckbox(Container cp) {
        CalcCheckbox checkBox = new CalcCheckbox("Inv");

        checkBox.addActionListener(listener -> {
            for (Map.Entry<RCPosition, CalcInvertibleButton> entry : this.unaryOperationButtonByPosition.entrySet()) {
                entry.getValue().setInverted(!entry.getValue().isInverted());
            }
        });

        cp.add(checkBox, new RCPosition(5, 7));
    }

    /**
     * Function to start the calculator app GUI.
     * @param args No arguments required
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Calculator(new CalcModelImpl()).setVisible(true));
    }

}
