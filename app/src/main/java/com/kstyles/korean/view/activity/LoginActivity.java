package com.kstyles.korean.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kstyles.korean.R;
import com.kstyles.korean.databinding.ActivityLoginBinding;
import com.kstyles.korean.databinding.InputFindPassBinding;
import com.kstyles.korean.language.LanguageManager;
import com.kstyles.korean.repository.FirebaseManager;
import com.kstyles.korean.repository.user.User;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = "LoginActivity";
    private String uid;
    private String login_success;
    private String login_failed;
    private FirebaseAuth firebaseAuth; // 파이어베이스 인증
    private FirebaseUser user;
    private DatabaseReference databaseReference; // 실시간 데이터 베이스
    private Spinner spinner;
    private ActivityLoginBinding binding;
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * View Binding Setting
         */
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        /**
         * user & SharedPreferences
         */
        FirebaseManager firebaseManager = new FirebaseManager();
        User user1 = firebaseManager.getUser();
        uid = user1.getUid();

        SharedPreferences sharedPreferences = getSharedPreferences(uid, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        /**
         * firebase setting
         */
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        binding.loginTvGoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.loginLinearAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    binding.loginIconButton.setBackground(getDrawable(R.drawable.icon_button_unchecked));
                    flag = false;
                } else {
                    binding.loginIconButton.setBackground(getDrawable(R.drawable.icon_button_check));
                    flag = true;
                }
            }
        });

        spinner = binding.loginSpinner;
        int language_num = sharedPreferences.getInt("language_num", 0);
        spinner.setSelection(language_num);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLanguage = (String) parent.getItemAtPosition(position);

                if (selectedLanguage.equals("Việt Nam")) {
                    editor.putString("language", "vi");
                    editor.putInt("language_num", 1);
                }
                if (selectedLanguage.equals("France")) {
                    editor.putString("language", "fr");
                    editor.putInt("language_num", 2);
                }
                if (selectedLanguage.equals("日本")) {
                    editor.putString("language", "ja");
                    editor.putInt("language_num", 3);
                }
                if (selectedLanguage.equals("Deutschland")) {
                    editor.putString("language", "de");
                    editor.putInt("language_num", 4);
                }
                if (selectedLanguage.equals("ประเทศไทย")) {
                    editor.putString("language", "th");
                    editor.putInt("language_num", 5);
                }
                if (selectedLanguage.equals("English")) {
                    editor.putString("language", "");
                    editor.putInt("language_num", 0);
                }
                editor.apply();

                setTranslation();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = binding.userEmail.getText().toString();
                String userPassword = binding.userPassword.getText().toString();

                if (userEmail != null && userPassword != null) {
                    firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // 로그인 성공 로직
                                if (flag) {
                                    editor.putString("user_id", userEmail);
                                    editor.putString("user_pass", userPassword);
                                    editor.apply();
                                }

                                Toast.makeText(LoginActivity.this, login_success, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // 로그인 실패 로직
                                Toast.makeText(LoginActivity.this, login_failed, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        binding.findIdPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputFindPassBinding bindingDialog = InputFindPassBinding.inflate(LayoutInflater.from(binding.getRoot().getContext()), binding.getRoot(), false);

                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("Find password By Email")
                        .setView(bindingDialog.getRoot())
                        .setPositiveButton("Modify", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String email = bindingDialog.loginFindEmail.getText().toString();

                                FirebaseManager firebaseManager = new FirebaseManager();
                                firebaseManager.sendPasswordRestByEmail(email);
                                Log.d(TAG, "SUCCESS");
                            }
                        })
                        .setNegativeButton("Refuse", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d(TAG, "ERROR");
                            }
                        }).show();
            }
        });
    }

    private void setTranslation() {
        LanguageManager languageManager = new LanguageManager(this);
        languageManager.setLanguage();
        binding.userEmail.setHint(languageManager.getTranslatedString(R.string.hint_id));
        binding.userPassword.setHint(languageManager.getTranslatedString(R.string.hint_password));
        binding.loginAutoLogin.setText(languageManager.getTranslatedString(R.string.tv_auto_login));
        binding.findIdPass.setText(languageManager.getTranslatedString(R.string.tv_find_id_pass));
        binding.tvRegisterText.setText(languageManager.getTranslatedString(R.string.tv_register_text));
        binding.loginTvGoRegister.setText(languageManager.getTranslatedString(R.string.tv_go_register));
        login_success = languageManager.getTranslatedString(R.string.tv_success_login);
        login_failed = languageManager.getTranslatedString(R.string.tv_fail_login);
    }
}