package a450sp16team2.tacoma.uw.edu.chainreaction;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import a450sp16team2.tacoma.uw.edu.chainreaction.model.Highscore;

/**
 * Created by mat on 6/2/16.
 */
public class HighScoreRecyclerViewAdapter extends RecyclerView.Adapter<HighScoreRecyclerViewAdapter.ViewHolder> {
    private final List<Highscore> mValues;

    public HighScoreRecyclerViewAdapter(List<Highscore> theList) {
        mValues = theList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public Highscore mHighscore;
        public TextView mRank;
        public TextView mUsername;
        public TextView mScore;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mRank = (TextView) view.findViewById(R.id.highscore_rank);
            mUsername = (TextView) view.findViewById(R.id.highscore_username);
            mScore = (TextView) view.findViewById(R.id.highscore_score);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mHighscore = mValues.get(position);
        holder.mRank.setText("" + (position + 1));
        holder.mUsername.setText(holder.mHighscore.getUsername());
        holder.mScore.setText("" + holder.mHighscore.getScore());
    }

    @Override
    public HighScoreRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.highscore_list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
