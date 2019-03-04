package com.ashswini.amura;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register_activity extends AppCompatActivity {
    private View btnLogin;
    private View btnSignUp;
    private ProgressDialog progressDialog;
    private EditText email;
    private EditText password;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity);
        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();
        btnLogin = findViewById(R.id.login);
        btnSignUp = findViewById(R.id.sign_up);
        email = (EditText) findViewById(R.id.edt_username);
        password = (EditText) findViewById(R.id.edt_username);
        //go to Login Activity
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register_activity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //sign up a new account
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(email.getText().toString())) {
                    AppUtil.showToast(Register_activity.this, "Please input your email");
                } else if (TextUtils.isEmpty(password.getText().toString())) {
                    AppUtil.showToast(Register_activity.this, "Please input your password");
                } else {
                    //requesting Firebase server
                    showProcessDialog();
                    auth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                            .addOnCompleteListener(Register_activity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        AppUtil.showToast(Register_activity.this, task.toString()+"Register failed!");
                                    } else {
                                        AppUtil.showToast(Register_activity.this, "Register successful!");
                                       startActivity(new Intent(Register_activity.this, LoginActivity.class));
                                        progressDialog.dismiss();
                                      //  finish();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void showProcessDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Register");
        progressDialog.setMessage("Register a new account...");
        progressDialog.show();
    }

}
