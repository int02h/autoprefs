package com.dpforge.autoprefs.processor;

import javax.lang.model.type.TypeMirror;

class PrefInfo {
    private final String javaName;

    private final String key;

    private final TypeMirror type;

    private final DefaultValue defaultValue;

    private PrefInfo(final Builder builder) {
        javaName = builder.javaName;
        key = builder.key;
        type = builder.type;
        defaultValue = builder.defaultValue;
    }

    String getJavaName() {
        return javaName;
    }

    String getKey() {
        return key;
    }

    TypeMirror getType() {
        return type;
    }

    DefaultValue getDefaultValue() {
        return defaultValue;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String javaName;
        private String key;
        private DefaultValue defaultValue;
        private TypeMirror type;

        private Builder() {
        }

        public Builder javaName(final String javaName) {
            this.javaName = javaName;
            return this;
        }

        public Builder key(final String key) {
            this.key = key;
            return this;
        }

        public Builder type(final TypeMirror type) {
            this.type = type;
            return this;
        }

        public Builder defaultValue(final DefaultValue defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        public PrefInfo build() {
            return new PrefInfo(this);
        }
    }
}
