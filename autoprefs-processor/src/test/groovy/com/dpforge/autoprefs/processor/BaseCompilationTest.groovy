package com.dpforge.autoprefs.processor

import com.dpforge.ocubator.CompilationResult
import com.dpforge.ocubator.OcubatorCompiler

abstract class BaseCompilationTest {
    static CompilationResult getCompilatiomResult(String code) {
        final File rootDir = new File(".").getAbsoluteFile().getParentFile().getParentFile()
        final File libDir = new File(rootDir, "autoprefs/src/main/java")

        return OcubatorCompiler.compile()
                .sourceCode(code)
                .withProcessor(new PrefsProcessor())
                .sourcePath(libDir)
                .please()
    }
}