package com.dpforge.autoprefs.processor;

import javax.lang.model.element.AnnotationValue;
import javax.lang.model.type.TypeMirror;

class DefaultValue {

    final TypeMirror type;

    final AnnotationValue value;

    DefaultValue(final TypeMirror type, final AnnotationValue value) {
        this.type = type;
        this.value = value;
    }
}
