package com.dpforge.autoprefs;

import android.content.SharedPreferences;

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

    public boolean getBoolean() {
        return getOrDefaultBoolean(defaultValue);
    }

    public boolean getOrDefaultBoolean(final boolean defaultValue) {
        return prefs.getBoolean(key, defaultValue);
    }

    public void setBoolean(final boolean value) {
        prefs.edit().putBoolean(key, value).apply();
    }
}
