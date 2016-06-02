package a450sp16team2.tacoma.uw.edu.chainreaction;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import a450sp16team2.tacoma.uw.edu.chainreaction.data.LocalHighscoreDB;
import a450sp16team2.tacoma.uw.edu.chainreaction.model.ChainWord;

/**
 * Game Activity is the main activity for a game.
 * This has a textview holding the score and a fragment list holding words.
 */
public class GameActivity extends AppCompatActivity implements ChainWordFragment.OnListFragmentInteractionListener {

    private static final String LOG_TAG = GameActivity.class.getSimpleName();

    private String mGuess;
    private TextView mScoreKeeper;
    private int mScore;

    /**
     * gets the TextView and stores it as an instance variable for updating purposes
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        HomeActivity.chainReactionSetTheme(this);

//        String theme = PreferenceManager.getDefaultSharedPreferences(this)
//                .getString("pref_theme_key", "AppTheme");
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
        setContentView(R.layout.activity_game);
        mScoreKeeper = (TextView) findViewById(R.id.score);

        //set initial score to 0
        mScore = 0;
        updateScore(0);
    }

    /**
     *  This method creates an Alert Dialog which prompts the user for their guess
     *  and checks it against the word they are guessing against. when a guess is incorrect,
     *  reveal another letter then when the prompt is closed, it notifies the adapter to both
     *  redraw its fragments and update some internal values.
     * @param word The ChainWord reference that was clicked on
     * @param myChainWordRecyclerViewAdapter Adapter that causes behavior with the fragment.
     */
    @Override
    public void onListFragmentInteraction(final ChainWord word, final MyChainWordRecyclerViewAdapter myChainWordRecyclerViewAdapter) {
        // get prompts.xml view
        LayoutInflater li = (LayoutInflater)getApplicationContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View promptsView = li.inflate(R.layout.prompt, null);

        // Get the current theme key
        String theme = PreferenceManager.getDefaultSharedPreferences(this)
                .getString("pref_theme_key", "AppTheme");

        AlertDialog.Builder alertDialogBuilder = null;

        // set theme dependent upon selected theme key
        switch (theme) {
            case "AppTheme":
                alertDialogBuilder = new AlertDialog.Builder(this, R.style.AppTheme_AlertDialog);
                break;
            case "Deadpool":
                alertDialogBuilder = new AlertDialog.Builder(this, R.style.AppTheme_Deadpool_AlertDialog);
                break;
            case "Thing":
                alertDialogBuilder = new AlertDialog.Builder(this, R.style.AppTheme_Thing_AlertDialog);
                break;
            case "Joker":
                alertDialogBuilder = new AlertDialog.Builder(this, R.style.AppTheme_Joker_AlertDialog);
                break;
            case "Inception":
                alertDialogBuilder = new AlertDialog.Builder(this, R.style.AppTheme_Inception_AlertDialog);
                break;
        }
//
        if (alertDialogBuilder == null) {
            // This only happens if they are first install on default theme
            alertDialogBuilder = new AlertDialog.Builder(this);
            Log.e(LOG_TAG, "Alert Dialog theming went wrong");
        }


        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);
        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.input);

        // setup dialog

        //Set title to show hint so far
        String previousWord = myChainWordRecyclerViewAdapter.getPreviousWord();
        alertDialogBuilder.setTitle(getString(R.string.word_hint_dialog)
                + previousWord + " " + myChainWordRecyclerViewAdapter.getHintAndBlank());

        alertDialogBuilder
                .setPositiveButton(getString(R.string.guess_dialog_guess),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
                                mGuess = userInput.getText().toString();
                                dialog.dismiss();
                                //guess the word and reveal a letter if wrong
                                if (!word.guess(mGuess) && !word.isRevealed) {
                                    word.revealLetter();
                                    myChainWordRecyclerViewAdapter.updateScoreForMiss();
                                }
                                myChainWordRecyclerViewAdapter.notifyDataSetChanged();
                                myChainWordRecyclerViewAdapter.update();
                            }
                        });

        // Allow the user to cancel a guess so they can look at the word again
        alertDialogBuilder
                .setCancelable(true)
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

//        alertDialogBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                myChainWordRecyclerViewAdapter.notifyDataSetChanged();
//                myChainWordRecyclerViewAdapter.update();
//            }
//        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

//            HomeActivity.chainReactionSetTheme(alertDialog.getContext());

        // show it
        alertDialog.show();
    }

    /**
     * sets the score keeping box TextView to the most recently updated score.
     * @param mScore
     */
    public void updateScore(int mScore) {
        this.mScore = mScore;
        Resources res = getResources();
        mScoreKeeper.setText(String.format(res.getString(R.string.scorekeeper_text), mScore));
    }

    /**
     * Creates a Dialog that tells the user what their final score was
     * and when the dialog is closed, returns to the main menu.
     */
    public void gameOver() {

        //This could probably be somewhere else but for right now it's going here.
        saveScore();

        final AlertDialog gameOverMessage= new AlertDialog.Builder(this).create();
        gameOverMessage.setTitle("Game Over");
        gameOverMessage.setMessage("Congratulations!\n" +
                "Your " + mScoreKeeper.getText().toString() + " points!");
        gameOverMessage.setButton(AlertDialog.BUTTON_NEGATIVE, "Back to Main Menu",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        gameOverMessage.setButton(AlertDialog.BUTTON_POSITIVE, "Share Score",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "New score on Chain Reaction!");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, mScoreKeeper.getText().toString().substring("Score:".length()));
                        startActivity(Intent.createChooser(shareIntent, "Share via"));
                    }
                });
        gameOverMessage.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                GameActivity.this.finish();
            }
        });
        gameOverMessage.show();
        //TODO: Data lab has SQLite info, possibly for storing high scores
    }

    private void saveScore() {
        String username = getSharedPreferences(getString(R.string.LOGIN_PREFS),Context.MODE_PRIVATE).getString(getString(R.string.LOGGEDIN_USERNAME), "OFFLINEUSER");
//        String username = PreferenceManager.getDefaultSharedPreferences(this).getString(getString(R.string.LOGGEDIN_USERNAME), "OFFLINEUSER");
//        String scoreString = mScoreKeeper.getText().toString().substring("Score:".length());
//        int score = Integer.parseInt(scoreString);
        LocalHighscoreDB highscoreDB = new LocalHighscoreDB(this);
        highscoreDB.insertHighscore(username, mScore, this);
        Log.i(LOG_TAG, "Saved score: " + username + ", Score: " + mScore);
    }
}
