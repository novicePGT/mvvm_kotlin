package com.kstyles.korean.activity;

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

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth; // 파이어베이스 인증
    private DatabaseReference databaseReference; // 실시간 데이터 베이스
    private ActivityRegisterBinding binding;

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
        databaseReference = FirebaseDatabase.getInstance().getReference();

        binding.registerBtnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회원가입 로직
                String userEmail = binding.registerUserEmail.getText().toString();
                String userPassword = binding.registerUserPassword.getText().toString();
                String userName = binding.registerUserName.getText().toString();

                firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            // 회원가입 성공 로직
                            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                            UserAccount userAccount = new UserAccount(userEmail, userPassword, userName);
                            userAccount.setIdToken(firebaseAuth.getUid());

                            databaseReference.child("UserAccount").child(currentUser.getUid()).setValue(userAccount);
                            Toast.makeText(RegisterActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
