package a450sp16team2.tacoma.uw.edu.chainreaction.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import a450sp16team2.tacoma.uw.edu.chainreaction.R;
import a450sp16team2.tacoma.uw.edu.chainreaction.model.Highscore;

/**
 * Created by mat on 6/1/16.
 */
public class LocalHighscoreDB {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "ChainReactionDB.db";
    private static final String LOCALHS_TABLE = "LocalHighscores";

    private LocalHighscoreDBHelper mLocalHighscoreDBHelper;
    private SQLiteDatabase mSQLiteDatabase;

    public LocalHighscoreDB(Context context) {
        mLocalHighscoreDBHelper = new LocalHighscoreDBHelper(
                context, DB_NAME, null, DB_VERSION);
        mSQLiteDatabase = mLocalHighscoreDBHelper.getWritableDatabase();
    }

    /**
     * Returns the list of highscores from the local localHighscores table.
     * @return list
     */
    public List<Highscore> getLocalHighscores() {
        String[] columns = {"username", "score"};

        Cursor c = mSQLiteDatabase.query(
                LOCALHS_TABLE,
                columns,
                null,
                null,
                null,
                null,
                columns[1] + " DESC"
        );
        c.moveToFirst();
        List<Highscore> list = new ArrayList<>();
        for (int counter = 0; counter < c.getCount(); counter++) {
            String username = c.getString(c.getColumnIndex("username"));
            int score = c.getInt(c.getColumnIndex("score"));
//            String username = c.getString(1);
//            int score = c.getInt(2);
            Highscore hs = new Highscore(username, score);
            list.add(hs);
            c.moveToNext();
            Log.i("Database", "Added " + username + " and " + score);
        }
        c.close();
        return list;
    }

    /**
     * Inserts the highscore into the local sqlite table. Returns true if successful, false otherwise.
     * @param username
     * @param score
     * @return true or false
     */
    public void insertHighscore(String username, int score, Context theContext) {
        AsyncDBInserter dbInserter = new AsyncDBInserter(username, score, theContext);
        dbInserter.execute((Void) null);
    }

    public void closeDB() {
        mSQLiteDatabase.close();
    }

    public void dropTables() {
        mSQLiteDatabase.execSQL("DROP TABLE IF EXISTS LocalHighscores");
    }

    class LocalHighscoreDBHelper extends SQLiteOpenHelper {

        private final String CREATE_LOCALHIGHSCORES_SQL;

        private final String DROP_LOCALHIGHSCORES_SQL;

        Context myContext;

        public LocalHighscoreDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
            myContext = context;
//            CREATE_LOCALHIGHSCORES_SQL = context.getString(R.string.CREATE_LOCALHIGHSCORES_SQL);
            CREATE_LOCALHIGHSCORES_SQL = "CREATE TABLE IF NOT EXISTS LocalHighscores(_id INTEGER PRIMARY KEY AUTOINCREMENT, username STRING, score INTEGER);";
            DROP_LOCALHIGHSCORES_SQL = context.getString(R.string.DROP_LOCALHIGHSCORES_SQL);

        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_LOCALHIGHSCORES_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(DROP_LOCALHIGHSCORES_SQL);
            onCreate(sqLiteDatabase);
        }
    }

    private class AsyncDBInserter extends AsyncTask<Void, Void, Boolean> {

        private final String mUsername;
        private final int mScore;

        private Context mContext;

        public AsyncDBInserter(String theUsername, int theScore, Context theContext) {
            mUsername = theUsername;
            mScore = theScore;
            mContext = theContext;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("username", mUsername);
            contentValues.put("score", mScore);

            long rowId = mSQLiteDatabase.insert(LOCALHS_TABLE, null, contentValues);
            return rowId != -1;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                Toast.makeText(mContext,
                        mContext.getString(R.string.score_save_success),
                        Toast.LENGTH_SHORT)
                    .show();
            } else {
                Toast.makeText(mContext,
                        mContext.getString(R.string.score_save_failed),
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

//    private class AsyncHighscoreGrabber extends AsyncTask<Void, Void, List<Highscore>> {
//
//        private Context mContext;
//
//        public AsyncHighscoreGrabber(Context theContext) {
//            mContext = theContext;
//        }
//
//        @Override
//        protected List<Highscore> doInBackground(Void... params) {
//            return mLocalHighscoreDB.getLocalHighscores();
//        }
//
//        @Override
//        protected void onPostExecute(List<Highscore> theList) {
//            if (theList != null && mRecyclerView != null) {
//                if (!theList.isEmpty()) {
//                    mAdapter = new HighScoreRecyclerViewAdapter(theList);
//                    mRecyclerView.setAdapter(mAdapter);
//                }
//            } else {
//                Log.e(LOG_TAG, "The List was null");
//            }
//
//        }
//    }
}
