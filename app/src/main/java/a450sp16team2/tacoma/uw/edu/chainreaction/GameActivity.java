package a450sp16team2.tacoma.uw.edu.chainreaction;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import a450sp16team2.tacoma.uw.edu.chainreaction.model.ChainWord;

/**
 * Game Activity is the main activity for a game.
 * This has a textview holding the score and a fragment list holding words.
 */
public class GameActivity extends AppCompatActivity implements ChainWordFragment.OnListFragmentInteractionListener {

    private String mGuess;
    private TextView mScoreKeeper;

    /**
     * gets the TextView and stores it as an instance variable for updating purposes
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mScoreKeeper = (TextView) findViewById(R.id.score);
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

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.input);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
                                mGuess = userInput.getText().toString();
                                dialog.dismiss();
                                //guess the word and reveal a letter if wrong
                                if (!word.guess(mGuess) && !word.isRevealed) {
                                    word.revealLetter();
                                }
                            }
                        });
        alertDialogBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                myChainWordRecyclerViewAdapter.notifyDataSetChanged();
                myChainWordRecyclerViewAdapter.update();
            }
        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }

    /**
     * sets the score keeping box TextView tothe most recently updated score.
     * @param mScore
     */
    public void updateScore(int mScore) {
        mScoreKeeper.setText("" + mScore);
    }

    /**
     * Creates a Dialog that tells the user what their final score was
     * and when the dialogis closed, returns to the main menu.
     */
    public void gameOver() {
        AlertDialog gameOverMessage= new AlertDialog.Builder(this).create();
        gameOverMessage.setTitle("Game Over");
        gameOverMessage.setMessage("Congradulations!\n" +
                "You scored " + mScoreKeeper.getText().toString() + " points!");
        gameOverMessage.setButton(AlertDialog.BUTTON_NEUTRAL, "Back to Main Menu",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        gameOverMessage.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                GameActivity.this.finish();
            }
        });
        gameOverMessage.show();
    }
}
