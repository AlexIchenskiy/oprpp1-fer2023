package hr.fer.oprpp1.hw08.jnotepadpp.components;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Notepad statusbar.
 */
public class JNotepadStatusbar extends JToolBar {

    /**
     * Label for length display.
     */
    private final JLabel lengthLabel = new JLabel("Length: 0");

    /**
     * Label for current line display.
     */
    private final JLabel lineLabel = new JLabel("Line: 0");

    /**
     * Label for current column display.
     */
    private final JLabel columnLabel = new JLabel("Column: 0");

    /**
     * Label for current selection display.
     */
    private final JLabel selectionLabel = new JLabel("Selection: 0");

    /**
     * Length value for display.
     */
    private int length = 0;

    /**
     * Line value for display.
     */
    private int line = 1;

    /**
     * Column value for display.
     */
    private int column = 1;

    /**
     * Selection value for display.
     */
    private int selection = 0;

    /**
     * Label for time display.
     */
    private final JLabel timeLabel = new JLabel();

    /**
     * Localization provider
     */
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

    /**
     * Function for GUI initialization.
     */
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

    /**
     * Function for timer initialization.
     */
    private void initTimer() {
        Timer timer = new Timer(1000, e -> updateTime(timeLabel));

        timer.start();
    }

    /**
     * Function for updating the time.
     * @param timeLabel Label with time being updated
     */
    private static void updateTime(JLabel timeLabel) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String formattedTime = dateFormat.format(new Date());
        timeLabel.setText(formattedTime);
    }

    /**
     * Setter for length.
     * @param length New length value
     */
    public void setLength(int length) {
        this.length = length;
        this.lengthLabel.setText(this.provider.getString("length") + ": " + length);
    }

    /**
     * Setter for line.
     * @param line New line value
     */
    public void setLine(int line) {
        this.line = line;
        this.lineLabel.setText(this.provider.getString("line") + ": " + line);
    }

    /**
     * Setter for column.
     * @param column New column value
     */
    public void setColumn(int column) {
        this.column = column;
        this.columnLabel.setText(this.provider.getString("column") + ": " + column);
    }

    /**
     * Setter for selection.
     * @param selection New selection value
     */
    public void setSelection(int selection) {
        this.selection = selection;
        this.selectionLabel.setText(this.provider.getString("selection") + ": " + selection);
    }

    /**
     * Function for handling the language change.
     */
    private void handleLanguageChange() {
        this.setLength(this.length);
        this.setLine(this.line);
        this.setColumn(this.column);
        this.setSelection(this.selection);
    }

}
