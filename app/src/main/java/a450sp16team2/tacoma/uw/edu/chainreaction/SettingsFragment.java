package a450sp16team2.tacoma.uw.edu.chainreaction;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Set;

import a450sp16team2.tacoma.uw.edu.chainreaction.authenticate.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    SharedPreferences.OnSharedPreferenceChangeListener mListener;
    SettingsActivity mSettingsActivity;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        addPreferencesFromResource(R.xml.general_settings);

        Preference loginButton = findPreference(getString(R.string.logout_key));
        loginButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {
                logout();
                return true;
            }
        });

        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        mSettingsActivity = (SettingsActivity) getActivity();
    }

    private void logout() {

        // Set logged out key.
        SharedPreferences sharedPreferences =
                getActivity().getSharedPreferences(getString(R.string.LOGIN_PREFS), Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(getString(R.string.LOGGEDIN), false)
                .apply(); // can also be .commit()

        Intent i = new Intent(getActivity(), LoginActivity.class);
        startActivity(i);

        //finish the home activity so we can't go "Back" to it after logging out
        HomeActivity.homeActivity.finish();

        // Finish this activity so we can't go back to it after logging out.
        getActivity().finish();
    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
//            @Override
//            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//                Log.i("Key", key);
//                if (!key.equals("pref_theme_key")) {
//                    return;
//                }
//
//                getActivity().finish();
//                final Intent intent = getActivity().getIntent();
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
//                getActivity().startActivity(intent);
//            }
//        };
//
//        getActivity().getSharedPreferences(getString(R.string.LOGIN_PREFS), Context.MODE_PRIVATE)
//                .registerOnSharedPreferenceChangeListener(mListener);
//    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.i("Key", key);
        if (!key.equals("pref_theme_key")) {
            return;
        }

        try {
            HomeActivity.homeActivity.recreate();
            getActivity().recreate();
        } catch (NullPointerException e) {
            // brown hair don't care
            // for some reason, if we don't do this, the app will crash
            // but then be like, "jk we're still working"
            // so if we do this, changing themes will not crash the app
            // This workaround feels dirty but it works... for now.
        }
    }
}
