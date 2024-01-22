package hr.fer.oprpp1.hw08.jnotepadpp.util;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;

import javax.swing.*;

public class JNotepadUserInteraction {

    public static int warningConfirmationWindow(String messageKey, ILocalizationProvider provider) {
        return JOptionPane.showConfirmDialog(
                null,
                provider.getString(messageKey),
                provider.getString("warning"),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
    }

    public static int optionConfirmationWindow(String messageKey, String[] options, ILocalizationProvider provider,
                                               String postfix) {
        String[] optionsText = new String[options.length];

        for (int i = 0; i < options.length; i++) optionsText[i] = provider.getString(options[i]);

        return JOptionPane.showOptionDialog(
                null,
                provider.getString(messageKey) + (postfix != null ? postfix : ""),
                provider.getString("warning"),
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                optionsText,
                optionsText[0]
        );
    }

    public static void infoWindow(String messageKey, ILocalizationProvider provider, String document, int allChars,
                                  int allNonEmptyChars, int lines) {
        JOptionPane.showMessageDialog(
                null,
                String.format(provider.getString(messageKey), document, allChars, allNonEmptyChars, lines),
                provider.getString("info"),
                JOptionPane.INFORMATION_MESSAGE
        );
    }

}
