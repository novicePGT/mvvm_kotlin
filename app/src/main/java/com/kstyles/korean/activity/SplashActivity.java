package com.kstyles.korean.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kstyles.korean.databinding.ActivitySplashBinding;
import com.kstyles.korean.repository.FirebaseManager;

public class SplashActivity extends AppCompatActivity {

    private final String TAG = "[SplashActivity]";
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

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseManager firebaseManager = new FirebaseManager();
                firebaseManager.signInWithToken(SplashActivity.this);
            }
        }, SPLASH_DURATION);
    }
}
