package com.dpforge.autoprefs.processor;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

class CodeWriter {
    private final ProcessingEnvironment processingEnv;

    CodeWriter(final ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
    }

    void write(final PrefsDescriptor descriptor, final TypeElement element) {
        try {
            writeInternal(descriptor, element);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void writeInternal(final PrefsDescriptor descriptor, final TypeElement element) throws IOException {
        final CodeGenerator generator = new CodeGenerator();
        JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(descriptor.getPackageName() + "." + descriptor.getClassName(), element);
        try (Writer writer = sourceFile.openWriter()) {
            writer.write(generator.generate(descriptor));
        }
    }
}
