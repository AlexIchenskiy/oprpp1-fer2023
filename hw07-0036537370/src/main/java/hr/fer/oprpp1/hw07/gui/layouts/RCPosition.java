package hr.fer.oprpp1.hw07.gui.layouts;

import java.util.Objects;

/**
 * Class representing a grid element with defined row and column values.
 */
public class RCPosition {

    /**
     * Element row (x-axis position).
     */
    private final int row;

    /**
     * Element column (y-axis position).
     */
    private final int column;

    /**
     * Creates a grid element with a defined (row, column) position.
     * @param row Element row
     * @param column Element column
     */
    public RCPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Element row getter.
     * @return element row
     */
    public int getRow() {
        return row;
    }

    /**
     * Element column getter.
     * @return Element column
     */
    public int getColumn() {
        return column;
    }

    /**
     * Function that parses a string representation of "x, y" format where x, y are numbers into an
     * instance of the grid element.
     * @param text A string representation of the grid element in "x, y" format where x, y are numbers
     * @return A grid element with defined row (x) and column (y) position
     */
    public static RCPosition parse(String text) {
        Objects.requireNonNull(text, "RCPosition value cant be null!");

        String[] values = text.split(",");

        if (values.length != 2) {
            throw new IllegalArgumentException("RCPosition must be of format \"x, y\" where x, y are numbers!");
        }

        int x, y;

        try {
            x = Integer.parseInt(values[0].trim());
            y = Integer.parseInt(values[1].trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("x and y must be valid numbers!");
        }

        return new RCPosition(x, y);
    }

    /**
     * Function to check whether two grid elements are equal by checking their x, y coordinates.
     * @param o The second grid element to be checked
     * @return Boolean representing whether the two grid elements are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RCPosition)) return false;
        RCPosition that = (RCPosition) o;
        return row == that.row && column == that.column;
    }

    /**
     * Function that generates a grid element hashcode.
     * @return A grid elements hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

}
