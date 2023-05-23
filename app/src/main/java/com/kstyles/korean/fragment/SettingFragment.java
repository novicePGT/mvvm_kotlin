package com.kstyles.korean.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.load.engine.Resource;
import com.kstyles.korean.R;
import com.kstyles.korean.databinding.ActivityFragmentSettingBinding;
import com.kstyles.korean.databinding.InputChangePasswordBinding;
import com.kstyles.korean.databinding.InputLogoutBinding;
import com.kstyles.korean.language.LanguageManager;
import com.kstyles.korean.repository.FirebaseManager;

import org.w3c.dom.Text;

import java.util.Locale;

public class SettingFragment extends Fragment {

    private ActivityFragmentSettingBinding binding;
    private Spinner spinner;

    public SettingFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityFragmentSettingBinding.inflate(inflater, container, false);

        /**
         * 비밀번호 변경 로직을 포함한 AlertDialog
         */
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
         * Logout Logic
         */
        binding.settingBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputLogoutBinding bindingDialog = InputLogoutBinding.inflate(LayoutInflater.from(binding.getRoot().getContext()), binding.getRoot(), false);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Logout")
                        .setView(bindingDialog.getRoot())
                        .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 수락버튼 수행
                                FirebaseManager firebaseManager = new FirebaseManager();
                                firebaseManager.signOut((Activity) getContext());
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
         * Toggle button Sound Control
         */
        binding.settingSoundToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AudioManager audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
                if (isChecked) {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                }
                if (!isChecked) {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                }
            }
        });

        /**
         * Toggle button set Notification control
         */
        binding.settingNotificationPushToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                if (isChecked) {
                    notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE);
                }
                if (!isChecked) {
                    notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL);
                }
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
                String selectedLanguage = (String) parent.getItemAtPosition(position);

                SharedPreferences sharedPreferences = getContext().getSharedPreferences("language", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                SharedPreferences intSharedPreference = getContext().getSharedPreferences("languageNum", Context.MODE_PRIVATE);
                SharedPreferences.Editor numEditor = intSharedPreference.edit();

                if (selectedLanguage.equals("English")) {
                    editor.putString("language", "");
                    numEditor.putString("languageNum", "1");
                }
                if (selectedLanguage.equals("Vietnamese")) {
                    editor.putString("language", "vi");
                    numEditor.putString("languageNum", "2");
                }
                if (selectedLanguage.equals("French")) {
                    editor.putString("language", "fr");
                    numEditor.putString("languageNum", "3");
                }
                editor.apply();
                numEditor.apply();

                String languageNum = intSharedPreference.getString("languageNum", "0");
                spinner.setSelection(Integer.parseInt(languageNum));
                setTranslation();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return binding.getRoot();
    }

    private void setTranslation() {
        LanguageManager languageManager = new LanguageManager(getContext());
        languageManager.setLanguage();
        binding.tvSetting.setText(languageManager.getTranslatedString(R.string.tv_setting));
        binding.tvAccountSettings.setText(languageManager.getTranslatedString(R.string.tv_account_settings));
        binding.tvEditProfile.setText(languageManager.getTranslatedString(R.string.tv_edit_profile));
        binding.tvChangePassword.setText(languageManager.getTranslatedString(R.string.tv_change_password));
        binding.tvSettings.setText(languageManager.getTranslatedString(R.string.tv_settings));
        binding.tvLanguage.setText(languageManager.getTranslatedString(R.string.tv_language));
        binding.tvSound.setText(languageManager.getTranslatedString(R.string.tv_sound));
        binding.tvPushNotifications.setText(languageManager.getTranslatedString(R.string.tv_push_notifications));
        binding.tvLogout.setText(languageManager.getTranslatedString(R.string.tv_logout));
    }
}
