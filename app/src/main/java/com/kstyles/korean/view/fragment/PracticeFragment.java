package com.kstyles.korean.view.fragment;

import android.animation.Animator;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DatabaseError;
import com.kstyles.korean.R;
import com.kstyles.korean.databinding.ActivityFragmentPracticeBinding;
import com.kstyles.korean.databinding.InputPracticeViewBinding;
import com.kstyles.korean.language.TranslationManager;
import com.kstyles.korean.preferences.user.UserProfile;
import com.kstyles.korean.view.fragment.bottomView.BottomViewManipulationListener;
import com.kstyles.korean.view.fragment.item.PracticeItem;
import com.kstyles.korean.view.fragment.item.RecyclerItem;
import com.kstyles.korean.language.LanguageManager;
import com.kstyles.korean.preferences.count.QuizCount;
import com.kstyles.korean.repository.FirebaseCallback;
import com.kstyles.korean.repository.FirebaseManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;

public class PracticeFragment extends Fragment implements BottomViewManipulationListener {

    private final String TAG = "[PracticeFragment]";
    private ActivityFragmentPracticeBinding binding;
    private FirebaseManager firebaseManager;
    private ArrayList<RecyclerItem> recyclerItems;
    private ArrayList<PracticeItem> practiceItems;
    private TranslationManager translationManager;
    private String selectLevel;
    private QuizCount quizCount;
    private Button buttons[];
    private int position;
    private String answer;
    private TextToSpeech tts;

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
        practiceItems = new ArrayList<>();
        translationManager = new TranslationManager(requireContext());

        hideBottomView();
        setPracticeView();
        setUserProfile();
        initTts();

        return binding.getRoot();
    }

    private void setExam(int currentPosition) {
        binding.practicePosition.setText(String.valueOf(currentPosition+1));
        binding.practiceSeekbar.setProgress(currentPosition);

        binding.loadView.setVisibility(View.VISIBLE);
        binding.loadView.playAnimation();
        binding.loadView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animation) {
                binding.frameLayout3.setBackgroundColor(Color.argb(32, 0, 0, 0));
            }

            @Override
            public void onAnimationEnd(@NonNull Animator animation) {
                binding.loadView.setVisibility(View.INVISIBLE);
                binding.frameLayout3.setBackgroundColor(Color.TRANSPARENT);
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animation) {

            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animation) {

            }
        });

        firebaseManager.getPracticeItems(new FirebaseCallback<List<PracticeItem>>() {
            @Override
            public void onSuccess(List<PracticeItem> result) {
                binding.practiceImg.setBackground(null);
                if (practiceItems.size() == 0) {
                    practiceItems.addAll(result);
                }
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
        languageManager.setLanguage();
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
                        quizCount.increaseWordCount();
                        button.setBackground(getContext().getDrawable(R.drawable.custom_btn_correct));
                        button.setTextColor(Color.WHITE);

                        InputPracticeViewBinding inputPracticeViewBinding = InputPracticeViewBinding.inflate(LayoutInflater.from(binding.getRoot().getContext()), binding.getRoot(), false);

                        String findByAnswer = translationManager.getTranslatedLanguage(answer);
                        inputPracticeViewBinding.description.setText(findByAnswer);

                        showDialog(inputPracticeViewBinding);
                    } else {
                        String buttonText = button.getText().toString();
                        String findByAnswer = translationManager.getTranslatedLanguage(buttonText);
                        binding.practiceDescription.setText(findByAnswer);

                        button.setBackground(getContext().getDrawable(R.drawable.custom_btn_incorrect));
                        button.setTextColor(Color.WHITE);
                        binding.listenWord.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                tts.setPitch(1.0f);
                                tts.setSpeechRate(1.0f);
                                tts.speak(buttonText, TextToSpeech.QUEUE_FLUSH, null);
                                Log.d(TAG, "SPEAK");
                            }
                        });
                    }
                }
            });
            button.setBackground(getContext().getDrawable(R.drawable.custom_btn_white));
            button.setTextColor(Color.BLACK);
        }
    }

    private List<String> setButtonText(int buttonIndex) {
        List<String> combinedList = new ArrayList<>();
        String[] beginnerArray = getResources().getStringArray(R.array.beginner);
        String[] intermediateArray = getResources().getStringArray(R.array.intermediate);
        String[] advancedArray = getResources().getStringArray(R.array.advanced);
        combinedList.addAll(Arrays.asList(beginnerArray));
        combinedList.addAll(Arrays.asList(intermediateArray));
        combinedList.addAll(Arrays.asList(advancedArray));
        List<String> buttonTexts = new ArrayList<>(Collections.nCopies(buttons.length, ""));
        buttonTexts.set(buttonIndex, answer);

        List<Integer> usedIndices = new ArrayList<>();
        usedIndices.add(buttonIndex);

        Random random = new Random();

        for (int i=0; i<buttons.length; i++) {
            if (i != buttonIndex) {
                int randomButtonIndex = random.nextInt(combinedList.size());

                while (usedIndices.contains(randomButtonIndex)) {
                    randomButtonIndex = random.nextInt(combinedList.size());
                }

                usedIndices.add(randomButtonIndex);
                if(!Objects.equals(combinedList.get(randomButtonIndex), answer)) {
                    buttonTexts.set(i, combinedList.get(randomButtonIndex));
                }
            }
        }

        return buttonTexts;
    }

    private void showDialog(InputPracticeViewBinding inputPracticeViewBinding) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        inputPracticeViewBinding.correctView.playAnimation();
        builder.setView(inputPracticeViewBinding.getRoot())
                .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (quizCount.getLevelPosition() >= 10) {
                            ProgressFragment progressFragment = new ProgressFragment();
                            FragmentManager fragmentManager = requireFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.main_frame, progressFragment);
                            fragmentTransaction.commit();
                        } else {
                            setPracticeView();
                        }
                    }
                }).show();
    }

    private void initTts() {
        tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.KOREAN);
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setPracticeView() {
        Button button = getActivity().findViewById(R.id.recycler_item_progress_btn);

        if (quizCount.getLevelPosition() >= 10) {
            quizCount.setLevelPosition();
            practiceItems.clear();
        }
        if (button != null && "Revise".equals(button.getText())) {
            setExam(button != null && "Revise".equals(button.getText())
                    ? quizCount.setLevelPosition()
                    : quizCount.getLevelPosition());
        } else {
            setExam(quizCount.getLevelPosition());
        }
    }

    private void setUserProfile() {
        UserProfile userProfile = new UserProfile();
        String userProfileImageUrl = userProfile.getUserProfileImageUrl(getContext());
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.icon_user)
                .error(R.drawable.icon_user);

        Glide.with(binding.getRoot())
                .load(userProfileImageUrl)
                .override(500, 500)
                .circleCrop()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .apply(requestOptions)
                .into(binding.mainUserProfile);
    }

    @Override
    public void hideBottomView() {
        TextView textView = (TextView) getActivity().findViewById(R.id.bottom_navigate_shadow);
        textView.setVisibility(View.GONE);
        LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.bottom_navigate_view);
        layout.setVisibility(View.GONE);
    }

    @Override
    public void showBottomView() {

    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
            tts = null;
        }
        super.onDestroy();
    }
}
