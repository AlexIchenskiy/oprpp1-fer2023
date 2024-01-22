package hr.fer.oprpp1.hw08.jnotepadpp.components;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JNotepadStatusbar extends JToolBar {

    private final JLabel lengthLabel = new JLabel("Length: 0");

    private final JLabel lineLabel = new JLabel("Line: 0");

    private final JLabel columnLabel = new JLabel("Column: 0");

    private final JLabel selectionLabel = new JLabel("Selection: 0");

    private int length = 0;
    private int line = 1;
    private int column = 1;
    private int selection = 0;

    private final JLabel timeLabel = new JLabel();

    private final ILocalizationProvider provider;

    /**
     * Creates a new tool bar; orientation defaults to <code>HORIZONTAL</code>.
     */
    public JNotepadStatusbar(ILocalizationProvider provider) {
        this.setFloatable(true);
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));

        this.provider = provider;
        this.provider.addLocalizationListener(this::handleLanguageChange);

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
        this.length = length;
        this.lengthLabel.setText(this.provider.getString("length") + ": " + length);
    }

    public void setLine(int line) {
        this.line = line;
        this.lineLabel.setText(this.provider.getString("line") + ": " + line);
    }

    public void setColumn(int column) {
        this.column = column;
        this.columnLabel.setText(this.provider.getString("column") + ": " + column);
    }

    public void setSelection(int selection) {
        this.selection = selection;
        this.selectionLabel.setText(this.provider.getString("selection") + ": " + selection);
    }

    private void handleLanguageChange() {
        this.setLength(this.length);
        this.setLine(this.line);
        this.setColumn(this.column);
        this.setSelection(this.selection);
    }

}
