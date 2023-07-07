package com.kstyles.korean.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kstyles.korean.R;
import com.kstyles.korean.databinding.ActivityTranslationBinding;
import com.kstyles.korean.repository.FirebaseManager;
import com.kstyles.korean.view.fragment.item.TranslationItem;

import java.util.ArrayList;
import java.util.HashMap;

public class TranslationActivity extends AppCompatActivity {

    private ActivityTranslationBinding binding;
    private FirebaseManager firebaseManager;
    private Spinner spinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTranslationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        firebaseManager = new FirebaseManager();

        HashMap<String, TranslationItem> allWordItem = firebaseManager.getAllWordItem();
        ArrayList<String> wordSet = new ArrayList<>(allWordItem.keySet());
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, wordSet);
        spinnerAdapter.setDropDownViewResource(com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item);

        spinner = binding.translationSpinner;
        spinner.setAdapter(spinnerAdapter);
    }
}
