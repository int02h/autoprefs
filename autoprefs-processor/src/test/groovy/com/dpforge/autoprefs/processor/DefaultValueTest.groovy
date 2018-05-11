package com.dpforge.autoprefs.processor

import com.dpforge.autoprefs.annotation.DefaultInt
import org.junit.Test

import javax.tools.Diagnostic

class DefaultValueTest {
    @Test
    void defaultBoolean() {
        def prefs = compile("@DefaultBoolean(true) BooleanPref bar();")
        assert prefs.bar.defaultValue == true
    }

    @Test
    void defaultFloat() {
        def prefs = compile("@DefaultFloat(-123.456f) FloatPref bar();")
        assert prefs.bar.defaultValue == -123.456f
    }

    @Test
    void defaultInt() {
        def prefs = compile("@DefaultInt(-1) IntPref bar();")
        assert prefs.bar.defaultValue == -1f
    }

    @Test
    void defaultLong() {
        def prefs = compile("@DefaultLong(Long.MAX_VALUE) LongPref bar();")
        assert prefs.bar.defaultValue == Long.MAX_VALUE
    }

    @Test
    void defaultString() {
        def prefs = compile("@DefaultString(\"Hello\") StringPref bar();")
        assert prefs.bar.defaultValue == "Hello"
    }

    @Test
    void defaultStringSet() {
        def prefs = compile("@DefaultStringSet({\"q\", \"w\", \"e\"}) StringSetPref bar();")
        assert prefs.bar.defaultValue == ["q", "w", "e"] as Set
    }

    @Test
    void wrongDefaultType() {
        def errorList = new ArrayList<CompilationError>()
        def prefs = compile("@DefaultInt(-1) LongPref bar();", errorList)
        assert prefs == null
        assert errorList.size() == 1

        def error = errorList.get(0)
        assert error.kind == Diagnostic.Kind.ERROR
        assert error.message == "Default value for this field cannot be set via '${DefaultInt.class.name}' annotation"
    }

    @Test
    void wrongDefaultBoolean() {
        def errorList = new ArrayList<CompilationError>()
        compile("""
                @DefaultFloat(-1)     BooleanPref bar1();
                @DefaultInt(-1)       BooleanPref bar2();
                @DefaultLong(-1)      BooleanPref bar3();
                @DefaultString("")    BooleanPref bar4();
                @DefaultStringSet({}) BooleanPref bar5();
                """, errorList)
        assert errorList.size() == 5
        errorList.each { assert it.message.startsWith('Default value for this field cannot be set via')}
    }

    @Test
    void wrongDefaultFloat() {
        def errorList = new ArrayList<CompilationError>()
        compile("""
                @DefaultBoolean(true) FloatPref bar1();
                @DefaultInt(-1)       FloatPref bar2();
                @DefaultLong(-1)      FloatPref bar3();
                @DefaultString("")    FloatPref bar4();
                @DefaultStringSet({}) FloatPref bar5();
                """, errorList)
        assert errorList.size() == 5
        errorList.each { assert it.message.startsWith('Default value for this field cannot be set via')}
    }

    @Test
    void wrongDefaultInt() {
        def errorList = new ArrayList<CompilationError>()
        compile("""
                @DefaultBoolean(true)  IntPref bar1();
                @DefaultFloat(-1)      IntPref bar2();
                @DefaultLong(-1)       IntPref bar3();
                @DefaultString("")     IntPref bar4();
                @DefaultStringSet({})  IntPref bar5();
                """, errorList)
        assert errorList.size() == 5
        errorList.each { assert it.message.startsWith('Default value for this field cannot be set via')}
    }

    @Test
    void wrongDefaultLong() {
        def errorList = new ArrayList<CompilationError>()
        compile("""
                @DefaultBoolean(true)  LongPref bar1();
                @DefaultFloat(-1)      LongPref bar2();
                @DefaultInt(-1)        LongPref bar3();
                @DefaultString("")     LongPref bar4();
                @DefaultStringSet({})  LongPref bar5();
                """, errorList)
        assert errorList.size() == 5
        errorList.each { assert it.message.startsWith('Default value for this field cannot be set via')}
    }

    @Test
    void wrongDefaultString() {
        def errorList = new ArrayList<CompilationError>()
        compile("""
                @DefaultBoolean(true)  StringPref bar1();
                @DefaultFloat(-1)      StringPref bar2();
                @DefaultInt(-1)        StringPref bar3();
                @DefaultLong(-1)       StringPref bar4();
                @DefaultStringSet({})  StringPref bar5();
                """, errorList)
        assert errorList.size() == 5
        errorList.each { assert it.message.startsWith('Default value for this field cannot be set via')}
    }

    @Test
    void wrongDefaultStringSet() {
        def errorList = new ArrayList<CompilationError>()
        compile("""
                @DefaultBoolean(true) StringSetPref bar1();
                @DefaultFloat(-1)     StringSetPref bar2();
                @DefaultInt(-1)       StringSetPref bar3();
                @DefaultLong(-1)      StringSetPref bar4();
                @DefaultString("")    StringSetPref bar5();
                """, errorList)
        assert errorList.size() == 5
        errorList.each { assert it.message.startsWith('Default value for this field cannot be set via')}
    }

    @Test
    void badDefaultValue() {
        def errorList = new ArrayList<CompilationError>()
        compile("""@BadDefault(badValue = 0) IntPref bar();""", errorList)
        assert errorList.size() == 1
        def error = errorList.get(0)
        assert error.kind == Diagnostic.Kind.ERROR
        assert error.message == 'Default value annotation has no value()'
    }

    private static Object compile(String body) {
        return compile(body, new ArrayList<CompilationError>())
    }

    private static Object compile(String body, List<CompilationError> compilationErrors) {
        def packageName = 'test'
        def className = 'Foo'
        def code = """
            package ${packageName};
            import com.dpforge.autoprefs.annotation.*;
            import com.dpforge.autoprefs.*;
            @AutoPrefs
            interface ${className} {
                ${body}
            }
            """

        return TestCompiler.create()
                .packageName(packageName)
                .className(className)
                .code(code)
                .compilationErrors(compilationErrors)
                .compile()
    }
}