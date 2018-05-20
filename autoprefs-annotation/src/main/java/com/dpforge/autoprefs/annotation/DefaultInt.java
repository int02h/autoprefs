package com.dpforge.autoprefs.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Set up default value of the int preference
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
@PrefDefaultValue("com.dpforge.autoprefs.IntPref")
public @interface DefaultInt {
    int value();
}
