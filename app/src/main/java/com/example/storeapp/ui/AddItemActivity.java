package com.example.storeapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;
import com.example.storeapp.R;
import com.example.storeapp.db.DataBaseHelper;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddItemActivity extends AppCompatActivity {
    private static final int RESULT_LOAD_IMG =1 ;
    ImageView img_item, pickImg;
    EditText mItemName, mItemPrice, mItemDescrip;
    RadioButton rb_cash, rb_installment;
    Button btn_save;
    String paymentType;
    DataBaseHelper db;
     Uri imageUri;
    byte[] byteArray;
    private static final String TAG = "AddItemActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        db = new DataBaseHelper(AddItemActivity.this);
        initView();
    }

    private void initView() {
        img_item = findViewById(R.id.img_item);
        pickImg = findViewById(R.id.pick_img);
        mItemName = findViewById(R.id.et_itemName);
        mItemPrice = findViewById(R.id.et_itemPrice);
        mItemDescrip = findViewById(R.id.et_itemDescription);
        rb_cash = findViewById(R.id.cash);
        rb_installment =findViewById(R.id.installment);
        btn_save = findViewById(R.id.btn_save);
        initListeners();
    }

    private void initListeners() {
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });
        pickImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        });
    }

    private void addItem() {
        String name = mItemName.getText().toString();
        String price = mItemPrice.getText().toString();
        String description = mItemDescrip.getText().toString();
        if (rb_cash.isChecked()) {
            paymentType = rb_cash.getText().toString();
            Log.e(TAG, "addItem: " + paymentType);
        } else if (rb_installment.isChecked()) {
            paymentType = rb_installment.getText().toString();
        }


        if (byteArray != null  && byteArray.length > 0){
            if (validForm(byteArray, name, price, description, paymentType)) {
                Boolean isAdd= db.addItem(byteArray,name,price,description,paymentType);
                Log.e(TAG, "addItem into DB: " + isAdd);
                Toast.makeText(AddItemActivity.this,"Added Success",Toast.LENGTH_LONG).show();
                finish();
            }
        }else {
            Toast.makeText(AddItemActivity.this, "Please Select Photo", Toast.LENGTH_LONG).show();
        }


    }

    private boolean validForm(byte[] image, String name, String price, String description, String paymentType) {
        boolean valid = true;
        if (name.isEmpty()) {
            mItemName.setError("name is required");
            mItemName.requestFocus();
            valid = false;
        } else if (price.isEmpty()) {
            mItemPrice.setError("Price is required");
            mItemPrice.requestFocus();
            valid = false;
        } else if (description.isEmpty()) {
            mItemDescrip.setError("Description is required");
            mItemDescrip.requestFocus();
            valid = false;
        } else if (paymentType.isEmpty()) {
            Toast.makeText(AddItemActivity.this, "Please Select Payment Type", Toast.LENGTH_LONG).show();
            valid = false;
        }
        return valid;

    }
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                imageUri = data.getData();
                Log.e(TAG, "onActivityResult: "+imageUri );
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                img_item.setImageBitmap(selectedImage);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                selectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byteArray = stream.toByteArray();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(AddItemActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(AddItemActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
}