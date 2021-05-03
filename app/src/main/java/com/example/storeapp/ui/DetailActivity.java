package com.example.storeapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.storeapp.R;
import com.example.storeapp.db.DataBaseHelper;
import com.example.storeapp.model.Item;
import com.example.storeapp.utilis.AppSharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {
    TextView mName,mDetail,mPrice,mTotalPrice,mAmount;
    ImageView imgCategory,mPlus,mMinus;
    int amount;
    Button btn_save;
    DataBaseHelper db;
    String userName;
    private static final String TAG = "DetailActivity";
    int totalPrice;
    int user_id,item_id;
    String formattedDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        db = new DataBaseHelper(DetailActivity.this);
        userName=new AppSharedPreferences(DetailActivity.this).getUser().getUserName();
        user_id=new AppSharedPreferences(DetailActivity.this).getUser().getId();
        Intent i=getIntent();
        initView();
        if (i!=null){
            mName.setText(i.getStringExtra("name"));
            mDetail.setText(i.getStringExtra("detail"));
            mPrice.setText(i.getStringExtra("price"));
            item_id=i.getIntExtra("id",-1);
        }
        amount =Integer.parseInt(mAmount.getText().toString());
        int price=Integer.parseInt(mPrice.getText().toString());
        totalPrice=amount*price;
        mTotalPrice.setText(String.valueOf(totalPrice));
        Log.e(TAG, "onCreate: user,item id "+user_id+item_id );
    }

    private void initListener() {
      mMinus.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             amount--;
             mAmount.setText(String.valueOf(amount));
             totalPrice=Integer.parseInt(mPrice.getText().toString())*amount;
             mTotalPrice.setText(String.valueOf(totalPrice));
         }
     });
     mPlus.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             amount++;
             mAmount.setText(String.valueOf(amount));
              totalPrice=Integer.parseInt(mPrice.getText().toString())*amount;
             mTotalPrice.setText(String.valueOf(totalPrice));
         }
     });
     btn_save.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             getCurrentDate();
             Boolean isAdd= db.addPurchase(item_id,user_id,userName,mName.getText().toString(),amount,formattedDate,totalPrice);
             Log.e(TAG, "addPurchase into DB: " + isAdd);
             Toast.makeText(DetailActivity.this,"Success",Toast.LENGTH_LONG).show();
             finish();
         }
     });

    }
    private void getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        formattedDate = df.format(c);
        Log.e(TAG, "getCurrentDate: "+ formattedDate);
    }

    private void initView() {
        mName=findViewById(R.id.tv_name);
        mDetail=findViewById(R.id.tv_description);
        mPrice=findViewById(R.id.tv_price);
        mTotalPrice=findViewById(R.id.tv_totalPrice);
        mAmount=findViewById(R.id.tv_num);
        mPlus=findViewById(R.id.plus);
        mMinus=findViewById(R.id.minus);
        imgCategory=findViewById(R.id.img_category);
        btn_save=findViewById(R.id.btn_save);
        initListener();
    }
}