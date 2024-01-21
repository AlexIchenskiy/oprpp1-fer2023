package hr.fer.oprpp1.hw08.jnotepadpp.components;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JNotepadStatusbar extends JToolBar {

    private final JLabel lengthLabel = new JLabel("Length: 0");

    private final JLabel lineLabel = new JLabel("Line: 0");

    private final JLabel columnLabel = new JLabel("Column: 0");

    private final JLabel selectionLabel = new JLabel("Selection: 0");

    private final JLabel timeLabel = new JLabel();

    /**
     * Creates a new tool bar; orientation defaults to <code>HORIZONTAL</code>.
     */
    public JNotepadStatusbar() {
        this.setFloatable(true);
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));

        this.initGUI();
        this.initTimer();
    }

    private void initGUI() {
        JPanel dataPanel = new JPanel();

        dataPanel.setLayout(new GridLayout(1, 2));
        dataPanel.add(lengthLabel);

        JPanel selectionPanel = new JPanel(new GridLayout(1, 3, 8, 8));
        selectionPanel.add(lineLabel);
        selectionPanel.add(columnLabel);
        selectionPanel.add(selectionLabel);

        dataPanel.add(selectionPanel);

        this.add(dataPanel, BorderLayout.WEST);

        JPanel timePanel = new JPanel();

        timePanel.add(this.timeLabel);

        this.add(timePanel, BorderLayout.EAST);
    }

    private void initTimer() {
        Timer timer = new Timer(1000, e -> updateTime(timeLabel));

        timer.start();
    }

    private static void updateTime(JLabel timeLabel) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String formattedTime = dateFormat.format(new Date());
        timeLabel.setText(formattedTime);
    }

    public void setLength(int length) {
        this.lengthLabel.setText("Length: " + length);
    }

    public void setLine(int line) {
        this.lineLabel.setText("Ln: " + line);
    }

    public void setColumn(int column) {
        this.columnLabel.setText("Col: " + column);
    }

    public void setSelection(int selection) {
        this.selectionLabel.setText("Selection: " + selection);
    }

}
