package com.example.storeapp.model;

public class Purchase {
    private int id;
    private int item_id;
    private int user_id;
    private String user_name;
    private String item_name;
    private int amount;
    private int totalPrice;
   private String date;

    public final static String TABLE_NAME = "purchase";
    public final static String COL_ID = "id";
    public final static String COL_ITEM_ID = "item_id";
    public final static String COL_USER_ID = "user_id";
    public final static String COL_USER_NAME = "user_name";
    public final static String COL_ITEM_NAME = "item_name";
    public final static String COL_AMOUNT = "amount";
    public final static String COL_TOTAL_PRICE = "total_price";
    public final static String COL_DATE = "date";

    ///
    public final static String STM_CREATE_TABLE = "CREATE TABLE ";

    public final static String STM_PRIMARY_KEY = "PRIMARY KEY ";
    public final static String STM_FOREIGN_KEY = " FOREIGN KEY ";
    public final static String STM_REFERENCES = " REFERENCES ";
    public final static String STM_TEXT = " TEXT ";
    public final static String STM_AUTOINCREMENT = " AUTOINCREMENT, ";

    public final static String STM_TEXT_PRIMARY_KEY = STM_TEXT + STM_PRIMARY_KEY;
    public final static String STM_NOT_NULL = " NOT NULL, ";
    public final static String STM_REAL = " REAL ";
    public final static String STM_INTEGER = " INTEGER ";

//    public static final String CREATE_PURCHASE_TABLE =
//            "CREATE TABLE " + TABLE_NAME + "("
//                    + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//                    + COL_USER_NAME + " TEXT NOT NULL,"
//                    + COL_ITEM_NAME + " TEXT NOT NULL,"
//                    + COL_AMOUNT + " INTEGER,"
//                    + COL_DATE + " TEXT NOT NULL,"
//                    + COL_ITEM_ID + " INTEGER," + " FOREIGN KEY ("+COL_ITEM_ID+") REFERENCES "+Item.TABLE_NAME+"("+Item.COL_ID+"),"
//                    + COL_USER_ID + " INTEGER," + " FOREIGN KEY ("+COL_USER_ID+") REFERENCES "+UserData.TABLE_NAME+"("+UserData.COL_ID+")" +
//                    ")";

    public static final String CREATE_PURCHASE_TABLE = STM_CREATE_TABLE + TABLE_NAME + " ( " +
            COL_ID + STM_TEXT_PRIMARY_KEY + ", " +
            COL_USER_NAME + STM_TEXT + STM_NOT_NULL +
            COL_ITEM_NAME + STM_TEXT + STM_NOT_NULL +
            COL_AMOUNT + STM_INTEGER + ", " +
            COL_TOTAL_PRICE + STM_INTEGER + ", " +
            COL_DATE + STM_TEXT + STM_NOT_NULL +
            COL_ITEM_ID + STM_INTEGER + ", " +
            COL_USER_ID + STM_INTEGER + ", " +
            STM_FOREIGN_KEY + "(" + COL_ITEM_ID + ")" + STM_REFERENCES + Item.TABLE_NAME + "(" + Item.COL_ID + "), " +
            STM_FOREIGN_KEY + "(" + COL_USER_ID + ")" + STM_REFERENCES + UserData.TABLE_NAME + "(" + UserData.COL_ID + ") "
            + ");";


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int Item_id) {
        this.item_id = Item_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String Item_name) {
        this.item_name = Item_name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}


