package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for the localization provider.
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

    /**
     * List of provider listeners.
     */
    private final List<ILocalizationListener> listeners;

    /**
     * Creates a localization provider with no listeners.
     */
    public AbstractLocalizationProvider() {
        this.listeners = new ArrayList<>();
    }

    /**
     * Adds a localization listener to the current provider.
     * @param listener Localization listener
     */
    @Override
    public void addLocalizationListener(ILocalizationListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes a localization listener from the current provider.
     * @param listener Localization listener
     */
    @Override
    public void removeLocalizationListener(ILocalizationListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notify all listeners about localization change.
     */
    public void fire() {
        this.listeners.forEach(ILocalizationListener::localizationChanged);
    }

}
