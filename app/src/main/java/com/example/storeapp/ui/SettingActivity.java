package com.example.storeapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.storeapp.R;
import com.example.storeapp.db.DataBaseHelper;
import com.example.storeapp.model.Purchase;
import com.example.storeapp.ui.auth.ResetPasswordActivity;
import com.example.storeapp.utilis.AppSharedPreferences;

public class SettingActivity extends AppCompatActivity {
    private Button showPurchase, showLastPurchase, changePassword, clearPurchase, addNewItem;
    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        db = new DataBaseHelper(SettingActivity.this);
        initView();

    }

    private void initView() {
        showPurchase = findViewById(R.id.btn_show_purchase);
        showLastPurchase = findViewById(R.id.btn_last_purchase);
        changePassword = findViewById(R.id.btn_change_pass);
        clearPurchase = findViewById(R.id.btn_clear_purchase);
        addNewItem = findViewById(R.id.btn_add_item);
        initListeners();
        if (new AppSharedPreferences(SettingActivity.this).getUser().getAdmin() == 1) {
            addNewItem.setVisibility(View.VISIBLE);
        } else {
            addNewItem.setVisibility(View.GONE);

        }
    }

    private void initListeners() {
        addNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, AddItemActivity.class));
            }
        });
        showPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, AllPurchaseActivity.class));
            }
        });
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, ResetPasswordActivity.class));
            }
        });
        clearPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = db.deleteAllPurchases();
                if (num > 0) {
                    Toast.makeText(SettingActivity.this, "Deleted Success", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SettingActivity.this, "Deleted Failed, There are no Purchases", Toast.LENGTH_LONG).show();

                }
            }
        });
        showLastPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.getAllPurchase().size() > 0) {
                    Purchase purchase = db.getAllPurchase().get(db.getAllPurchase().size() - 1);
                    Toast.makeText(SettingActivity.this, purchase.getItem_name() + " , " + purchase.getDate(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SettingActivity.this, "Purchase is Empty", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}