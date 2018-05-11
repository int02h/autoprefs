package com.dpforge.autoprefs.app;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dpforge.autoprefs.BooleanPref;
import com.dpforge.autoprefs.IntPref;
import com.dpforge.autoprefs.PrefsProvider;
import com.dpforge.autoprefs.StringPref;
import com.dpforge.autoprefs.annotation.AutoPrefs;
import com.dpforge.autoprefs.annotation.ClearMethod;
import com.dpforge.autoprefs.annotation.DefaultBoolean;
import com.dpforge.autoprefs.annotation.DefaultString;
import com.dpforge.autoprefs.annotation.PrefKey;

public class MainActivity extends AppCompatActivity {

    private final SamplePrefs prefs = PrefsProvider.get(SamplePrefs.class);

    private TextView startCount;

    private EditText nameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startCount = findViewById(R.id.start_count);
        nameInput = findViewById(R.id.name_input);

        checkFirstRun();
        incrementStartCount();
        nameInput.setText(prefs.username().get());

        findViewById(R.id.btn_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                prefs.reset();
                recreate();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        prefs.username().set(nameInput.getText().toString());
    }

    private void checkFirstRun() {
        if (prefs.needWelcome().get()) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.welcome_title)
                    .setMessage(R.string.welcome_message)
                    .setPositiveButton(R.string.btn_ok, null)
                    .show();
            prefs.needWelcome().set(false);
        }
    }

    private void incrementStartCount() {
        prefs.startCount().set(prefs.startCount().get() + 1);
        startCount.setText(getString(R.string.start_count, prefs.startCount().get()));
    }

    @AutoPrefs
    interface SamplePrefs {

        @DefaultBoolean(true)
        BooleanPref needWelcome();

        IntPref startCount();

        @DefaultString("")
        @PrefKey("user-name")
        StringPref username();

        @ClearMethod
        void reset();
    }
}
