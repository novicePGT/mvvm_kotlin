package com.kstyles.korean.view.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.kstyles.korean.databinding.ActivityFragmentUploadBinding;

public class UploadFragment extends Fragment {

    private String TAG = "[UploadFragment]";
    private ActivityFragmentUploadBinding binding;
    private Uri image;
    private final int REQUEST_CODE = 1002;
    private int position;
    private boolean isImageSelected = false;

    public UploadFragment() {

    }
    public UploadFragment(int position) {
        this.position = position;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (isImageSelected && image != null) {
            Glide.with(this)
                    .load(image)
                    .override(500, 500)
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .into(binding.uploadImage);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityFragmentUploadBinding.inflate(inflater, container, false);

        binding.uploadTvSequence.setText(String.valueOf(position + 1));

        binding.uploadBtnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        return binding.getRoot();
    }

    public String getWordName() {
        String wordName = binding.uploadWordName.getText().toString();
        return wordName;
    }

    public String getTranslation() {
        String translation = binding.uploadTranslation.getText().toString();
        return translation;
    }

    public Uri getImageUri() {
        return image;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            image = data.getData();
            isImageSelected = true;
            Glide.with(this)
                    .load(image)
                    .override(500, 500)
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .into(binding.uploadImage);
        } else {
            Log.e(TAG, "image 로드 오류");
        }
    }
}
