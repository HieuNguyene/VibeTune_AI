package com.vibetune.app.ui.profile;

public class Artist {
    private String name;
    private String genre;

    public Artist(String name, String genre) {
        this.name = name;
        this.genre = genre;
    }

    public String getName() { return name; }
    public String getGenre() { return genre; }
}
