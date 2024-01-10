package hr.fer.oprpp1.hw07.gui.layouts;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RCPositionTest {

    @Test
    public void testIllegalFormat() {
        assertThrows(IllegalArgumentException.class, () -> RCPosition.parse("35"));
        assertThrows(IllegalArgumentException.class, () -> RCPosition.parse("3.5"));
        assertThrows(IllegalArgumentException.class, () -> RCPosition.parse("3x5"));
        assertThrows(IllegalArgumentException.class, () -> RCPosition.parse("3AAA5"));
    }

    @Test
    public void testIllegalValues() {
        assertThrows(IllegalArgumentException.class, () -> RCPosition.parse("x,y"));
        assertThrows(IllegalArgumentException.class, () -> RCPosition.parse("x,5"));
        assertThrows(IllegalArgumentException.class, () -> RCPosition.parse("3,y"));
        assertThrows(IllegalArgumentException.class, () -> RCPosition.parse("x, "));
    }

    @Test
    public void testLegalPosition() {
        RCPosition rcPosition = RCPosition.parse("3, 5");
        assertEquals(3, rcPosition.getRow());
        assertEquals(5, rcPosition.getColumn());
    }

}
