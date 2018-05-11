package com.dpforge.autoprefs;

import android.content.SharedPreferences;

import org.junit.Before;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public abstract class AbstractTest {

    protected SharedPreferences prefsMock;
    protected SharedPreferences.Editor editorMock;

    @Before
    public void setup() {
        prefsMock = mock(SharedPreferences.class);
        editorMock = mock(SharedPreferences.Editor.class);
        when(prefsMock.edit()).thenReturn(editorMock);
    }

    protected SharedPreferences.Editor verifyEditApply() {
        verify(prefsMock).edit();
        verify(editorMock).apply();
        return verify(editorMock);
    }
}
