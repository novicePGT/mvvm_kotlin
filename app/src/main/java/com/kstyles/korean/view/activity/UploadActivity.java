package com.kstyles.korean.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.kstyles.korean.databinding.ActivityManagementUploadBinding;
import com.kstyles.korean.repository.FirebaseManager;
import com.kstyles.korean.view.fragment.UploadFragment;


public class UploadActivity extends AppCompatActivity {

    private final Fragment[] fragments = {new UploadFragment(), new UploadFragment()};

    private ActivityManagementUploadBinding binding;
    private FirebaseManager firebaseManager;
    private String level;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * View Binding Setting
         */
        binding = ActivityManagementUploadBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        firebaseManager = new FirebaseManager();

        switchFragment(fragments[0]);

        binding.levelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                level = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.createUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sequence = binding.levelSequence.getText().toString();
                String recyclerItemName = level + " Vocabulary " + sequence;

                firebaseManager.uploadRecyclerItem(recyclerItemName);
            }
        });

    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(binding.managementFrameLayout.getId(), fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
