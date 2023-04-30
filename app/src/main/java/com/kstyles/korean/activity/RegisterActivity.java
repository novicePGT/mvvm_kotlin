package com.kstyles.korean.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kstyles.korean.databinding.ActivityRegisterBinding;
import com.kstyles.korean.firebase.UserAccount;
import com.kstyles.korean.verification.EditTextWatcher;
import com.kstyles.korean.verification.PasswordWatcher;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth; // 파이어베이스 인증
    private DatabaseReference reference; // 실시간 데이터 베이스
    private ActivityRegisterBinding binding;

    private String userEmail;
    private String userPassword;
    private String userName;

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
         * 비밀번호가 6자리 이하면 텍스트로 정보를 노출
         */
        PasswordWatcher passwordWatcher = new PasswordWatcher(binding.registerTvPasswordVerification, binding.registerUserPassword, binding.registerUserRePassword);
        binding.registerUserPassword.addTextChangedListener(passwordWatcher);

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

                firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                            UserAccount userAccount = new UserAccount(userEmail, userPassword, userName, firebaseAuth.getUid());
                            reference.child("UserAccount").child(currentUser.getUid()).setValue(userAccount);

                            // 이메일 인증 메일 보내기
                            currentUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "이메일 인증 메일을 보냈습니다. 이메일을 확인해주세요.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "이메일 인증 메일 보내기 실패", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            Toast.makeText(RegisterActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
