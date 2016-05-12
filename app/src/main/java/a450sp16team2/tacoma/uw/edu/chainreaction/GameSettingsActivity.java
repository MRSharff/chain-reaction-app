package a450sp16team2.tacoma.uw.edu.chainreaction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.widget.ArrayAdapter;

public class GameSettingsActivity extends AppCompatActivity {

    private static final String LOG_TAG = GameSettingsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_settings);

        AppCompatSpinner difficultySpinner = (AppCompatSpinner) findViewById(R.id.difficulty_spinner);

        ArrayAdapter<CharSequence> diffSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.classic_difficulty,
                android.R.layout.simple_spinner_item);

        AppCompatSpinner modeSpinner = (AppCompatSpinner) findViewById(R.id.mode_spinner);

        ArrayAdapter<CharSequence> modeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.game_modes,
                android.R.layout.simple_spinner_item);

        try {
            modeSpinner.setAdapter(modeSpinnerAdapter);
            difficultySpinner.setAdapter(diffSpinnerAdapter);
        } catch (NullPointerException e) {
            Log.e(LOG_TAG, "A Spinner was null");
        }
    }
}
