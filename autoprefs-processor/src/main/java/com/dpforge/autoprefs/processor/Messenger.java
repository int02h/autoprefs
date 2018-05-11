package com.dpforge.autoprefs.processor;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

class Messenger {

    private final ProcessingEnvironment processingEnv;

    Messenger(final ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
    }

    void error(final Element element, final String message) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, element);
    }
}
