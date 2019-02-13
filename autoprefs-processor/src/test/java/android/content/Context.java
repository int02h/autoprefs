package android.content;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("unused") // used in tests
public class Context {

    public final SharedPreferences prefsMock = mock(SharedPreferences.class);
    public final SharedPreferences.Editor editorMock = mock(SharedPreferences.Editor.class);

    public String lastPrefsName;

    public Context() {
        when(prefsMock.edit()).thenReturn(editorMock);
    }

    public SharedPreferences getSharedPreferences(final String name, final int mode) {
        lastPrefsName = name;
        return prefsMock;
    }
}
