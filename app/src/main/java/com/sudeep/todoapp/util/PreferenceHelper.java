package com.sudeep.todoapp.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {
    private static final String KEY_APP_PREFERENCES= "app-preferences";

    public static void putString(Context context, String cKeyAppLoginValues, String value) {
        SharedPreferences app_preferences = context.getSharedPreferences(KEY_APP_PREFERENCES, context.MODE_PRIVATE);
        SharedPreferences.Editor editor =app_preferences.edit();
        editor.putString(cKeyAppLoginValues, value);
        editor.apply();editor.commit();
    }


    public static String getString(Context context,String key,String defaultvalue){
        SharedPreferences app_preferences = context.getSharedPreferences(KEY_APP_PREFERENCES,Context.MODE_PRIVATE);
        return app_preferences.getString(key,defaultvalue);
    }
}
