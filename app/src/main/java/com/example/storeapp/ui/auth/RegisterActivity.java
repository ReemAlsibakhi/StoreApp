package com.example.storeapp.ui.auth;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.storeapp.R;
import com.example.storeapp.db.DataBaseHelper;
import com.example.storeapp.model.UserData;
import com.example.storeapp.ui.HomeActivity;
import com.example.storeapp.utilis.AppSharedPreferences;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.ByteBuffer;


public class RegisterActivity extends AppCompatActivity {
    private static final int RESULT_LOAD_IMG =1 ;
    private Button btnSave;
    EditText mFullName, mUserName, mEmail, mPass, mAddress, mRePass, mPhone;
    TextView mBirthDate;
    Spinner sp_country;
    CheckBox cb_IsAdmin;
    RadioGroup rg_gender;
    RadioButton rb_male, rb_female;
    String gender;
    int isAdmin = 0;
    ImageView pickImage, imgDatePicker,imgProfile;
    String country;
    private int mYear, mMonth, mDay;
    DataBaseHelper db;
    private static final String TAG = "RegisterActivity";
    private int RESULT_LOAD_IMAGE=1;
    byte[] byteArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        checkIsLogin();
        initView();
        initSpinnerCountry();
    }

    private void checkIsLogin() {
     if (new AppSharedPreferences(RegisterActivity.this).isLogin()){
         Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
         startActivity(intent);
         finish();
     }
    }

    private void initSpinnerCountry() {
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.country));
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        sp_country.setAdapter(dataAdapter);
        sp_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                country = parent.getItemAtPosition(position).toString();
                Log.e(TAG, "onItemSelected: " + country);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initListener() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });
        imgDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                mBirthDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        pickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }});


    }

    private void addUser() {
        String name = mFullName.getText().toString();
        String username = mUserName.getText().toString();
        String email = mEmail.getText().toString();
        String password = mPass.getText().toString();
        String rePassword = mRePass.getText().toString();
        String phone = mPhone.getText().toString();
        String address = mAddress.getText().toString();
        String birth_date = mBirthDate.getText().toString();
        if (rb_male.isChecked()) {
            gender = "male";
        } else if (rb_female.isChecked()) {
            gender = "female";
        }
        if (cb_IsAdmin.isChecked()) {
            isAdmin = 1;
        } else {
            isAdmin = 0;
        }
        if (!validForm(name, username, email, password, rePassword, phone, address, gender, country, birth_date)) {
            return;
        }
        Boolean isAdd = db.addUser(byteArray, name, username, email, password, country, phone, birth_date, address, gender, isAdmin);
        new AppSharedPreferences(RegisterActivity.this).setUserData(new UserData(username, name, email, password, birth_date, country, gender, byteArray, phone, address, isAdmin));
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        Log.e(TAG, "addUser into DB: " + isAdd);
    }


    private boolean validForm(String name, String username, String email, String password, String rePassword,
                              String phone, String address, String gender, String country, String birth_date) {
        boolean valid = true;
        if (name.isEmpty()) {
            mFullName.setError("name is required");
            mFullName.requestFocus();
            valid = false;
        } else if (email.isEmpty()) {
            mEmail.setError("Email is required");
            mEmail.requestFocus();
            valid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("Email is not valid");
            mEmail.requestFocus();
            valid = false;
        } else if (username.isEmpty()) {
            mUserName.setError("user name is required");
            mUserName.requestFocus();
            valid = false;
        } else if (password.isEmpty()) {
            mPass.setError("password is required");
            mPass.requestFocus();
            valid = false;
        } else if (rePassword.isEmpty()) {
            mRePass.setError("Re Pass is required");
            mRePass.requestFocus();
            valid = false;}
        else if (!password.equals(rePassword)) {
            mRePass.setError("Password are not match");
            mRePass.requestFocus();
            valid = false;
        } else if (country == null) {
            Toast.makeText(RegisterActivity.this, "Select Country", Toast.LENGTH_LONG).show();
            valid = false;
        } else if (phone.isEmpty()) {
            mPhone.setError("Phone is required");
            mPhone.requestFocus();
            valid = false;
        } else if (birth_date.isEmpty()) {
            mBirthDate.setError("Birth Date is required");
            mBirthDate.requestFocus();
            valid = false;
        } else if (address.isEmpty()) {
            mAddress.setError("address is required");
            mAddress.requestFocus();
            valid = false;

        } else if (gender == null) {
            Toast.makeText(RegisterActivity.this, "gender is required", Toast.LENGTH_LONG).show();
            valid = false;

        }
        return valid;

    }
    private void initView() {
        mFullName = findViewById(R.id.et_fullName);
        mUserName = findViewById(R.id.et_userName);
        mEmail = findViewById(R.id.et_email);
        mPass = findViewById(R.id.et_password);
        mRePass = findViewById(R.id.et_re_pass);
        mAddress = findViewById(R.id.et_address);
        mPhone = findViewById(R.id.et_phone);
        mBirthDate = findViewById(R.id.tv_birthDate);
        cb_IsAdmin = findViewById(R.id.cb_admin);
        rg_gender = findViewById(R.id.rg_gender);
        rb_female = findViewById(R.id.female);
        rb_male = findViewById(R.id.male);
        sp_country = findViewById(R.id.sp_country);
        btnSave = findViewById(R.id.btn_save);
        imgDatePicker = findViewById(R.id.date_picker);
        pickImage=findViewById(R.id.pick_img);
        imgProfile=findViewById(R.id.img_profile);
        db = new DataBaseHelper(RegisterActivity.this);
        initListener();
    }
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imgProfile.setImageBitmap(selectedImage);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                selectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byteArray = stream.toByteArray();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(RegisterActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(RegisterActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }}