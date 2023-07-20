package com.kstyles.korean.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.kstyles.korean.databinding.ActivityManagementUploadBinding;
import com.kstyles.korean.repository.FirebaseManager;
import com.kstyles.korean.view.fragment.UploadFragment;
import com.kstyles.korean.view.fragment.item.PracticeItem;
import com.kstyles.korean.view.fragment.item.TranslationItem;

import java.util.ArrayList;


public class UploadActivity extends AppCompatActivity {

    private final Fragment[] fragments = new Fragment[10];
    private ActivityManagementUploadBinding binding;
    private FirebaseManager firebaseManager;
    private String level;
    private int position;
    private ArrayList<PracticeItem> practiceItems;
    private ArrayList<TranslationItem> translationItems;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * Fragment Setting
         */
        position = 0;
        for (int i = 0; i < fragments.length; i++) {
            fragments[i] = new UploadFragment(i);
        }

        /**
         * View Binding Setting
         */
        binding = ActivityManagementUploadBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        firebaseManager = new FirebaseManager();
        practiceItems = new ArrayList<>();
        translationItems = new ArrayList<>();

        switchFragment(fragments[0]);

        /**
         * level spinner
         */
        binding.levelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                level = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.uploadPrevious.setOnClickListener(v -> {
            position--;
            if (position < 0) {
                position = 0;
            }
            switchFragment(fragments[position]);
        });
        binding.uploadNext.setOnClickListener(v -> {
            position++;
            if (position > 9) {
                position = 9;
            }
            switchFragment(fragments[position]);
        });

        binding.uploadBtnAddWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadFragment currentFragment = (UploadFragment) fragments[position];
                String wordName = currentFragment.getWordName();
                String translation = currentFragment.getTranslation();
                String imageUri = String.valueOf(currentFragment.getImageUri());

                PracticeItem practiceItem = new PracticeItem(wordName, imageUri);
                practiceItems.add(position, practiceItem);

                TranslationItem translationItem = new TranslationItem(translation, UploadActivity.this);
                translationItems.add(position, translationItem);

                Toast.makeText(UploadActivity.this, "Success to add word, Go next", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * CREATE Button
         */
        binding.createUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sequence = binding.levelSequence.getText().toString();
                String recyclerItemName = level + " Vocabulary " + sequence;

                firebaseManager.uploadRecyclerItem(recyclerItemName);
                firebaseManager.uploadWordItem(practiceItems, translationItems);
                firebaseManager.uploadPracticeItem(practiceItems, recyclerItemName, UploadActivity.this);

                Intent intent = new Intent(UploadActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(binding.managementFrameLayout.getId(), fragment);
        transaction.commit();
    }
}
