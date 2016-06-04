package a450sp16team2.tacoma.uw.edu.chainreaction;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

/**
 * Created by mat on 6/3/16.
 */
public class HomeActivityTest extends ActivityInstrumentationTestCase2<HomeActivity> {

    private Solo solo;

    public HomeActivityTest() {
        super(HomeActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        //tearDown() is run after a test case has finished.
        //finishOpenedActivities() will finish all the activities that have been opened during the test execution.
        solo.finishOpenedActivities();
    }

    public void testGameActivity() {
        solo.clickOnButton(getActivity().getString(R.string.single_player));
        boolean textFound = solo.searchText(getActivity().getString(R.string.score));
        assertTrue("Singleplayer game loaded", textFound);
        solo.clickInRecyclerView(2);
        boolean guessShows = solo.searchText(getActivity().getString(R.string.guess_dialog_guess));
    }

    public void testOnlineButton() {
        solo.clickOnButton(getActivity().getString(R.string.online));
        boolean textFound = solo.searchText(getActivity().getString(R.string.coming_soon));
        assertTrue("Online game shows coming soon dialog", textFound);
    }

    public void testOfflineButton() {
        solo.clickOnButton(getActivity().getString(R.string.offline));
        boolean textFound = solo.searchText(getActivity().getString(R.string.coming_soon));
        assertTrue("Offline game shows coming soon dialog", textFound);
    }

    public void testHighscore() {
        solo.clickOnButton(getActivity().getString(R.string.highscores));
        boolean textFound = solo.searchText(getActivity().getString(R.string.rank));
        assertTrue("Highscores work", textFound);
    }

    public void testSettings() {
        solo.clickOnButton(getActivity().getString(R.string.action_settings));
        boolean textFound = solo.searchText(getActivity().getString(R.string.pref_title_theme));
        assertTrue("Settings work", textFound);
    }

    public void testLogout() {
        solo.clickOnButton(getActivity().getString(R.string.action_settings));
        solo.clickOnText(getActivity().getString(R.string.pref_title_logout));
        boolean textFound = solo.searchText(getActivity().getString(R.string.no_account_text));
        assertTrue("Login activity loaded", textFound);
        solo.enterText(0, "mrsharff");
        solo.enterText(1, "meow123");
        solo.clickOnButton(getActivity().getString(R.string.login));
        boolean worked = solo.searchText(getActivity().getString(R.string.single_player));
        assertTrue("Login worked!", worked);
    }
}
