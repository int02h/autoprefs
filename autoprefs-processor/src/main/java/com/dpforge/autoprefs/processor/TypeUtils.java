package com.dpforge.autoprefs.processor;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleTypeVisitor7;

class TypeUtils {
    private TypeUtils() {
    }

    static boolean isDescendantOf(final TypeMirror descendant, final String superQualifiedName) {
        TypeElement type = asTypeElement(descendant);
        if (type == null) {
            return false;
        }
        while (type != null && !isSameType(type, superQualifiedName)) {
            type = asTypeElement(type.getSuperclass());
        }
        return type != null;
    }

    static boolean isSameType(final TypeMirror typeMirror, final String qualifiedName) {
        TypeElement type = asTypeElement(typeMirror);
        return type != null && isSameType(type, qualifiedName);
    }

    static String getPackageName(final Element element) {
        Element enclosing = element.getEnclosingElement();
        while (enclosing.getKind() != ElementKind.PACKAGE) {
            enclosing = enclosing.getEnclosingElement();
        }
        return ((PackageElement) enclosing).getQualifiedName().toString();
    }

    private static boolean isSameType(final TypeElement element, final String superQualifiedName) {
        return superQualifiedName.equals(element.getQualifiedName().toString());
    }

    private static TypeElement asTypeElement(final TypeMirror typeMirror) {
        return typeMirror.accept(new SimpleTypeVisitor7<TypeElement, Void>() {
            @Override
            public TypeElement visitDeclared(final DeclaredType declaredType, final Void aVoid) {
                return (TypeElement) declaredType.asElement();
            }
        }, null);
    }
}
