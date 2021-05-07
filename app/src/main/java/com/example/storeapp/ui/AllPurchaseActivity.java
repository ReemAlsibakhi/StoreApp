package com.example.storeapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import com.example.storeapp.R;
import com.example.storeapp.adapter.ItemAdapter;
import com.example.storeapp.adapter.PurchaseAdapter;
import com.example.storeapp.db.DataBaseHelper;
import com.example.storeapp.model.Item;
import com.example.storeapp.model.Purchase;
import java.util.ArrayList;
import java.util.List;

public class AllPurchaseActivity extends AppCompatActivity {
    List<Purchase> purchaseList=new ArrayList<>();
    private RecyclerView rvCategory;
    private PurchaseAdapter adapter;
    DataBaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_purchase);
        db = new DataBaseHelper(AllPurchaseActivity.this);
        initView();
    }


    private void buildRecy() {
        adapter = new PurchaseAdapter(purchaseList, getApplicationContext());
        rvCategory.setAdapter(adapter);
    }

    private void initView() {
        rvCategory = findViewById(R.id.cv_purchase);
        rvCategory.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        purchaseList=db.getAllPurchase();
        buildRecy();
        super.onResume();
    }
}