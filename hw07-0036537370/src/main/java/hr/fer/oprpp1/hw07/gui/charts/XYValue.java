package hr.fer.oprpp1.hw07.gui.charts;

/**
 * A structure for storing X and Y int values.
 */
public class XYValue {

    /**
     * X value.
     */
    private final int x;

    /**
     * Y value.
     */
    private final int y;

    /**
     * Creates a new structure with x and y values.
     * @param x X value
     * @param y Y value
     */
    public XYValue(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter for the X value.
     * @return X value
     */
    public int getX() {
        return x;
    }

    /**
     * Getter for the Y value.
     * @return Y value
     */
    public int getY() {
        return y;
    }

}
