package com.example.storeapp.ui.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.storeapp.R;
import com.example.storeapp.model.UserData;
import com.example.storeapp.ui.HomeActivity;
import com.example.storeapp.utilis.AppSharedPreferences;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin, btnRegister;
    private EditText mUserName, mPassword;
    private CheckBox cb_remember;
    private UserData userData;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userData = new AppSharedPreferences(LoginActivity.this).getUser();
        initView();

    }

    private void initView() {
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
        mUserName = (EditText) findViewById(R.id.et_username);
        mPassword = (EditText) findViewById(R.id.et_pass);
        cb_remember = findViewById(R.id.cb_remember);
        initListeners();

        if (userData != null) {
            mUserName.setText(userData.getUserName());
            mPassword.setText(userData.getPassword());
        }


    }

    private void initListeners() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

    }

    private void loginUser() {
        String userName = mUserName.getText().toString();
        String password = mPassword.getText().toString();
        if (userName.equals(userData.getUserName()) && password.equals(userData.getPassword())) {
            if (cb_remember.isChecked())
                new AppSharedPreferences(LoginActivity.this).setLogin(true);
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        } else {
            Toast.makeText(LoginActivity.this, "User Name Or Password is not correct", Toast.LENGTH_LONG).show();
        }
    }


}