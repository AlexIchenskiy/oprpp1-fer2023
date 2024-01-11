package hr.fer.oprpp1.hw07.gui.charts;

import java.util.List;

/**
 * Class representing a bar chart.
 */
public class BarChart {

    /**
     * List of (X, Y) values for the bar chart.
     */
    private final List<XYValue> xyValues;

    /**
     * Description for the chart X axis.
     */
    private final String xAxisText;

    /**
     * Description for the chart Y axis.
     */
    private final String yAxisText;

    /**
     * Min Y value for the chart.
     */
    private final int minY;

    /**
     * Max Y value for the chart.
     */
    private final int maxY;

    /**
     * Distance between the two points on the Y axis of the chart.
     */
    private final int offsetY;

    /**
     * Creates a bar chart with provided parameters.
     * @param xyValues (X, Y) values of the bar chart
     * @param xAxisText Description for the chart X axis
     * @param yAxisText Description for the chart Y axis
     * @param minY Min Y value for the chart
     * @param maxY Max Y value for the chart
     * @param offsetY Distance between the two points on the Y axis of the chart
     */
    public BarChart(List<XYValue> xyValues, String xAxisText, String yAxisText, int minY, int maxY, int offsetY) {
        this.xyValues = xyValues;
        this.xAxisText = xAxisText;
        this.yAxisText = yAxisText;
        this.minY = minY;
        this.maxY = maxY;
        this.offsetY = offsetY;

        if (this.minY < 0) throw new BarChartException("Min Y value cant be negative!");
        if (this.minY >= this.maxY) throw new BarChartException("Min Y value cant be greater than the max Y value!");

        this.xyValues.forEach(xyValue -> {
            if (xyValue.getY() < this.minY) throw new BarChartException("All Y values must be greater or equal than" +
                    " the Y min value!");
        });
    }

    /**
     * Getter for (X, Y) values.
     * @return (X, Y) values
     */
    public List<XYValue> getXyValues() {
        return xyValues;
    }

    /**
     * Getter for X axis text.
     * @return X-axis text
     */
    public String getxAxisText() {
        return xAxisText;
    }

    /**
     * Getter for Y axis text.
     * @return Y-axis text
     */
    public String getyAxisText() {
        return yAxisText;
    }

    /**
     * Getter for Y min value.
     * @return Y min value
     */
    public int getMinY() {
        return minY;
    }

    /**
     * Getter for Y max value.
     * @return Y max value
     */
    public int getMaxY() {
        return maxY;
    }

    /**
     * Getter for Y offset.
     * @return Y offset
     */
    public int getOffsetY() {
        return offsetY;
    }

}
