package a450sp16team2.tacoma.uw.edu.chainreaction.authenticate;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import a450sp16team2.tacoma.uw.edu.chainreaction.HomeActivity;
import a450sp16team2.tacoma.uw.edu.chainreaction.R;

public class LoginActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String OFFLINE_USERNAME = "OFFLINEUSER";
    private static final String OFFLINE_PASSWORD = "OFFLINEPASS";

    private static final String LOG_TAG = LoginActivity.class.getSimpleName();

    /** ID to identify READ_CONTACTS permission request. */
    private static final int REQUEST_READ_CONTACTS = 0;

    /** Keep track of the login task to ensure we can cancel it if requested. */
    private UserLoginTask mAuthTask = null;

    // UI References.
    private EditText mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private SharedPreferences mSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mSharedPreferences = getSharedPreferences(getString(R.string.LOGIN_PREFS)
                , Context.MODE_PRIVATE);
        if (!mSharedPreferences.getBoolean(getString(R.string.LOGGEDIN), false)) {
            // Set up the login form.
            mUsernameView = (EditText) findViewById(R.id.input_username);

            mPasswordView = (EditText) findViewById(R.id.input_password);
            mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                    if (id == R.id.login || id == EditorInfo.IME_NULL) {
                        attemptLogin(false);
                        return true;
                    }
                    return false;
                }
            });

            Button mLoginButton = (Button) findViewById(R.id.btn_login);
            mLoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    attemptLogin(false);
                }
            });

            Button mPlayOfflineButton = (Button) findViewById(R.id.btn_play_offline);
            mPlayOfflineButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    attemptLogin(true);
                }
            });

            TextView mRegisterText = (TextView) findViewById(R.id.link_signup);
            final Context context = this;
            mRegisterText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, RegisterActivity.class);
                    startActivity(intent);
                }
            });

            mLoginFormView = findViewById(R.id.login_form);
            mProgressView = findViewById(R.id.login_progress);
        } else {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }


    }



    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid username, missing fields, etc.), the
     * errors are presented and no actual login attempt is mode.
     */
    private void attemptLogin(boolean isOffline) {
        if (mAuthTask != null) {
            return;
        }

        if (isOffline ) {
            showProgress(true);
            mAuthTask = new UserLoginTask(OFFLINE_USERNAME, OFFLINE_PASSWORD, this);
            mAuthTask.execute((Void) null);
        } else {

            // Reset errors.
            mUsernameView.setError(null);
            mPasswordView.setError(null);

            // Store values at the time of the login attempt.
            String username = mUsernameView.getText().toString();
            String password = mPasswordView.getText().toString();

            boolean cancel = false;
            View focusView = null;

            // check for a valid password, if the user entered one.
            if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
                mPasswordView.setError(getString(R.string.error_invalid_password));
                focusView = mPasswordView;
                cancel = true;
            }

            // Check for a valid username.
            if (TextUtils.isEmpty(username)) {
                mUsernameView.setError(getString(R.string.error_field_required));
                focusView = mUsernameView;
                cancel = true;
            } else if (!isUsernameValid(username)) {
                mUsernameView.setError(getString(R.string.error_invalid_username));
                focusView = mUsernameView;
                cancel = true;
            }

            if (cancel) {
                // There was an error; don't attempt login and focus the first
                // form field with an error.
                focusView.requestFocus();
            } else {
                // Show a progress spinner, and kick off a background task to
                // perform the user login attempt.
                showProgress(true);
                mAuthTask = new UserLoginTask(username, password, this);
                mAuthTask.execute((Void) null);
            }
        }

    }

    private boolean isUsernameValid(String username) {
        return username.length() > 3;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. if available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimeTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimeTime).alpha(show ? 0 : 1)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                        }
                    });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimeTime).alpha(show ? 1 : 0)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                        }
                    });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundlr) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contant.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only username.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> usernames = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            usernames.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        // Here is where we would add emails to autocomplete
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final static String AUTHENTICATE_URL =
                "http://cssgate.insttech.washington.edu/~mrsharff/Android/crlogin.php?";

        private final String mUsername;
        private final String mPassword;

        private Context mContext;

        public UserLoginTask(String username, String password, Context context) {
            mUsername = username;
            mPassword = password;
            mContext = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            String shaPassword = getSHA256(mPassword);
            Log.i(LOG_TAG, "SHA: " + shaPassword);
            String response = "";
            HttpURLConnection urlConnection = null;
            String url = buildLoginURL(shaPassword);

            try {
                URL urlObject = new URL(url);
                urlConnection = (HttpURLConnection) urlObject.openConnection();

                InputStream content = urlConnection.getInputStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s = buffer.readLine();
                Log.i(LOG_TAG, "S: " + s);
            } catch (Exception e) {
                response = "Unable to login, reason: " + e.getMessage();
                Log.e(LOG_TAG, response);
                return false;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                //Only set the shared prefs to "logged in" if we actually successfully log in
                mSharedPreferences.edit().putBoolean(getString(R.string.LOGGEDIN), true).apply();
                mSharedPreferences.edit().putString(getString(R.string.LOGGEDIN_USERNAME), mUsername);
                Intent intent = new Intent(mContext, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }

        private String getSHA256(String password) {
            MessageDigest mdSHA256 = null;
            String shaHash = null;
            try {
                mdSHA256 = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e1) {
                Log.e(LOG_TAG, "Error initializing SHA256 message digest");
            }
            try {
                mdSHA256.update(password.getBytes("ASCII"));
            } catch (UnsupportedEncodingException e2) {
                e2.printStackTrace();
            }

            byte[] data = mdSHA256.digest();
            try {
                shaHash = convertToHex(data);
            } catch (IOException e3) {
                e3.printStackTrace();
            }

            return shaHash;
        }

        private String buildLoginURL(String password) {

            StringBuilder sb = new StringBuilder(AUTHENTICATE_URL);

            try {
                sb.append("email=");
                sb.append(mUsername);

                sb.append("&pwd=");
                sb.append(password);

                Log.i("LoginURL", sb.toString());

            }
            catch(Exception e) {
                Log.e(LOG_TAG, "Something wrong with the url" + e.getMessage());
            }
            return sb.toString();
        }

        private String convertToHex(byte[] theData) throws java.io.IOException {
            StringBuffer stringBuffer = new StringBuffer();
            String hex;
            hex = Base64.encodeToString(theData, 0, theData.length, 0);

            stringBuffer.append(hex);
            return stringBuffer.toString();
        }
    }
}