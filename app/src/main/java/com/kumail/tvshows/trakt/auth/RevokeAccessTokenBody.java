package com.kumail.tvshows.trakt.auth;

import com.google.gson.annotations.SerializedName;

/**
 * Body used to revoke an access token.
 */
public class RevokeAccessTokenBody {

    // Properties

    @SerializedName("access_token")
    public String accessToken;

    // Constructor

    public RevokeAccessTokenBody(String accessToken) {
        super();

        this.accessToken = accessToken;
    }
}