package a450sp16team2.tacoma.uw.edu.chainreaction;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import a450sp16team2.tacoma.uw.edu.chainreaction.model.ChainWord;

import java.util.List;

public class MyChainWordRecyclerViewAdapter extends RecyclerView.Adapter<MyChainWordRecyclerViewAdapter.ViewHolder> {

    private final List<ChainWord> mValues;
    private final ChainWordFragment.OnListFragmentInteractionListener mListener;
    private int mCurrentWord;

    public MyChainWordRecyclerViewAdapter(List<ChainWord> items, ChainWordFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        mCurrentWord = 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_chainword, parent, false);
        return new ViewHolder(view);
    }

    public void update() {
        if (mValues.get(mCurrentWord).isRevealed) {
            if (mValues.size() > mCurrentWord + 1)
                mValues.get(mCurrentWord + 1).revealLetter();
        }
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mWord = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).getDisplay());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    if (!holder.mWord.isRevealed) {
                        mCurrentWord = position;
                        mListener.onListFragmentInteraction(holder.mWord, MyChainWordRecyclerViewAdapter.this);
                    }
                }
                MyChainWordRecyclerViewAdapter.this.notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public ChainWord mWord;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
