package com.dpforge.autoprefs.processor

import android.content.Context
import com.dpforge.autoprefs.annotation.AutoPrefs
import org.junit.Test

import javax.tools.Diagnostic

import static org.mockito.Mockito.verify
import static org.mockito.Mockito.when

class PrefsProcessorTest {

    @Test
    void className() {
        final Context context = new Context()
        compile("""
                package test;
                import com.dpforge.autoprefs.annotation.*;
                @AutoPrefs
                interface Foo {}""", context)
        assert context.lastPrefsName == 'Foo'
    }

    @Test
    void annotationName() {
        final Context context = new Context()
        compile("""
                package test;
                import com.dpforge.autoprefs.annotation.*;
                @AutoPrefs("Bar")
                interface Foo {}""", context)
        assert context.lastPrefsName == 'Bar'
    }

    @Test
    void methodKey() {
        def prefs = compile("""
                package test;
                import com.dpforge.autoprefs.annotation.*;
                import com.dpforge.autoprefs.*;
                @AutoPrefs
                interface Foo {
                    IntPref value();
                }""")
        assert 'value' == prefs.value.key
    }

    @Test
    void annotationKey() {
        def prefs = compile("""
                package test;
                import com.dpforge.autoprefs.annotation.*;
                import com.dpforge.autoprefs.*;
                @AutoPrefs
                interface Foo {
                    @PrefKey("custom-key")
                    IntPref value();
                }""")
        assert 'custom-key' == prefs.value.key
    }

    @Test
    void clearMethod() {
        final Context context = new Context()
        when(context.editorMock.clear()).thenReturn(context.editorMock)

        def prefs = compile("""
                package test;
                import com.dpforge.autoprefs.annotation.*;
                import com.dpforge.autoprefs.*;
                @AutoPrefs
                interface Foo {
                    @ClearMethod
                    void reset();
                }""", context)
        prefs.reset()

        verify(context.prefsMock).edit()
        verify(context.editorMock).clear()
        verify(context.editorMock).apply()
    }

    @Test
    void nonInterface() {
        final List<CompilationError> errors = new ArrayList<>()
        compile("""
                package test;
                import com.dpforge.autoprefs.annotation.*;
                @AutoPrefs class Foo {}
                """, errors)
        assert errors.size() == 1
        def error = errors.get(0)
        assert error.kind == Diagnostic.Kind.ERROR
        assert error.message == "Only interfaces can be annotated with " + AutoPrefs.class.name
    }

    @Test
    void badPrefType() {
        final List<CompilationError> errors = new ArrayList<>()
        compile("""
                package test;
                import com.dpforge.autoprefs.annotation.*;
                import java.util.ArrayList;
                @AutoPrefs 
                interface Foo {
                    ArrayList<String> bar();
                }
                """, errors)
        assert errors.size() == 1
        def error = errors.get(0)
        assert error.kind == Diagnostic.Kind.ERROR
        assert error.message == 'Preference\'s type must be descendant of com.dpforge.autoprefs.BasePref'
    }

    @Test
    void badPrefPrimitiveType() {
        final List<CompilationError> errors = new ArrayList<>()
        compile("""
                package test;
                import com.dpforge.autoprefs.annotation.*;
                import java.util.ArrayList;
                @AutoPrefs 
                interface Foo {
                    int bar();
                }
                """, errors)
        assert errors.size() == 1
        def error = errors.get(0)
        assert error.kind == Diagnostic.Kind.ERROR
        assert error.message == 'Preference\'s type must be descendant of com.dpforge.autoprefs.BasePref'
    }

    @Test
    void basePrefType() {
        final List<CompilationError> errors = new ArrayList<>()
        compile("""
                package test;
                import com.dpforge.autoprefs.annotation.*;
                import com.dpforge.autoprefs.*;
                @AutoPrefs 
                interface Foo {
                    BasePref bar();
                }
                """, errors)
        assert errors.size() == 1
        def error = errors.get(0)
        assert error.kind == Diagnostic.Kind.ERROR
        assert error.message == 'Preference\'s type cannot be com.dpforge.autoprefs.BasePref'
    }

    @Test
    void nestedClass() {
        final Context context = new Context()
        compile("""
                package test;
                import com.dpforge.autoprefs.annotation.*;
                class Bar {
                    @AutoPrefs
                    interface Foo {} 
                }
                """, context)
        assert context.lastPrefsName == 'Foo'
    }

    private static Object compile(String code) {
        return compile(code, new Context())
    }

    private static Object compile(String code, Context context) {
        return TestCompiler.create()
                .packageName("test")
                .className("Foo")
                .code(code)
                .context(context)
                .compile()
    }

    private static Object compile(String code, List<CompilationError> compilationErrors) {
        return TestCompiler.create()
                .packageName("test")
                .className("Foo")
                .code(code)
                .compilationErrors(compilationErrors)
                .compile()
    }
}