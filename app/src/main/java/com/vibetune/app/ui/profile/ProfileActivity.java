package com.vibetune.app.ui.profile;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.vibetune.app.R;
import com.vibetune.app.databinding.ActivityProfileBinding;
import com.vibetune.app.databinding.DialogEditProfileNameBinding;
import com.vibetune.app.databinding.DialogManagePlaylistsBinding;
import com.vibetune.app.databinding.DialogProfilePrivacyBinding;
import com.vibetune.app.databinding.DialogShareProfileBinding;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "VibeTuneProfilePrefs";
    public static final String KEY_DISPLAY_NAME = "key_display_name";
    public static final String KEY_USER_BIO = "key_user_bio";
    public static final String KEY_PRIVACY_FOLLOWERS = "key_privacy_followers";
    public static final String KEY_PRIVACY_PLAYLISTS = "key_privacy_playlists";
    public static final String KEY_PRIVACY_ARTISTS = "key_privacy_artists";

    private ActivityProfileBinding binding;
    private SharedPreferences prefs;

    private String currentDisplayName;
    private String currentUserBio;

    private final List<UserPlaylist> playlistList = new ArrayList<>();
    private UserPlaylistAdapter playlistAdapter;

    private final List<Artist> artistList = new ArrayList<>();
    private ArtistAdapter artistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // 1. Load User Profile Info from Prefs
        loadProfileData();

        // 2. Setup Top Bar Actions
        binding.btnBack.setOnClickListener(v -> finish());
        binding.btnShareTop.setOnClickListener(v -> showShareProfileDialog());
        binding.btnMoreOptions.setOnClickListener(v -> showPrivacySettingsDialog());

        // 3. Setup Edit Name & Avatar Actions
        binding.btnEditProfile.setOnClickListener(v -> showEditNameDialog());
        binding.btnEditNameInline.setOnClickListener(v -> showEditNameDialog());
        binding.btnEditAvatar.setOnClickListener(v -> {
            Toast.makeText(this, "Tính năng tải ảnh đại diện từ thư viện", Toast.LENGTH_SHORT).show();
        });

        // 4. Setup Stats Click (Followers / Following)
        binding.colFollowers.setOnClickListener(v -> showFollowersDialog());
        binding.colFollowing.setOnClickListener(v -> showFollowingDialog());

        // 5. Setup Action Buttons: Share & Privacy
        binding.btnShareProfile.setOnClickListener(v -> showShareProfileDialog());
        binding.btnPrivacySettings.setOnClickListener(v -> showPrivacySettingsDialog());

        // 6. Setup Playlists Section & Manage Playlists
        setupPlaylistsSection();

        // 7. Setup Recent Artists Section
        setupRecentArtistsSection();

        // 8. Setup In-Page Privacy Switches
        setupPrivacySwitches();
    }

    private void loadProfileData() {
        currentDisplayName = prefs.getString(KEY_DISPLAY_NAME, "Nguyễn Minh Hiếu");
        currentUserBio = prefs.getString(KEY_USER_BIO, "@minhhieu • Thành viên Premium");

        binding.tvProfileName.setText(currentDisplayName);
        binding.tvProfileHandle.setText(currentUserBio);
        binding.tvToolbarTitle.setText("Hồ sơ • " + currentDisplayName);
    }

    private void setupPlaylistsSection() {
        playlistList.clear();
        playlistList.add(new UserPlaylist("pl_1", "Giai Điệu Acoustic Chill", 8, true, "Vũ., Chillies"));
        playlistList.add(new UserPlaylist("pl_2", "V-Pop Đình Đám 2024", 12, true, "Đen Vâu, Hoàng Dũng"));
        playlistList.add(new UserPlaylist("pl_3", "Tập Trung Học Tập & Coding", 15, true, "Lofi & Instrumental"));
        playlistList.add(new UserPlaylist("pl_4", "Những Ngày Mưa Rơi", 6, false, "Indie Ballad Việt"));

        binding.rvProfilePlaylists.setLayoutManager(new LinearLayoutManager(this));
        playlistAdapter = new UserPlaylistAdapter(playlistList, playlist -> {
            Toast.makeText(this, "▶ Đang phát danh sách: " + playlist.getName(), Toast.LENGTH_SHORT).show();
        });
        binding.rvProfilePlaylists.setAdapter(playlistAdapter);
        updatePlaylistsCount();

        binding.btnManagePlaylists.setOnClickListener(v -> showManagePlaylistsDialog());
    }

    private void updatePlaylistsCount() {
        binding.tvPlaylistsCount.setText(String.valueOf(playlistList.size()));
    }

    private void setupRecentArtistsSection() {
        artistList.clear();
        artistList.add(new Artist("Vũ.", "Indie Ballad"));
        artistList.add(new Artist("Đen Vâu", "Hip-hop Việt"));
        artistList.add(new Artist("Chillies", "Pop Rock"));
        artistList.add(new Artist("Hoàng Dũng", "Acoustic Pop"));
        artistList.add(new Artist("Hà Anh Tuấn", "Acoustic Live"));

        binding.rvProfileArtists.setLayoutManager(
            new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );
        artistAdapter = new ArtistAdapter(artistList);
        binding.rvProfileArtists.setAdapter(artistAdapter);
    }

    private void setupPrivacySwitches() {
        boolean allowFollowers = prefs.getBoolean(KEY_PRIVACY_FOLLOWERS, true);
        boolean allowPlaylists = prefs.getBoolean(KEY_PRIVACY_PLAYLISTS, true);
        boolean allowArtists = prefs.getBoolean(KEY_PRIVACY_ARTISTS, true);

        // Followers Switch
        binding.swFollowersPrivacy.setChecked(allowFollowers);
        updateFollowersPrivacyDesc(allowFollowers);
        binding.swFollowersPrivacy.setOnCheckedChangeListener((btn, isChecked) -> {
            prefs.edit().putBoolean(KEY_PRIVACY_FOLLOWERS, isChecked).apply();
            updateFollowersPrivacyDesc(isChecked);
            Toast.makeText(this, isChecked ? "Đã công khai người theo dõi" : "Đã chuyển người theo dõi sang 'Chỉ mình tôi'", Toast.LENGTH_SHORT).show();
        });

        // Playlists Switch
        binding.swPlaylistsPrivacy.setChecked(allowPlaylists);
        updatePlaylistsVisibilityUI(allowPlaylists);
        binding.swPlaylistsPrivacy.setOnCheckedChangeListener((btn, isChecked) -> {
            prefs.edit().putBoolean(KEY_PRIVACY_PLAYLISTS, isChecked).apply();
            updatePlaylistsVisibilityUI(isChecked);
            Toast.makeText(this, isChecked ? "Danh sách phát đang hiển thị công khai" : "Đã ẩn danh sách phát khỏi hồ sơ", Toast.LENGTH_SHORT).show();
        });

        // Recent Artists Switch
        binding.swRecentArtistsPrivacy.setChecked(allowArtists);
        updateArtistsVisibilityUI(allowArtists);
        binding.swRecentArtistsPrivacy.setOnCheckedChangeListener((btn, isChecked) -> {
            prefs.edit().putBoolean(KEY_PRIVACY_ARTISTS, isChecked).apply();
            updateArtistsVisibilityUI(isChecked);
            Toast.makeText(this, isChecked ? "Nghệ sĩ nghe gần đây đang hiển thị" : "Đã ẩn nghệ sĩ nghe gần đây", Toast.LENGTH_SHORT).show();
        });
    }

    private void updateFollowersPrivacyDesc(boolean isPublic) {
        binding.tvFollowersPrivacyDesc.setText(
            isPublic ? "Mọi người có thể xem danh sách follower" : "Chỉ mình tôi (người khác không thể xem)"
        );
    }

    private void updatePlaylistsVisibilityUI(boolean isPublic) {
        binding.layoutPlaylistsHiddenBanner.setVisibility(isPublic ? View.GONE : View.VISIBLE);
        binding.tvPlaylistsPrivacyDesc.setText(
            isPublic ? "Cho phép người khác xem & nghe theo" : "Đang ẩn khỏi người khác"
        );
    }

    private void updateArtistsVisibilityUI(boolean isPublic) {
        binding.layoutArtistsHiddenBanner.setVisibility(isPublic ? View.GONE : View.VISIBLE);
        binding.tvArtistsPrivacyDesc.setText(
            isPublic ? "Chia sẻ các nghệ sĩ bạn thường nghe nhất" : "Đã ẩn với công chúng"
        );
    }

    // ==========================================
    // DIALOG 1: CHỈNH SỬA TÊN HỒ SƠ
    // ==========================================
    private void showEditNameDialog() {
        DialogEditProfileNameBinding dialogBinding = DialogEditProfileNameBinding.inflate(getLayoutInflater());
        AlertDialog dialog = new AlertDialog.Builder(this)
            .setView(dialogBinding.getRoot())
            .create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        dialogBinding.etDisplayName.setText(currentDisplayName);
        dialogBinding.etBio.setText(currentUserBio);

        dialogBinding.btnCancelEdit.setOnClickListener(v -> dialog.dismiss());
        dialogBinding.btnSaveEdit.setOnClickListener(v -> {
            String newName = dialogBinding.etDisplayName.getText().toString().trim();
            String newBio = dialogBinding.etBio.getText().toString().trim();

            if (newName.isEmpty()) {
                dialogBinding.tilDisplayName.setError("Tên hiển thị không được để trống");
                return;
            }

            currentDisplayName = newName;
            currentUserBio = newBio.isEmpty() ? "@" + newName.toLowerCase().replace(" ", "") : newBio;

            // Save to SharedPreferences
            prefs.edit()
                .putString(KEY_DISPLAY_NAME, currentDisplayName)
                .putString(KEY_USER_BIO, currentUserBio)
                .apply();

            binding.tvProfileName.setText(currentDisplayName);
            binding.tvProfileHandle.setText(currentUserBio);
            binding.tvToolbarTitle.setText("Hồ sơ • " + currentDisplayName);

            Toast.makeText(this, "Đã cập nhật tên hồ sơ thành công! ✨", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        dialog.show();
    }

    // ==========================================
    // DIALOG 2: CHIA SẺ TRANG CÁ NHÂN
    // ==========================================
    private void showShareProfileDialog() {
        BottomSheetDialog bottomSheet = new BottomSheetDialog(this);
        DialogShareProfileBinding shareBinding = DialogShareProfileBinding.inflate(getLayoutInflater());
        bottomSheet.setContentView(shareBinding.getRoot());

        String usernameTag = currentDisplayName.toLowerCase().replace(" ", "_");
        String profileUrl = "https://vibetune.app/user/" + usernameTag;

        shareBinding.tvShareNamePreview.setText(currentDisplayName);
        shareBinding.tvProfileLink.setText(profileUrl);

        // Copy link to Clipboard
        shareBinding.btnCopyLink.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("VibeTune Profile Link", profileUrl);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, "Đã sao chép liên kết hồ sơ vào khay nhớ tạm! 🎉", Toast.LENGTH_SHORT).show();
        });

        // Share via Other Apps (Intent.ACTION_SEND)
        shareBinding.btnShareOtherApps.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Hồ sơ âm nhạc VibeTune AI");
            shareIntent.putExtra(
                Intent.EXTRA_TEXT,
                "Hãy cùng nghe nhạc với " + currentDisplayName + " trên VibeTune AI nè!\n" +
                "Xem các danh sách phát yêu thích tại: " + profileUrl + " 🎵✨"
            );
            startActivity(Intent.createChooser(shareIntent, "Chia sẻ hồ sơ qua"));
            bottomSheet.dismiss();
        });

        bottomSheet.show();
    }

    // ==========================================
    // DIALOG 3: QUẢN LÝ DANH SÁCH PHÁT
    // ==========================================
    private void showManagePlaylistsDialog() {
        BottomSheetDialog bottomSheet = new BottomSheetDialog(this);
        DialogManagePlaylistsBinding manageBinding = DialogManagePlaylistsBinding.inflate(getLayoutInflater());
        bottomSheet.setContentView(manageBinding.getRoot());

        manageBinding.rvManagePlaylists.setLayoutManager(new LinearLayoutManager(this));
        ManagePlaylistAdapter manageAdapter = new ManagePlaylistAdapter(playlistList, (playlist, isPublic) -> {
            playlistAdapter.notifyDataSetChanged();
            Toast.makeText(
                this,
                playlist.getName() + ": " + (isPublic ? "Hiển thị công khai" : "Đã ẩn khỏi hồ sơ"),
                Toast.LENGTH_SHORT
            ).show();
        });
        manageBinding.rvManagePlaylists.setAdapter(manageAdapter);

        // Add new playlist
        manageBinding.btnAddNewPlaylist.setOnClickListener(v -> {
            String name = manageBinding.etNewPlaylistName.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tên danh sách phát", Toast.LENGTH_SHORT).show();
                return;
            }
            UserPlaylist newPlaylist = new UserPlaylist(
                "pl_" + System.currentTimeMillis(),
                name,
                0,
                true,
                "Danh sách mới tạo"
            );
            playlistList.add(0, newPlaylist);
            playlistAdapter.notifyItemInserted(0);
            manageAdapter.notifyItemInserted(0);
            updatePlaylistsCount();
            manageBinding.etNewPlaylistName.setText("");
            Toast.makeText(this, "Đã thêm danh sách: " + name, Toast.LENGTH_SHORT).show();
        });

        manageBinding.btnCloseManage.setOnClickListener(v -> bottomSheet.dismiss());
        bottomSheet.show();
    }

    // ==========================================
    // DIALOG 4: CÀI ĐẶT QUYỀN RIÊNG TƯ MODAL
    // ==========================================
    private void showPrivacySettingsDialog() {
        BottomSheetDialog bottomSheet = new BottomSheetDialog(this);
        DialogProfilePrivacyBinding privBinding = DialogProfilePrivacyBinding.inflate(getLayoutInflater());
        bottomSheet.setContentView(privBinding.getRoot());

        boolean allowFollowers = prefs.getBoolean(KEY_PRIVACY_FOLLOWERS, true);
        boolean allowPlaylists = prefs.getBoolean(KEY_PRIVACY_PLAYLISTS, true);
        boolean allowArtists = prefs.getBoolean(KEY_PRIVACY_ARTISTS, true);

        privBinding.swModalFollowers.setChecked(allowFollowers);
        privBinding.swModalPlaylists.setChecked(allowPlaylists);
        privBinding.swModalArtists.setChecked(allowArtists);

        privBinding.swModalFollowers.setOnCheckedChangeListener((btn, isChecked) -> {
            prefs.edit().putBoolean(KEY_PRIVACY_FOLLOWERS, isChecked).apply();
            binding.swFollowersPrivacy.setChecked(isChecked);
            updateFollowersPrivacyDesc(isChecked);
        });

        privBinding.swModalPlaylists.setOnCheckedChangeListener((btn, isChecked) -> {
            prefs.edit().putBoolean(KEY_PRIVACY_PLAYLISTS, isChecked).apply();
            binding.swPlaylistsPrivacy.setChecked(isChecked);
            updatePlaylistsVisibilityUI(isChecked);
        });

        privBinding.swModalArtists.setOnCheckedChangeListener((btn, isChecked) -> {
            prefs.edit().putBoolean(KEY_PRIVACY_ARTISTS, isChecked).apply();
            binding.swRecentArtistsPrivacy.setChecked(isChecked);
            updateArtistsVisibilityUI(isChecked);
        });

        privBinding.btnSavePrivacy.setOnClickListener(v -> {
            bottomSheet.dismiss();
            Toast.makeText(this, "Đã lưu cài đặt quyền riêng tư", Toast.LENGTH_SHORT).show();
        });

        bottomSheet.show();
    }

    private void showFollowersDialog() {
        boolean allowFollowers = prefs.getBoolean(KEY_PRIVACY_FOLLOWERS, true);
        String statusNote = allowFollowers ?
            "Danh sách người theo dõi hiện đang được hiển thị CÔNG KHAI." :
            "Danh sách người theo dõi hiện đang ở chế độ CHỈ MÌNH TÔI.";

        new AlertDialog.Builder(this)
            .setTitle("Người theo dõi (128)")
            .setMessage("Danh sách người theo dõi bạn trên VibeTune AI:\n\n• Trần Anh Dũng\n• Lê Thu Thảo\n• Hoàng Minh Quân\n• Phạm Quỳnh Chi\n... và 124 người khác.\n\n" + statusNote)
            .setPositiveButton("Đóng", null)
            .show();
    }

    private void showFollowingDialog() {
        new AlertDialog.Builder(this)
            .setTitle("Đang theo dõi (64)")
            .setMessage("Các tài khoản và nghệ sĩ bạn đang theo dõi:\n\n• Vũ. (Nghệ sĩ)\n• Đen Vâu (Nghệ sĩ)\n• Chillies (Ban nhạc)\n• Hoàng Dũng (Nghệ sĩ)\n• Nguyễn Phương Ly\n... và 59 người khác.")
            .setPositiveButton("Đóng", null)
            .show();
    }
}
