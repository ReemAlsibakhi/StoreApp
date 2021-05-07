package com.example.storeapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
    private int catId;
    private byte[] image;
    private String name;
    private String price;
    private String detail;
    private String paymentType;

    public final static String TABLE_NAME = "Category";
    public final static String COL_ID = "id";
    public final static String COL_IMG = "image";
    public final static String COL_NAME = "name";
    public final static String COL_PRICE = "price";
    public final static String COL_DETAIL = "detail";
    public final static String COL_PAYMENT_TYPE = "payType";

    public static final String CREATE_CATEGORY_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COL_IMG + " BLOB,"
                    + COL_NAME + " TEXT NOT NULL,"
                    + COL_PRICE + " TEXT NOT NULL,"
                    + COL_DETAIL + " TEXT NOT NULL,"
                    + COL_PAYMENT_TYPE + " TEXT NOT NULL"
                    + ")";

    public Item() {

    }

    public Item(int catId, byte[] image, String name, String price, String detail, String paymentType) {
        this.catId = catId;
        this.image = image;
        this.name = name;
        this.price = price;
        this.detail = detail;
        this.paymentType = paymentType;
    }

    protected Item(Parcel in) {
        catId = in.readInt();
        image = in.createByteArray();
        name = in.readString();
        price = in.readString();
        detail = in.readString();
        paymentType = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(catId);
        dest.writeByteArray(image);
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(detail);
        dest.writeString(paymentType);
    }
}
