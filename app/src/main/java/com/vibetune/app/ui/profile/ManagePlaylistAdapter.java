package com.vibetune.app.ui.profile;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.vibetune.app.databinding.ItemManagePlaylistBinding;
import java.util.List;

public class ManagePlaylistAdapter extends RecyclerView.Adapter<ManagePlaylistAdapter.ViewHolder> {

    public interface OnVisibilityChangeListener {
        void onVisibilityChanged(UserPlaylist playlist, boolean isPublic);
    }

    private final List<UserPlaylist> playlists;
    private final OnVisibilityChangeListener listener;

    public ManagePlaylistAdapter(List<UserPlaylist> playlists, OnVisibilityChangeListener listener) {
        this.playlists = playlists;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemManagePlaylistBinding binding = ItemManagePlaylistBinding.inflate(
            LayoutInflater.from(parent.getContext()), parent, false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserPlaylist playlist = playlists.get(position);
        holder.binding.tvManagePlaylistName.setText(playlist.getName());
        holder.binding.tvManagePlaylistStatus.setText(
            playlist.isPublic() ? "Hiển thị trên trang cá nhân" : "Đã ẩn khỏi trang cá nhân"
        );
        holder.binding.swManageVisibility.setOnCheckedChangeListener(null);
        holder.binding.swManageVisibility.setChecked(playlist.isPublic());

        holder.binding.swManageVisibility.setOnCheckedChangeListener((buttonView, isChecked) -> {
            playlist.setPublic(isChecked);
            holder.binding.tvManagePlaylistStatus.setText(
                isChecked ? "Hiển thị trên trang cá nhân" : "Đã ẩn khỏi trang cá nhân"
            );
            if (listener != null) {
                listener.onVisibilityChanged(playlist, isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ItemManagePlaylistBinding binding;

        ViewHolder(ItemManagePlaylistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
