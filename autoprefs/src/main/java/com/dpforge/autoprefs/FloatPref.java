package com.dpforge.autoprefs;

import android.content.SharedPreferences;

/**
 * Represent preference holding float value
 */
public class FloatPref extends BasePref<Float> {

    protected final float defaultValue;

    public FloatPref(final SharedPreferences prefs, final String key) {
        this(prefs, key, 0f);
    }

    public FloatPref(final SharedPreferences prefs, final String key, final float defaultValue) {
        super(prefs, key);
        this.defaultValue = defaultValue;
    }

    @Override
    public Float get() {
        return getFloat();
    }

    @Override
    public Float getOrDefault(final Float defaultValue) {
        return getOrDefaultFloat(defaultValue);
    }

    @Override
    public void set(final Float value) {
        setFloat(value);
    }

    /**
     * Same as {@link #get()} but for those who care about boxing
     */
    public float getFloat() {
        return getOrDefaultFloat(defaultValue);
    }

    /**
     * Same as {@link #getOrDefault(Float)} but for those who care about boxing
     */
    public float getOrDefaultFloat(final float defaultValue) {
        return prefs.getFloat(key, defaultValue);
    }

    /**
     * Same as {@link #set(Float)} but for those who care about boxing
     */
    public void setFloat(final float value) {
        prefs.edit().putFloat(key, value).apply();
    }
}
