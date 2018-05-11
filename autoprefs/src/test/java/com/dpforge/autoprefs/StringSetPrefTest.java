package com.dpforge.autoprefs;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StringSetPrefTest extends AbstractTest {

    @Test
    public void get() {
        when(prefsMock.getStringSet(anyString(), eq((Set<String>) null))).thenReturn(set("a", "b"));
        StringSetPref pref = new StringSetPref(prefsMock, "foo");
        assertEquals(set("a", "b"), pref.get());
        verify(prefsMock).getStringSet("foo", null);
    }

    @Test
    public void getOrDefault() {
        when(prefsMock.getStringSet("foo", set("c"))).thenReturn(set("a", "b"));
        StringSetPref pref = new StringSetPref(prefsMock, "foo");
        assertEquals(set("a", "b"), pref.getOrDefault(set("c")));
        verify(prefsMock).getStringSet("foo", set("c"));
    }

    @Test
    public void set() {
        when(editorMock.putStringSet("foo", set("a", "b"))).thenReturn(editorMock);
        StringSetPref pref = new StringSetPref(prefsMock, "foo");
        pref.set(set("a", "b"));
        verifyEditApply().putStringSet("foo", set("a", "b"));
    }

    @Test
    public void setDefault() {
        when(prefsMock.getStringSet("foo", set("c"))).thenReturn(set("a", "b"));
        StringSetPref pref = new StringSetPref(prefsMock, "foo", new String[]{"c"});
        assertEquals(set("a", "b"), pref.get());
        verify(prefsMock).getStringSet("foo", set("c"));
    }

    private static Set<String> set(String... items) {
        return new HashSet<>(Arrays.asList(items));
    }
}