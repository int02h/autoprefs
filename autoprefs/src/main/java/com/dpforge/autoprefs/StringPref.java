package com.dpforge.autoprefs;

import android.content.SharedPreferences;

/**
 * Represent preference holding String value
 */
public class StringPref extends BasePref<String> {

    protected final String defaultValue;

    public StringPref(final SharedPreferences prefs, final String key) {
        this(prefs, key, null);
    }

    public StringPref(final SharedPreferences prefs, final String key, final String defaultValue) {
        super(prefs, key);
        this.defaultValue = defaultValue;
    }

    @Override
    public String get() {
        return getOrDefault(defaultValue);
    }

    @Override
    public String getOrDefault(final String defaultValue) {
        return prefs.getString(key, defaultValue);
    }

    @Override
    public void set(final String value) {
        prefs.edit().putString(key, value).apply();
    }
}
