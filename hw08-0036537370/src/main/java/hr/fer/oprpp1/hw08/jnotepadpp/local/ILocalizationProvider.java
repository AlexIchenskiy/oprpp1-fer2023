package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * An interface for the localization provider.
 */
public interface ILocalizationProvider {

    /**
     * Adds a localization listener to the current provider.
     * @param listener Localization listener
     */
    void addLocalizationListener(ILocalizationListener listener);

    /**
     * Removes a localization listener from the current provider.
     * @param listener Localization listener
     */
    void removeLocalizationListener(ILocalizationListener listener);

    /**
     * Get localized string by the key.
     * @param key Name key
     * @return Localized text value
     */
    String getString(String key);

    String getCurrentLanguage();

}
