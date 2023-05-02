package com.kstyles.korean.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseError;
import com.kstyles.korean.R;
import com.kstyles.korean.databinding.ActivityFragmentPracticeBinding;
import com.kstyles.korean.item.PracticeItem;
import com.kstyles.korean.item.RandomButtonListener;
import com.kstyles.korean.item.RecyclerItem;
import com.kstyles.korean.repository.FirebaseCallback;
import com.kstyles.korean.repository.FirebaseManager;

import java.util.ArrayList;
import java.util.List;

public class PracticeFragment extends Fragment {

    private final String TAG = "[PracticeFragment]";
    private ActivityFragmentPracticeBinding binding;
    private FirebaseManager firebaseManager;
    private ArrayList<RecyclerItem> recyclerItems;
    private ArrayList<PracticeItem> practiceItems;
    private String selectLevel;
    private int position;

    public PracticeFragment() {
        recyclerItems = MainFragment.items;
        firebaseManager = new FirebaseManager();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            position = bundle.getInt("position");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityFragmentPracticeBinding.inflate(inflater, container, false);

        selectLevel = String.format("%s %s", recyclerItems.get(position).getLevel(), recyclerItems.get(position).getName());
        binding.practiceLevel.setText(selectLevel);
        firebaseManager.setPathString(selectLevel);

        firebaseManager.getPracticeItems(new FirebaseCallback<List<PracticeItem>>() {
            @Override
            public void onSuccess(List<PracticeItem> result) {
                binding.practiceImg.setBackground(null);
                practiceItems = new ArrayList<>();
                practiceItems.addAll(result);
                Glide.with(binding.getRoot())
                        .load(practiceItems.get(9).getImageUrl())
                        .into(binding.practiceImg);
                RandomButtonListener randomButtonListener = new RandomButtonListener(
                        binding.practiceBtn1, binding.practiceBtn2, binding.practiceBtn3, binding.practiceBtn4, binding.getRoot().getContext(), practiceItems.get(9).getAnswer()
                );
                randomButtonListener.randomButtonEvent();
            }

            @Override
            public void onFailure(DatabaseError error) {
                Log.e(TAG, "onFailure() 메서드가 호출됨. {}", error.toException());
            }
        });

        return binding.getRoot();
    }
}
