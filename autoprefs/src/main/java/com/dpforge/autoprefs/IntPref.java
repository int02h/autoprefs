package com.dpforge.autoprefs;

import android.content.SharedPreferences;

public class IntPref extends BasePref<Integer> {

    protected final int defaultValue;

    public IntPref(final SharedPreferences prefs, final String key) {
        this(prefs, key, 0);
    }

    public IntPref(final SharedPreferences prefs, final String key, final int defaultValue) {
        super(prefs, key);
        this.defaultValue = defaultValue;
    }

    @Override
    public Integer get() {
        return getInt();
    }

    @Override
    public Integer getOrDefault(final Integer defaultValue) {
        return getOrDefaultInt(defaultValue);
    }

    @Override
    public void set(final Integer value) {
        setInt(value);
    }

    public int getInt() {
        return getOrDefaultInt(defaultValue);
    }

    public int getOrDefaultInt(final int defaultValue) {
        return prefs.getInt(key, defaultValue);
    }

    public void setInt(final int value) {
        prefs.edit().putInt(key, value).apply();
    }
}
