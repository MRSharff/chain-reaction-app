package a450sp16team2.tacoma.uw.edu.chainreaction;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import a450sp16team2.tacoma.uw.edu.chainreaction.model.ChainWord;

public class GameActivity extends AppCompatActivity implements ChainWordFragment.OnListFragmentInteractionListener{

    private String mGuess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

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
}
