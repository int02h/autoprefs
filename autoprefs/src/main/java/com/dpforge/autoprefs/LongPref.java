package com.dpforge.autoprefs;

import android.content.SharedPreferences;

public class LongPref extends BasePref<Long> {

    protected final long defaultValue;

    public LongPref(final SharedPreferences prefs, final String key) {
        this(prefs, key, 0L);
    }

    public LongPref(final SharedPreferences prefs, final String key, final long defaultValue) {
        super(prefs, key);
        this.defaultValue = defaultValue;
    }

    @Override
    public Long get() {
        return getLong();
    }

    @Override
    public Long getOrDefault(final Long defaultValue) {
        return getOrDefaultLong(defaultValue);
    }

    @Override
    public void set(final Long value) {
        setLong(value);
    }

    public long getLong() {
        return getOrDefaultLong(defaultValue);
    }

    public long getOrDefaultLong(final long defaultValue) {
        return prefs.getLong(key, defaultValue);
    }

    public void setLong(final long value) {
        prefs.edit().putLong(key, value).apply();
    }
}
