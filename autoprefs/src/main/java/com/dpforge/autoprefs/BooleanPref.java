package com.dpforge.autoprefs;

import android.content.SharedPreferences;

/**
 * Represent preference holding boolean value
 */
public class BooleanPref extends BasePref<Boolean> {

    protected final boolean defaultValue;

    public BooleanPref(final SharedPreferences prefs, final String key) {
        this(prefs, key, false);
    }

    public BooleanPref(final SharedPreferences prefs, final String key, final boolean defaultValue) {
        super(prefs, key);
        this.defaultValue = defaultValue;
    }

    @Override
    public Boolean get() {
        return getBoolean();
    }

    @Override
    public Boolean getOrDefault(final Boolean defaultValue) {
        return getOrDefaultBoolean(defaultValue);
    }

    @Override
    public void set(final Boolean value) {
        setBoolean(value);
    }

    /**
     * Same as {@link #get()} but for those who care about boxing
     */
    public boolean getBoolean() {
        return getOrDefaultBoolean(defaultValue);
    }

    /**
     * Same as {@link #getOrDefault(Boolean)} but for those who care about boxing
     */
    public boolean getOrDefaultBoolean(final boolean defaultValue) {
        return prefs.getBoolean(key, defaultValue);
    }

    /**
     * Same as {@link #set(Boolean)} but for those who care about boxing
     */
    public void setBoolean(final boolean value) {
        prefs.edit().putBoolean(key, value).apply();
    }
}
