package com.dpforge.autoprefs;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BooleanPrefTest extends AbstractTest {

    @Test
    public void get() {
        when(prefsMock.getBoolean(anyString(), anyBoolean())).thenReturn(true);
        BooleanPref pref = new BooleanPref(prefsMock, "foo");
        assertEquals(Boolean.TRUE, pref.get());
        verify(prefsMock).getBoolean("foo", false);
    }

    @Test
    public void getOrDefault() {
        when(prefsMock.getBoolean(anyString(), anyBoolean())).thenReturn(true);
        BooleanPref pref = new BooleanPref(prefsMock, "foo");
        assertEquals(Boolean.TRUE, pref.getOrDefault(Boolean.TRUE));
        verify(prefsMock).getBoolean("foo", true);
    }

    @Test
    public void set() {
        when(editorMock.putBoolean(anyString(), anyBoolean())).thenReturn(editorMock);
        BooleanPref pref = new BooleanPref(prefsMock, "foo");
        pref.set(Boolean.TRUE);
        verifyEditApply().putBoolean("foo", true);
    }

    @Test
    public void setDefault() {
        when(prefsMock.getBoolean(anyString(), anyBoolean())).thenReturn(true);
        BooleanPref pref = new BooleanPref(prefsMock, "foo", true);
        assertEquals(Boolean.TRUE, pref.get());
        verify(prefsMock).getBoolean("foo", true);
    }
}