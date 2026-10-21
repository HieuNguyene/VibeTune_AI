package com.vibetune.app.ui.profile;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.vibetune.app.R;
import com.vibetune.app.databinding.ItemRecentArtistBinding;
import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ViewHolder> {

    private final List<Artist> artists;

    public ArtistAdapter(List<Artist> artists) {
        this.artists = artists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRecentArtistBinding binding = ItemRecentArtistBinding.inflate(
            LayoutInflater.from(parent.getContext()), parent, false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Artist artist = artists.get(position);
        holder.binding.tvArtistName.setText(artist.getName());
        holder.binding.tvArtistTag.setText(artist.getGenre());
        holder.binding.imgArtistAvatar.setImageResource(R.drawable.ic_avatar_default);
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ItemRecentArtistBinding binding;

        ViewHolder(ItemRecentArtistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
