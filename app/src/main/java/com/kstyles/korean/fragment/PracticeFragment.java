package com.kstyles.korean.fragment;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseError;
import com.kstyles.korean.R;
import com.kstyles.korean.databinding.ActivityFragmentPracticeBinding;
import com.kstyles.korean.item.PracticeItem;
import com.kstyles.korean.item.RandomButtonListener;
import com.kstyles.korean.item.RecyclerItem;
import com.kstyles.korean.preferences.count.QuizCount;
import com.kstyles.korean.repository.FirebaseCallback;
import com.kstyles.korean.repository.FirebaseManager;
import com.kstyles.korean.preferences.count.SeekbarPosition;

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
    private SeekbarPosition seekbarPosition;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityFragmentPracticeBinding.inflate(inflater, container, false);

        selectLevel = String.format("%s %s", recyclerItems.get(position).getLevel(), recyclerItems.get(position).getName());
        binding.practiceLevel.setText(selectLevel);
        firebaseManager.setPathString(selectLevel);
        QuizCount quizCount = new QuizCount(getContext(), selectLevel);
        seekbarPosition = new SeekbarPosition(quizCount.getLevelPosition());

        if (quizCount.getLevelPosition() >= 10){
            quizCount.setLevelPosition();
            quizCount.increaseWordCount(selectLevel);
            getExamToFirebase(quizCount.getLevelPosition());
        }
        if (getActivity() != null) {
            Button button = getActivity().findViewById(R.id.recycler_item_progress_btn);
            getExamToFirebase(button != null && "Revise".equals(button.getText())
                    ? quizCount.setLevelPosition()
                    : quizCount.getLevelPosition());
        } else {
            getExamToFirebase(quizCount.getLevelPosition());
        }

        binding.practiceBtnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = seekbarPosition.increasePosition(seekbarPosition.getPosition());
                getExamToFirebase(currentPosition);
            }
        });

        binding.practiceBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = seekbarPosition.decreasePosition(seekbarPosition.getPosition());
                getExamToFirebase(currentPosition);
            }
        });

        binding.practiceSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekbarPosition = new SeekbarPosition(progress);
                int currentPosition = seekbarPosition.getPosition();
                getExamToFirebase(currentPosition);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getExamToFirebase(int currentPosition) {
        binding.practicePosition.setText(String.valueOf(currentPosition+1));
        binding.practiceSeekbar.setProgress(currentPosition);

        firebaseManager.getPracticeItems(new FirebaseCallback<List<PracticeItem>>() {
            @Override
            public void onSuccess(List<PracticeItem> result) {
                binding.practiceImg.setBackground(null);
                practiceItems = new ArrayList<>();
                practiceItems.addAll(result);
                Glide.with(binding.getRoot())
                        .load(practiceItems.get(currentPosition).getImageUrl())
                        .centerCrop()
                        .into(binding.practiceImg);
                RandomButtonListener randomButtonListener = new RandomButtonListener(
                        binding.practiceBtn1, binding.practiceBtn2, binding.practiceBtn3,
                        binding.practiceBtn4, binding.getRoot().getContext(),
                        practiceItems.get(currentPosition).getAnswer(), selectLevel
                );
                randomButtonListener.randomButtonEvent();
            }

            @Override
            public void onFailure(DatabaseError error) {
                Log.e(TAG, "onFailure() 메서드가 호출됨. {}", error.toException());
            }
        });

        if (currentPosition == binding.practiceSeekbar.getMax()) {
            binding.practiceBtnGo.setVisibility(View.INVISIBLE);
            binding.practiceBtnBack.setVisibility(View.VISIBLE);
        }
        if (currentPosition == binding.practiceSeekbar.getMin()) {
            binding.practiceBtnBack.setVisibility(View.INVISIBLE);
            binding.practiceBtnGo.setVisibility(View.VISIBLE);
        }
        if (currentPosition != binding.practiceSeekbar.getMax() && currentPosition != binding.practiceSeekbar.getMin()) {
            binding.practiceBtnBack.setVisibility(View.VISIBLE);
            binding.practiceBtnGo.setVisibility(View.VISIBLE);
        }
    }
}
