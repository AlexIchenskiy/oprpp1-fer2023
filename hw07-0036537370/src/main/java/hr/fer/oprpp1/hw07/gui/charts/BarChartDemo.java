package hr.fer.oprpp1.hw07.gui.charts;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A demo for bar chart GUI.
 */
public class BarChartDemo extends JFrame {

    private final BarChart chart;

    public BarChartDemo(String filePath) {
        super();
        this.chart = this.readDataFromFile(filePath);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.initGUI(filePath);
        pack();
        this.setVisible(true);
    }

    private BarChart readDataFromFile(String filePath) {
        List<XYValue> xyValues = new ArrayList<>();

        String xAxisText, yAxisText;
        int minY, maxY, offsetY;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            xAxisText = br.readLine().trim();
            yAxisText = br.readLine().trim();

            String[] xyValuesInput = br.readLine().trim().split("\\s+");

            for (String value : xyValuesInput) {
                String[] xy = value.split(",");
                xyValues.add(new XYValue(Integer.parseInt(xy[0]), Integer.parseInt(xy[1])));
            }

            minY = Integer.parseInt(br.readLine().trim());
            maxY = Integer.parseInt(br.readLine().trim());
            offsetY = Integer.parseInt(br.readLine().trim());
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid file path!");
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid data provided!");
        }

        return new BarChart(xyValues, xAxisText, yAxisText, minY, maxY, offsetY);
    }

    private void initGUI(String path) {
        Container cp = this.getContentPane();

        cp.setLayout(new BorderLayout());
        cp.setPreferredSize(new Dimension(600, 400));
        cp.add(new JLabel(path, SwingConstants.CENTER), BorderLayout.PAGE_START);
        cp.add(new BarChartComponent(this.chart), BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        if (args.length != 1) throw new IllegalArgumentException("One argument must be provided!");

        SwingUtilities.invokeLater(() -> new BarChartDemo(args[0]));
    }

}
