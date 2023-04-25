package com.kstyles.korean.verification;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class PasswordWatcher implements TextWatcher {

    private TextView verificationView;
    private EditText password;
    private EditText confirmPassword;

    public PasswordWatcher(TextView verificationView, EditText password, EditText confirmPassword) {
        this.verificationView = verificationView;
        this.password = password;
        this.confirmPassword = confirmPassword;
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
        String currentPassword = password.getText().toString().trim();
        String rePassword = confirmPassword.getText().toString().trim();

        if (text.length() <= 6) {
            verificationView.setText("Please enter at least 7 digits of the password.");
            verificationView.setTextColor(Color.RED);
        }
        if (text.length() >= 7) {
            if (currentPassword.isEmpty() || rePassword.isEmpty()) {
                verificationView.setText("");
            }
            if (currentPassword.equals(rePassword.trim())) {
                verificationView.setText("The two passwords match.");
                verificationView.setTextColor(Color.BLUE);
            }
            if (!currentPassword.equals(rePassword.trim())) {
                verificationView.setText("The two passwords do not match.");
                verificationView.setTextColor(Color.RED);
            }
        }
        if (text.isEmpty()) {
            verificationView.setText("");
        }
    }
}
