package com.dpforge.autoprefs.processor

import org.junit.Test

import javax.annotation.processing.Filer
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.TypeElement

import static org.mockito.ArgumentMatchers.any
import static org.mockito.ArgumentMatchers.anyString
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class CodeWriterTest {

    @Test(expected = UncheckedIOException.class)
    void writeFail() {
        def env = mock(ProcessingEnvironment.class)
        def filer = mock(Filer.class)
        when(env.getFiler()).thenReturn(filer)
        when(filer.createSourceFile(anyString(), any())).thenThrow(new IOException('Test'))
        new CodeWriter(env).write(mock(PrefsDescriptor.class), mock(TypeElement.class))
    }
}