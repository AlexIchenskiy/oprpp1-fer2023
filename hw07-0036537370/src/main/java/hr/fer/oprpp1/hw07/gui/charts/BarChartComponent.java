package hr.fer.oprpp1.hw07.gui.charts;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class representing a bar chart GUI.
 */
public class BarChartComponent extends JComponent {

    /**
     * Bar chart data.
     */
    private final BarChart chart;

    /**
     * Bar chart block offset.
     */
    private final int blockOffset = 40;

    /**
     * Bar chart offset for arrows and X, Y axes.
     */
    private final int chartOffset = 5;

    /**
     * Creates a bar chart GUI from a provided bar chart data.
     * @param chart Bar chart data
     */
    public BarChartComponent(BarChart chart) {
        this.chart = chart;
    }

    /**
     * Calls the UI delegate's paint method, if the UI delegate
     * is non-<code>null</code>.  We pass the delegate a copy of the
     * <code>Graphics</code> object to protect the rest of the
     * paint code from irrevocable changes
     * (for example, <code>Graphics.translate</code>).
     * <p>
     * If you override this in a subclass you should not make permanent
     * changes to the passed in <code>Graphics</code>. For example, you
     * should not alter the clip <code>Rectangle</code> or modify the
     * transform. If you need to do these operations you may find it
     * easier to create a new <code>Graphics</code> from the passed in
     * <code>Graphics</code> and manipulate it. Further, if you do not
     * invoke super's implementation you must honor the opaque property, that is
     * if this component is opaque, you must completely fill in the background
     * in an opaque color. If you do not honor the opaque property you
     * will likely see visual artifacts.
     * <p>
     * The passed in <code>Graphics</code> object might
     * have a transform other than the identify transform
     * installed on it.  In this case, you might get
     * unexpected results if you cumulatively apply
     * another transform.
     *
     * @param g the <code>Graphics</code> object to protect
     * @see #paint
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);

        FontMetrics fontMetrics = g2d.getFontMetrics();

        int availableWidth = getWidth() - this.chartOffset * 2;
        int availableHeight = getHeight() - this.chartOffset * 2;

        this.initGrid(g2d, availableWidth, availableHeight);
        this.initAxes(g2d, availableWidth, availableHeight, fontMetrics);
        this.initBars(g2d, availableWidth, availableHeight);

        this.setVisible(true);
    }

    /**
     * Function for bar chart axes initialization.
     * @param g2d Graphics 2d
     * @param width Available chart width
     * @param height Available chart height
     * @param fontMetrics Swing font metrics
     */
    private void initAxes(Graphics2D g2d, int width, int height, FontMetrics fontMetrics) {
        g2d.setColor(Color.decode("#231F20"));

        int xAxisHeight = height - this.blockOffset - this.chartOffset;
        List<Integer> xPoints = this.getXPoints(width);
        List<Integer> yPoints = this.getYPoints(height);

        g2d.drawLine(this.blockOffset + this.chartOffset, this.blockOffset,
                this.blockOffset + this.chartOffset, height - this.blockOffset);

        this.drawArrowHead(g2d, this.blockOffset + this.chartOffset,
                this.blockOffset - 5, 5, Math.PI / 2);

        for (int i = 0; i < xPoints.size(); i++) {
            g2d.drawLine(xPoints.get(i), xAxisHeight, xPoints.get(i), xAxisHeight + this.chartOffset);

            if (i != 0) {
                g2d.drawString("" + i, (xPoints.get(i) + xPoints.get(i - 1)) / 2 - fontMetrics.stringWidth("" + i) / 2,
                        xAxisHeight + this.blockOffset / 2 + this.chartOffset);
            }
        }

        g2d.drawString(this.chart.getxAxisText(),
                width / 2 - fontMetrics.stringWidth(chart.getxAxisText()) / 2, height);

        g2d.drawLine(this.blockOffset, xAxisHeight,
                width - this.blockOffset, xAxisHeight);

        this.drawArrowHead(g2d, width + this.chartOffset - this.blockOffset,
                height - this.blockOffset - this.chartOffset, 5, 0);

        for (int i = 0; i < yPoints.size(); i++) {
            g2d.drawLine(this.blockOffset + this.chartOffset, yPoints.get(i), this.blockOffset, yPoints.get(i));

            String num = "" + (i * this.chart.getOffsetY());
            g2d.drawString(num, this.blockOffset - 2 * this.chartOffset - fontMetrics.stringWidth(num) / 2,
                    yPoints.get(i) + 3);
        }

        AffineTransform old = g2d.getTransform();
        AffineTransform at = new AffineTransform(old);
        at.rotate(-Math.PI / 2);
        g2d.setTransform(at);

        g2d.drawString(this.chart.getyAxisText(),
                -(height / 2 + fontMetrics.stringWidth(this.chart.getyAxisText()) / 2), this.blockOffset / 4);

        g2d.setTransform(old);
    }

    /**
     * Function for bar chart grid initialization.
     * @param g2d Graphics 2d
     * @param width Available chart width
     * @param height Available chart height
     */
    private void initGrid(Graphics2D g2d, int width, int height) {
        List<Integer> xPoints = this.getXPoints(width);
        List<Integer> yPoints = this.getYPoints(height);

        g2d.setColor(Color.lightGray);

        for (Integer xPoint : xPoints) {
            g2d.drawLine(xPoint, this.blockOffset, xPoint, height - this.blockOffset - this.chartOffset);
        }

        for (Integer yPoint : yPoints) {
            g2d.drawLine(this.blockOffset, yPoint, width - this.blockOffset, yPoint);
        }
    }

    /**
     * Function for bar chart bars initialization.
     * @param g2d Graphics 2d
     * @param width Available chart width
     * @param height Available chart height
     */
    private void initBars(Graphics2D g2d, int width, int height) {
        List<Integer> xPoints = this.getXPoints(width);

        double sectionHeight = (1.0 /
                (this.chart.getMaxY() * 1.0 / this.chart.getOffsetY()) *
                (height - 2 * this.blockOffset - 2 * this.chartOffset));

        for (int i = 0; i < this.chart.getXyValues().size(); i++) {
            int barHeight = (int) (this.chart.getXyValues().get(i).getY() * sectionHeight / this.chart.getOffsetY());
            int barWidth = ((width - 2 * this.chartOffset - 2 * this.blockOffset)
                    / this.chart.getXyValues().size()) - 2;

            int xOffset = xPoints.get(i) + 1;
            int yOffset = height - this.blockOffset - this.chartOffset - barHeight - 1;

            if (i != this.chart.getXyValues().size() - 1) {
                g2d.setColor(Color.decode("#d7d7d7"));
                g2d.fillRect(xOffset + barWidth, yOffset + 5, 5, barHeight - 5);
            }

            g2d.setColor(Color.white);
            g2d.fillRect(xOffset - 1, yOffset - 1, barWidth + 2, barHeight + 2);

            g2d.setColor(Color.decode("#ff6e20"));
            g2d.fillRect(xOffset, yOffset, barWidth, barHeight);
        }
    }

    /**
     * Function for arrowheads initialization.
     * @param g2d Graphics 2d
     * @param x X coordinate of the arrowhead
     * @param y Y coordinate of the arrowhead
     * @param size Arrowhead size
     * @param rotate If rotate is 0, arrowhead is heading right
     */
    private void drawArrowHead(Graphics2D g2d, int x, int y, int size, double rotate) {
        if (rotate == 0) {
            int[] xPoints = {x - size, x, x - size};
            int[] yPoints = {y - size, y, y + size};
            g2d.fillPolygon(xPoints, yPoints, 3);
            return;
        }

        int[] xPoints = {x - size, x, x + size};
        int[] yPoints = {y + size, y, y + size};
        g2d.fillPolygon(xPoints, yPoints, 3);
    }

    /**
     * Returns X points for the chart grid.
     * @param width Available chart width
     * @return X points for the chart grid
     */
    private List<Integer> getXPoints(int width) {
        List<Integer> xPoints = new ArrayList<>();

        for (int i = 0; i <= this.chart.getXyValues().size(); i++) {
            int offset = this.chartOffset +
                    (int) (i * 1.0 /
                            this.chart.getXyValues().size() * (width - 2 * this.chartOffset - 2 * this.blockOffset));
            xPoints.add(this.blockOffset + offset);
        }

        return xPoints;
    }

    /**
     * Returns Y points for the chart grid.
     * @param height Available chart height
     * @return Y points for the chart grid
     */
    private List<Integer> getYPoints(int height) {
        List<Integer> yPoints = new ArrayList<>();

        for (int i = 0; i <= this.chart.getMaxY() / this.chart.getOffsetY(); i++) {
            int offset = this.chartOffset +
                    (int) (i * 1.0 /
                            (this.chart.getMaxY() / this.chart.getOffsetY()) *
                            (height - 2 * this.blockOffset - 2 * this.chartOffset));
            yPoints.add(this.blockOffset + offset);
        }

        Collections.reverse(yPoints);

        return yPoints;
    }

}
