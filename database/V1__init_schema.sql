-- ====================================================================
-- Project: VibeTune AI
-- Description: Initial Database Schema (Flyway V1)
-- Database: PostgreSQL 14+
-- ====================================================================

-- 1. Enable pgcrypto for UUID generation (standard in PostgreSQL)
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- ====================================================================
-- Table 1: users (Quản lý tài khoản người dùng)
-- ====================================================================
CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    display_name VARCHAR(100) NOT NULL,
    avatar_url VARCHAR(500),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- ====================================================================
-- Table 2: artists (Thông tin nghệ sĩ / ca sĩ)
-- ====================================================================
CREATE TABLE IF NOT EXISTS artists (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    bio TEXT,
    image_url VARCHAR(500),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- ====================================================================
-- Table 3: genres (Danh mục thể loại âm nhạc)
-- ====================================================================
CREATE TABLE IF NOT EXISTS genres (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT
);

-- ====================================================================
-- Table 4: songs (Kho siêu dữ liệu bài hát)
-- ====================================================================
CREATE TABLE IF NOT EXISTS songs (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR(255) NOT NULL,
    artist_id UUID NOT NULL REFERENCES artists(id) ON DELETE CASCADE,
    genre_id UUID NOT NULL REFERENCES genres(id) ON DELETE RESTRICT,
    audio_source VARCHAR(500) NOT NULL,
    duration_seconds INT NOT NULL CHECK (duration_seconds > 0),
    cover_url VARCHAR(500),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- ====================================================================
-- Table 5: playlists (Danh sách phát của người dùng)
-- ====================================================================
CREATE TABLE IF NOT EXISTS playlists (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- ====================================================================
-- Table 6: playlist_songs (Chi tiết bài hát trong danh sách phát)
-- ====================================================================
CREATE TABLE IF NOT EXISTS playlist_songs (
    playlist_id UUID NOT NULL REFERENCES playlists(id) ON DELETE CASCADE,
    song_id UUID NOT NULL REFERENCES songs(id) ON DELETE CASCADE,
    position INT NOT NULL DEFAULT 0,
    added_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (playlist_id, song_id)
);

-- ====================================================================
-- Table 7: listening_history (Nhật ký nghe nhạc và tín hiệu telemetry)
-- ====================================================================
CREATE TABLE IF NOT EXISTS listening_history (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    song_id UUID NOT NULL REFERENCES songs(id) ON DELETE CASCADE,
    started_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    listened_seconds INT NOT NULL DEFAULT 0,
    completed BOOLEAN NOT NULL DEFAULT FALSE,
    skipped BOOLEAN NOT NULL DEFAULT FALSE
);

-- ====================================================================
-- Table 8: user_favorites (Danh sách bài hát yêu thích)
-- ====================================================================
CREATE TABLE IF NOT EXISTS user_favorites (
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    song_id UUID NOT NULL REFERENCES songs(id) ON DELETE CASCADE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, song_id)
);

-- ====================================================================
-- Table 9: user_preferences (Trọng số sở thích thể loại - Cold-start)
-- ====================================================================
CREATE TABLE IF NOT EXISTS user_preferences (
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    genre_id UUID NOT NULL REFERENCES genres(id) ON DELETE CASCADE,
    weight DOUBLE PRECISION NOT NULL DEFAULT 1.0 CHECK (weight >= 0.0 AND weight <= 1.0),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, genre_id)
);

-- ====================================================================
-- Indexes for Search & AI Recommendation Engine Optimization
-- ====================================================================
CREATE INDEX IF NOT EXISTS idx_songs_title ON songs (title);
CREATE INDEX IF NOT EXISTS idx_songs_artist_id ON songs (artist_id);
CREATE INDEX IF NOT EXISTS idx_songs_genre_id ON songs (genre_id);
CREATE INDEX IF NOT EXISTS idx_songs_is_active ON songs (is_active);

CREATE INDEX IF NOT EXISTS idx_history_user_started ON listening_history (user_id, started_at DESC);
CREATE INDEX IF NOT EXISTS idx_history_song_id ON listening_history (song_id);

CREATE INDEX IF NOT EXISTS idx_playlists_user_id ON playlists (user_id);
CREATE INDEX IF NOT EXISTS idx_playlist_songs_playlist ON playlist_songs (playlist_id);

CREATE INDEX IF NOT EXISTS idx_user_favorites_user ON user_favorites (user_id);
CREATE INDEX IF NOT EXISTS idx_user_preferences_user ON user_preferences (user_id);
