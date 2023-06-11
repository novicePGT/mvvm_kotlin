package com.kstyles.korean.verification;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.kstyles.korean.R;
import com.kstyles.korean.language.LanguageManager;

public class EditTextWatcher implements TextWatcher {

    private TextView verificationView;
    private LanguageManager languageManager;

    public EditTextWatcher(TextView verificationView) {
        this.verificationView = verificationView;
        this.languageManager = new LanguageManager(verificationView.getContext());
        languageManager.setLanguage();
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

        String email_valid = languageManager.getTranslatedString(R.string.tv_email_valid);
        String email_invalid = languageManager.getTranslatedString(R.string.tv_email_invalid);

        if (EmailValidator.isValidEmail(text)) {
            verificationView.setText(email_valid);
            verificationView.setTextColor(Color.GREEN);
        }
        if (!EmailValidator.isValidEmail(text)) {
            verificationView.setText(email_invalid);
            verificationView.setTextColor(Color.RED);
        }
        if (text.isEmpty()) {
            verificationView.setText("");
        }
    }
}
