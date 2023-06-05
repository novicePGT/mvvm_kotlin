package com.kstyles.korean.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseError;
import com.kstyles.korean.R;
import com.kstyles.korean.databinding.ActivityFragmentPracticeBinding;
import com.kstyles.korean.databinding.InputPracticeViewBinding;
import com.kstyles.korean.item.PracticeItem;
import com.kstyles.korean.item.RecyclerItem;
import com.kstyles.korean.language.LanguageManager;
import com.kstyles.korean.preferences.count.QuizCount;
import com.kstyles.korean.repository.FirebaseCallback;
import com.kstyles.korean.repository.FirebaseManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class PracticeFragment extends Fragment {

    private final String TAG = "[PracticeFragment]";
    private ActivityFragmentPracticeBinding binding;
    private FirebaseManager firebaseManager;
    private ArrayList<RecyclerItem> recyclerItems;
    private ArrayList<PracticeItem> practiceItems;
    private String selectLevel;
    private QuizCount quizCount;
    private Button buttons[];
    private int position;
    private String answer;

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
        quizCount = new QuizCount(getContext(), selectLevel);

        setPracticeView();

        return binding.getRoot();
    }

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
                answer = practiceItems.get(currentPosition).getAnswer();
                randomButtonEvent();
            }

            @Override
            public void onFailure(DatabaseError error) {
                Log.e(TAG, "onFailure() 메서드가 호출됨. {}", error.toException());
            }
        });
    }

    private void randomButtonEvent() {
        LanguageManager languageManager = new LanguageManager(getContext());
        buttons = new Button[]{binding.practiceBtn1, binding.practiceBtn2, binding.practiceBtn3, binding.practiceBtn4};
        int buttonIndex = new Random().nextInt(4);

        List<String> buttonTexts = setButtonText(buttonIndex);

        IntStream.range(0, buttons.length)
                .forEach(i -> buttons[i].setText(buttonTexts.get(i)));

        for (int i = 0; i < buttons.length; i++) {
            String valueText = buttons[i].getText().toString();
            final Button button = buttons[i]; // final 변수로 할당

            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    button.setClickable(false);
                    if (valueText.equals(answer)) {
                        quizCount.increaseWordCount(selectLevel);
                        button.setBackground(getContext().getDrawable(R.drawable.custom_btn_correct));
                        button.setTextColor(Color.WHITE);

                        InputPracticeViewBinding inputPracticeViewBinding = InputPracticeViewBinding.inflate(LayoutInflater.from(binding.getRoot().getContext()), binding.getRoot(), false);

                        int identifier = getResources().getIdentifier(answer, "string", getContext().getPackageName());
                        String findByAnswer = getContext().getString(identifier);
                        inputPracticeViewBinding.description.setText(findByAnswer);

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        inputPracticeViewBinding.correctView.playAnimation();
                        builder.setView(inputPracticeViewBinding.getRoot())
                                .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.O)
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        setPracticeView();
                                    }
                                }).show();
                    } else {
                        Toast.makeText(getContext(), "오답", Toast.LENGTH_SHORT).show();
                        button.setBackground(getContext().getDrawable(R.drawable.custom_btn_incorrect));
                        button.setTextColor(Color.WHITE);
                    }
                }
            });
            button.setBackground(getContext().getDrawable(R.drawable.custom_btn_white));
            button.setTextColor(Color.BLACK);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setPracticeView() {
        Button button = getActivity().findViewById(R.id.recycler_item_progress_btn);

        if (quizCount.getLevelPosition() >= 10) {
            quizCount.setLevelPosition();
            getExamToFirebase(quizCount.getLevelPosition());
        }
        if (button != null && "Revise".equals(button.getText())) {
            getExamToFirebase(button != null && "Revise".equals(button.getText())
                    ? quizCount.setLevelPosition()
                    : quizCount.getLevelPosition());
        } else {
            getExamToFirebase(quizCount.getLevelPosition());
        }
    }

    private List<String> setButtonText(int buttonIndex) {
        String[] buttonString = getResources().getStringArray(R.array.words);
        List<String> buttonList = Arrays.asList(buttonString);
        List<String> buttonTexts = new ArrayList<>(Collections.nCopies(buttons.length, ""));
        buttonTexts.set(buttonIndex, answer);

        List<Integer> usedIndices = new ArrayList<>();
        usedIndices.add(buttonIndex);

        Random random = new Random();

        for (int i=0; i<buttons.length; i++) {
            if (i != buttonIndex) {
                int randomButtonIndex = random.nextInt(buttonList.size());

                while (usedIndices.contains(randomButtonIndex)) {
                    randomButtonIndex = random.nextInt(buttonList.size());
                }

                usedIndices.add(randomButtonIndex);
                buttonTexts.set(i, buttonList.get(randomButtonIndex));
            }
        }

        return buttonTexts;
    }
}
