package hr.fer.oprpp1.hw08.jnotepadpp.util;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

/**
 * Utility class for icon usage.
 */
public class JNotepadIcon {

    /**
     * Factory for a single icon.
     * @param path Icon path
     * @param parent Icon parent path
     * @return ImageIcon built from the icon path
     */
    public static ImageIcon createSingleIcon(String path, Object parent) {
        try {
            InputStream is = parent.getClass().getResourceAsStream(path);
            if (is == null) {
                throw new IllegalArgumentException("Invalid path!");
            }
            byte[] bytes = is.readAllBytes();
            is.close();

            return new ImageIcon(
                    new ImageIcon(bytes).getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT)
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
