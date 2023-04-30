package com.kstyles.korean.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.kstyles.korean.databinding.ActivityMainBinding;
import com.kstyles.korean.fragment.Ex2Fragment;
import com.kstyles.korean.fragment.ProgressFragment;
import com.kstyles.korean.fragment.SettingFragment;
import com.kstyles.korean.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    private final Fragment[] fragments = {new MainFragment(), new Ex2Fragment(), new ProgressFragment(), new SettingFragment()};

    private String TAG = "[MainActivity]";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * View Binding Setting
         */
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        switchFragment(fragments[0]);

        binding.mainBtnHome.setOnClickListener(v -> switchFragment(fragments[0]));
        binding.mainBtnEx2.setOnClickListener(v -> switchFragment(fragments[1]));
        binding.mainBtnEx3.setOnClickListener(v -> switchFragment(fragments[2]));
        binding.mainBtnEx4.setOnClickListener(v -> switchFragment(fragments[3]));
    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(binding.mainFrame.getId(), fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
