package com.dpforge.autoprefs.processor;

import com.dpforge.autoprefs.annotation.AutoPrefs;
import com.dpforge.autoprefs.annotation.ClearMethod;
import com.dpforge.autoprefs.annotation.PrefKey;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;

public final class PrefsProcessor extends AbstractProcessor {

    private Messenger messenger;
    private CodeWriter codeWriter;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        final Set<String> supported = new HashSet<>();
        supported.add(AutoPrefs.class.getCanonicalName());
        return supported;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public synchronized void init(final ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messenger = new Messenger(processingEnvironment);
        codeWriter = new CodeWriter(processingEnvironment);
    }

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(AutoPrefs.class)) {
            if (element.getKind() == ElementKind.INTERFACE) {
                processAutoPrefs((TypeElement) element);
            } else {
                messenger.error(element, "Only interfaces can be annotated with " + AutoPrefs.class.getName());
            }
        }
        // We are the only ones handling AutoPrefs annotations
        return true;
    }

    private void processAutoPrefs(final TypeElement type) {
        final PrefsDescriptor.Builder builder = PrefsDescriptor.newBuilder()
                .declarationInterface(type.asType())
                .name(getPrefsName(type))
                .packageName(TypeUtils.getPackageName(type))
                .className(type.getSimpleName() + "_Prefs");

        for (Element element : type.getEnclosedElements()) {
            if (element.getKind() == ElementKind.METHOD) {
                processMethod((ExecutableElement) element, builder);
            }
        }

        codeWriter.write(builder.build(), type);
    }

    private void processMethod(final ExecutableElement element, final PrefsDescriptor.Builder builder) {
        if (element.getReturnType().getKind() == TypeKind.VOID) {
            processVoidMethod(element, builder);
        } else {
            processPrefMethod(element, builder);
        }
    }

    private void processVoidMethod(final ExecutableElement element, final PrefsDescriptor.Builder builder) {
        if (element.getAnnotation(ClearMethod.class) != null) {
            builder.clearMethodName(element.getSimpleName().toString());
        }
    }

    private void processPrefMethod(final ExecutableElement element, final PrefsDescriptor.Builder builder) {
        if (!checkPrefMethod(element)) {
            return;
        }

        final DefaultValueExtractor extractor = new DefaultValueExtractor(messenger);
        final PrefInfo info = PrefInfo.newBuilder()
                .javaName(element.getSimpleName().toString())
                .key(getPrefKey(element))
                .type(element.getReturnType())
                .defaultValue(extractor.extract(element))
                .build();
        builder.addPref(info);
    }

    private boolean checkPrefMethod(final ExecutableElement element) {
        final String basePref = "com.dpforge.autoprefs.BasePref";
        if (!TypeUtils.isDescendantOf(element.getReturnType(), basePref)) {
            messenger.error(element, "Preference's type must be descendant of com.dpforge.autoprefs.BasePref");
            return false;
        }
        if (TypeUtils.isSameType(element.getReturnType(), basePref)) {
            messenger.error(element, "Preference's type cannot be com.dpforge.autoprefs.BasePref");
            return false;
        }
        return true;
    }

    private static String getPrefsName(final TypeElement element) {
        final AutoPrefs annotation = element.getAnnotation(AutoPrefs.class);
        if (!annotation.value().isEmpty()) {
            return annotation.value();
        }
        return element.getSimpleName().toString();
    }

    private static String getPrefKey(final ExecutableElement element) {
        final PrefKey annotation = element.getAnnotation(PrefKey.class);
        if (annotation != null && !annotation.value().isEmpty()) {
            return annotation.value();
        }
        return element.getSimpleName().toString();
    }
}
