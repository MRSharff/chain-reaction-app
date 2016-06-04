package a450sp16team2.tacoma.uw.edu.chainreaction;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import a450sp16team2.tacoma.uw.edu.chainreaction.data.LocalHighscoreDB;

public class HomeActivity extends AppCompatActivity {

    private static final String LOG_TAG = HomeActivity.class.getSimpleName();


    /** Static Home activity for finishing when a user logs out */
    public static HomeActivity homeActivity;

//    /** Keep track of current theme resource id */
//    private Integer myCurrentTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Set the static variable to this instance of home activity
        homeActivity = this;

        chainReactionSetTheme(this);


//        // Get the current theme key
//        String theme = PreferenceManager.getDefaultSharedPreferences(this)
//                .getString("pref_theme_key", "AppTheme");
//
//        // set theme dependent upon selected theme key
//        if (theme.equals("AppTheme")) {
//            setTheme(R.style.AppTheme);
//        } else if (theme.equals("Deadpool")) {
//            setTheme(R.style.AppTheme_Deadpool);
//        } else if (theme.equals("Thing")) {
//            setTheme(R.style.AppTheme_Thing);
//        } else if (theme.equals("Joker")) {
//            setTheme(R.style.AppTheme_Joker);
//        } else if (theme.equals("Inception")) {
//            setTheme(R.style.AppTheme_Inception);
//        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        AppCompatButton singlePlayerButton = (AppCompatButton) findViewById(R.id.btn_singleplayer);
        AppCompatButton playOnlineButton = (AppCompatButton) findViewById(R.id.btn_online);
        AppCompatButton playOfflineButton = (AppCompatButton) findViewById(R.id.btn_offline);
        AppCompatButton highscoreButton = (AppCompatButton) findViewById(R.id.btn_highscores);
        AppCompatButton settingsButton = (AppCompatButton) findViewById(R.id.btn_settings);



        if (singlePlayerButton != null && settingsButton != null && highscoreButton != null
                && playOnlineButton != null && playOfflineButton != null) {
            singlePlayerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startSinglePlayer();
                }
            });
            playOnlineButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    comingSoon();
                }
            });
            playOfflineButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    comingSoon();
                }
            });
            settingsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startSettings();
                }
            });
            highscoreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startHighscore();
                }
            });

        } else {
            Log.e(LOG_TAG, "A button was null");
        }

//        String username = PreferenceManager.getDefaultSharedPreferences(this).getString(getString(R.string.LOGGEDIN_USERNAME), "OFFLINEUSER"); // does not work
        String username = getSharedPreferences(getString(R.string.LOGIN_PREFS),Context.MODE_PRIVATE).getString(getString(R.string.LOGGEDIN_USERNAME), "OFFLINEUSER");
        Toast.makeText(this, String.format(getString(R.string.login_toast), username), Toast.LENGTH_SHORT).show();

    }

    private void startSinglePlayer() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    private void startSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void startHighscore() {
//        LocalHighscoreDB db = new LocalHighscoreDB(this);
//        db.dropTables();
        Intent intent = new Intent(this, HighscoreActivity.class);
        startActivity(intent);
    }

    private void comingSoon() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage(R.string.coming_soon)
            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //do nothing
                }
            });
        AlertDialog dialog = alertBuilder.create();
        dialog.show();
    }

    public static void chainReactionSetTheme(Context theContext) {
        // Get the current theme key
        String theme = PreferenceManager.getDefaultSharedPreferences(theContext)
                .getString("pref_theme_key", "AppTheme");

        // set theme dependent upon selected theme key
        switch (theme) {
            case "AppTheme":
                theContext.setTheme(R.style.AppTheme);
                break;
            case "Deadpool":
                theContext.setTheme(R.style.AppTheme_Deadpool);
                break;
            case "Thing":
                theContext.setTheme(R.style.AppTheme_Thing);
                break;
            case "Joker":
                theContext.setTheme(R.style.AppTheme_Joker);
                break;
            case "Inception":
                theContext.setTheme(R.style.AppTheme_Inception);
                break;
        }
    }

}
