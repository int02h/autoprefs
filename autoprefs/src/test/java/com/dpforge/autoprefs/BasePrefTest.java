package com.dpforge.autoprefs;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BasePrefTest extends AbstractTest {

    private BasePref<?> pref;

    @Override
    public void setup() {
        super.setup();
        pref = new BasePref<Object>(prefsMock, "foo") {
            @Override
            public Object get() {
                throw new UnsupportedOperationException();
            }

            @Override
            public Object getOrDefault(final Object defaultValue) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void set(final Object value) {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Test
    public void contains() {
        when(prefsMock.contains(anyString())).thenReturn(true);
        assertTrue(pref.contains());
        verify(prefsMock).contains("foo");
    }

    @Test
    public void remove() {
        when(editorMock.remove(anyString())).thenReturn(editorMock);
        pref.remove();
        verifyEditApply().remove("foo");
    }
}