package com.kstyles.korean.item;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.airbnb.lottie.LottieAnimationView;
import com.kstyles.korean.R;
import com.kstyles.korean.preferences.count.QuizCount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class RandomButtonListener {

    private Button buttons[];
    private Context context;
    private String answer;
    private String selectLevel;
    private LottieAnimationView lottieAnimationView;

    public RandomButtonListener(Button button1, Button button2, Button button3, Button button4, Context context, String answer, String selectLevel, LottieAnimationView lottieAnimationView) {
        buttons = new Button[]{button1, button2, button3, button4};
        this.context = context;
        this.answer = answer;
        this.selectLevel = selectLevel;
        this.lottieAnimationView = lottieAnimationView;
    }

    public void randomButtonEvent() {
        int randomIndex = new Random().nextInt(4);
        QuizCount quizCount = new QuizCount(context, selectLevel);

        List<String> buttonTexts = setButtonText(randomIndex);

        IntStream.range(0, buttons.length)
                .forEach(i -> buttons[i].setText(buttonTexts.get(i)));

        for (int i = 0; i < buttons.length; i++) {
            String valueText = buttons[i].getText().toString();
            final Button button = buttons[i]; // final 변수로 할당

            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (valueText.equals(answer)) {
                        lottieAnimationView.setVisibility(View.VISIBLE);
                        lottieAnimationView.playAnimation();
                        quizCount.increaseWordCount(selectLevel);
                        button.setBackground(context.getDrawable(R.drawable.custom_btn_correct));
                        button.setTextColor(Color.WHITE);
                    } else {
                        Toast.makeText(context, "오답", Toast.LENGTH_SHORT).show();
                        button.setBackground(context.getDrawable(R.drawable.custom_btn_incorrect));
                        button.setTextColor(Color.WHITE);
                    }
                }
            });
            button.setBackground(context.getDrawable(R.drawable.custom_btn_white));
            button.setTextColor(Color.BLACK);
        }
    }

    @NonNull
    private List<String> setButtonText(int randomIndex) {
        String[] buttonString = context.getResources().getStringArray(R.array.words);
        List<String> buttonList = Arrays.asList(buttonString);
        List<String> buttonTexts = new ArrayList<>(Collections.nCopies(buttons.length, ""));
        buttonTexts.set(randomIndex, answer);

        List<Integer> usedIndices = new ArrayList<>();
        usedIndices.add(randomIndex);

        Random random = new Random();

        for (int i = 0; i < buttons.length; i++) {
            if (i != randomIndex) {
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
