package com.example.storeapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.storeapp.model.Item;
import com.example.storeapp.model.Purchase;
import com.example.storeapp.model.UserData;

import java.util.ArrayList;
import java.util.List;

import static android.icu.text.MessagePattern.ArgType.SELECT;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "storeDB";
    private static final int DATABASE_VERSION = 1;

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Item.CREATE_CATEGORY_TABLE);
        db.execSQL(UserData.CREATE_USER_TABLE);
        db.execSQL(Purchase.CREATE_PURCHASE_TABLE);
//        db.execSQL("INSERT INTO " + Item.TABLE_NAME + " (" +
//                Item.COL_IMG + ", " +
//                Item.COL_NAME + ", " +
//                Item.COL_PRICE + ", " +
//                Item.COL_DETAIL + ", " +
//                Item.COL_PAYMENT_TYPE +
//                ") VALUES('1', 'Pizza', '10','aaa','cash')");
//        db.execSQL("INSERT INTO " + Item.TABLE_NAME + " (" +
//                Item.COL_IMG + ", " +
//                Item.COL_NAME + ", " +
//                Item.COL_PRICE + ", " +
//                Item.COL_DETAIL + ", " +
//                Item.COL_PAYMENT_TYPE +
//                ") VALUES('R.drawable.pizza', 'Pizza', '9','ccc','cash')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + UserData.TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    public Boolean addUser(byte[] image, String fullName, String userName, String email, String password, String country,
                           String phone, String birthDate, String address, String gender, int isAdmin) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(UserData.COL_IMG, image);
        cv.put(UserData.COL_FULL_NAME, fullName);
        cv.put(UserData.COL_USER_NAME, userName);
        cv.put(UserData.COL_EMAIL, email);
        cv.put(UserData.COL_PASSWORD, password);
        cv.put(UserData.COL_COUNTRY, country);
        cv.put(UserData.COL_MOBILE, phone);
        cv.put(UserData.COL_BIRTH_DATE, birthDate);
        cv.put(UserData.COL_ADDRESS, address);
        cv.put(UserData.COL_GENDER, gender);
        cv.put(UserData.COL_IS_ADMIN, isAdmin);
        long id = db.insert(UserData.TABLE_NAME, null, cv);
        // close db connection
        db.close();
        return id > 0;
    }

    public Boolean addItem(byte[] image, String name, String price, String detail, String payment) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Item.COL_IMG, image);
        cv.put(Item.COL_NAME, name);
        cv.put(Item.COL_PRICE, price);
        cv.put(Item.COL_DETAIL, detail);
        cv.put(Item.COL_PAYMENT_TYPE, payment);
        long id = db.insert(Item.TABLE_NAME, null, cv);
        // close db connection
        db.close();
        return id > 0;
    }

    public List<Item> getAllCategory() {
        List<Item> items = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + Item.TABLE_NAME + " ORDER BY " + Item.COL_ID + " ASC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Item category = new Item();
                category.setCatId(cursor.getInt(cursor.getColumnIndex(Item.COL_ID)));
                category.setImage(cursor.getBlob(cursor.getColumnIndex(Item.COL_IMG)));
                category.setName(cursor.getString(cursor.getColumnIndex(Item.COL_NAME)));
                category.setPrice(cursor.getString(cursor.getColumnIndex(Item.COL_PRICE)));
                category.setDetail(cursor.getString(cursor.getColumnIndex(Item.COL_DETAIL)));
                category.setPaymentType(cursor.getString(cursor.getColumnIndex(Item.COL_PAYMENT_TYPE)));
                items.add(category);
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();
        // return notes list
        return items;
    }
    public Item getItem(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Item.TABLE_NAME,
                new String[]{Item.COL_ID, Item.COL_IMG, Item.COL_NAME,Item.COL_PRICE,Item.COL_DETAIL,Item.COL_PAYMENT_TYPE},
                Item.COL_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        Item item = new Item(
                cursor.getInt(cursor.getColumnIndex(Item.COL_ID)),
                cursor.getBlob(cursor.getColumnIndex(Item.COL_IMG)),
                cursor.getString(cursor.getColumnIndex(Item.COL_NAME)),
                cursor.getString(cursor.getColumnIndex(Item.COL_PRICE)),
                cursor.getString(cursor.getColumnIndex(Item.COL_DETAIL)),
                cursor.getString(cursor.getColumnIndex(Item.COL_PAYMENT_TYPE)));
        // close the db connection
        cursor.close();
        return item;
    }
    public Boolean addPurchase(int item_id, int user_id, String userName, String itemName, int amount, String date, int totalPrice) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Purchase.COL_ITEM_ID, item_id);
        cv.put(Purchase.COL_USER_ID, user_id);
        cv.put(Purchase.COL_USER_NAME, userName);
        cv.put(Purchase.COL_ITEM_NAME, itemName);
        cv.put(Purchase.COL_AMOUNT, amount);
        cv.put(Purchase.COL_DATE, date);
        cv.put(Purchase.COL_TOTAL_PRICE, totalPrice);
        long id = db.insert(Purchase.TABLE_NAME, null, cv);
        // close db connection
        db.close();
        return id > 0;
    }

    public List<Purchase> getAllPurchase() {
        List<Purchase> list = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + Purchase.TABLE_NAME + " ORDER BY " + Purchase.COL_ID + " ASC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Purchase purchase = new Purchase();
                purchase.setId(cursor.getInt(cursor.getColumnIndex(Purchase.COL_ID)));
                purchase.setItem_name(cursor.getString(cursor.getColumnIndex(Purchase.COL_ITEM_NAME)));
                purchase.setDate(cursor.getString(cursor.getColumnIndex(Purchase.COL_DATE)));
                purchase.setTotalPrice(cursor.getInt(cursor.getColumnIndex(Purchase.COL_TOTAL_PRICE)));
                list.add(purchase);
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();
        // return notes list
        return list;
    }

    public int deleteAllPurchases() {
        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("delete from "+ Purchase.TABLE_NAME);
        int values = db.delete(Purchase.TABLE_NAME, null, null);
        return values;
    }
}
