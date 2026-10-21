package com.vibetune.core.model.dto;

import com.google.gson.annotations.SerializedName;
import com.vibetune.core.model.Song;
import java.io.Serializable;

public class RecommendedSongDTO implements Serializable {
    @SerializedName("song")
    private Song song;

    @SerializedName("score")
    private double score;

    @SerializedName("reason")
    private String reason;

    public RecommendedSongDTO() {}

    public RecommendedSongDTO(Song song, double score, String reason) {
        this.song = song;
        this.score = score;
        this.reason = reason;
    }

    public Song getSong() { return song; }
    public void setSong(Song song) { this.song = song; }

    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }

    public String getReason() { return reason != null ? reason : "Matches your taste"; }
    public void setReason(String reason) { this.reason = reason; }
}
