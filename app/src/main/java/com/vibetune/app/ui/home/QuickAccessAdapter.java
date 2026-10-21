package com.vibetune.app.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.vibetune.app.R;
import com.vibetune.app.databinding.ItemQuickAccessBinding;
import com.vibetune.core.model.Song;
import java.util.List;

public class QuickAccessAdapter extends RecyclerView.Adapter<QuickAccessAdapter.QuickViewHolder> {

    public interface OnQuickClickListener {
        void onQuickClick(Song song);
    }

    private final List<Song> list;
    private final OnQuickClickListener listener;

    public QuickAccessAdapter(List<Song> list, OnQuickClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public QuickViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemQuickAccessBinding binding = ItemQuickAccessBinding.inflate(
            LayoutInflater.from(parent.getContext()), parent, false
        );
        return new QuickViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull QuickViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list != null ? Math.min(list.size(), 6) : 0;
    }

    class QuickViewHolder extends RecyclerView.ViewHolder {
        private final ItemQuickAccessBinding binding;

        QuickViewHolder(ItemQuickAccessBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Song song) {
            binding.tvQuickTitle.setText(song.getTitle());
            Context context = binding.getRoot().getContext();
            if (song.getCoverUrl() != null && !song.getCoverUrl().isEmpty()) {
                Glide.with(context)
                    .load(song.getCoverUrl())
                    .placeholder(R.drawable.ic_library)
                    .into(binding.imgQuickCover);
            }

            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) {
                    listener.onQuickClick(song);
                }
            });
        }
    }
}
