package com.dpforge.autoprefs.processor;

import com.dpforge.autoprefs.annotation.PrefDefaultValue;

import java.util.Map;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;

class DefaultValueExtractor {
    private final Messenger messenger;

    DefaultValueExtractor(final Messenger messenger) {
        this.messenger = messenger;
    }

    DefaultValue extract(final ExecutableElement element) {
        final AnnotationMirror annotation = getDefaultValueAnnotation(element);
        if (annotation == null) {
            return null;
        }
        final DefaultValue value = getDefaultValue(annotation);
        if (value == null) {
            messenger.error(element, "Default value annotation has no value()");
        }
        return value;
    }

    private AnnotationMirror getDefaultValueAnnotation(final ExecutableElement element) {
        for (AnnotationMirror am : element.getAnnotationMirrors()) {
            if (isSupported(am, element)) {
                return am;
            }
        }
        return null;
    }

    private boolean isSupported(final AnnotationMirror annotationMirror,
                                final ExecutableElement element) {
        final TypeElement annotationType = ((TypeElement) annotationMirror.getAnnotationType().asElement());
        final PrefDefaultValue annotation = annotationType.getAnnotation(PrefDefaultValue.class);
        if (annotation == null) {
            return false;
        }

        final DeclaredType returnType = (DeclaredType) element.getReturnType();
        final String type = ((TypeElement) returnType.asElement()).getQualifiedName().toString();
        for (String className : annotation.value()) {
            if (className.equals(type)) {
                return true;
            }
        }
        messenger.error(element, String.format("Default value for this field cannot be set via '%s' annotation",
                annotationType.getQualifiedName()));
        return false;
    }

    private static DefaultValue getDefaultValue(final AnnotationMirror annotation) {
        for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : annotation.getElementValues().entrySet()) {
            if ("value".equals(entry.getKey().getSimpleName().toString())) {
                return new DefaultValue(entry.getKey().getReturnType(), entry.getValue());
            }
        }
        return null;
    }

}
