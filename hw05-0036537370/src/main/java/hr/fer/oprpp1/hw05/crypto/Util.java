package hr.fer.oprpp1.hw05.crypto;

/**
 * A class with utility functions for working with hex values and arrays of bytes.
 */
public class Util {

    /**
     * Function to convert a string hex value to an array of bytes.
     * @param keyText A string hex value
     * @return Converted array of bytes
     */
    public static byte[] hextobyte(String keyText) {
        if (keyText == null) throw new NullPointerException("Key cant be null!");

        if (keyText.length() == 0) return new byte[] {};

        if (!(keyText.length() % 2 == 0)) throw new IllegalArgumentException("Key cant be of uneven length!");

        if (!(keyText.matches("^[0-9a-fA-F]+$")))
            throw new IllegalArgumentException("Key must have only valid hex digits!");

        byte[] data = new byte[keyText.length() / 2];

        for (int i = 0; i < keyText.length(); i+= 2) {
            data[i / 2] = (byte) ((Character.digit(keyText.charAt(i), 16) << 4) +
                    (Character.digit(keyText.charAt(i + 1), 16)));
        }

        return data;
    }

    /**
     * Function to convert a byte array into a hex string.
     * @param bytearray Array of bytes
     * @return Converted hex string
     */
    public static String bytetohex(byte[] bytearray) {
        if (bytearray == null) throw new NullPointerException("Array cant be null!");

        if (bytearray.length == 0) return "";

        StringBuilder sb = new StringBuilder();

        for (byte b : bytearray) {
            int val = b & 0xFF;
            sb.append(Character.forDigit((val >> 4) & 0xF, 16))
                    .append(Character.forDigit(val & 0xF, 16));
        }

        return sb.toString();
    }

}
