package com.example.storeapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.storeapp.R;
import com.example.storeapp.adapter.ItemAdapter;
import com.example.storeapp.db.DataBaseHelper;
import com.example.storeapp.model.Item;
import com.example.storeapp.ui.auth.RegisterActivity;
import com.example.storeapp.utilis.AppSharedPreferences;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    List<Item> catList=new ArrayList<>();
    private RecyclerView rvCategory;
    private ItemAdapter itemAdapter;
    DataBaseHelper db;
    EditText mSearch;
    RadioGroup radioGroup;
    RadioButton rb_cash, rb_installment;
    private static final String TAG = "HomeActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        db = new DataBaseHelper(HomeActivity.this);
        initView();
    }

    private void fillList() {
//     catList.add(new Item(0,R.drawable.pizza,"Pizza","9","aaaaaaa","Cash"));
//     catList.add(new Item(1,R.drawable.pizza,"Pizza","9","aaaaaaa","Cash"));
//     catList.add(new Item(2,R.drawable.pizza,"Pizza","9","aaaaaaa","Cash"));
//     catList.add(new Item(3,R.drawable.pizza,"Pizza","9","aaaaaaa","Cash"));
//     catList.add(new Item(4,R.drawable.pizza,"Pizza","9","aaaaaaa","Cash"));
//     catList.add(new Item(5,R.drawable.pizza,"Pizza","9","aaaaaaa","Cash"));
//     catList.add(new Item(6,R.drawable.pizza,"Pizza","9","aaaaaaa","Cash"));
    }

    private void buildRecy() {
        itemAdapter = new ItemAdapter(catList, getApplicationContext());
        rvCategory.setAdapter(itemAdapter);
        itemAdapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onClicked(int id) {
                Intent i = new Intent(HomeActivity.this, DetailActivity.class);
                i.putExtra("id",id);
                startActivity(i);
            }
        });
    }

    private void initView() {
        rvCategory = findViewById(R.id.recy_category);
        rvCategory.setLayoutManager(new LinearLayoutManager(this));
        mSearch=findViewById(R.id.et_search);
        rb_cash = findViewById(R.id.cash);
        rb_installment =findViewById(R.id.installment);
        radioGroup=findViewById(R.id.radio);
        initListeners();
    }

    private void initListeners() {
        searchListeners();
        checkBoxListeners();

    }

    private void searchListeners() {
        mSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e(TAG, "afterTextChanged: "+s );
                filter(s.toString());
            }
        });
    }

    private void checkBoxListeners() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radio = findViewById(checkedId);
                Toast.makeText(HomeActivity.this,radio.getText().toString(), Toast.LENGTH_SHORT).show();
                filterPay(radio.getText().toString());
            }
        });
        rb_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterPay(rb_cash.getText().toString());
            }
        });
        if (rb_cash.isChecked()) {
            filterPay(rb_cash.getText().toString());
        } else if (rb_installment.isChecked()) {
            filterPay(rb_installment.getText().toString());
        }
    }

    private void filter(String text) {
        ArrayList<Item> filteredList = new ArrayList<>();
       for (Item item : catList) {
            Log.e(TAG, "filter: 0000"+text );
            if (item.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);

            }
            itemAdapter.filterList(filteredList);
        }

    }
    private void filterPay(String text) {
        Log.e(TAG, "filterPay: "+text );
        ArrayList<Item> filteredList = new ArrayList<>();
        for (Item item : catList) {
            if (item.getPaymentType().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
            itemAdapter.filterList(filteredList);
        }

    }

    @Override
    protected void onResume() {
        catList=db.getAllCategory();
        buildRecy();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_setting: {
                startActivity(new Intent(HomeActivity.this,SettingActivity.class));
                return true;
            }
            case R.id.action_logout: {
                logout();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {
        new AppSharedPreferences(HomeActivity.this).setLogin(false);
        Intent intent = new Intent(HomeActivity.this, RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }
}