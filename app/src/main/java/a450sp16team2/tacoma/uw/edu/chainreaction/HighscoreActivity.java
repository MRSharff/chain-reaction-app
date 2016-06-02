package a450sp16team2.tacoma.uw.edu.chainreaction;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class HighscoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        HomeActivity.chainReactionSetTheme(this);

        ViewPager viewPager = (ViewPager) findViewById(R.id.scorepager);
        viewPager.setAdapter(new HighScorePagerAdapter(getSupportFragmentManager(), HighscoreActivity.this));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.score_tabs);
        tabLayout.setupWithViewPager(viewPager);

//        mlocalHighScoreFragment = new LocalHighScoreFragment();
//        if (savedInstanceState == null) {
//            getFragmentManager().beginTransaction()
//                    .add(R.id.scorepagerr, mlocalHighScoreFragment);
//        }

    }

    public class HighScorePagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 1;
        private Context mContext;

        public HighScorePagerAdapter(FragmentManager fm, Context context) {
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
                    return getString(R.string.tab0);
            }
            return null;
        }
    }
}
