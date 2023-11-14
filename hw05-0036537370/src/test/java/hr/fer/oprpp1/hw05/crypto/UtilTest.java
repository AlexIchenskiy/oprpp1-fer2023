package hr.fer.oprpp1.hw05.crypto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UtilTest {

    @Test
    public void testHexToByteNull() {
        assertThrows(NullPointerException.class, () -> Util.hextobyte(null));
    }

    @Test
    public void testHexToByteInvalidLength() {
        assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("0"));
    }

    @Test
    public void testHexToByteInvalidFormat() {
        assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("0W"));
    }

    @Test
    public void testHexToByteEmpty() {
        assertArrayEquals(new byte[] {}, Util.hextobyte(""));
    }

    @Test
    public void testHexToByteValidCombined() {
        assertArrayEquals(new byte[] {1, -82, 34}, Util.hextobyte("01aE22"));
    }

    @Test
    public void testHexToByteValidDecimal() {
        assertArrayEquals(new byte[] {1, 2, 3}, Util.hextobyte("010203"));
    }

    @Test
    public void testHexToByteValidUppercase() {
        assertArrayEquals(new byte[] {-86, -69, -52}, Util.hextobyte("AABBCC"));
    }

    @Test
    public void testHexToByteValidLowercase() {
        assertArrayEquals(new byte[] {-86, -69, -52}, Util.hextobyte("aabbcc"));
    }

    @Test
    public void testByteToHexNull() {
        assertThrows(NullPointerException.class, () -> Util.bytetohex(null));
    }

    @Test
    public void testByteToHexEmpty() {
        assertEquals("", Util.bytetohex(new byte[] {}));
    }

    @Test
    public void testByteToHexCombined() {
        assertEquals("01ae22", Util.bytetohex(new byte[] {1, -82, 34}));
    }

    @Test
    public void testByteToHexDecimal() {
        assertEquals("010203", Util.bytetohex(new byte[] {1, 2, 3}));
    }

    @Test
    public void testByteToHexHexadecimal() {
        assertEquals("aabbcc", Util.bytetohex(new byte[] {-86, -69, -52}));
    }

}
