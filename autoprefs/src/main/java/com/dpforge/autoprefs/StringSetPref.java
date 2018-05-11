package com.dpforge.autoprefs;

import android.content.SharedPreferences;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class StringSetPref extends BasePref<Set<String>> {

    protected final Set<String> defaultValue;

    public StringSetPref(final SharedPreferences prefs, final String key) {
        super(prefs, key);
        this.defaultValue = null;
    }

    public StringSetPref(final SharedPreferences prefs, final String key, final String[] defaultValue) {
        super(prefs, key);
        this.defaultValue = new HashSet<>(Arrays.asList(defaultValue));
    }

    @Override
    public Set<String> get() {
        return getOrDefault(defaultValue);
    }

    @Override
    public Set<String> getOrDefault(final Set<String> defaultValue) {
        return prefs.getStringSet(key, defaultValue);
    }

    @Override
    public void set(final Set<String> value) {
        prefs.edit().putStringSet(key, value).apply();
    }
}
