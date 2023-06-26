package com.kstyles.korean.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kstyles.korean.R;
import com.kstyles.korean.databinding.ActivityRegisterBinding;
import com.kstyles.korean.view.fragment.item.UserAccount;
import com.kstyles.korean.language.LanguageManager;
import com.kstyles.korean.repository.FirebaseManager;
import com.kstyles.korean.verification.EditTextWatcher;
import com.kstyles.korean.verification.PasswordValidator;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth; // 파이어베이스 인증
    private DatabaseReference reference; // 실시간 데이터 베이스
    private ActivityRegisterBinding binding;
    private String TAG = "[Register]";
    private String userEmail;
    private String userPassword;
    private String userName;
    private Uri userProfile;
    private int REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * View Binding Setting
         */
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        /**
         * Set Language
         */
        LanguageManager languageManager = new LanguageManager(this);
        languageManager.setLanguage();
        binding.tvRegister.setText(languageManager.getTranslatedString(R.string.tv_register));
        binding.registerTvUser.setText(languageManager.getTranslatedString(R.string.tv_user_profile));
        binding.tvEmail.setText(languageManager.getTranslatedString(R.string.tv_email));
        binding.registerUserEmail.setHint(languageManager.getTranslatedString(R.string.hint_register_email));
        binding.tvPassword.setText(languageManager.getTranslatedString(R.string.tv_password));
        binding.registerUserPassword.setHint(languageManager.getTranslatedString(R.string.hint_register_password));
        binding.tvCheckPassword.setText(languageManager.getTranslatedString(R.string.tv_check_password));
        binding.registerUserRePassword.setHint(languageManager.getTranslatedString(R.string.hint_check_password));
        binding.registerTvPasswordVerification.setText(languageManager.getTranslatedString(R.string.tv_password_expression));
        binding.tvNickname.setText(languageManager.getTranslatedString(R.string.tv_nickname));
        binding.registerUserName.setHint(languageManager.getTranslatedString(R.string.hint_nickname));
        binding.registerBtnJoin.setText(languageManager.getTranslatedString(R.string.btn_join));
        String pass_valid = languageManager.getTranslatedString(R.string.tv_pass_valid);
        String pass_invalid = languageManager.getTranslatedString(R.string.tv_pass_invalid);

        /**
         * firebase setting
         */
        firebaseAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("UserAccount");

        /**
         * 정규식을 이용한 이메일 형식 검증 -> 텍스트로 정보를 노출
         */
        EditTextWatcher editTextWatcher = new EditTextWatcher(binding.registerTvVerification);
        binding.registerUserEmail.addTextChangedListener(editTextWatcher);

        /**
         * 뒤로가기 버튼 구현
         */
        binding.registerBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        /**
         * User Profile Setting
         */
        binding.registerUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        /**
         * firebaseAuth을 사용해 이메일 url 인증 기능을 포함한 이벤트 리스너
         * Join 버튼을 클릭하면 Email, password, name 을 가지고 데이터베이스로 넘어간다.
         */
        binding.registerBtnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회원가입 로직
                userEmail = binding.registerUserEmail.getText().toString();
                userPassword = binding.registerUserPassword.getText().toString();
                userName = binding.registerUserName.getText().toString();

                String password1 = binding.registerUserPassword.getText().toString();
                String password2 = binding.registerUserRePassword.getText().toString();
                if (PasswordValidator.validatePassword(password1, password2) && !binding.registerUserName.equals("") && !binding.registerUserEmail.equals("")) {
                    binding.registerTvPasswordVerification.setText(pass_valid);
                    binding.registerTvPasswordVerification.setTextColor(Color.BLUE);
                    register();
                }
                if (!PasswordValidator.validatePassword(password1, password2)) {
                    binding.registerTvPasswordVerification.setText(pass_invalid);
                    binding.registerTvPasswordVerification.setTextColor(Color.RED);
                }
                if (password1.isEmpty() || password2.isEmpty()) {
                    binding.registerTvPasswordVerification.setText("");
                }
            }
        });
    }

    private void register() {
        firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                    UserAccount userAccount = new UserAccount(userEmail, userPassword, userName, firebaseAuth.getUid());
                    reference.child("UserAccount").child(currentUser.getUid()).setValue(userAccount);

                    Toast.makeText(RegisterActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();

                    if (userProfile != null) {
                        FirebaseManager firebaseManager = new FirebaseManager();
                        firebaseManager.uploadUserProfile(RegisterActivity.this, userProfile);
                    }

                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(RegisterActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            userProfile = data.getData();
            Glide.with(this)
                    .load(userProfile)
                    .override(500, 500)
                    .circleCrop()
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .into(binding.registerUserProfile);
        } else {
            Log.e(TAG, "image 로드 오류");
        }
    }
}
