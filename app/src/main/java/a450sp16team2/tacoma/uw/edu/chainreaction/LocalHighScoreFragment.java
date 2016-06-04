package a450sp16team2.tacoma.uw.edu.chainreaction;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import a450sp16team2.tacoma.uw.edu.chainreaction.data.LocalHighscoreDB;
import a450sp16team2.tacoma.uw.edu.chainreaction.model.Highscore;


/**
 * A simple {@link Fragment} subclass which shows the local highscores using a recycler view
 * which is filled with a SQLite query.
 */
public class LocalHighScoreFragment extends Fragment {

    private static final String LOG_TAG = LocalHighScoreFragment.class.getSimpleName();

    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;

    private RecyclerView mRecyclerView;
    private HighScoreRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private LocalHighscoreDB mLocalHighscoreDB;

    public LocalHighScoreFragment() {
        // Required empty public constructor
    }

    public static LocalHighScoreFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        LocalHighScoreFragment fragment = new LocalHighScoreFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_local_high_score, container, false);

        mLocalHighscoreDB = new LocalHighscoreDB(getContext());

        List<Highscore> highScoreList = mLocalHighscoreDB.getLocalHighscores();

        if (highScoreList.size() == 0) {
            TextView noScoresTextView = (TextView) view.findViewById(R.id.highscore_placeholder);
            noScoresTextView.setVisibility(TextView.VISIBLE);
        }

        mRecyclerView = (RecyclerView) view.findViewById(R.id.highscore_recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


//        AsyncHighscoreGrabber highscoreGrabber = new AsyncHighscoreGrabber(getContext());
//        highscoreGrabber.execute();

//        mAdapter = new HighScoreRecyclerViewAdapter(); //this goes in asynctas
        mAdapter = new HighScoreRecyclerViewAdapter(mLocalHighscoreDB.getLocalHighscores());
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }
}
