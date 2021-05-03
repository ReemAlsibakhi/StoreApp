package com.example.storeapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.example.storeapp.R;
import com.example.storeapp.adapter.ItemAdapter;
import com.example.storeapp.db.DataBaseHelper;
import com.example.storeapp.model.Item;
import com.example.storeapp.ui.auth.RegisterActivity;
import com.example.storeapp.utilis.AppSharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    List<Item> catList=new ArrayList<>();
    private RecyclerView rvCategory;
    private ItemAdapter itemAdapter;
    DataBaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        db = new DataBaseHelper(HomeActivity.this);
      //  catList=db.getAllCategory();
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
            public void onClicked(Item category) {
                Intent i = new Intent(HomeActivity.this, DetailActivity.class);
                i.putExtra("id",category.getCatId());
                i.putExtra("name",category.getName());
                i.putExtra("price",category.getPrice());
                i.putExtra("detail",category.getDetail());
                i.putExtra("payment",category.getPaymentType());
                startActivity(i);

            }
        });
    }

    private void initView() {
        rvCategory = findViewById(R.id.recy_category);
        rvCategory.setLayoutManager(new LinearLayoutManager(this));
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