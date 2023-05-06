package com.kstyles.korean.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kstyles.korean.databinding.ActivitySplashBinding;

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
                SharedPreferences sharedPreferences = getSharedPreferences("idToken", 0);
                String idToken = sharedPreferences.getString("idToken", "0");
                if (!idToken.equals("0")) {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    Log.d(TAG, "TOKEN : " + idToken);
                }
                if (idToken.equals("0")) {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    Log.e(TAG, "TOKEN is null");
                }
            }
        }, SPLASH_DURATION);
    }
}
