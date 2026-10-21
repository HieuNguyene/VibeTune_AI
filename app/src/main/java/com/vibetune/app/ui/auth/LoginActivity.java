package com.vibetune.app.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.vibetune.app.MainActivity;
import com.vibetune.app.R;
import com.vibetune.app.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 1. Password Visibility Toggle
        binding.btnTogglePassword.setOnClickListener(v -> togglePasswordVisibility());

        // 2. Login Button
        binding.btnLogin.setOnClickListener(v -> handleLogin());

        // 3. Google Sign-In
        binding.btnGoogleLogin.setOnClickListener(v -> {
            Toast.makeText(this, "Đăng nhập Google thành công! Chào mừng bạn.", Toast.LENGTH_SHORT).show();
            navigateToMain();
        });

        // 4. Guest Mode (Explore without login)
        binding.btnGuestMode.setOnClickListener(v -> {
            Toast.makeText(this, "Truy cập chế độ Khách khám phá", Toast.LENGTH_SHORT).show();
            navigateToMain();
        });

        // 5. Navigate to Register
        binding.tvGoToRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // 6. Forgot Password Click
        binding.tvForgotPassword.setOnClickListener(v -> {
            Toast.makeText(this, "Tính năng lấy lại mật khẩu qua Email đang được tích hợp!", Toast.LENGTH_SHORT).show();
        });
    }

    private void togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible;
        if (isPasswordVisible) {
            binding.edtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            binding.btnTogglePassword.setImageResource(R.drawable.ic_eye_off);
        } else {
            binding.edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            binding.btnTogglePassword.setImageResource(R.drawable.ic_eye);
        }
        binding.edtPassword.setSelection(binding.edtPassword.getText().length());
    }

    private void handleLogin() {
        String email = binding.edtEmail.getText().toString().trim();
        String password = binding.edtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            binding.edtEmail.setError("Vui lòng nhập Email hoặc tên đăng nhập");
            binding.edtEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            binding.edtPassword.setError("Vui lòng nhập mật khẩu");
            binding.edtPassword.requestFocus();
            return;
        }

        // Demo validation / Seed account support
        Toast.makeText(this, "Đăng nhập thành công! Chào mừng trở lại VibeTune AI.", Toast.LENGTH_SHORT).show();
        navigateToMain();
    }

    private void navigateToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
