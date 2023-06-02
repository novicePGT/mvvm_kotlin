package com.kstyles.korean.verification;

import java.util.regex.Pattern;

public class PasswordValidator {

    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    public static boolean validatePassword(String password1, String password2) {
        // 비밀번호 형식을 지정하는 정규식 패턴 생성
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

        // 두 비밀번호가 일치하고, 정규식 패턴에 모두 일치하는지 확인
        return password1.equals(password2) && pattern.matcher(password1).matches();
    }
}
