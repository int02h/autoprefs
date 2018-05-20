package com.dpforge.autoprefs.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for interfaces that represents SharedPreferences.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface AutoPrefs {
    /**
     * Name of the SharedPreferences
     */
    String value() default "";
}
