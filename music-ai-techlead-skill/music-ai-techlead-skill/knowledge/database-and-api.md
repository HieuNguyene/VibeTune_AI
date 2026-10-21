# Database and REST API Reference

## MVP schema

Use UUID or another consistent ID strategy throughout the application. The schema below is a logical baseline; adjust field types and constraints during migrations.

### Tables
- `users`: `id`, `email`, `password_hash`, `created_at`
- `artists`: `id`, `name`
- `genres`: `id`, `name`
- `songs`: `id`, `title`, `artist_id`, `genre_id`, `audio_source`, `duration_seconds`, `is_active`
- `playlists`: `id`, `user_id`, `name`, `created_at`
- `playlist_songs`: `playlist_id`, `song_id`, `position`, `added_at`
- `listening_history`: `id`, `user_id`, `song_id`, `started_at`, `listened_seconds`, `completed`, `skipped`
- `user_favorites`: `user_id`, `song_id`, `created_at`
- `user_preferences`: `user_id`, `genre_id`, `weight`

### Constraints and indexes
- Unique `users.email`.
- Unique `user_favorites(user_id, song_id)`.
- Unique `playlist_songs(playlist_id, song_id)` if duplicate songs are not allowed in one playlist.
- Foreign keys for every relationship.
- Index common filters such as `songs.title`, `songs.artist_id`, `songs.genre_id`, `listening_history(user_id, started_at)`, and user-owned playlists.
- Use migrations (for example Flyway) so schema changes are versioned.
- Never store plaintext passwords.
- `audio_source` must point to a source the project has permission to use.
- Store only listening events that support product functionality and evaluation.

Add tables such as `recommendation_impressions`, `recommendation_feedback`, or `song_features` only when there is a concrete measurement or feature need.

## API prefix
Use `/api/v1`.

| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/api/v1/songs?page=0&size=20` | Paginated catalog |
| GET | `/api/v1/songs/{id}` | Song details |
| GET | `/api/v1/songs?keyword=...` | Search |
| GET | `/api/v1/songs/{id}/similar` | Similar songs |
| GET | `/api/v1/playlists` | Current user's playlists |
| POST | `/api/v1/playlists` | Create playlist |
| GET | `/api/v1/playlists/{id}` | Playlist detail |
| POST | `/api/v1/playlists/{id}/songs` | Add song |
| DELETE | `/api/v1/playlists/{id}/songs/{songId}` | Remove song |
| GET | `/api/v1/me/favorites` | Current user's favorites |
| PUT | `/api/v1/me/favorites/{songId}` | Add/set favorite |
| DELETE | `/api/v1/me/favorites/{songId}` | Remove favorite |
| POST | `/api/v1/me/history` | Record listening event |
| GET | `/api/v1/me/history` | Listening history |
| GET | `/api/v1/recommendations?limit=10` | Recommended songs |
| POST | `/api/v1/recommendations/playlists` | Generate personalized playlist |
| PUT | `/api/v1/me/preferences` | Update preferences |

## API conventions
- `200 OK`: successful read/update.
- `201 Created`: resource created.
- `400 Bad Request`: invalid input.
- `401 Unauthorized`: missing/invalid authentication.
- `403 Forbidden`: authenticated but not authorized.
- `404 Not Found`: resource does not exist or is not visible to caller.
- `500 Internal Server Error`: unexpected server error; never return stack traces to clients.

For protected routes, derive user identity from verified authentication. Never trust a client-supplied `userId` for authorization.

## Example recommendation response

```json
{
  "success": true,
  "message": "Recommendations retrieved",
  "data": {
    "items": [
      {
        "songId": "example-uuid",
        "title": "Acoustic Sample",
        "reason": "Matches your preferred genre",
        "score": 0.82
      }
    ]
  }
}
```

The example is a contract illustration, not real catalog data. Do not describe `score` as a probability unless it has been calibrated as one.

## Example playlist-generation request

```json
{
  "name": "Focus Session",
  "genreIds": ["genre-1", "genre-2"],
  "targetDurationMinutes": 30,
  "limit": 10
}
```

Validate limits and duration, handle insufficient catalog items, prevent duplicates, and document how the target duration is interpreted.
