package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Form localization provider.
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

    /**
     * Localization provider.
     */
    private final LocalizationProvider provider;

    /**
     * Instantiates a form localization provider.
     * @param provider Localization provider
     * @param frame The frame to add localization listeners to
     */
    public FormLocalizationProvider(LocalizationProvider provider, JFrame frame) {
        super(provider);

        this.provider = provider;

        frame.addWindowListener(new WindowAdapter() {
            /**
             * Invoked when a window has been opened.
             *
             * @param e
             */
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);

                connect();
            }

            /**
             * Invoked when a window has been closed.
             *
             * @param e
             */
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);

                disconnect();
            }
        });
    }

    /**
     * Localization provider getter.
     * @return Localization provider
     */
    public LocalizationProvider getProvider() {
        return provider;
    }

}
