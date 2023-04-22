package com.sudeep.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.sudeep.todoapp.home.MainActivity;
import com.sudeep.todoapp.util.Common;
import com.sudeep.todoapp.util.PreferenceHelper;

public class LauncherActivity extends AppCompatActivity {
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        String loginValues = PreferenceHelper.getString(context, Common.C_KEY_APP_LOGIN_VALUES,null);
        if (loginValues != null) {
            Intent intent = new Intent(context, MainActivity.class);
            finish();
            startActivity(intent);
        } else {
            Intent intent = new Intent(context, LoginActivity.class);
            finish();
            startActivity(intent);
        }

    }
}