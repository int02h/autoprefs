package com.dpforge.autoprefs;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StringPrefTest extends AbstractTest {

    @Test
    public void get() {
        when(prefsMock.getString(anyString(), eq((String) null))).thenReturn("Hello");
        StringPref pref = new StringPref(prefsMock, "foo");
        assertEquals("Hello", pref.get());
        verify(prefsMock).getString("foo", null);
    }

    @Test
    public void getOrDefault() {
        when(prefsMock.getString(anyString(), anyString())).thenReturn("Hello");
        StringPref pref = new StringPref(prefsMock, "foo");
        assertEquals("Hello", pref.getOrDefault("World"));
        verify(prefsMock).getString("foo", "World");
    }

    @Test
    public void set() {
        when(editorMock.putString(anyString(), anyString())).thenReturn(editorMock);
        StringPref pref = new StringPref(prefsMock, "foo");
        pref.set("Hello");
        verifyEditApply().putString("foo", "Hello");
    }

    @Test
    public void defaultValue() {
        when(prefsMock.getString(anyString(), anyString())).thenReturn("Hello");
        StringPref pref = new StringPref(prefsMock, "foo", "World");
        assertEquals("Hello", pref.get());
        verify(prefsMock).getString("foo", "World");
    }
}
