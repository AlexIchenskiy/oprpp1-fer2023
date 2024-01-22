package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.Objects;

public class LocalizationProviderBridge extends AbstractLocalizationProvider {

    private boolean connected;

    private String cachedLanguage;

    private final ILocalizationProvider provider;

    private final ILocalizationListener listener;

    public LocalizationProviderBridge(ILocalizationProvider provider) {
        this.connected = false;
        this.cachedLanguage = null;
        this.provider = provider;
        this.listener = this::fire;
    }

    public void disconnect() {
        if (!connected) return;

        this.connected = false;
        this.provider.removeLocalizationListener(this.listener);
        this.cachedLanguage = this.provider.getCurrentLanguage();
    }

    public void connect() {
        if (connected) return;

        this.connected = true;
        this.provider.addLocalizationListener(this.listener);
        if (this.cachedLanguage != null &&
                !Objects.equals(this.provider.getCurrentLanguage(), this.cachedLanguage)) {
            this.cachedLanguage = this.provider.getCurrentLanguage();
        }
    }

    @Override
    public String getString(String key) {
        return this.provider.getString(key);
    }

    @Override
    public String getCurrentLanguage() {
        return this.provider.getCurrentLanguage();
    }

}
