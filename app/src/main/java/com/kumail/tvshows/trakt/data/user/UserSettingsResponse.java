package com.kumail.tvshows.trakt.data.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserSettingsResponse {

    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("account")
    @Expose
    private Account account;
    @SerializedName("connections")
    @Expose
    private Connections connections;
    @SerializedName("sharing_text")
    @Expose
    private SharingText sharingText;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Connections getConnections() {
        return connections;
    }

    public void setConnections(Connections connections) {
        this.connections = connections;
    }

    public SharingText getSharingText() {
        return sharingText;
    }

    public void setSharingText(SharingText sharingText) {
        this.sharingText = sharingText;
    }

}