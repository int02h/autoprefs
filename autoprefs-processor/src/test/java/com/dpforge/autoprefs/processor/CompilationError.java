package com.dpforge.autoprefs.processor;

import java.util.Locale;

import javax.tools.Diagnostic;

public class CompilationError {

    private final Diagnostic.Kind kind;
    private final String message;
    private final long lineNumber;

    CompilationError(final Diagnostic<?> diagnostic) {
        this.kind = diagnostic.getKind();
        this.message = diagnostic.getMessage(Locale.US);
        this.lineNumber = diagnostic.getLineNumber();
    }

    public Diagnostic.Kind getKind() {
        return kind;
    }

    public String getMessage() {
        return message;
    }

    public long getLineNumber() {
        return lineNumber;
    }
}
