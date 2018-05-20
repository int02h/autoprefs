package com.dpforge.autoprefs.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Set up default value of the String set preference
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
@PrefDefaultValue("com.dpforge.autoprefs.StringSetPref")
public @interface DefaultStringSet {
    String[] value();
}
