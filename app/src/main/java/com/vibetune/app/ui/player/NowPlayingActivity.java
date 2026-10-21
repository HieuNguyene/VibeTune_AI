package com.vibetune.app.ui.player;

import android.content.ComponentName;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.SeekBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.os.Build;
import androidx.media3.common.Player;
import androidx.media3.session.MediaController;
import androidx.media3.session.SessionToken;
import com.bumptech.glide.Glide;
import com.google.common.util.concurrent.ListenableFuture;
import com.vibetune.app.R;
import com.vibetune.app.databinding.ActivityNowPlayingBinding;
import com.vibetune.core.model.Song;
import com.vibetune.core.playback.MusicPlaybackService;

public class NowPlayingActivity extends AppCompatActivity {

    private ActivityNowPlayingBinding binding;
    private MediaController mediaController;
    private ListenableFuture<MediaController> controllerFuture;
    private Song song;
    private boolean isFavorite = false;
    private final Handler progressHandler = new Handler(Looper.getMainLooper());
    private Runnable progressRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNowPlayingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            song = getIntent().getSerializableExtra("EXTRA_SONG", Song.class);
        } else {
            song = (Song) getIntent().getSerializableExtra("EXTRA_SONG");
        }

        if (song != null) {
            binding.tvSongTitle.setText(song.getTitle());
            binding.tvArtistName.setText(song.getArtistName());

            int m = song.getDurationSeconds() / 60;
            int s = song.getDurationSeconds() % 60;
            binding.tvTotalTime.setText(String.format("%02d:%02d", m, s));

            if (song.getCoverUrl() != null && !song.getCoverUrl().isEmpty()) {
                Glide.with(this)
                    .load(song.getCoverUrl())
                    .placeholder(R.drawable.ic_library)
                    .into(binding.imgLargeCover);
            }
        }

        binding.btnCollapse.setOnClickListener(v -> finish());

        // Toggle Favorite Heart Icon (Spotify Green)
        binding.btnFavorite.setOnClickListener(v -> {
            isFavorite = !isFavorite;
            binding.btnFavorite.setColorFilter(
                ContextCompat.getColor(this, isFavorite ? R.color.spotify_green : R.color.spotify_white)
            );
        });

        initializeMediaController();
    }

    private void initializeMediaController() {
        SessionToken sessionToken = new SessionToken(this, new ComponentName(this, MusicPlaybackService.class));
        controllerFuture = new MediaController.Builder(this, sessionToken).buildAsync();
        controllerFuture.addListener(() -> {
            try {
                mediaController = controllerFuture.get();
                setupPlaybackControls();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, getMainExecutor());
    }

    private void setupPlaybackControls() {
        if (mediaController == null) return;

        // Sync initial play/pause state
        updatePlayPauseIcon(mediaController.isPlaying());

        binding.btnPlayPause.setOnClickListener(v -> {
            if (mediaController.isPlaying()) {
                mediaController.pause();
            } else {
                mediaController.play();
            }
        });

        binding.btnNext.setOnClickListener(v -> mediaController.seekToNextMediaItem());
        binding.btnPrevious.setOnClickListener(v -> mediaController.seekToPreviousMediaItem());

        mediaController.addListener(new Player.Listener() {
            @Override
            public void onIsPlayingChanged(boolean isPlaying) {
                updatePlayPauseIcon(isPlaying);
            }
        });

        // SeekBar Tracking
        setupSeekBarProgress();
    }

    private void updatePlayPauseIcon(boolean isPlaying) {
        binding.imgPlayPauseIcon.setImageResource(
            isPlaying ? R.drawable.ic_pause : R.drawable.ic_play
        );
    }

    private void setupSeekBarProgress() {
        progressRunnable = new Runnable() {
            @Override
            public void run() {
                if (mediaController != null && mediaController.isPlaying()) {
                    long current = mediaController.getCurrentPosition();
                    long total = mediaController.getDuration();
                    if (total > 0) {
                        int progress = (int) ((current * 100) / total);
                        binding.seekBarTrack.setProgress(progress);

                        long curSec = current / 1000;
                        binding.tvCurrentTime.setText(String.format("%02d:%02d", curSec / 60, curSec % 60));
                    }
                }
                progressHandler.postDelayed(this, 1000);
            }
        };
        progressHandler.post(progressRunnable);

        binding.seekBarTrack.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && mediaController != null) {
                    long total = mediaController.getDuration();
                    if (total > 0) {
                        long target = (total * progress) / 100;
                        mediaController.seekTo(target);
                    }
                }
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressHandler != null && progressRunnable != null) {
            progressHandler.removeCallbacks(progressRunnable);
        }
        if (controllerFuture != null) {
            MediaController.releaseFuture(controllerFuture);
        }
    }
}
