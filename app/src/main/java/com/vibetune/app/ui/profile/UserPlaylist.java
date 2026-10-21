package com.vibetune.app.ui.profile;

import java.io.Serializable;

public class UserPlaylist implements Serializable {
    private String id;
    private String name;
    private int songCount;
    private boolean isPublic;
    private String artistsPreview;

    public UserPlaylist(String id, String name, int songCount, boolean isPublic, String artistsPreview) {
        this.id = id;
        this.name = name;
        this.songCount = songCount;
        this.isPublic = isPublic;
        this.artistsPreview = artistsPreview;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getSongCount() { return songCount; }
    public void setSongCount(int songCount) { this.songCount = songCount; }
    public boolean isPublic() { return isPublic; }
    public void setPublic(boolean aPublic) { isPublic = aPublic; }
    public String getArtistsPreview() { return artistsPreview; }
}
