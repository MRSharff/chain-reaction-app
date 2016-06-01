package a450sp16team2.tacoma.uw.edu.chainreaction;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HomeActivity extends AppCompatActivity {

    /** Static Home activity for finishing when a user logs out */
    public static HomeActivity homeActivity;

    /** Keep track of current theme resource id */
    private Integer myCurrentTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Set the static variable to this instance of home activity
        homeActivity = this;

//        Log.i("Theme", "prefmanager: " + PreferenceManager.getDefaultSharedPreferences(this)
//                .getInt("pref_theme_key", R.style.AppTheme));

//        String theme = PreferenceManager.getDefaultSharedPreferences(this)
//                .getString("pref_theme_key", "AppTheme");




//        setTheme();

//        setTheme(themeStyle);




//        TypedValue outValue = new TypedValue();
//        getTheme().resolveAttribute(R.attr.theme, outValue, true);



        // Check if which the
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
        setContentView(R.layout.activity_home);

        AppCompatButton singlePlayerButton = (AppCompatButton) findViewById(R.id.btn_singleplayer);
        AppCompatButton settingsButton = (AppCompatButton) findViewById(R.id.btn_settings);


        singlePlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSinglePlayer();

            }
        });
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSettings();
            }
        });



        //TODO: Update functionality on Online play to grey out button if no internet connection
        //TODO: Or, have it pop up an alert dialog saying that we need an internet connection

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        getSupportActionBar().hide();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private void startSinglePlayer() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    private void startSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

}
