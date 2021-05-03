package com.example.storeapp.model;

public class UserData {
    private int id;
    private String userName;
    private String fullName;
    private String email;
    private String password;
    private String birthDate;
    private String country;
    private String gender;
    private byte[] img;
    private String mobile;
    private String address;
    private int isAdmin;
    public static final String TABLE_NAME = "Users";
    public static final String COL_ID = "id";
    public static final String COL_IMG = "image";
    public static final String COL_USER_NAME = "userName";
    public static final String COL_FULL_NAME = "fullName";
    public static final String COL_EMAIL = "email";
    public static final String COL_PASSWORD = "password";
    public static final String COL_BIRTH_DATE = "birthDate";
    public static final String COL_COUNTRY = "country";
    public static final String COL_GENDER = "gender";
    public static final String COL_MOBILE = "mobile";
    public static final String COL_ADDRESS = "address";
    public static final String COL_IS_ADMIN = "isAdmin";

    public static final String CREATE_USER_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COL_IMG + " BLOB,"
                    + COL_USER_NAME + " TEXT NOT NULL,"
                    + COL_FULL_NAME + " TEXT NOT NULL,"
                    + COL_EMAIL + " TEXT NOT NULL,"
                    + COL_PASSWORD + " TEXT NOT NULL,"
                    + COL_BIRTH_DATE + " TEXT NOT NULL,"
                    + COL_COUNTRY + " TEXT NOT NULL,"
                    + COL_GENDER + " TEXT NOT NULL,"
                    + COL_MOBILE + " TEXT NOT NULL,"
                    + COL_ADDRESS + " TEXT NOT NULL,"
                    + COL_IS_ADMIN + " INTEGER"
                    + ")";

    public UserData( String userName, String fullName, String email,
                     String password, String birthDate, String country, String gender,
                     byte[] img, String mobile, String address, int isAdmin) {
        this.userName = userName;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.country = country;
        this.gender = gender;
        this.img = img;
        this.mobile = mobile;
        this.address = address;
        this.isAdmin = isAdmin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAdmin() {
        return isAdmin;
    }

    public void setAdmin(int admin) {
        isAdmin = admin;
    }
}
