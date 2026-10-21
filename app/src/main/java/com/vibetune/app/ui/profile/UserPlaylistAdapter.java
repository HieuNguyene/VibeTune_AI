package com.vibetune.app.ui.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.vibetune.app.R;
import com.vibetune.app.databinding.ItemUserPlaylistBinding;
import java.util.List;

public class UserPlaylistAdapter extends RecyclerView.Adapter<UserPlaylistAdapter.ViewHolder> {

    public interface OnPlaylistClickListener {
        void onPlayPlaylist(UserPlaylist playlist);
    }

    private final List<UserPlaylist> playlists;
    private final OnPlaylistClickListener listener;

    public UserPlaylistAdapter(List<UserPlaylist> playlists, OnPlaylistClickListener listener) {
        this.playlists = playlists;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUserPlaylistBinding binding = ItemUserPlaylistBinding.inflate(
            LayoutInflater.from(parent.getContext()), parent, false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserPlaylist playlist = playlists.get(position);
        holder.bind(playlist, listener);
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemUserPlaylistBinding binding;

        public ViewHolder(ItemUserPlaylistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(UserPlaylist playlist, OnPlaylistClickListener listener) {
            Context context = itemView.getContext();
            binding.tvPlaylistName.setText(playlist.getName());
            binding.tvPlaylistSubtitle.setText(
                playlist.getSongCount() + " bài hát • " + playlist.getArtistsPreview()
            );

            if (playlist.isPublic()) {
                binding.tvVisibilityBadge.setText("Công khai");
                binding.tvVisibilityBadge.setBackgroundColor(ContextCompat.getColor(context, R.color.spotify_green));
                binding.tvVisibilityBadge.setTextColor(ContextCompat.getColor(context, R.color.spotify_black));
            } else {
                binding.tvVisibilityBadge.setText("Đã ẩn");
                binding.tvVisibilityBadge.setBackgroundColor(ContextCompat.getColor(context, R.color.spotify_chip_surface));
                binding.tvVisibilityBadge.setTextColor(ContextCompat.getColor(context, R.color.spotify_light_gray));
            }

            itemView.setOnClickListener(v -> {
                if (listener != null) listener.onPlayPlaylist(playlist);
            });

            binding.btnPlayPlaylist.setOnClickListener(v -> {
                if (listener != null) listener.onPlayPlaylist(playlist);
            });
        }
    }
}
