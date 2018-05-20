package com.dpforge.autoprefs.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Every annotation that represents default preference value must be annotated with this annotation.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.ANNOTATION_TYPE)
public @interface PrefDefaultValue {
    /**
     * Qualified name of the preference holder class
     */
    String[] value();
}
