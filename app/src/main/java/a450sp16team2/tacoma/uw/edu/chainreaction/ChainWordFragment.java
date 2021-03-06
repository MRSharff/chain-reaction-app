package a450sp16team2.tacoma.uw.edu.chainreaction;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import Model.WordListGenerator;
import a450sp16team2.tacoma.uw.edu.chainreaction.model.ChainWord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ChainWordFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private int mCurrentWord;
    private List<ChainWord> mWords;
    private OnListFragmentInteractionListener mListener;
    public GameActivity mGameActivity;
    public RecyclerView mRecyclerView;
    public WordListGenerator mWordListGenerator;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ChainWordFragment() {
    }

    /**
     * setWords accepts a list of strings representing the words of the game and creates
     * a Chainword object for each one. Chainwords hold the game information for a single word
     * in the game and are also used for the fragment list.
     * @param theWords List of  words. the words should follow the standards for the game where adjacent
     *                 words form a common english phrase.
     */
    private void setWords(List<String> theWords) {
        if (mWords == null) {
            mWords = new ArrayList<ChainWord>();
        }
        for (String s : theWords) {
            ChainWord c = new ChainWord(s);
            if (s.equals(theWords.get(0))) {
                c.makeRevealed();
            } else if (s.equals(theWords.get(1))) {
                c.revealLetter();
            }
            mWords.add(c);
        }
    }

    /**
     * onCreate Makes a wordListGenerator which generates the listof linked words for the game.
     * this is the model for the game.
     * saves the parent Game activity for reference and to pass to the Recycler View.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGameActivity = (GameActivity) getActivity();
        mCurrentWord = 0;
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        try {
            mWordListGenerator = new WordListGenerator(6, getActivity().getAssets().open("chainreaction.txt"));
            mWordListGenerator.buildChain();
            setWords(mWordListGenerator.getWordList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inflate the Chainword list and create the recyclerview adapter
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chainword_list, container, false);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            mRecyclerView = recyclerView;
            recyclerView.setAdapter(new MyChainWordRecyclerViewAdapter(mWords, mListener, this));
        }
        return view;
    }

    public void updateList() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(ChainWord item, MyChainWordRecyclerViewAdapter myChainWordRecyclerViewAdapter);
    }
}
