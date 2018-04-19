package com.kumail.tvshows.trakt.auth;

import android.net.Uri;

import java.net.URL;

import de.rheinfabrik.heimdall.OAuth2AccessToken;
import de.rheinfabrik.heimdall.grants.OAuth2AuthorizationCodeGrant;
import rx.Observable;

public final class TraktTVAuthorizationCodeGrant extends OAuth2AuthorizationCodeGrant<OAuth2AccessToken> {

    public String clientSecret;

    @Override
    public URL buildAuthorizationUrl() {
        try {
            return new URL(
                    Uri.parse("https://trakt.tv/oauth/authorize")
                            .buildUpon()
                            .appendQueryParameter("client_id", clientId)
                            .appendQueryParameter("redirect_uri", redirectUri)
                            .appendQueryParameter("response_type", RESPONSE_TYPE)
                            .build()
                            .toString()
            );
        } catch (Exception ignored) {
            return null;
        }
    }

    @Override
    public Observable<OAuth2AccessToken> exchangeTokenUsingCode(String code) {
        AccessTokenRequestBody body = new AccessTokenRequestBody(code, clientId, redirectUri, clientSecret, GRANT_TYPE);
        return TraktTvApiFactory.newApiService().grantNewAccessToken(body);
    }
}