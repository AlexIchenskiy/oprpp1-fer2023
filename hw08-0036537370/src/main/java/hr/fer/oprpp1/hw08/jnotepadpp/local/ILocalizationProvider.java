package hr.fer.oprpp1.hw08.jnotepadpp.local;

public interface ILocalizationProvider {

    void addLocalizationListener(ILocalizationListener listener);

    void removeLocalizationListener(ILocalizationListener listener);

    String getString(String key);

    String getCurrentLanguage();

}
