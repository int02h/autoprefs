package com.dpforge.autoprefs.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.lang.model.type.TypeMirror;

class PrefsDescriptor {

    private final TypeMirror declarationInterface;

    private final String name;

    private final String packageName;

    private final String className;

    private final List<PrefInfo> prefs;

    private final String clearMethodName;

    private PrefsDescriptor(final Builder builder) {
        declarationInterface = builder.declarationInterface;
        name = builder.name;
        packageName = builder.packageName;
        className = builder.className;
        prefs = builder.prefs;
        clearMethodName = builder.clearMethodName;
    }

    TypeMirror getDeclarationInterface() {
        return declarationInterface;
    }

    String getName() {
        return name;
    }

    String getPackageName() {
        return packageName;
    }

    String getClassName() {
        return className;
    }

    List<PrefInfo> getPrefs() {
        return Collections.unmodifiableList(prefs);
    }

    boolean hasClearMethod() {
        return clearMethodName != null;
    }

    String getClearMethodName() {
        return clearMethodName;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private TypeMirror declarationInterface;
        private String name;
        private String packageName;
        private String className;
        private String clearMethodName;
        private final List<PrefInfo> prefs = new ArrayList<>();

        private Builder() {
        }

        public Builder declarationInterface(final TypeMirror declarationInterface) {
            this.declarationInterface = declarationInterface;
            return this;
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder packageName(final String packageName) {
            this.packageName = packageName;
            return this;
        }

        public Builder className(final String className) {
            this.className = className;
            return this;
        }

        public Builder addPref(final PrefInfo prefInfo) {
            this.prefs.add(prefInfo);
            return this;
        }

        public Builder clearMethodName(final String clearMethodName) {
            this.clearMethodName = clearMethodName;
            return this;
        }

        public PrefsDescriptor build() {
            return new PrefsDescriptor(this);
        }
    }
}
