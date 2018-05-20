package com.dpforge.autoprefs;

import android.content.SharedPreferences;

/**
 * Base class for every preference
 * @param <T> preference type
 */
public abstract class BasePref<T> {

    protected final SharedPreferences prefs;

    protected final String key;

    protected BasePref(final SharedPreferences prefs, final String key) {
        this.prefs = prefs;
        this.key = key;
    }

    /**
     * Return value of the preference or default value if key does not exist
     */
    public abstract T get();

    /**
     * Return value of the preference or value passed as argument if key does not exist
     */
    public abstract T getOrDefault(T defaultValue);

    /**
     * Set value of the preference
     */
    public abstract void set(T value);

    /**
     * Check if preference exists
     */
    public boolean contains() {
        return prefs.contains(key);
    }

    /**
     * Remove preference
     */
    public void remove() {
        prefs.edit().remove(key).apply();
    }
}
