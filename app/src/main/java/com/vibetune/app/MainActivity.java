package com.vibetune.app;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.session.MediaController;
import androidx.media3.session.SessionToken;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.common.util.concurrent.ListenableFuture;
import com.vibetune.app.databinding.ActivityMainBinding;
import com.vibetune.app.databinding.DialogProfileInfoBinding;
import com.vibetune.app.ui.auth.LoginActivity;
import com.vibetune.app.ui.home.HomeFragment;
import com.vibetune.app.ui.player.NowPlayingActivity;
import com.vibetune.core.model.Song;
import com.vibetune.core.playback.MusicPlaybackService;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MediaController mediaController;
    private ListenableFuture<MediaController> controllerFuture;
    private Song currentSong;
    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 1. Setup Bottom Navigation
        loadFragment(new HomeFragment());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                loadFragment(new HomeFragment());
                return true;
            } else if (id == R.id.nav_search) {
                Toast.makeText(this, "Tính năng Tìm kiếm bài hát đang kết nối...", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.nav_library) {
                Toast.makeText(this, "Thư viện bài hát cá nhân", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });

        // 2. Setup Mini Player Click -> Open Fullscreen Player
        binding.miniPlayerLayout.miniPlayerCard.setOnClickListener(v -> {
            if (currentSong != null) {
                Intent intent = new Intent(MainActivity.this, NowPlayingActivity.class);
                intent.putExtra("EXTRA_SONG", currentSong);
                startActivity(intent);
            }
        });

        // 3. Mini Player Favorite toggle
        binding.miniPlayerLayout.btnMiniFavorite.setOnClickListener(v -> {
            isFavorite = !isFavorite;
            binding.miniPlayerLayout.btnMiniFavorite.setColorFilter(
                ContextCompat.getColor(this, isFavorite ? R.color.spotify_green : R.color.spotify_white)
            );
        });

        // 4. Setup Navigation Drawer items
        setupNavigationDrawer();

        // 5. Connect to MusicPlaybackService
        initializeMediaController();
    }


    @Override
    protected void onResume() {
        super.onResume();
        android.content.SharedPreferences prefs = getSharedPreferences(
            com.vibetune.app.ui.profile.ProfileActivity.PREFS_NAME,
            MODE_PRIVATE
        );
        String savedName = prefs.getString(
            com.vibetune.app.ui.profile.ProfileActivity.KEY_DISPLAY_NAME,
            "Nguyễn Minh Hiếu"
        );
        if (binding != null && binding.navigationDrawer != null) {
            binding.navigationDrawer.tvDrawerUserName.setText(savedName);
        }
    }

    public void openDrawer() {
        if (binding.drawerLayout != null) {
            binding.drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    private void setupNavigationDrawer() {
        // A. Header: Click Avatar or Name -> Open Full Profile Activity
        binding.navigationDrawer.layoutProfileHeader.setOnClickListener(v -> {
            closeDrawer();
            Intent intent = new Intent(MainActivity.this, com.vibetune.app.ui.profile.ProfileActivity.class);
            startActivity(intent);
        });

        // B. Thêm tài khoản
        binding.navigationDrawer.menuItemAddAccount.setOnClickListener(v -> {
            closeDrawer();
            showAddAccountDialog();
        });

        // C. Số liệu hoạt động nghe
        binding.navigationDrawer.menuItemListeningStats.setOnClickListener(v -> {
            closeDrawer();
            showListeningStatsDialog();
        });

        // D. Gần đây
        binding.navigationDrawer.menuItemRecent.setOnClickListener(v -> {
            closeDrawer();
            showRecentPlayedDialog();
        });

        // E. Tin cập nhật
        binding.navigationDrawer.menuItemNews.setOnClickListener(v -> {
            closeDrawer();
            showNewsDialog();
        });

        // F. Cài đặt và quyền riêng tư
        binding.navigationDrawer.menuItemSettings.setOnClickListener(v -> {
            closeDrawer();
            showSettingsDialog();
        });

        // G. Đăng xuất
        binding.navigationDrawer.menuItemLogout.setOnClickListener(v -> {
            closeDrawer();
            handleLogout();
        });
    }

    private void closeDrawer() {
        if (binding.drawerLayout != null && binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void showProfileInfoDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        DialogProfileInfoBinding dialogBinding = DialogProfileInfoBinding.inflate(getLayoutInflater());
        dialog.setContentView(dialogBinding.getRoot());

        dialogBinding.btnDialogClose.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void showAddAccountDialog() {
        new AlertDialog.Builder(this)
            .setTitle("Thêm tài khoản")
            .setMessage("Bạn muốn chuyển đổi tài khoản hay thêm tài khoản VibeTune AI mới?")
            .setPositiveButton("Thêm tài khoản mới", (dialog, which) -> {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            })
            .setNegativeButton("Hủy", null)
            .show();
    }

    private void showListeningStatsDialog() {
        new AlertDialog.Builder(this)
            .setTitle("📊 Số liệu hoạt động nghe (AI)")
            .setMessage("• Tổng thời gian: 34 giờ nghe\n• Nghệ sĩ yêu thích nhất: Vũ. (12 giờ)\n• Thể loại nghe nhiều: Acoustic (45%), Indie (35%)\n• Chỉ số Vibe Score: 92/100 (Tích cực & Thư giãn)")
            .setPositiveButton("Tuyệt vời", null)
            .show();
    }

    private void showRecentPlayedDialog() {
        new AlertDialog.Builder(this)
            .setTitle("🕒 Đã nghe gần đây")
            .setMessage("1. Lạ Lùng - Vũ.\n2. Bước Qua Mùa Cô Đơn - Vũ.\n3. Đi Về Nhà - Đen Vâu\n4. Nàng Thơ - Hoàng Dũng")
            .setPositiveButton("Đóng", null)
            .show();
    }

    private void showNewsDialog() {
        new AlertDialog.Builder(this)
            .setTitle("🔔 Tin cập nhật VibeTune AI")
            .setMessage("✨ Tính năng mới: AI Playlist Studio theo thời lượng chuẩn bị ra mắt trong bản cập nhật kế tiếp!\n\n⚡ Thuật toán gợi ý âm nhạc đã được nâng cấp chính xác hơn 30%.")
            .setPositiveButton("Đã hiểu", null)
            .show();
    }

    private void showSettingsDialog() {
        new AlertDialog.Builder(this)
            .setTitle("⚙️ Cài đặt & Quyền riêng tư")
            .setMessage("• Chất lượng âm thanh: Rất cao (320kbps)\n• Chế độ tiết kiệm dữ liệu: Tắt\n• Chuẩn hóa âm lượng: Bật\n• Hoạt động nghe riêng tư: Bật\n• Quản lý quyền truy cập AI: Đã cấp phép")
            .setPositiveButton("Đóng", null)
            .show();
    }

    private void handleLogout() {
        new AlertDialog.Builder(this)
            .setTitle("Đăng xuất")
            .setMessage("Bạn có chắc chắn muốn đăng xuất khỏi VibeTune AI?")
            .setPositiveButton("Đăng xuất", (dialog, which) -> {
                Toast.makeText(this, "Đã đăng xuất tài khoản thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            })
            .setNegativeButton("Hủy", null)
            .show();
    }

    private void initializeMediaController() {
        SessionToken sessionToken = new SessionToken(this, new ComponentName(this, MusicPlaybackService.class));
        controllerFuture = new MediaController.Builder(this, sessionToken).buildAsync();
        controllerFuture.addListener(() -> {
            try {
                mediaController = controllerFuture.get();
                setupPlayerControls();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, getMainExecutor());
    }

    private void setupPlayerControls() {
        if (mediaController == null) return;

        binding.miniPlayerLayout.btnMiniPlayPause.setOnClickListener(v -> {
            if (mediaController.isPlaying()) {
                mediaController.pause();
            } else {
                mediaController.play();
            }
        });

        mediaController.addListener(new Player.Listener() {
            @Override
            public void onIsPlayingChanged(boolean isPlaying) {
                binding.miniPlayerLayout.btnMiniPlayPause.setImageResource(
                    isPlaying ? R.drawable.ic_pause : R.drawable.ic_play
                );
            }
        });
    }

    public void playSong(Song song) {
        this.currentSong = song;

        // 1. Update Mini Player UI
        binding.miniPlayerLayout.miniPlayerCard.setVisibility(View.VISIBLE);
        binding.miniPlayerLayout.tvMiniTitle.setText(song.getTitle());
        binding.miniPlayerLayout.tvMiniArtist.setText(song.getArtistName());
        binding.miniPlayerLayout.btnMiniPlayPause.setImageResource(R.drawable.ic_pause);

        if (song.getCoverUrl() != null && !song.getCoverUrl().isEmpty()) {
            Glide.with(this)
                .load(song.getCoverUrl())
                .placeholder(R.drawable.ic_library)
                .into(binding.miniPlayerLayout.imgMiniCover);
        }

        // 2. Play Audio via ExoPlayer in Service
        if (mediaController != null && song.getAudioSource() != null) {
            MediaItem mediaItem = MediaItem.fromUri(Uri.parse(song.getAudioSource()));
            mediaController.setMediaItem(mediaItem);
            mediaController.prepare();
            mediaController.play();
        }
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout != null && binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (controllerFuture != null) {
            MediaController.releaseFuture(controllerFuture);
        }
    }
}
