package com.sudeep.todoapp.util;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONObject;

public class UserAccountManager {
//    if user logged in it will return user_id else null
    public static String getUserId(Context context) {
        String login_values = PreferenceHelper.getString(context, Common.C_KEY_APP_LOGIN_VALUES,null);
        if (login_values != null){
            try {
                JSONObject jsonObject = new JSONObject(login_values);
                String user_id = jsonObject.getString("user_id");
                return user_id;
            }
            catch (Exception e) {
                Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
                return null;
            }

        }
        else {
            return null;
        }
    }
}
