package com.vibetune.app.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.vibetune.app.MainActivity;
import com.vibetune.app.data.SampleDataProvider;
import com.vibetune.app.databinding.FragmentHomeBinding;
import com.vibetune.core.model.Song;
import java.util.Calendar;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Dynamic Greeting based on time of day
        setupGreeting();

        // 2. Avatar Click -> Open Spotify Side Navigation Drawer
        binding.btnMenuAvatar.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).openDrawer();
            }
        });
        binding.imgUserAvatar.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).openDrawer();
            }
        });

        // 3. Load Sample Songs
        List<Song> allSongs = SampleDataProvider.getSampleSongs();

        // 4. Setup Quick Access 6-Grid
        binding.rvQuickAccess.setLayoutManager(new GridLayoutManager(getContext(), 2));
        QuickAccessAdapter quickAdapter = new QuickAccessAdapter(allSongs, this::onSongSelected);
        binding.rvQuickAccess.setAdapter(quickAdapter);

        // 5. Setup AI Recommendations (Horizontal)
        binding.rvAiRecommendations.setLayoutManager(
            new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );
        AiRecommendationAdapter aiAdapter = new AiRecommendationAdapter(
            SampleDataProvider.getAiRecommendations(), this::onSongSelected
        );
        binding.rvAiRecommendations.setAdapter(aiAdapter);

        // 6. Setup All Songs List (Vertical)
        binding.rvAllSongs.setLayoutManager(new LinearLayoutManager(getContext()));
        SongAdapter songAdapter = new SongAdapter(allSongs, this::onSongSelected);
        binding.rvAllSongs.setAdapter(songAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        setupGreeting();
    }

    private void setupGreeting() {
        if (binding == null) return;
        android.content.SharedPreferences prefs = requireContext().getSharedPreferences(
            "VibeTuneProfilePrefs", android.content.Context.MODE_PRIVATE
        );
        String savedName = prefs.getString("key_display_name", "Minh Hiếu");
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        String greeting;
        if (hour >= 5 && hour < 12) {
            greeting = "Chào buổi sáng, " + savedName + " ☀️";
        } else if (hour >= 12 && hour < 18) {
            greeting = "Chào buổi chiều, " + savedName + " 🌤️";
        } else {
            greeting = "Chào buổi tối, " + savedName + " 🌙";
        }
        binding.tvGreeting.setText(greeting);
    }

    private void onSongSelected(Song song) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).playSong(song);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
