package com.kstyles.korean.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.kstyles.korean.R;
import com.kstyles.korean.databinding.ActivityMainBinding;
import com.kstyles.korean.repository.FirebaseManager;
import com.kstyles.korean.repository.user.User;
import com.kstyles.korean.view.fragment.AllWordFragment;
import com.kstyles.korean.view.fragment.ProgressFragment;
import com.kstyles.korean.view.fragment.SettingFragment;
import com.kstyles.korean.view.fragment.MainFragment;
import com.kstyles.korean.preferences.time.OperateUseTime;

public class MainActivity extends AppCompatActivity {

    private final Fragment[] fragments = {new MainFragment(), new ProgressFragment(), new AllWordFragment(), new SettingFragment()};

    private String TAG = "[MainActivity]";
    private ActivityMainBinding binding;
    private OperateUseTime operateUseTime;
    private String uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        operateUseTime = new OperateUseTime(this);

        /**
         * View Binding Setting
         */
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        switchFragment(fragments[0]);

        binding.mainBtnHome.setOnClickListener(v -> switchFragment(fragments[0]));
        binding.mainBtnProgress.setOnClickListener(v -> switchFragment(fragments[1]));
        binding.mainBtnWord.setOnClickListener(v -> switchFragment(fragments[2]));
        binding.mainBtnSetting.setOnClickListener(v -> switchFragment(fragments[3]));

        setForManagement();
        binding.mainManagerSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ManagementActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setForManagement() {
        binding.mainManagerSetting.setVisibility(View.GONE);
        FirebaseManager firebaseManager = new FirebaseManager();
        User user = firebaseManager.getUser();
        uid = user.getUid();
        if (uid.equals("Q2CaQQYieDU17uVEa4YYuIoInbv1")) {
            binding.mainManagerSetting.setVisibility(View.VISIBLE);
        } else {
            binding.mainManagerSetting.setVisibility(View.GONE);
        }
    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(binding.mainFrame.getId(), fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        binding.mainBtnHome.setBackgroundResource(fragment instanceof MainFragment ? R.drawable.icon_home_black : R.drawable.icon_home);
        binding.mainBtnProgress.setBackgroundResource(fragment instanceof ProgressFragment ? R.drawable.icon_chart_black : R.drawable.icon_chart);
        binding.mainBtnWord.setBackgroundResource(fragment instanceof AllWordFragment ? R.drawable.icon_book_black : R.drawable.icon_book);
        binding.mainBtnSetting.setBackgroundResource(fragment instanceof SettingFragment ? R.drawable.icon_setup_black : R.drawable.icon_setup);

    }

    @Override
    protected void onResume() {
        super.onResume();
        operateUseTime.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        operateUseTime.onStop();
    }
}
