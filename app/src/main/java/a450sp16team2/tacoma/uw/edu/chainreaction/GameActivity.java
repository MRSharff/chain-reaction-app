package a450sp16team2.tacoma.uw.edu.chainreaction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;

import Model.WordListGenerator;

public class GameActivity extends AppCompatActivity {

    public WordListGenerator wordListGenerator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        try {
            wordListGenerator = new WordListGenerator(7, getAssets().open("chainreaction.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        TextView textView = (TextView) findViewById(R.id.word_list);
        wordListGenerator.buildChain();
        textView.setText(wordListGenerator.getWordList().toString());
    }

}
