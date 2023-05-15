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
import com.sudeep.todoapp.util.AbstractAppActivity;
import com.sudeep.todoapp.util.ValidationHelper;

public class SignupActivity extends AbstractAppActivity {
    private Context context = this;
    private EditText etSignUpFirstName, etSignUpLastName, etSignUpEmail, etSignUpPassword1, etSignUpPassword2;
    private TextView tvGoToLogin;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etSignUpFirstName = findViewById(R.id.etSignUpFirstName);
        etSignUpLastName = findViewById(R.id.etSignUpLastName);
        etSignUpEmail = findViewById(R.id.etSignUpEmail);
        etSignUpPassword1 = findViewById(R.id.etSignUpPassword1);
        etSignUpPassword2 = findViewById(R.id.etSignUpPassword2);
        tvGoToLogin = findViewById(R.id.tvGoToLogin);

        btnSignUp = findViewById(R.id.btnSignUp);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = etSignUpFirstName.getText().toString();
                String lastName = etSignUpLastName.getText().toString();
                String email = etSignUpEmail.getText().toString();
                String password1 = etSignUpPassword1.getText().toString();
                String password2 = etSignUpPassword2.getText().toString();

                if (!ValidationHelper.isValidEmail(email)){
                    Toast.makeText(SignupActivity.this, "Enter valid email!", Toast.LENGTH_SHORT).show();
                }
                else if (ValidationHelper.isTextEmpty(password1)) {
                    Toast.makeText(SignupActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                }
                else if (ValidationHelper.isPasswordMatch(password1, password2)){
                    Toast.makeText(SignupActivity.this, "Password do not match", Toast.LENGTH_SHORT).show();
                }
                else {
                    User user = new User();
                    user.setEmail(email);
                    user.setFirstName(firstName);
                    user.setLastName(lastName);

                    FirebaseAuthManger.signUpUser(context, user, password1  );

                }
            }
        });

        tvGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, LoginActivity.class));
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

    @Override
    protected void refreshUI() {

    }
}