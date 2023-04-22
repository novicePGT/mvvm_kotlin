package com.kstyles.korean.verification;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class EditTextWatcher implements TextWatcher {

    private TextView verificationView;

    public EditTextWatcher(TextView verificationView) {
        this.verificationView = verificationView;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String text = s.toString();

        if (EmailValidator.isValidEmail(text)) {
            verificationView.setText("Email is a valid format.");
            verificationView.setTextColor(Color.GREEN);
        }
        if (EmailValidator.isValidEmail(text) == false) {
            verificationView.setText("Email is not in a valid format.");
            verificationView.setTextColor(Color.RED);
        }
        if (text.isEmpty()) {
            verificationView.setText("");
        }
    }
}
