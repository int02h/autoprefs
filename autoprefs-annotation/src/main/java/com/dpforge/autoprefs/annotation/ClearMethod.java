package com.dpforge.autoprefs.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for method that intended to clear all values in SharedPreferences
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface ClearMethod {
}
