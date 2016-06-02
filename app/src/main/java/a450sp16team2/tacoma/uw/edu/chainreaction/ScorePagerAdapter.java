package a450sp16team2.tacoma.uw.edu.chainreaction;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by mat on 6/2/16.
 */
public class ScorePagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 1;
    private Context mContext;

    public ScorePagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return LocalHighScoreFragment.newInstance(position + 1);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
//                return getString(R.string.tab0);
        }
        return null;
    }
}
