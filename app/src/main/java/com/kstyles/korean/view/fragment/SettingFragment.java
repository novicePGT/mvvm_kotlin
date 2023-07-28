package com.kstyles.korean.view.fragment;


import static com.kstyles.korean.verification.noti.NotificationPolicy.isNotificationPolicyAccessGranted;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.kstyles.korean.R;
import com.kstyles.korean.databinding.ActivityFragmentSettingBinding;
import com.kstyles.korean.databinding.InputEditProfileBinding;
import com.kstyles.korean.databinding.InputFindPassBinding;
import com.kstyles.korean.databinding.InputLogoutBinding;
import com.kstyles.korean.preferences.user.UserProfile;
import com.kstyles.korean.view.fragment.bottomView.BottomViewManipulationListener;
import com.kstyles.korean.language.LanguageManager;
import com.kstyles.korean.repository.FirebaseManager;
import com.kstyles.korean.repository.user.User;

public class SettingFragment extends Fragment implements BottomViewManipulationListener {

    private ActivityFragmentSettingBinding binding;
    private String uid;
    private Spinner spinner;
    private int REQUEST_CODE = 1002;
    private String TAG = "[SettingFragment}";
    private Uri userProfile;
    private Uri previousUserProfile;
    private InputEditProfileBinding inputEditProfileBinding;

    public SettingFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityFragmentSettingBinding.inflate(inflater, container, false);

        showBottomView();

        /**
         * user & SharedPreferences
         */
        FirebaseManager firebaseManager = new FirebaseManager();
        User user = firebaseManager.getUser();
        uid = user.getUid();

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(uid, Context.MODE_PRIVATE);

        /**
         * 비밀번호 변경 로직을 포함한 AlertDialog
         */
        binding.settingChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputFindPassBinding bindingDialog = InputFindPassBinding.inflate(LayoutInflater.from(binding.getRoot().getContext()), binding.getRoot(), false);

                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
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
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.remove("user_id");
                                editor.remove("user_pass");
                                editor.apply();
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
         * Update User Profile
         */
        binding.settingBtnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseManager firebaseManager = new FirebaseManager();
                inputEditProfileBinding = InputEditProfileBinding.inflate(LayoutInflater.from(binding.getRoot().getContext()), binding.getRoot(), false);
                previousUserProfile = Uri.parse(sharedPreferences.getString("user_profile", ""));

                inputEditProfileBinding.inputUserProfile.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.icon_user));

                if (!previousUserProfile.equals("")) {
                    Glide.with(getContext())
                            .load(previousUserProfile)
                            .override(500, 500)
                            .circleCrop()
                            .format(DecodeFormat.PREFER_ARGB_8888)
                            .into(inputEditProfileBinding.inputUserProfile);
                }
                inputEditProfileBinding.inputUserProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                        intent.setType("image/*");
                        startActivityForResult(intent, REQUEST_CODE);
                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Update your profile")
                        .setView(inputEditProfileBinding.getRoot())
                        .setPositiveButton("Modify", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    firebaseManager.deleteUserProfile(previousUserProfile);
                                } catch (Exception e) {
                                    Log.e(TAG, "등록된 유저 프로필 없음");
                                } finally {
                                    firebaseManager.uploadUserProfile(getContext(), userProfile);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("user_profile", String.valueOf(userProfile));
                                    editor.apply();
                                }
                            }
                        })
                        .setNegativeButton("Refuse", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

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
                if (isNotificationPolicyAccessGranted(getContext())) {
                    AudioManager audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
                    if (isChecked) {
                        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                    } else {
                        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    }
                } else {
                    showNotificationPolicyAccessDialog();
                }
            }
        });

        /**
         * Toggle button set Notification control
         */
        binding.settingNotificationPushToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isNotificationPolicyAccessGranted(getContext())) {
                    NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                    if (isChecked) {
                        notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE);
                    }
                    if (!isChecked) {
                        notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL);
                    }
                } else {
                    showNotificationPolicyAccessDialog();
                }
            }
        });

        /**
         * Spinner setting
         */
        spinner = binding.settingSpinner;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int language_num = sharedPreferences.getInt("language_num", 0);
        spinner.setSelection(language_num);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLanguage = (String) parent.getItemAtPosition(position);

                if (selectedLanguage.equals("English")) {
                    editor.putString("language", "en");
                    editor.putInt("language_num", 0);
                }
                if (selectedLanguage.equals("Deutschland")) {
                    editor.putString("language", "de");
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
                if (selectedLanguage.equals("Việt Nam")) {
                    editor.putString("language", "vi");
                    editor.putInt("language_num", 4);
                }
                if (selectedLanguage.equals("ประเทศไทย")) {
                    editor.putString("language", "th");
                    editor.putInt("language_num", 5);
                }
                editor.apply();

                setTranslation();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            userProfile = data.getData();
            Glide.with(this)
                    .load(userProfile)
                    .override(500, 500)
                    .circleCrop()
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .into(inputEditProfileBinding.inputUserProfile);
        } else {
            Log.e(TAG, "image 로드 오류");
        }
    }

    private void showNotificationPolicyAccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Notification Policy Access")
                .setMessage("Please grant notification policy access to enable this feature.")
                .setPositiveButton("Grant Access", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
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

    private void setUserProfile() {
        UserProfile userProfile = new UserProfile();
        String userProfileImageUrl = userProfile.getUserProfileImageUrl(getContext());
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.icon_user)
                .error(R.drawable.icon_user);

        Glide.with(binding.getRoot())
                .load(userProfileImageUrl)
                .override(500, 500)
                .circleCrop()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .apply(requestOptions)
                .into(binding.mainUserProfile);
    }

    @Override
    public void hideBottomView() {

    }

    @Override
    public void showBottomView() {
        TextView textView = (TextView) getActivity().findViewById(R.id.bottom_navigate_shadow);
        textView.setVisibility(View.VISIBLE);
        LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.bottom_navigate_view);
        layout.setVisibility(View.VISIBLE);
    }
}
