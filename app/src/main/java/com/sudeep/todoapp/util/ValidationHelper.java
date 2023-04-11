package com.sudeep.todoapp.util;

import android.text.TextUtils;

public class ValidationHelper {
    public static boolean isTextEmpty(String text) {
        return TextUtils.isEmpty(text);
    }
}
