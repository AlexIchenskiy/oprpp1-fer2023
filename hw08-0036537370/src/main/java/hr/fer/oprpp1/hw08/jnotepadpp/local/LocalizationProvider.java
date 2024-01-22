package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Localization provider implementation.
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

    /**
     * Language tag.
     */
    private String language;

    /**
     * Resource bundle aka mapped string localized values by string keys.
     */
    private ResourceBundle bundle;

    /**
     * Singleton localization provider.
     */
    private static final LocalizationProvider provider = new LocalizationProvider();

    /**
     * Creates a default localization provider for the Croatian language.
     */
    private LocalizationProvider() {
        this.language = "hr";
        this.bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi",
                Locale.forLanguageTag(this.language),
                ResourceBundle.Control.getControl(ResourceBundle.Control.FORMAT_PROPERTIES));
    }

    /**
     * Get a singleton instance of the localization provider.
     * @return Localization provider
     */
    public static LocalizationProvider getInstance() {
        return provider;
    }

    /**
     * Language setter.
     * @param language New language tag
     */
    public void setLanguage(String language) {
        this.language = language;
        this.bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi",
                Locale.forLanguageTag(this.language),
                ResourceBundle.Control.getControl(ResourceBundle.Control.FORMAT_PROPERTIES));
        this.fire();
    }

    /**
     * Getter for the localized string value.
     * @param key Name key
     * @return Localized string value
     */
    @Override
    public String getString(String key) {
        return new String(this.bundle.getString(key).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }

    /**
     * Getter for the current language.
     * @return Current language tag
     */
    @Override
    public String getCurrentLanguage() {
        return this.language;
    }

}
