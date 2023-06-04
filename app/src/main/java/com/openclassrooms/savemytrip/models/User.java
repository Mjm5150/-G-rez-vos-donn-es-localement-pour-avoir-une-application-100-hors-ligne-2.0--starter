package com.openclassrooms.savemytrip.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey
    private long id;

    private String username;

    private String urlPicture;

    public User(long id, String username, String urlPicture) {

        this.id = id;

        this.username = username;

        this.urlPicture = urlPicture;

    }

    // --- GETTERS ---

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getUrlPicture() {
        return urlPicture;
    }

    // --- SETTERS ---

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
    }


}
