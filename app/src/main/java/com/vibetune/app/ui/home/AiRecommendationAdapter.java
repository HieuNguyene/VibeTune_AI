package com.vibetune.app.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.vibetune.app.R;
import com.vibetune.app.databinding.ItemAiRecommendationBinding;
import com.vibetune.core.model.Song;
import com.vibetune.core.model.dto.RecommendedSongDTO;
import java.util.List;

public class AiRecommendationAdapter extends RecyclerView.Adapter<AiRecommendationAdapter.AiViewHolder> {

    public interface OnRecClickListener {
        void onRecClick(Song song);
    }

    private final List<RecommendedSongDTO> recList;
    private final OnRecClickListener listener;

    public AiRecommendationAdapter(List<RecommendedSongDTO> recList, OnRecClickListener listener) {
        this.recList = recList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAiRecommendationBinding binding = ItemAiRecommendationBinding.inflate(
            LayoutInflater.from(parent.getContext()), parent, false
        );
        return new AiViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AiViewHolder holder, int position) {
        RecommendedSongDTO item = recList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return recList != null ? recList.size() : 0;
    }

    class AiViewHolder extends RecyclerView.ViewHolder {
        private final ItemAiRecommendationBinding binding;

        AiViewHolder(ItemAiRecommendationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(RecommendedSongDTO item) {
            Song song = item.getSong();
            binding.tvRecTitle.setText(song.getTitle());
            binding.tvRecArtist.setText(song.getArtistName());
            binding.tvRecReason.setText(item.getReason());

            Context context = binding.getRoot().getContext();
            if (song.getCoverUrl() != null && !song.getCoverUrl().isEmpty()) {
                Glide.with(context)
                    .load(song.getCoverUrl())
                    .placeholder(R.drawable.ic_library)
                    .into(binding.imgRecCover);
            }

            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) {
                    listener.onRecClick(song);
                }
            });
        }
    }
}
