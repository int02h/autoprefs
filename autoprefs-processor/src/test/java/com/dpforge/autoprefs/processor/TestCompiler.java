package com.dpforge.autoprefs.processor;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

public class TestCompiler {
    private String packageName = "test";
    private String className = "Foo";
    private String code = "";
    private Context context = new Context();
    private List<CompilationError> compilationErrors = new ArrayList<>();

    public TestCompiler packageName(final String packageName) {
        this.packageName = packageName;
        return this;
    }

    public TestCompiler className(final String className) {
        this.className = className;
        return this;
    }

    public TestCompiler code(final String code) {
        this.code = code;
        return this;
    }

    public TestCompiler context(final Context context) {
        this.context = context;
        return this;
    }

    public TestCompiler compilationErrors(final List<CompilationError> compilationErrors) {
        this.compilationErrors = compilationErrors;
        return this;
    }

    public Object compile() throws IOException {
        final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        final StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        final File output = Files.createTempDirectory("output").toFile();
        fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Collections.singletonList(output));

        final List<JavaFileObject> compilationUnits = getCompilationUnits(fileManager);
        compilationUnits.add(new JavaSourceFromString(packageName + "." + className, code));

        final DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        final JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits);
        task.setProcessors(Collections.singleton(new PrefsProcessor()));
        task.call();

        for (Diagnostic<?> diagnostic : diagnostics.getDiagnostics()) {
            compilationErrors.add(new CompilationError(diagnostic));
        }

        if (compilationErrors.isEmpty()) {
            try {
                final String prefsClassName = packageName + "." + className + "_Prefs";
                final Class<?> clazz = Class.forName(prefsClassName, true, fileManager.getClassLoader(StandardLocation.CLASS_OUTPUT));
                return clazz.getConstructor(Context.class).newInstance(context);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public static TestCompiler create() {
        return new TestCompiler();
    }

    private static List<JavaFileObject> getCompilationUnits(final StandardJavaFileManager fileManager) {
        final File rootDir = new File(".").getAbsoluteFile().getParentFile().getParentFile();
        final File libDir = new File(rootDir, "autoprefs/src/main/java");
        final List<File> javaFiles = new ArrayList<>();
        getAllJavaFiles(libDir, javaFiles);
        return new ArrayList<>(iterableToList(fileManager.getJavaFileObjectsFromFiles(javaFiles)));
    }

    private static void getAllJavaFiles(final File dir, final List<File> files) {
        for (File f : Objects.requireNonNull(dir.listFiles())) {
            if (f.isDirectory()) {
                getAllJavaFiles(f, files);
            } else if (f.getName().endsWith("Pref.java")) {
                files.add(f);
            }
        }
    }

    private static <T> List<T> iterableToList(final Iterable<T> iterable) {
        final List<T> list = new ArrayList<>();
        for (T item : iterable) {
            list.add(item);
        }
        return list;
    }

    private static class JavaSourceFromString extends SimpleJavaFileObject {
        final String code;

        JavaSourceFromString(String name, String code) {
            super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension),
                    Kind.SOURCE);
            this.code = code;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return code;
        }
    }
}
