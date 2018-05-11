package com.dpforge.autoprefs;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LongPrefTest extends AbstractTest {

    @Test
    public void get() {
        when(prefsMock.getLong(anyString(), anyLong())).thenReturn(123L);
        LongPref pref = new LongPref(prefsMock, "foo");
        assertEquals(Long.valueOf(123), pref.get());
        verify(prefsMock).getLong("foo", 0L);
    }

    @Test
    public void getOrDefault() {
        when(prefsMock.getLong(anyString(), anyLong())).thenReturn(123L);
        LongPref pref = new LongPref(prefsMock, "foo");
        assertEquals(Long.valueOf(123), pref.getOrDefault(-1L));
        verify(prefsMock).getLong("foo", -1L);
    }

    @Test
    public void set() {
        when(editorMock.putLong(anyString(), anyLong())).thenReturn(editorMock);
        LongPref pref = new LongPref(prefsMock, "foo");
        pref.set(123L);
        verifyEditApply().putLong("foo", 123L);
    }

    @Test
    public void setDefault() {
        when(prefsMock.getLong(anyString(), anyLong())).thenReturn(123L);
        LongPref pref = new LongPref(prefsMock, "foo", -1L);
        assertEquals(Long.valueOf(123), pref.get());
        verify(prefsMock).getLong("foo", -1L);
    }
}