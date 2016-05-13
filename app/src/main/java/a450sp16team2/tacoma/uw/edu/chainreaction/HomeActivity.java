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
import android.view.View;

import java.util.Set;

public class HomeActivity extends AppCompatActivity {

    public static HomeActivity homeActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        homeActivity = this;


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
