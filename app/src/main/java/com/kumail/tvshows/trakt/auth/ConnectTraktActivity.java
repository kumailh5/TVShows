package com.kumail.tvshows.trakt.auth;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import com.google.gson.GsonBuilder;
import com.kumail.tvshows.ApiUtils;
import com.kumail.tvshows.MainActivity;
import com.kumail.tvshows.R;
import com.kumail.tvshows.db.AppDatabase;
import com.kumail.tvshows.db.entity.UserEntity;
import com.kumail.tvshows.trakt.TraktService;
import com.kumail.tvshows.trakt.data.user.UserSettingsResponse;
import com.uwetrottmann.trakt5.TraktV2;
import com.uwetrottmann.trakt5.entities.AccessToken;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class ConnectTraktActivity extends AppCompatActivity {
    Toolbar toolbar;
    String state;
    TraktV2 traktV2;
    TraktService traktService;
    AccessTokenResponse accessTokenResponse;
    AccessToken accessTokenTraktV2;
    AppDatabase appDatabase;
    UserSettingsResponse userSettingsResponse;
    UserEntity userEntity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_trakt);
        setupToolBar();

        appDatabase = AppDatabase.getInstance(this);
        traktV2 = new TraktV2(TraktKeys.CLIENT_ID, TraktKeys.CLIENT_SECRET, TraktKeys.OAUTH_CALLBACK_URL_CUSTOM);
        state = new BigInteger(130, new SecureRandom()).toString(32);
        String authUrl = traktV2.buildAuthorizationUrl(state);

        Timber.d(authUrl);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(authUrl));
        startActivity(browserIntent);

    }

    private void setupToolBar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getColor(R.color.colorWhite));
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleAuthIntent(intent);
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

    }

    private boolean handleAuthIntent(Intent intent) {
        // handle auth callback from external browser
        Uri callbackUri = intent.getData();
        if (callbackUri == null || !callbackUri.getScheme().equals(TraktKeys.OAUTH_URI_SCHEME)) {
            Timber.d("Callback uri is null or scheme doesn't match");
            return false;
        }
        Timber.d(String.format("Code %s \n State %s", callbackUri.getQueryParameter("code"),
                callbackUri.getQueryParameter("state")));
        fetchTokensAndFinish(callbackUri.getQueryParameter("code"),
                callbackUri.getQueryParameter("state"));
        return true;
    }

    private void fetchTokensAndFinish(@Nullable String authCode, @Nullable String state) {
//        activateFallbackButtons();
//
//        if (taskFragment == null) {
//            taskFragment = new ConnectTraktTaskFragment();
//            getSupportFragmentManager().beginTransaction()
//                    .add(taskFragment, TRAKT_CONNECT_TASK_TAG)
//                    .commit();
//        }
//
//        if (taskFragment.getTask() != null
//                && taskFragment.getTask().getStatus() != AsyncTask.Status.FINISHED) {
//            // connect task is still running
//            setMessage(getString(R.string.waitplease), true);
//            return;
//        }

        // if state does not match what we sent, drop the auth code
        if (this.state == null || !this.state.equals(state)) {
            // log trakt OAuth failures
//            Utils.trackCustomEvent(this, CATEGORY_OAUTH_ERROR, ACTION_FETCHING_TOKENS,
//                    ERROR_DESCRIPTION_STATE_MISMATCH);
//            Timber.tag(CATEGORY_OAUTH_ERROR);
//            Timber.e("%s: %s", ACTION_FETCHING_TOKENS, ERROR_DESCRIPTION_STATE_MISMATCH);
//
//            setMessage(getAuthErrorMessage() + (this.state == null ?
//                    "\n\n(State is null.)" :
//                    "\n\n(State does not match. Cross-site request forgery detected.)"));
            Timber.d("States don't match");
            return;
        }

        if (TextUtils.isEmpty(authCode)) {
            // no valid auth code, remain in activity and show fallback buttons
//            setMessage(getAuthErrorMessage() + "\n\n(No auth code returned.)");
            Timber.d("No auth code");
            return;
        }

        // fetch access token with given OAuth auth code
//        setMessage(getString(R.string.waitplease), true);
//        ConnectTraktTask task = new ConnectTraktTask(this);
//        Utils.executeInOrder(task, authCode);
//        taskFragment.setTask(task);

        AsyncTask.execute(() -> {
            try {
                accessTokenTraktV2 = traktV2.exchangeCodeForAccessToken(authCode).body();
                assert accessTokenTraktV2 != null;
                String accessToken = accessTokenTraktV2.access_token;
                Timber.d("Access token");
                Timber.d(accessToken);
                String tokenType = accessTokenTraktV2.token_type;
                int expiresIn = accessTokenTraktV2.expires_in;
                String refreshToken = accessTokenTraktV2.refresh_token;
                String scope = accessTokenTraktV2.scope;
                accessTokenResponse = new AccessTokenResponse(accessToken, tokenType, expiresIn, refreshToken, scope, 1);
                appDatabase.accessTokenDao().insert(accessTokenResponse);
                fetchUserSettings();

            } catch (IOException e) {
                e.printStackTrace();
            }

        });

    }

    private void fetchUserSettings() {
        traktService = ApiUtils.getOAuthTraktService(accessTokenResponse.getAccessToken());
        Call<UserSettingsResponse> call = traktService.getUserSettings();
        call.enqueue(new Callback<UserSettingsResponse>() {
            @Override
            public void onResponse(Call<UserSettingsResponse> call, Response<UserSettingsResponse> response) {

                if (response.isSuccessful()) {
                    userSettingsResponse = response.body();

                    userEntity = new UserEntity(1, userSettingsResponse.getUser().getIds().getSlug());
                    AsyncTask.execute(() -> appDatabase.userDao().insert(userEntity));

                    Timber.d(new GsonBuilder().setPrettyPrinting().create().toJson(response));
                    Timber.d(userSettingsResponse.getUser().getName());
                    Timber.d(userSettingsResponse.getUser().getUsername());

                } else {
                    Timber.d("Response Unsuccessful");
                    Timber.d(Integer.toString(response.code()));
                    Timber.d(new GsonBuilder().setPrettyPrinting().create().toJson(response));
                }
            }

            @Override
            public void onFailure(Call<UserSettingsResponse> call, Throwable t) {
                Timber.d(t);
            }
        });

    }


}
