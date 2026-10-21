package com.vibetune.app.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.vibetune.app.R;
import com.vibetune.app.databinding.ItemSongRowBinding;
import com.vibetune.core.model.Song;
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    public interface OnSongClickListener {
        void onSongClick(Song song);
    }

    private final List<Song> songList;
    private final OnSongClickListener listener;

    public SongAdapter(List<Song> songList, OnSongClickListener listener) {
        this.songList = songList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSongRowBinding binding = ItemSongRowBinding.inflate(
            LayoutInflater.from(parent.getContext()), parent, false
        );
        return new SongViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song song = songList.get(position);
        holder.bind(song);
    }

    @Override
    public int getItemCount() {
        return songList != null ? songList.size() : 0;
    }

    class SongViewHolder extends RecyclerView.ViewHolder {
        private final ItemSongRowBinding binding;

        SongViewHolder(ItemSongRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Song song) {
            binding.tvRowTitle.setText(song.getTitle());
            binding.tvRowSubtitle.setText(song.getArtistName());

            int m = song.getDurationSeconds() / 60;
            int s = song.getDurationSeconds() % 60;
            binding.tvRowDuration.setText(String.format("%02d:%02d", m, s));

            Context context = binding.getRoot().getContext();
            if (song.getCoverUrl() != null && !song.getCoverUrl().isEmpty()) {
                Glide.with(context)
                    .load(song.getCoverUrl())
                    .placeholder(R.drawable.ic_library)
                    .into(binding.imgSongCover);
            }

            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) {
                    listener.onSongClick(song);
                }
            });
        }
    }
}
