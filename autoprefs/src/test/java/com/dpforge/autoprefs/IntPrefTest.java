package com.dpforge.autoprefs;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IntPrefTest extends AbstractTest {

    @Test
    public void get() {
        when(prefsMock.getInt(anyString(), anyInt())).thenReturn(123);
        IntPref pref = new IntPref(prefsMock, "foo");
        assertEquals(Integer.valueOf(123), pref.get());
        verify(prefsMock).getInt("foo", 0);
    }

    @Test
    public void getOrDefault() {
        when(prefsMock.getInt(anyString(), anyInt())).thenReturn(123);
        IntPref pref = new IntPref(prefsMock, "foo");
        assertEquals(Integer.valueOf(123), pref.getOrDefault(-1));
        verify(prefsMock).getInt("foo", -1);
    }

    @Test
    public void set() {
        when(editorMock.putInt(anyString(), anyInt())).thenReturn(editorMock);
        IntPref pref = new IntPref(prefsMock, "foo");
        pref.set(123);
        verifyEditApply().putInt("foo", 123);
    }

    @Test
    public void setDefault() {
        when(prefsMock.getInt(anyString(), anyInt())).thenReturn(123);
        IntPref pref = new IntPref(prefsMock, "foo", -1);
        assertEquals(123, pref.getInt());
        verify(prefsMock).getInt("foo", -1);
    }
}