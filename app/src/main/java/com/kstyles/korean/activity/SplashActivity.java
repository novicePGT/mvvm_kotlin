package com.kstyles.korean.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.kstyles.korean.R;
import com.kstyles.korean.databinding.ActivitySplashBinding;
import com.kstyles.korean.language.LanguageManager;
import com.kstyles.korean.repository.FirebaseManager;
import com.kstyles.korean.repository.user.User;

public class SplashActivity extends AppCompatActivity {

    private final String TAG = "[SplashActivity]";
    private String uid;
    private ActivitySplashBinding binding;
    private final int SPLASH_DURATION = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * View Binding Setting
         */
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        /**
         * Set Translation
         */
        LanguageManager languageManager = new LanguageManager(this);
        languageManager.setLanguage();
        binding.tvTop.setText(languageManager.getTranslatedString(R.string.tv_top));
        binding.tvTop2.setText(languageManager.getTranslatedString(R.string.tv_top2));

        /**
         * user & SharedPreferences
         */
        FirebaseManager firebaseManager = new FirebaseManager();
        User user = firebaseManager.getUser();
        uid = user.getUid();
        SharedPreferences sharedPreferences = getSharedPreferences(uid, MODE_PRIVATE);

        String userEmail = sharedPreferences.getString("user_id", "0");
        String userPass = sharedPreferences.getString("user_pass", "0");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseManager firebaseManager = new FirebaseManager();
                firebaseManager.signInWithEmailAndPass(SplashActivity.this, userEmail, userPass);
            }
        }, SPLASH_DURATION);
    }
}
