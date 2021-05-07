package com.example.storeapp.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.storeapp.R;
import com.example.storeapp.utilis.AppSharedPreferences;

public class ResetPasswordActivity extends AppCompatActivity {
     Button btn_Reset;
     private EditText mPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        initView();
    }

    private void initView() {
     btn_Reset=findViewById(R.id.btn_resetPass);
     mPass=findViewById(R.id.edit_password);
     String pass=mPass.getText().toString();
     btn_Reset.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
           if (mPass.getText().toString().isEmpty()){
               Toast.makeText(ResetPasswordActivity.this,"Please enter Password",Toast.LENGTH_LONG).show();
           }else {
               new AppSharedPreferences(ResetPasswordActivity.this).setPassword(pass);
               Toast.makeText(ResetPasswordActivity.this,"Password Changed",Toast.LENGTH_LONG).show();
               finish();
           }
         }
     });
    }
}