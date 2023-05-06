package com.kstyles.korean.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.kstyles.korean.R;
import com.kstyles.korean.databinding.ActivityMainBinding;
import com.kstyles.korean.fragment.Ex2Fragment;
import com.kstyles.korean.fragment.ProgressFragment;
import com.kstyles.korean.fragment.SettingFragment;
import com.kstyles.korean.fragment.MainFragment;
import com.kstyles.korean.preferences.time.OperateUseTime;

public class MainActivity extends AppCompatActivity {

    private final Fragment[] fragments = {new MainFragment(), new Ex2Fragment(), new ProgressFragment(), new SettingFragment()};

    private String TAG = "[MainActivity]";
    private ActivityMainBinding binding;
    private OperateUseTime operateUseTime;

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
        binding.mainBtnEx2.setOnClickListener(v -> switchFragment(fragments[1]));
        binding.mainBtnProgress.setOnClickListener(v -> switchFragment(fragments[2]));
        binding.mainBtnSetting.setOnClickListener(v -> switchFragment(fragments[3]));
    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(binding.mainFrame.getId(), fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        binding.mainBtnHome.setBackgroundResource(fragment instanceof MainFragment ? R.drawable.icon_home_black : R.drawable.icon_home);
        binding.mainBtnEx2.setBackgroundResource(fragment instanceof Ex2Fragment ? R.drawable.icon_search_black : R.drawable.icon_search);
        binding.mainBtnProgress.setBackgroundResource(fragment instanceof ProgressFragment ? R.drawable.icon_clip_black : R.drawable.icon_clip);
        binding.mainBtnSetting.setBackgroundResource(fragment instanceof SettingFragment ? R.drawable.icon_setup_black : R.drawable.icon_setup);

    }

    @Override
    protected void onStart() {
        super.onStart();
        operateUseTime.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        operateUseTime.onStop();
    }
}
