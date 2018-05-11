package com.dpforge.autoprefs.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
@PrefDefaultValue("com.dpforge.autoprefs.BooleanPref")
public @interface DefaultBoolean {
    boolean value();
}
