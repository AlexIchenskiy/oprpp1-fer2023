package hr.fer.oprpp1.hw08.jnotepadpp.util;

import javax.swing.*;

public class JNotepadUserInteraction {

    public static int warningConfirmationWindow(String message) {
        return JOptionPane.showConfirmDialog(
                null,
                message,
                "Warning",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
    }

    public static int optionConfirmationWindow(String message, String[] options) {
        return JOptionPane.showOptionDialog(
                null,
                message,
                "Warning",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
    }

    public static void infoWindow(String message) {
        JOptionPane.showMessageDialog(
                null,
                message,
                "Info",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

}
