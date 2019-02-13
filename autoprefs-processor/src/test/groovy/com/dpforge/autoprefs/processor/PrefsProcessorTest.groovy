package com.dpforge.autoprefs.processor

import android.content.Context
import com.dpforge.autoprefs.annotation.AutoPrefs
import com.dpforge.ocubator.CompilationResult
import org.junit.Test

import static org.mockito.Mockito.verify
import static org.mockito.Mockito.when

class PrefsProcessorTest extends BaseCompilationTest {

    @Test
    void className() {
        final Context context = new Context()
        successCompile("""
                package test;
                import com.dpforge.autoprefs.annotation.*;
                @AutoPrefs
                interface Foo {}""", context)
        assert context.lastPrefsName == 'Foo'
    }

    @Test
    void annotationName() {
        final Context context = new Context()
        successCompile("""
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

        def prefs = successCompile("""
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
        final def errors = failCompile("""
                package test;
                import com.dpforge.autoprefs.annotation.*;
                @AutoPrefs class Foo {}
                """)
        assert errors.size() == 1
        def error = errors.get(0)
        assert error.message == "Only interfaces can be annotated with " + AutoPrefs.class.name
    }

    @Test
    void badPrefType() {
        final def errors = failCompile("""
                package test;
                import com.dpforge.autoprefs.annotation.*;
                import java.util.ArrayList;
                @AutoPrefs 
                interface Foo {
                    ArrayList<String> bar();
                }
                """)
        assert errors.size() == 1
        def error = errors.get(0)
        assert error.message == 'Preference\'s type must be descendant of com.dpforge.autoprefs.BasePref'
    }

    @Test
    void badPrefPrimitiveType() {
        final def errors = failCompile("""
                package test;
                import com.dpforge.autoprefs.annotation.*;
                import java.util.ArrayList;
                @AutoPrefs 
                interface Foo {
                    int bar();
                }
                """)
        assert errors.size() == 1
        def error = errors.get(0)
        assert error.message == 'Preference\'s type must be descendant of com.dpforge.autoprefs.BasePref'
    }

    @Test
    void basePrefType() {
        final def errors = failCompile("""
                package test;
                import com.dpforge.autoprefs.annotation.*;
                import com.dpforge.autoprefs.*;
                @AutoPrefs 
                interface Foo {
                    BasePref bar();
                }
                """)
        assert errors.size() == 1
        def error = errors.get(0)
        assert error.message == 'Preference\'s type cannot be com.dpforge.autoprefs.BasePref'
    }

    @Test
    void nestedClass() {
        final Context context = new Context()
        successCompile("""
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
        return successCompile(code, new Context())
    }

    private static Object successCompile(String code, Context context) {
        CompilationResult result = getCompilatiomResult(code)
        assert result.success
        return result.newInstanceOf('test.Foo_Prefs')
                .withConstructor(Context.class)
                .please(context)
    }

    private static Object failCompile(String code) {
        CompilationResult result = getCompilatiomResult(code)
        assert !result.success
        return result.errors
    }
}