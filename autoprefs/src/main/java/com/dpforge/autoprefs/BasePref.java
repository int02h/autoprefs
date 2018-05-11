package com.dpforge.autoprefs;

import android.content.SharedPreferences;

public abstract class BasePref<T> {

    protected final SharedPreferences prefs;

    protected final String key;

    protected BasePref(final SharedPreferences prefs, final String key) {
        this.prefs = prefs;
        this.key = key;
    }

    public abstract T get();

    public abstract T getOrDefault(T defaultValue);

    public abstract void set(T value);

    public boolean contains() {
        return prefs.contains(key);
    }

    public void remove() {
        prefs.edit().remove(key).apply();
    }
}
