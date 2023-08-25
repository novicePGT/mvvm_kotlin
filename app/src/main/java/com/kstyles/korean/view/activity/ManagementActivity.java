package com.kstyles.korean.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseError;
import com.kstyles.korean.databinding.ActivityManagementBinding;
import com.kstyles.korean.databinding.InputDeleteExamBinding;
import com.kstyles.korean.repository.FirebaseCallback;
import com.kstyles.korean.repository.FirebaseManager;
import com.kstyles.korean.view.fragment.item.PracticeItem;

import java.util.ArrayList;
import java.util.List;

public class ManagementActivity extends AppCompatActivity {

    private ActivityManagementBinding binding;
    private InputDeleteExamBinding inputDeleteExamBinding;
    private FirebaseManager firebaseManager;
    private Spinner spinner;
    private Spinner inputSpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseManager = new FirebaseManager();

        SharedPreferences sharedPreferences = getSharedPreferences("manager", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        binding = ActivityManagementBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        inputDeleteExamBinding = InputDeleteExamBinding.inflate(LayoutInflater.from(binding.getRoot().getContext()), binding.getRoot(), false);
        inputSpinner = inputDeleteExamBinding.inputDeleteSpinner;
        findExam();

        /**
         * Select Group
         */
        spinner = binding.managerSpinner;
        int manager = sharedPreferences.getInt("manager", 0);
        spinner.setSelection(manager);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int spinnerId = parent.getId();
                String selectedLanguage = (String) parent.getItemAtPosition(position);

                if (spinnerId == spinner.getId()) {
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
         * Go to Translation
         */
        binding.managementTranslation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagementActivity.this, TranslationActivity.class);
                startActivity(intent);
            }
        });

        /**
         * AlertDialog - Delete exam
         */
        binding.managementDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ManagementActivity.this);
                builder.setTitle("Delete Exam")
                        .setView(inputDeleteExamBinding.getRoot())
                        .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String value = inputDeleteExamBinding.inputDeleteSelect.getText().toString();
                                deleteExam(value);
                            }
                        })
                        .setNegativeButton("Refuse", null)
                        .show();
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

    private void findExam() {
        firebaseManager.findExistExam(new FirebaseCallback<ArrayList<String>>() {
            @Override
            public void onSuccess(ArrayList<String> result) {
                ArrayAdapter<String> dialogSpinnerAdapter = new ArrayAdapter<>(ManagementActivity.this, com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, result);
                dialogSpinnerAdapter.setDropDownViewResource(com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item);

                inputSpinner.setAdapter(dialogSpinnerAdapter);
            }

            @Override
            public void onFailure(DatabaseError error) {

            }
        });
    }

    private void deleteExam(String value) {
        firebaseManager.setPathString(value);
        firebaseManager.getPracticeItems(new FirebaseCallback<List<PracticeItem>>() {
            @Override
            public void onSuccess(List<PracticeItem> result) {
                List<String> itemSet = new ArrayList<>();
                for (PracticeItem itemName : result) {
                    itemSet.add(itemName.getAnswer());
                }

                firebaseManager.deleteExam(value, itemSet);
            }

            @Override
            public void onFailure(DatabaseError error) {

            }
        });
    }
}
