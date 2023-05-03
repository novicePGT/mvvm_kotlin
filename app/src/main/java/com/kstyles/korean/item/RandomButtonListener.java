package com.kstyles.korean.item;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.kstyles.korean.R;
import com.kstyles.korean.fragment.PracticeFragment;

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

    public RandomButtonListener(Button button1, Button button2, Button button3, Button button4, Context context, String answer) {
        buttons = new Button[]{button1, button2, button3, button4};
        this.context = context;
        this.answer = answer;
    }

    public void randomButtonEvent() {
        int randomIndex = new Random().nextInt(4);

        List<String> buttonTexts = setButtonText(randomIndex);

        IntStream.range(0, buttons.length)
                .forEach(i -> buttons[i].setText(buttonTexts.get(i)));

        for (int i = 0; i < buttons.length; i++) {
            String valueText = buttons[i].getText().toString();
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (valueText.equals(answer)) {
                        Toast.makeText(context, "정답", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(context, "오답", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @NonNull
    private List<String> setButtonText(int randomIndex) {
        String[] buttonString = context.getResources().getStringArray(R.array.words);
        List<String> buttonList = Arrays.asList(buttonString);
        List<String> buttonTexts = new ArrayList<>(Collections.nCopies(buttons.length, ""));
        buttonTexts.set(randomIndex, answer);

        for (int i = 0; i < buttons.length; i++) {
            if (i != randomIndex) {
                String buttonText = buttonList.get(new Random().nextInt(buttonList.size()));
                while (buttonTexts.subList(0, i).contains(buttonText)) {
                    buttonText = buttonList.get(new Random().nextInt(buttonList.size()));
                }
                buttonTexts.set(i, buttonText);
            }
        }
        return buttonTexts;
    }
}
