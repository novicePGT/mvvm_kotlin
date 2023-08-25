package com.kstyles.korean.view.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseError;
import com.kstyles.korean.R;
import com.kstyles.korean.databinding.ActivityTranslationBinding;
import com.kstyles.korean.repository.FirebaseCallback;
import com.kstyles.korean.repository.FirebaseManager;
import com.kstyles.korean.view.fragment.item.TranslationItem;

import java.util.ArrayList;
import java.util.TreeMap;

public class TranslationActivity extends AppCompatActivity {

    private ActivityTranslationBinding binding;
    private FirebaseManager firebaseManager;
    private Spinner wordSpinner;
    private Spinner levelSpinner;
    private TreeMap<String, TranslationItem> allWordItem;
    private String item;
    private String level = "Beginner";

    public TranslationActivity() {
        firebaseManager = new FirebaseManager();
        allWordItem = new TreeMap<>();
        fetchWordsForLevels(level);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTranslationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        SharedPreferences sharedPreferences = getSharedPreferences("manager", MODE_PRIVATE);
        int manager = sharedPreferences.getInt("manager", 0);

        levelSpinner();

        binding.translationFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = wordSpinner.getSelectedItem().toString();
                TranslationItem translationItem = allWordItem.get(item);
                if (manager == 0) {
                    binding.translationHintEn.setHint("Here is your Enter: En");
                    disableNonSelectedEditText(binding.translationHintEn);
                }
                if (manager == 1) {
                    binding.translationHintDe.setHint("Here is your Enter: De");
                    disableNonSelectedEditText(binding.translationHintDe);
                }
                if (manager == 2) {
                    binding.translationHintFr.setHint("Here is your Enter: Fr");
                    disableNonSelectedEditText(binding.translationHintFr);
                }
                if (manager == 3) {
                    binding.translationHintJa.setHint("Here is your Enter: Ja");
                    disableNonSelectedEditText(binding.translationHintJa);
                }
                if (manager == 4) {
                    binding.translationHintTh.setHint("Here is your Enter: Th");
                    disableNonSelectedEditText(binding.translationHintTh);
                }
                if (manager == 5) {
                    binding.translationHintVi.setHint("Here is your Enter: Vi");
                    disableNonSelectedEditText(binding.translationHintVi);
                }

                binding.translationHintEn.setHint(translationItem.getEn());
                binding.translationHintDe.setHint(translationItem.getDe());
                binding.translationHintFr.setHint(translationItem.getFr());
                binding.translationHintJa.setHint(translationItem.getJa());
                binding.translationHintTh.setHint(translationItem.getTh());
                binding.translationHintVi.setHint(translationItem.getVi());
            }
        });

        binding.translationBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String translation = "";
                String language = "";
                if (manager == 0) {
                    translation = item + ": " + binding.translationHintEn.getText().toString();
                    language = "en";
                }
                if (manager == 1) {
                    translation = item + ": " + binding.translationHintDe.getText().toString();
                    language = "de";
                }
                if (manager == 2) {
                    translation = item + ": " + binding.translationHintFr.getText().toString();
                    language = "fr";
                }
                if (manager == 3) {
                    translation = item + ": " + binding.translationHintJa.getText().toString();
                    language = "ja";
                }
                if (manager == 4) {
                    translation = item + ": " + binding.translationHintTh.getText().toString();
                    language = "th";
                }
                if (manager == 5) {
                    translation = item + ": " + binding.translationHintVi.getText().toString();
                    language = "vi";
                }

                firebaseManager.updateTranslation(level, item, language, translation);

                Toast.makeText(TranslationActivity.this, "Success to update translation", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void levelSpinner() {
        String[] levels = getResources().getStringArray(R.array.level);
        levelSpinner = binding.translationLevelSpinner;
        ArrayAdapter<String> levelSpinnerAdapter = new ArrayAdapter<>(TranslationActivity.this, com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, levels);
        levelSpinnerAdapter.setDropDownViewResource(com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item);
        levelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                level = parent.getItemAtPosition(position).toString();
                fetchWordsForLevels(level);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        levelSpinner.setAdapter(levelSpinnerAdapter);
    }

    private void disableNonSelectedEditText(EditText editText) {
        binding.translationHintEn.setEnabled(false);
        binding.translationHintDe.setEnabled(false);
        binding.translationHintFr.setEnabled(false);
        binding.translationHintJa.setEnabled(false);
        binding.translationHintTh.setEnabled(false);
        binding.translationHintVi.setEnabled(false);

        editText.setEnabled(true);
    }

    private void fetchWordsForLevels(String level) {
        if (allWordItem.size() >= 10) {
            allWordItem.clear();
        }
        firebaseManager.getWordByLevel(level, new FirebaseCallback<TreeMap<String, TranslationItem>>() {
            @Override
            public void onSuccess(TreeMap<String, TranslationItem> result) {
                allWordItem.putAll(result);
                ArrayList<String> wordSet = new ArrayList<>(allWordItem.keySet());
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(TranslationActivity.this, com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, wordSet);
                spinnerAdapter.setDropDownViewResource(com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item);

                wordSpinner = binding.translationSpinner;
                wordSpinner.setAdapter(spinnerAdapter);
            }

            @Override
            public void onFailure(DatabaseError error) {
                // 실패 시 처리
            }});

    }
}
