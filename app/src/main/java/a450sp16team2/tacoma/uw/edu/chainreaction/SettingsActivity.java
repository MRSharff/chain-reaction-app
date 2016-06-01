package a450sp16team2.tacoma.uw.edu.chainreaction;

import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String theme = PreferenceManager.getDefaultSharedPreferences(this)
                .getString("pref_theme_key", "AppTheme");
        if (theme.equals("AppTheme")) {
            setTheme(R.style.AppTheme);
        } else if (theme.equals("Deadpool")) {
            setTheme(R.style.AppTheme_Deadpool);
        } else if (theme.equals("Thing")) {
            setTheme(R.style.AppTheme_Thing);
        } else if (theme.equals("Joker")) {
            setTheme(R.style.AppTheme_Joker);
        } else if (theme.equals("Inception")) {
            setTheme(R.style.AppTheme_Inception);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

//        TypedValue typedValue = new TypedValue();
//        Resources.Theme theme = getTheme();
//        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
//        int color = typedValue.data;
//
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.Settings);
        setSupportActionBar(toolbar);

    }

    @Override
    protected void onStart() {
        super.onStart();
        getFragmentManager().beginTransaction()
                .replace(R.id.settings_frame, new SettingsFragment())
                .commit();
    }
}
