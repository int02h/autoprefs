package com.dpforge.autoprefs.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeKind;

class CodeGenerator {

    private static final ClassName ANDROID_CONTEXT = ClassName.get("android.content", "Context");
    private static final ClassName SHARED_PREFERENCES = ClassName.get("android.content", "SharedPreferences");

    String generate(final PrefsDescriptor descriptor) {
        final TypeSpec.Builder builder = TypeSpec.classBuilder(descriptor.getClassName())
                .addSuperinterface(TypeName.get(descriptor.getDeclarationInterface()))
                .addModifiers(Modifier.PUBLIC);

        builder.addField(SHARED_PREFERENCES, "prefs", Modifier.PRIVATE, Modifier.FINAL);

        addPrefFields(builder, descriptor);
        addConstructor(builder, descriptor);
        addPrefMethods(builder, descriptor);
        addClearMethod(builder, descriptor);

        final JavaFile source = JavaFile.builder(descriptor.getPackageName(), builder.build())
                .indent("    ")
                .build();

        return source.toString();
    }

    private static void addPrefFields(final TypeSpec.Builder builder, final PrefsDescriptor descriptor) {
        for (PrefInfo prefInfo : descriptor.getPrefs()) {
            builder.addField(TypeName.get(prefInfo.getType()), prefInfo.getJavaName(),
                    Modifier.PRIVATE, Modifier.FINAL);
        }
    }

    private static void addConstructor(final TypeSpec.Builder typeBuilder, final PrefsDescriptor descriptor) {
        final ParameterSpec contextParam = ParameterSpec.builder(ANDROID_CONTEXT,
                "context")
                .build();
        final MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(contextParam)
                .addStatement("prefs = $N.getSharedPreferences($S, $L)", contextParam, descriptor.getName(), 0);
        for (PrefInfo prefInfo : descriptor.getPrefs()) {
            constructorBuilder.addStatement(getAssignStatement(prefInfo), TypeName.get(prefInfo.getType()), prefInfo.getKey());
        }
        typeBuilder.addMethod(constructorBuilder.build());
    }

    private static void addPrefMethods(final TypeSpec.Builder builder, final PrefsDescriptor descriptor) {
        for (PrefInfo prefInfo : descriptor.getPrefs()) {
            final MethodSpec method = MethodSpec.methodBuilder(prefInfo.getJavaName())
                    .addModifiers(Modifier.PUBLIC)
                    .addStatement("return " + prefInfo.getJavaName())
                    .returns(TypeName.get(prefInfo.getType()))
                    .addAnnotation(Override.class)
                    .build();
            builder.addMethod(method);
        }
    }

    private static void addClearMethod(final TypeSpec.Builder builder, final PrefsDescriptor descriptor) {
        if (descriptor.hasClearMethod()) {
            final MethodSpec clearMethod = MethodSpec.methodBuilder(descriptor.getClearMethodName())
                    .addModifiers(Modifier.PUBLIC)
                    .addStatement("prefs.edit().clear().apply()")
                    .build();
            builder.addMethod(clearMethod);
        }
    }

    private static String getAssignStatement(final PrefInfo prefInfo) {
        final DefaultValue defaultValue = prefInfo.getDefaultValue();
        if (defaultValue == null) {
            return "" + prefInfo.getJavaName() + " = new $T(prefs, $S)";
        } else {
            return "" + prefInfo.getJavaName() + " = new $T(prefs, $S, " + getRepresentation(defaultValue) + ")";
        }
    }

    private static String getRepresentation(final DefaultValue defaultValue) {
        if (defaultValue.type.getKind() == TypeKind.ARRAY) {
            return "new " + TypeName.get(defaultValue.type) + defaultValue.value.toString();
        }
        return defaultValue.value.toString();
    }
}
