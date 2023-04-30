package com.kstyles.korean.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kstyles.korean.R;
import com.kstyles.korean.databinding.ActivityFragmentSettingBinding;

public class SettingFragment extends Fragment {

    private ActivityFragmentSettingBinding binding;
    private Spinner spinner;

    public SettingFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityFragmentSettingBinding.inflate(inflater, container, false);

        /**
         * Spinner setting
         */
        spinner = binding.settingSpinner;
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 이 곳에 언어번역에 대한 코드를 작성할 것.
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return binding.getRoot();
    }
}
