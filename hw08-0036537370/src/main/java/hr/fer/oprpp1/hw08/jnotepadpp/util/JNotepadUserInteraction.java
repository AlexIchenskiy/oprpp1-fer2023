package hr.fer.oprpp1.hw08.jnotepadpp.util;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;

import javax.swing.*;

/**
 * Class representing some notepad dialog windows for interaction with user.
 */
public class JNotepadUserInteraction {

    /**
     * Yes/No window on warning level.
     * @param messageKey Message localization key
     * @param provider Localization provider
     * @return Selected value
     */
    public static int warningConfirmationWindow(String messageKey, ILocalizationProvider provider) {
        return JOptionPane.showConfirmDialog(
                null,
                provider.getString(messageKey),
                provider.getString("warning"),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
    }

    /**
     * Yes/No/Cancel window on warning level.
     * @param messageKey Message localization key
     * @param options Dialog options (Yes/No/Cancel respectively)
     * @param provider Localization provider
     * @param postfix Message postfix
     * @return Selected value
     */
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

    /**
     * Window for showing the file stats.
     * @param messageKey Message localization key
     * @param provider Localization provider
     * @param document Document name
     * @param allChars Number of all chars
     * @param allNonEmptyChars Number of all non-empty chars
     * @param lines Number of lines
     */
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
