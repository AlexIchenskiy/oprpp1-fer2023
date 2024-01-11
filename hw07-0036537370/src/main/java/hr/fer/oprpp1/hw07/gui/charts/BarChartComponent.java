package hr.fer.oprpp1.hw07.gui.charts;

import javax.swing.*;
import java.awt.*;

/**
 * Class representing a bar chart GUI.
 */
public class BarChartComponent extends JComponent {

    /**
     * Bar chart data.
     */
    private final BarChart chart;

    /**
     * Creates a bar chart GUI from a provided bar chart data.
     * @param chart Bar chart data
     */
    public BarChartComponent(BarChart chart) {
        this.chart = chart;
    }

    /**
     * This is invoked during a printing operation. This is implemented to
     * invoke <code>paintComponent</code> on the component. Override this
     * if you wish to add special painting behavior when printing.
     *
     * @param g the <code>Graphics</code> context in which to paint
     * @see #print
     * @since 1.3
     */
    @Override
    protected void printComponent(Graphics g) {
        super.printComponent(g);

//        int chartWidth = this.getWidth() - 40;
//        int chartHeight = this.getHeight() - 40;
//
//        g2d.fillRect(20, 20, chartWidth, chartHeight);
//
//        int barWidth = chartWidth / this.chart.getXyValues().size();

        this.initAxes(g2d);

        this.setVisible(true);
    }

    private void initAxes(Graphics2D g2d) {
        g2d.drawLine(0, 0, 50, 50);
    }

    private void initGrid() {

    }

    private void initBars() {

    }

}
