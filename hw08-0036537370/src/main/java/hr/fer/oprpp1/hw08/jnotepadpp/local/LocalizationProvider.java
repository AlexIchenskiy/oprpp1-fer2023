package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationProvider extends AbstractLocalizationProvider {

    private String language;

    private ResourceBundle bundle;

    private static final LocalizationProvider provider = new LocalizationProvider();

    private LocalizationProvider() {
        this.language = "hr";
        this.bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi",
                Locale.forLanguageTag(this.language),
                ResourceBundle.Control.getControl(ResourceBundle.Control.FORMAT_PROPERTIES));
    }

    public static LocalizationProvider getInstance() {
        return provider;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
        this.bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi",
                Locale.forLanguageTag(this.language),
                ResourceBundle.Control.getControl(ResourceBundle.Control.FORMAT_PROPERTIES));
        this.fire();
    }

    @Override
    public String getString(String key) {
        return new String(this.bundle.getString(key).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }

    @Override
    public String getCurrentLanguage() {
        return this.language;
    }

}
