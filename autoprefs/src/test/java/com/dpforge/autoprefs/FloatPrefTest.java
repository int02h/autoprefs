package com.dpforge.autoprefs;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FloatPrefTest extends AbstractTest {

    @Test
    public void get() {
        when(prefsMock.getFloat(anyString(), anyFloat())).thenReturn(12.34f);
        FloatPref pref = new FloatPref(prefsMock, "foo");
        assertEquals(Float.valueOf(12.34f), pref.get());
        verify(prefsMock).getFloat("foo", 0f);
    }

    @Test
    public void getOrDefault() {
        when(prefsMock.getFloat(anyString(), anyFloat())).thenReturn(12.34f);
        FloatPref pref = new FloatPref(prefsMock, "foo");
        assertEquals(Float.valueOf(12.34f), pref.getOrDefault(-1f));
        verify(prefsMock).getFloat("foo", -1f);
    }

    @Test
    public void set() {
        when(editorMock.putFloat(anyString(), anyFloat())).thenReturn(editorMock);
        FloatPref pref = new FloatPref(prefsMock, "foo");
        pref.set(12.34f);
        verifyEditApply().putFloat("foo", 12.34f);
    }

    @Test
    public void setDefault() {
        when(prefsMock.getFloat(anyString(), anyFloat())).thenReturn(12.34f);
        FloatPref pref = new FloatPref(prefsMock, "foo", -1f);
        assertEquals(Float.valueOf(12.34f), pref.get());
        verify(prefsMock).getFloat("foo", -1f);
    }
}