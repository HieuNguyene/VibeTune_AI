-- ====================================================================
-- Project: VibeTune AI
-- Description: Seed Data for Testing & AI Scoring (Flyway V2)
-- ====================================================================

-- 1. Insert Genres
INSERT INTO genres (id, name, description) VALUES
('11111111-1111-1111-1111-111111111101', 'Pop', 'Âm nhạc đại chúng hiện đại, giai điệu bắt tai và sôi động'),
('11111111-1111-1111-1111-111111111102', 'Acoustic', 'Âm nhạc mộc mạc sử dụng nhạc cụ không điện tử, nhẹ nhàng, sâu lắng'),
('11111111-1111-1111-1111-111111111103', 'Indie', 'Âm nhạc độc lập mang phong cách sáng tạo cá nhân và cảm xúc tự nhiên'),
('11111111-1111-1111-1111-111111111104', 'Ballad', 'Những bản tình ca giai điệu chậm, giàu cảm xúc và sâu sắc'),
('11111111-1111-1111-1111-111111111105', 'Lofi Chill', 'Giai điệu thư giãn với nhịp điệu chậm, phù hợp học tập và làm việc'),
('11111111-1111-1111-1111-111111111106', 'R&B / Soul', 'Tiết tấu nhịp nhàng kết hợp cảm xúc giai điệu hiện đại'),
('11111111-1111-1111-1111-111111111107', 'Electronic / Dance', 'Âm nhạc điện tử sôi động, tiết tấu mạnh mẽ cho buổi tập gym')
ON CONFLICT (id) DO NOTHING;

-- 2. Insert Artists
INSERT INTO artists (id, name, bio, image_url) VALUES
('22222222-2222-2222-2222-222222222201', 'Vũ.', 'Hoàng tử Indie Việt Nam với những bản tình ca mộc mạc và sâu lắng', 'https://images.unsplash.com/photo-1511671782779-c97d3d27a1d4?w=500'),
('22222222-2222-2222-2222-222222222202', 'Đen Vâu', 'Rapper hàng đầu với những ca từ mộc mạc, triết lý sống gần gũi', 'https://images.unsplash.com/photo-1493225457124-a3eb161ffa5f?w=500'),
('22222222-2222-2222-2222-222222222203', 'Hoàng Dũng', 'Ca sĩ, nhạc sĩ tài năng với chất giọng ballad truyền cảm', 'https://images.unsplash.com/photo-1514525253161-7a46d19cd819?w=500'),
('22222222-2222-2222-2222-222222222204', 'Chillies', 'Ban nhạc Pop-Rock/Indie với những giai điệu đầy hoài niệm', 'https://images.unsplash.com/photo-1470225620780-dba8ba36b745?w=500'),
('22222222-2222-2222-2222-222222222205', 'Bích Phương', 'Nữ ca sĩ với nhiều phong cách đa dạng từ Ballad đến Pop điện tử', 'https://images.unsplash.com/photo-1516450360452-9312f5e86fc7?w=500'),
('22222222-2222-2222-2222-222222222206', 'The Weeknd', 'Nghệ sĩ R&B/Pop quốc tế với âm hưởng Synth-pop thập niên 80', 'https://images.unsplash.com/photo-1508700115892-45ecd05ae2ad?w=500')
ON CONFLICT (id) DO NOTHING;

-- 3. Insert Songs (Audio links sử dụng nguồn royalty-free hợp lệ)
INSERT INTO songs (id, title, artist_id, genre_id, audio_source, duration_seconds, cover_url, is_active) VALUES
('33333333-3333-3333-3333-333333333301', 'Bước Qua Mùa Cô Đơn', '22222222-2222-2222-2222-222222222201', '11111111-1111-1111-1111-111111111103', 'https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3', 270, 'https://images.unsplash.com/photo-1445985543470-41fdd6ce388d?w=500', true),
('33333333-3333-3333-3333-333333333302', 'Lạ Lùng', '22222222-2222-2222-2222-222222222201', '11111111-1111-1111-1111-111111111102', 'https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3', 255, 'https://images.unsplash.com/photo-1518609878373-06d740f60d8b?w=500', true),
('33333333-3333-3333-3333-333333333303', 'Đi Về Nhà', '22222222-2222-2222-2222-222222222202', '11111111-1111-1111-1111-111111111101', 'https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3', 215, 'https://images.unsplash.com/photo-1489710437720-ebb67ec84dd2?w=500', true),
('33333333-3333-3333-3333-333333333304', 'Bài Này Chill Phết', '22222222-2222-2222-2222-222222222202', '11111111-1111-1111-1111-111111111105', 'https://www.soundhelix.com/examples/mp3/SoundHelix-Song-4.mp3', 260, 'https://images.unsplash.com/photo-1507525428034-b723cf961d3e?w=500', true),
('33333333-3333-3333-3333-333333333305', 'Nàng Thơ', '22222222-2222-2222-2222-222222222203', '11111111-1111-1111-1111-111111111104', 'https://www.soundhelix.com/examples/mp3/SoundHelix-Song-5.mp3', 252, 'https://images.unsplash.com/photo-1518895949257-7621c3c786d7?w=500', true),
('33333333-3333-3333-3333-333333333306', 'Đôi Lời Tình Ca', '22222222-2222-2222-2222-222222222203', '11111111-1111-1111-1111-111111111102', 'https://www.soundhelix.com/examples/mp3/SoundHelix-Song-6.mp3', 210, 'https://images.unsplash.com/photo-1470225620780-dba8ba36b745?w=500', true),
('33333333-3333-3333-3333-333333333307', 'Mascara', '22222222-2222-2222-2222-222222222204', '11111111-1111-1111-1111-111111111103', 'https://www.soundhelix.com/examples/mp3/SoundHelix-Song-7.mp3', 240, 'https://images.unsplash.com/photo-1459749411175-04bf5292ceea?w=500', true),
('33333333-3333-3333-3333-333333333308', 'Vùng Ký Ức', '22222222-2222-2222-2222-222222222204', '11111111-1111-1111-1111-111111111103', 'https://www.soundhelix.com/examples/mp3/SoundHelix-Song-8.mp3', 230, 'https://images.unsplash.com/photo-1506744038136-46273834b3fb?w=500', true),
('33333333-3333-3333-3333-333333333309', 'Đi Đu Đưa Đi', '22222222-2222-2222-2222-222222222205', '11111111-1111-1111-1111-111111111107', 'https://www.soundhelix.com/examples/mp3/SoundHelix-Song-9.mp3', 210, 'https://images.unsplash.com/photo-1514525253161-7a46d19cd819?w=500', true),
('33333333-3333-3333-3333-333333333310', 'Bùa Yêu', '22222222-2222-2222-2222-222222222205', '11111111-1111-1111-1111-111111111101', 'https://www.soundhelix.com/examples/mp3/SoundHelix-Song-10.mp3', 242, 'https://images.unsplash.com/photo-1492684223066-81342ee5ff30?w=500', true),
('33333333-3333-3333-3333-333333333311', 'Blinding Lights', '22222222-2222-2222-2222-222222222206', '11111111-1111-1111-1111-111111111107', 'https://www.soundhelix.com/examples/mp3/SoundHelix-Song-11.mp3', 200, 'https://images.unsplash.com/photo-1508700115892-45ecd05ae2ad?w=500', true),
('33333333-3333-3333-3333-333333333312', 'Save Your Tears', '22222222-2222-2222-2222-222222222206', '11111111-1111-1111-1111-111111111106', 'https://www.soundhelix.com/examples/mp3/SoundHelix-Song-12.mp3', 215, 'https://images.unsplash.com/photo-1511671782779-c97d3d27a1d4?w=500', true)
ON CONFLICT (id) DO NOTHING;

-- 4. Insert Demo User (Mật khẩu mặc định: 123456, mã hóa BCrypt)
INSERT INTO users (id, email, password_hash, display_name, avatar_url) VALUES
('44444444-4444-4444-4444-444444444401', 'hieu@vibetune.ai', '$2a$10$w0kX5x9J/ZgK1n1vJmKqA.h6A0dC9bM0eH0eK1eH1eK1eH1eK1eH1', 'Minh Hiếu', 'https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=500')
ON CONFLICT (id) DO NOTHING;

-- 5. Insert User Preferences (Sở thích ban đầu của user: Thích Acoustic và Indie)
INSERT INTO user_preferences (user_id, genre_id, weight) VALUES
('44444444-4444-4444-4444-444444444401', '11111111-1111-1111-1111-111111111102', 0.9), -- Acoustic
('44444444-4444-4444-4444-444444444401', '11111111-1111-1111-1111-111111111103', 0.8)  -- Indie
ON CONFLICT (user_id, genre_id) DO NOTHING;

-- 6. Insert User Favorites (Đã thích bài 'Lạ Lùng' và 'Nàng Thơ')
INSERT INTO user_favorites (user_id, song_id) VALUES
('44444444-4444-4444-4444-444444444401', '33333333-3333-3333-3333-333333333302'),
('44444444-4444-4444-4444-444444444401', '33333333-3333-3333-3333-333333333305')
ON CONFLICT (user_id, song_id) DO NOTHING;

-- 7. Insert Sample Playlist
INSERT INTO playlists (id, user_id, name) VALUES
('55555555-5555-5555-5555-555555555501', '44444444-4444-4444-4444-444444444401', 'Nhạc Thư Giãn Buổi Tối')
ON CONFLICT (id) DO NOTHING;

INSERT INTO playlist_songs (playlist_id, song_id, position) VALUES
('55555555-5555-5555-5555-555555555501', '33333333-3333-3333-3333-333333333301', 1),
('55555555-5555-5555-5555-555555555501', '33333333-3333-3333-3333-333333333302', 2),
('55555555-5555-5555-5555-555555555501', '33333333-3333-3333-3333-333333333304', 3)
ON CONFLICT (playlist_id, song_id) DO NOTHING;

-- 8. Insert Sample Listening History (Để thuật toán AI tính điểm)
INSERT INTO listening_history (user_id, song_id, started_at, listened_seconds, completed, skipped) VALUES
('44444444-4444-4444-4444-444444444401', '33333333-3333-3333-3333-333333333301', NOW() - INTERVAL '1 day', 270, true, false),
('44444444-4444-4444-4444-444444444401', '33333333-3333-3333-3333-333333333302', NOW() - INTERVAL '2 hour', 255, true, false),
('44444444-4444-4444-4444-444444444401', '33333333-3333-3333-3333-333333333309', NOW() - INTERVAL '3 hour', 15, false, true); -- Bị skip sau 15s
