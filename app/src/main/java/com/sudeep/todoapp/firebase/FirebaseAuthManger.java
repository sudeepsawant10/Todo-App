package com.sudeep.todoapp.firebase;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.sudeep.todoapp.LoginActivity;
import com.sudeep.todoapp.User;

public class FirebaseAuthManger {
    private static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    public static void signUpUser(Context context, User user, String password1) {
        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), password1)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //sign in success update user data in db
                            FirebaseUser firebaseUser =firebaseAuth.getCurrentUser();
                            user.setUser_id(firebaseUser.getUid());

                            FirebaseDbManger.addUserToDb(context, firebaseUser, user, new FirebaseDbManger.FirebaseDbCallbackInterface() {
                                @Override
                                public void onComplete(Object object) {
                                    Toast.makeText(context, "Signup successful", Toast.LENGTH_SHORT).show();
                                    context.startActivity(new Intent(context, LoginActivity.class));
                                }

                                @Override
                                public void onError() {
                                    Toast.makeText(context, "Signup failed!", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCancel() {
                                    Toast.makeText(context, "Signup failed!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else {
//                           if user already exists in db
                            Exception exception = task.getException();
                            if (exception instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(context, "Account already exists!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(context, "Signup failed!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
    }
}
