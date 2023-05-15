package com.sudeep.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sudeep.todoapp.firebase.FirebaseAuthManger;
import com.sudeep.todoapp.firebase.FirebaseDbManger;
import com.sudeep.todoapp.util.AbstractAppActivity;
import com.sudeep.todoapp.util.ValidationHelper;

public class LoginActivity extends AbstractAppActivity {
    private Context context = this;
    private EditText etLoginEmail, etLoginPassword;
    private Button btnLogin;
    private TextView tvGoToSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvGoToSignUp = findViewById(R.id.tvGoToSignUp);

        tvGoToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,SignupActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etLoginEmail.getText().toString();
                String password = etLoginPassword.getText().toString();
                if (ValidationHelper.isTextEmpty(email)){
                    Toast.makeText(context, "Please enter email", Toast.LENGTH_SHORT).show();
                }
                else if (ValidationHelper.isTextEmpty(password)){
                    Toast.makeText(context, "Please enter your password", Toast.LENGTH_SHORT).show();
                }
                else {
                    FirebaseAuthManger.loginUser(context, email, password);
                }
               
                
            }
        });
    }
    @Override
    public void whenInternet() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    public void whenNoInternet() {
        setContentView(R.layout.lottie_no_internet);
    }
}