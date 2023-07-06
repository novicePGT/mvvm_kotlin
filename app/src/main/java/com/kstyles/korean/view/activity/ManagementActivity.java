package com.kstyles.korean.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kstyles.korean.databinding.ActivityManagementBinding;

public class ManagementActivity extends AppCompatActivity {

    private ActivityManagementBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("manager", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        binding = ActivityManagementBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        /**
         * Select Group
         */
        Spinner spinner = binding.managerSpinner;
        int manager = sharedPreferences.getInt("manager", 0);
        spinner.setSelection(manager);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLanguage = (String) parent.getItemAtPosition(position);

                if (selectedLanguage.equals("Manager")) {
                    editor.putInt("manager", 0);
                }
                if (selectedLanguage.equals("Translator - Deutschland")) {
                    editor.putInt("manager", 1);
                }
                if (selectedLanguage.equals("Translator - France")) {
                    editor.putInt("manager", 2);
                }
                if (selectedLanguage.equals("Translator - 日本")) {
                    editor.putInt("manager", 3);
                }
                if (selectedLanguage.equals("Translator - ประเทศไทย")) {
                    editor.putInt("manager", 4);
                }
                if (selectedLanguage.equals("Translator - Việt Nam")) {
                    editor.putInt("manager", 5);
                }
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /**
         * Go to upload
         */
        binding.managementUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagementActivity.this, UploadActivity.class);
                startActivity(intent);
            }
        });

        /**
         * Back to app
         */
        binding.managementBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagementActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
