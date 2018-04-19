package com.kumail.tvshows.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "UserTable")
public class UserEntity {
    @PrimaryKey
    private int id;
    private String username;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public UserEntity() {
    }

    public UserEntity(int id, String username) {
        this.id = id;
        this.username = username;
    }


}
