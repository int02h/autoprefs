package com.dpforge.autoprefs;

import android.content.Context;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class PrefsProvider {

    private static final Map<Class<?>, Object> PREFS = new HashMap<>();

    /**
     * Create instance of the generated helper, store it in the memory cache and return it.
     * If there is already created instance then it will be returned.
     * @param prefsClass interface representing preferences
     */
    public synchronized static <T> T get(final Class<T> prefsClass) {
        T instance = prefsClass.cast(PREFS.get(prefsClass));
        if (instance == null) {
            instance = create(prefsClass);
            PREFS.put(prefsClass, instance);
        }
        return instance;
    }

    /**
     * Always return a newly created instance of generated helper
     * @param prefsClass interface representing preferences
     */
    public static <T> T create(final Class<T> prefsClass) {
        try {
            Object instance = Class.forName(getPrefsClassName(prefsClass))
                    .getDeclaredConstructor(Context.class)
                    .newInstance(ContextProvider.context);
            return prefsClass.cast(instance);
        } catch (ClassNotFoundException
                | InvocationTargetException
                | InstantiationException
                | IllegalAccessException
                | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getPrefsClassName(final Class<?> prefsClass) {
        return prefsClass.getPackage().getName() + "." + prefsClass.getSimpleName() + "_Prefs";
    }
}
