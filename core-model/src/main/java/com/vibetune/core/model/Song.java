package com.vibetune.core.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class Song implements Serializable {
    @SerializedName("id")
    private UUID id;

    @SerializedName("title")
    private String title;

    @SerializedName("artistId")
    private UUID artistId;

    @SerializedName("artistName")
    private String artistName;

    @SerializedName("genreId")
    private UUID genreId;

    @SerializedName("genreName")
    private String genreName;

    @SerializedName("audioSource")
    private String audioSource;

    @SerializedName("durationSeconds")
    private int durationSeconds;

    @SerializedName("coverUrl")
    private String coverUrl;

    @SerializedName("isActive")
    private boolean isActive;

    public Song() {}

    public Song(UUID id, String title, String artistName, String audioSource, int durationSeconds, String coverUrl) {
        this.id = id;
        this.title = title;
        this.artistName = artistName;
        this.audioSource = audioSource;
        this.durationSeconds = durationSeconds;
        this.coverUrl = coverUrl;
        this.isActive = true;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public UUID getArtistId() { return artistId; }
    public void setArtistId(UUID artistId) { this.artistId = artistId; }

    public String getArtistName() { return artistName != null ? artistName : "Unknown Artist"; }
    public void setArtistName(String artistName) { this.artistName = artistName; }

    public UUID getGenreId() { return genreId; }
    public void setGenreId(UUID genreId) { this.genreId = genreId; }

    public String getGenreName() { return genreName; }
    public void setGenreName(String genreName) { this.genreName = genreName; }

    public String getAudioSource() { return audioSource; }
    public void setAudioSource(String audioSource) { this.audioSource = audioSource; }

    public int getDurationSeconds() { return durationSeconds; }
    public void setDurationSeconds(int durationSeconds) { this.durationSeconds = durationSeconds; }

    public String getCoverUrl() { return coverUrl; }
    public void setCoverUrl(String coverUrl) { this.coverUrl = coverUrl; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return Objects.equals(id, song.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
