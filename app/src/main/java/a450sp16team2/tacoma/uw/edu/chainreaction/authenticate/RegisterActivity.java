package a450sp16team2.tacoma.uw.edu.chainreaction.authenticate;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
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
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class RegisterActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = RegisterActivity.class.getSimpleName();

    private UserRegisterTask mAuthTask = null;

    // UI Ref
    private EditText mUsernameView;
    private EditText mPasswordView;
    private EditText mRetypeView;
    private View mProgressView;
    private View mRegisterFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Set up the register form.
        mUsernameView = (EditText) findViewById(R.id.register_input_username);
        mPasswordView = (EditText) findViewById(R.id.register_input_password);
        mRetypeView = (EditText) findViewById(R.id.input_confirm_password);

        AppCompatButton mCreateAccountButton = (AppCompatButton) findViewById(R.id.btn_create_account);
        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
                Log.d(LOG_TAG, "Create Account Clicked");
            }
        });

        mRegisterFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.register_progress);

    }

    private void attemptRegister() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of register attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();
        String passwordRetype = mRetypeView.getText().toString();

        boolean cancel = false;
        View focusView = null;

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

        // Check for valid password
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            mPasswordView.setError("Password must be at least 4 characters");
            focusView = mPasswordView;
            cancel = true;
        }

        if (!TextUtils.equals(password, passwordRetype)) {
            mPasswordView.setError("Passwords are not the same");
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            mAuthTask = new UserRegisterTask(username, password, this);
            mAuthTask.execute((Void) null);
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

            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterFormView.animate().setDuration(shortAnimeTime).alpha(show ? 0 : 1)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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

    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {
        private final static String REGISTER_URL =
                "http://cssgate.insttech.washington.edu/~mrsharff/Android/addUser.php?";

        private final String mUsername;
        private final String mPassword;

        private Context mContext;

        public UserRegisterTask(String username, String password, Context context) {
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
            String url = buildRegisterURL(shaPassword);

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
                Intent intent = new Intent(mContext, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                // Something went bad
                // TODO: Error handling from returned php
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

        private String buildRegisterURL(String password) {

            StringBuilder sb = new StringBuilder(REGISTER_URL);

            try {
                sb.append("email=");
                sb.append(mUsername);

                sb.append("&pwd=");
                sb.append(password);

                Log.i(LOG_TAG, "AddURL " + sb.toString());

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
