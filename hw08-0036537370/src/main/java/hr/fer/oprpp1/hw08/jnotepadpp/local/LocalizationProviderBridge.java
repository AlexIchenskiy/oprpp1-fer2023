package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.Objects;

/**
 * Bridge for localization provider connections and disconnections.
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

    /**
     * Boolean representing whether the bridge is connected.
     */
    private boolean connected;

    /**
     * Cached localization language.
     */
    private String cachedLanguage;

    /**
     * Localization provider.
     */
    private final ILocalizationProvider provider;

    /**
     * Localization listener.
     */
    private final ILocalizationListener listener;

    /**
     * Creates a localization bridge.
     * @param provider Localization provider
     */
    public LocalizationProviderBridge(ILocalizationProvider provider) {
        this.connected = false;
        this.cachedLanguage = null;
        this.provider = provider;
        this.listener = this::fire;
    }

    /**
     * Function for the bridge disconnection.
     */
    public void disconnect() {
        if (!connected) return;

        this.connected = false;
        this.provider.removeLocalizationListener(this.listener);
        this.cachedLanguage = this.provider.getCurrentLanguage();
    }

    /**
     * Function for the bridge connection.
     */
    public void connect() {
        if (connected) return;

        this.connected = true;
        this.provider.addLocalizationListener(this.listener);
        if (this.cachedLanguage != null &&
                !Objects.equals(this.provider.getCurrentLanguage(), this.cachedLanguage)) {
            this.cachedLanguage = this.provider.getCurrentLanguage();
        }
    }

    /**
     * Localized string by name key getter.
     * @param key Name key
     * @return Localized string value
     */
    @Override
    public String getString(String key) {
        return this.provider.getString(key);
    }

    /**
     * Current language getter.
     * @return Current language tag
     */
    @Override
    public String getCurrentLanguage() {
        return this.provider.getCurrentLanguage();
    }

}
