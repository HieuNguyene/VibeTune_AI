package com.vibetune.core.network;

import com.vibetune.core.model.Playlist;
import com.vibetune.core.model.Song;
import com.vibetune.core.model.dto.RecommendedSongDTO;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import retrofit2.Call;
import retrofit2.http.*;

public interface MusicApiService {

    // Songs Catalog
    @GET("api/v1/songs")
    Call<List<Song>> getSongs(@Query("page") int page, @Query("size") int size);

    @GET("api/v1/songs/search")
    Call<List<Song>> searchSongs(@Query("keyword") String keyword);

    @GET("api/v1/songs/{id}")
    Call<Song> getSongById(@Path("id") UUID id);

    // AI Recommendations
    @GET("api/v1/recommendations/songs")
    Call<List<RecommendedSongDTO>> getRecommendedSongs(@Query("limit") int limit);

    @POST("api/v1/recommendations/playlists")
    Call<Playlist> generateSmartPlaylist(@Body Map<String, Object> request);

    // Playlists
    @GET("api/v1/playlists")
    Call<List<Playlist>> getUserPlaylists();

    @POST("api/v1/playlists")
    Call<Playlist> createPlaylist(@Body Map<String, String> body);

    // History & Favorites
    @POST("api/v1/me/history")
    Call<Void> recordListeningHistory(@Body Map<String, Object> historyEvent);

    @PUT("api/v1/me/favorites/{songId}")
    Call<Void> addToFavorites(@Path("songId") UUID songId);

    @DELETE("api/v1/me/favorites/{songId}")
    Call<Void> removeFromFavorites(@Path("songId") UUID songId);
}
