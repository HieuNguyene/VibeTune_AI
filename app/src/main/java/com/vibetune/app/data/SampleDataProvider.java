package com.vibetune.app.data;

import com.vibetune.core.model.Song;
import com.vibetune.core.model.dto.RecommendedSongDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SampleDataProvider {

    public static List<Song> getSampleSongs() {
        List<Song> list = new ArrayList<>();
        list.add(new Song(UUID.randomUUID(), "Lạ Lùng", "Vũ.", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3", 255, "https://images.unsplash.com/photo-1518609878373-06d740f60d8b?w=500"));
        list.add(new Song(UUID.randomUUID(), "Bước Qua Mùa Cô Đơn", "Vũ.", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3", 270, "https://images.unsplash.com/photo-1445985543470-41fdd6ce388d?w=500"));
        list.add(new Song(UUID.randomUUID(), "Đi Về Nhà", "Đen Vâu", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3", 215, "https://images.unsplash.com/photo-1489710437720-ebb67ec84dd2?w=500"));
        list.add(new Song(UUID.randomUUID(), "Bài Này Chill Phết", "Đen Vâu", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-4.mp3", 260, "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?w=500"));
        list.add(new Song(UUID.randomUUID(), "Nàng Thơ", "Hoàng Dũng", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-5.mp3", 252, "https://images.unsplash.com/photo-1518895949257-7621c3c786d7?w=500"));
        list.add(new Song(UUID.randomUUID(), "Đôi Lời Tình Ca", "Hoàng Dũng", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-6.mp3", 210, "https://images.unsplash.com/photo-1470225620780-dba8ba36b745?w=500"));
        list.add(new Song(UUID.randomUUID(), "Mascara", "Chillies", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-7.mp3", 240, "https://images.unsplash.com/photo-1459749411175-04bf5292ceea?w=500"));
        list.add(new Song(UUID.randomUUID(), "Vùng Ký Ức", "Chillies", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-8.mp3", 230, "https://images.unsplash.com/photo-1506744038136-46273834b3fb?w=500"));
        list.add(new Song(UUID.randomUUID(), "Đi Đu Đưa Đi", "Bích Phương", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-9.mp3", 210, "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?w=500"));
        list.add(new Song(UUID.randomUUID(), "Bùa Yêu", "Bích Phương", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-10.mp3", 242, "https://images.unsplash.com/photo-1492684223066-81342ee5ff30?w=500"));
        list.add(new Song(UUID.randomUUID(), "Blinding Lights", "The Weeknd", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-11.mp3", 200, "https://images.unsplash.com/photo-1508700115892-45ecd05ae2ad?w=500"));
        list.add(new Song(UUID.randomUUID(), "Save Your Tears", "The Weeknd", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-12.mp3", 215, "https://images.unsplash.com/photo-1511671782779-c97d3d27a1d4?w=500"));
        return list;
    }

    public static List<RecommendedSongDTO> getAiRecommendations() {
        List<RecommendedSongDTO> list = new ArrayList<>();
        List<Song> songs = getSampleSongs();
        list.add(new RecommendedSongDTO(songs.get(0), 0.95, "✨ Hợp gu Acoustic của bạn"));
        list.add(new RecommendedSongDTO(songs.get(4), 0.91, "✨ Vì bạn thường nghe Hoàng Dũng"));
        list.add(new RecommendedSongDTO(songs.get(6), 0.88, "✨ Đề xuất giai điệu Indie"));
        list.add(new RecommendedSongDTO(songs.get(2), 0.84, "✨ Top thịnh hành hôm nay"));
        list.add(new RecommendedSongDTO(songs.get(10), 0.80, "✨ Khám phá Synth-pop mới"));
        return list;
    }
}
