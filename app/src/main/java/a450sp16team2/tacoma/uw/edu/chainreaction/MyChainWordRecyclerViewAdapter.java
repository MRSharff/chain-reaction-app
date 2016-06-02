package a450sp16team2.tacoma.uw.edu.chainreaction;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import a450sp16team2.tacoma.uw.edu.chainreaction.model.ChainWord;

public class MyChainWordRecyclerViewAdapter extends RecyclerView.Adapter<MyChainWordRecyclerViewAdapter.ViewHolder> {

    private final List<ChainWord> mValues;
    private final ChainWordFragment.OnListFragmentInteractionListener mListener;
    private ChainWordFragment mChainWordFragment;

    private int mScore;

    public MyChainWordRecyclerViewAdapter(List<ChainWord> items, ChainWordFragment.OnListFragmentInteractionListener listener,
                                          ChainWordFragment chainList) {
        mValues = items;
        mListener = listener;
        mScore = 0;
        mChainWordFragment = chainList;
    }

    public boolean isCurrent(ChainWord theWord) {
        return mValues.indexOf(theWord) == mChainWordFragment.getmCurrentWord();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_chainword, parent, false);
        return new ViewHolder(view);
    }

    public void update() {
        boolean gameOver = true;
        if (mValues.get(mChainWordFragment.getmCurrentWord()).isRevealed) {
            if (mValues.size() > mChainWordFragment.getmCurrentWord() + 1)
                mValues.get(mChainWordFragment.getmCurrentWord() + 1).revealLetter();
            mScore += mValues.get(mChainWordFragment.getmCurrentWord()).getScore();
            mChainWordFragment.mGameActivity.updateScore(mScore);
            mChainWordFragment.setmCurrentWord(mChainWordFragment.getmCurrentWord() + 1);
            for (ChainWord c : mValues) {
                gameOver = (gameOver && c.isRevealed);
            }
            if (gameOver) mChainWordFragment.mGameActivity.gameOver();
        }
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mWord = mValues.get(position);
        List<Character> theChars = new ArrayList<Character>();
        for (char c : holder.mWord.getDisplay().toCharArray()) {
            theChars.add(Character.toUpperCase(c));
        }
        holder.setmChars(theChars);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    if (!holder.mWord.isRevealed) {
                        //mChainWordFragment.getmCurrentWord() = position;
                        mListener.onListFragmentInteraction(holder, MyChainWordRecyclerViewAdapter.this);
                    }
                }
                //MyChainWordRecyclerViewAdapter.this.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mLetter1;
        public final TextView mLetter2;
        public final TextView mLetter3;
        public final TextView mLetter4;
        public final TextView mLetter5;
        public final TextView mLetter6;
        public final TextView mLetter7;
        public final TextView mLetter8;
        public final TextView mLetter9;
        public final TextView mLetter10;
//        private final Drawable mLetterbox;
        public ChainWord mWord;

        private boolean showLength;

        public ViewHolder(View view) {
            super(view);
            showLength = true;
            mView = view;
            mLetter1 = (TextView) view.findViewById(R.id.letter1);
            mLetter2 = (TextView) view.findViewById(R.id.letter2);
            mLetter3 = (TextView) view.findViewById(R.id.letter3);
            mLetter4 = (TextView) view.findViewById(R.id.letter4);
            mLetter5 = (TextView) view.findViewById(R.id.letter5);
            mLetter6 = (TextView) view.findViewById(R.id.letter6);
            mLetter7 = (TextView) view.findViewById(R.id.letter7);
            mLetter8 = (TextView) view.findViewById(R.id.letter8);
            mLetter9 = (TextView) view.findViewById(R.id.letter9);
            mLetter10 = (TextView) view.findViewById(R.id.letter10);
//            mLetterbox = ResourcesCompat.getDrawable(view.getResources(), R.drawable.letterbox, null);
        }

        public void animateView() {
            Animator[] tiles = new Animator[10];
            Animator set = (Animator) AnimatorInflater.loadAnimator(mView.getContext(),
                    R.animator.rotate_char);
            set.setTarget(mLetter1);
            tiles[0] = set;
            set = (Animator) AnimatorInflater.loadAnimator(mView.getContext(),
                    R.animator.rotate_char);
            set.setTarget(mLetter2);
            tiles[1] = set;
            set = (Animator) AnimatorInflater.loadAnimator(mView.getContext(),
                    R.animator.rotate_char);
            set.setTarget(mLetter3);
            tiles[2] = set;
            set = (Animator) AnimatorInflater.loadAnimator(mView.getContext(),
                    R.animator.rotate_char);
            set.setTarget(mLetter4);
            tiles[3] = set;
            set = (Animator) AnimatorInflater.loadAnimator(mView.getContext(),
                    R.animator.rotate_char);
            set.setTarget(mLetter5);
            tiles[4] = set;
            set = (Animator) AnimatorInflater.loadAnimator(mView.getContext(),
                    R.animator.rotate_char);
            set.setTarget(mLetter6);
            tiles[5] = set;
            set = (Animator) AnimatorInflater.loadAnimator(mView.getContext(),
                    R.animator.rotate_char);
            set.setTarget(mLetter7);
            tiles[6] = set;
            set = (Animator) AnimatorInflater.loadAnimator(mView.getContext(),
                    R.animator.rotate_char);
            set.setTarget(mLetter8);
            tiles[7] = set;
            set = (Animator) AnimatorInflater.loadAnimator(mView.getContext(),
                    R.animator.rotate_char);
            set.setTarget(mLetter9);
            tiles[8] = set;
            set = (Animator) AnimatorInflater.loadAnimator(mView.getContext(),
                    R.animator.rotate_char);
            set.setTarget(mLetter10);
            tiles[9] = set;

            AnimatorSet as = new AnimatorSet();
            as.playSequentially(tiles);
            as.start();

        }

        public void setmChars(List<Character> theChars) {
            while (theChars.size() < 10) {
                theChars.add(' ');
            }
            mLetter1.setText(theChars.get(0).toString());
            mLetter2.setText(theChars.get(1).toString());
            mLetter3.setText(theChars.get(2).toString());
            mLetter4.setText(theChars.get(3).toString());
            mLetter5.setText(theChars.get(4).toString());
            mLetter6.setText(theChars.get(5).toString());
            mLetter7.setText(theChars.get(6).toString());
            mLetter8.setText(theChars.get(7).toString());
            mLetter9.setText(theChars.get(8).toString());
            mLetter10.setText(theChars.get(9).toString());

            if (showLength) {
                if (theChars.get(0) == ' ') mLetter1.setBackgroundColor(11184110);
                else mLetter1.setBackgroundResource(R.drawable.letterbox);
                if (theChars.get(1) == ' ') mLetter2.setBackgroundColor(11184110);
                else mLetter2.setBackgroundResource(R.drawable.letterbox);
                if (theChars.get(2) == ' ') mLetter3.setBackgroundColor(11184110);
                else mLetter3.setBackgroundResource(R.drawable.letterbox);
                if (theChars.get(3) == ' ') mLetter4.setBackgroundColor(11184110);
                else mLetter4.setBackgroundResource(R.drawable.letterbox);
                if (theChars.get(4) == ' ') mLetter5.setBackgroundColor(11184110);
                else mLetter5.setBackgroundResource(R.drawable.letterbox);
                if (theChars.get(5) == ' ') mLetter6.setBackgroundColor(11184110);
                else mLetter6.setBackgroundResource(R.drawable.letterbox);
                if (theChars.get(6) == ' ') mLetter7.setBackgroundColor(11184110);
                else mLetter7.setBackgroundResource(R.drawable.letterbox);
                if (theChars.get(7) == ' ') mLetter8.setBackgroundColor(11184110);
                else mLetter8.setBackgroundResource(R.drawable.letterbox);
                if (theChars.get(8) == ' ') mLetter9.setBackgroundColor(11184110);
                else mLetter9.setBackgroundResource(R.drawable.letterbox);
                if (theChars.get(9) == ' ') mLetter10.setBackgroundColor(11184110);
                else mLetter10.setBackgroundResource(R.drawable.letterbox);
            }
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mWord.getDisplay() + "'";
        }
    }
}
