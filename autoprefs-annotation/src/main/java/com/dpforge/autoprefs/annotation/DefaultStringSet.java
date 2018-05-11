package com.dpforge.autoprefs.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
@PrefDefaultValue("com.dpforge.autoprefs.StringSetPref")
public @interface DefaultStringSet {
    String[] value();
}
