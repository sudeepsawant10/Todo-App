package com.sudeep.todoapp.util;

import android.text.TextUtils;
import android.util.Patterns;

public class ValidationHelper {
    public static boolean isTextEmpty(String text) {
        return TextUtils.isEmpty(text);
    }
    public static boolean isValidEmail(CharSequence  target){
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());

    }
    public static boolean isPasswordMatch(String password1, String password2) {
        if(password1.equals(password2)){
            return false;
        }
        else {
            return true;
        }
    }
}
