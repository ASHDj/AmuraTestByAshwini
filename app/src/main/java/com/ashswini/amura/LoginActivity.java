package com.ashswini.amura;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private View btnLogin;
   FirebaseAuth auth;
    private ProgressDialog progressDialog;
    private EditText edtemail;
    private EditText edtpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_activity);
        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();
        btnLogin = findViewById(R.id.btnlogin);
     //   btnSignUp = findViewById(R.id.sign_up);
        edtemail = (EditText) findViewById(R.id.edt_username);
        edtpassword = (EditText) findViewById(R.id.edt_username);
        btnLogin.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case  R.id.btnlogin:
                String email,password;
                email=edtemail.getText().toString().trim();
                password=edtpassword.getText().toString().trim();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
                {

                }else {
                    authenticateUser(email, password);
                }

                break;
        }
    }
    private void authenticateUser(String email, String password) {
        showProcessDialog();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // When login failed
                        progressDialog.dismiss();
                        if (!task.isSuccessful()) {
                            AppUtil.showToast(LoginActivity.this, "Login error!");
                        } else {
                            //When login successful, redirect user to main activity
                            Intent intent = new Intent(LoginActivity.this, EventList.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }
    private void showProcessDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Logging in Firebase server...");
        progressDialog.show();
    }


}
