package com.kstyles.korean.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import com.kstyles.korean.databinding.InputChangePasswordBinding;
import com.kstyles.korean.repository.FirebaseManager;

public class SettingFragment extends Fragment {

    private ActivityFragmentSettingBinding binding;
    private Spinner spinner;

    public SettingFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityFragmentSettingBinding.inflate(inflater, container, false);

        binding.settingChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputChangePasswordBinding bindingDialog = InputChangePasswordBinding.inflate(LayoutInflater.from(binding.getRoot().getContext()), binding.getRoot(), false);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Change Password")
                        .setView(bindingDialog.getRoot())
                        .setPositiveButton("Modify", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 확인버튼 수행
                                String email = bindingDialog.settingEmail.getText().toString();
                                String password = bindingDialog.settingPassword.getText().toString();
                                String newPassword = bindingDialog.settingChangePassword.getText().toString();
                                FirebaseManager firebaseManager = new FirebaseManager();
                                firebaseManager.setUserPassword(getContext(), email, password, newPassword);
                            }
                        })
                        .setNegativeButton("Refuse", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 거절버튼 수행
                            }
                        }).show();
            }
        });

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
