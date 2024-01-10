package hr.fer.oprpp1.hw07.gui.layouts;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class CalcLayoutTest {

    CalcLayout layout = new CalcLayout();

    @Test
    public void testOutOfRangeConstraint() {
        assertThrows(CalcLayoutException.class, () -> layout.addLayoutComponent(new Button(), "-1, 1"));
        assertThrows(CalcLayoutException.class, () -> layout.addLayoutComponent(new Button(), "2, -1"));
        assertThrows(CalcLayoutException.class, () -> layout.addLayoutComponent(new Button(), "-1, -1"));
        assertThrows(CalcLayoutException.class, () -> layout.addLayoutComponent(new Button(), "6, 1"));
        assertThrows(CalcLayoutException.class, () -> layout.addLayoutComponent(new Button(), "2, 8"));
    }

    @Test
    public void testOccupiedFirstCellConstraint() {
        assertThrows(CalcLayoutException.class, () -> layout.addLayoutComponent(new Button(), "1, 2"));
        assertThrows(CalcLayoutException.class, () -> layout.addLayoutComponent(new Button(), "1, 5"));
    }

    @Test
    public void testOccupiedCellConstraint() {
        layout.addLayoutComponent(new Button(), "2, 2");
        assertThrows(CalcLayoutException.class, () -> layout.addLayoutComponent(new Button(), "2, 2"));
    }

    @Test
    public void testPreferredSizeFirst() {
        JPanel p = new JPanel(new CalcLayout(2));
        JLabel l1 = new JLabel("");
        l1.setPreferredSize(new Dimension(10, 30));
        JLabel l2 = new JLabel("");
        l2.setPreferredSize(new Dimension(20, 15));
        p.add(l1, new RCPosition(2, 2));
        p.add(l2, new RCPosition(3, 3));
        Dimension dim = p.getPreferredSize();

        assertEquals(152, dim.getWidth());
        assertEquals(158, dim.getHeight());
    }

    @Test
    public void testPreferredSize() {
        JPanel p = new JPanel(new CalcLayout(2));
        JLabel l1 = new JLabel("");
        l1.setPreferredSize(new Dimension(108, 15));
        JLabel l2 = new JLabel("");
        l2.setPreferredSize(new Dimension(16, 30));
        p.add(l1, new RCPosition(1, 1));
        p.add(l2, new RCPosition(3, 3));
        Dimension dim = p.getPreferredSize();

        assertEquals(152, dim.getWidth());
        assertEquals(158, dim.getHeight());
    }

}
