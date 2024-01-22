package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FormLocalizationProvider extends LocalizationProviderBridge {

    private final LocalizationProvider provider;

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

    public LocalizationProvider getProvider() {
        return provider;
    }

}
