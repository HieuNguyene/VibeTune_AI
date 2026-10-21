package com.vibetune.app.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.vibetune.app.MainActivity;
import com.vibetune.app.R;
import com.vibetune.app.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private boolean isPassVisible = false;
    private boolean isConfirmPassVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(v -> finish());

        binding.btnTogglePassReg.setOnClickListener(v -> togglePassVisibility());
        binding.btnToggleConfirmPass.setOnClickListener(v -> toggleConfirmPassVisibility());

        binding.btnRegister.setOnClickListener(v -> handleRegister());

        binding.tvGoToLogin.setOnClickListener(v -> finish());
    }

    private void togglePassVisibility() {
        isPassVisible = !isPassVisible;
        if (isPassVisible) {
            binding.edtPasswordReg.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            binding.btnTogglePassReg.setImageResource(R.drawable.ic_eye_off);
        } else {
            binding.edtPasswordReg.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            binding.btnTogglePassReg.setImageResource(R.drawable.ic_eye);
        }
        binding.edtPasswordReg.setSelection(binding.edtPasswordReg.getText().length());
    }

    private void toggleConfirmPassVisibility() {
        isConfirmPassVisible = !isConfirmPassVisible;
        if (isConfirmPassVisible) {
            binding.edtConfirmPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            binding.btnToggleConfirmPass.setImageResource(R.drawable.ic_eye_off);
        } else {
            binding.edtConfirmPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            binding.btnToggleConfirmPass.setImageResource(R.drawable.ic_eye);
        }
        binding.edtConfirmPass.setSelection(binding.edtConfirmPass.getText().length());
    }

    private void handleRegister() {
        String fullName = binding.edtFullName.getText().toString().trim();
        String email = binding.edtEmailReg.getText().toString().trim();
        String password = binding.edtPasswordReg.getText().toString().trim();
        String confirmPass = binding.edtConfirmPass.getText().toString().trim();

        if (TextUtils.isEmpty(fullName)) {
            binding.edtFullName.setError("Vui lòng nhập họ và tên");
            binding.edtFullName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edtEmailReg.setError("Địa chỉ Email không hợp lệ");
            binding.edtEmailReg.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password) || password.length() < 6) {
            binding.edtPasswordReg.setError("Mật khẩu phải từ 6 ký tự trở lên");
            binding.edtPasswordReg.requestFocus();
            return;
        }

        if (!password.equals(confirmPass)) {
            binding.edtConfirmPass.setError("Mật khẩu xác nhận không khớp");
            binding.edtConfirmPass.requestFocus();
            return;
        }

        Toast.makeText(this, "Đăng ký thành công tài khoản " + fullName + "!", Toast.LENGTH_SHORT).show();

        // Navigate directly to Home
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
