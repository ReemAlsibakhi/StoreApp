package com.example.storeapp.utilis;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.storeapp.model.UserData;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class AppSharedPreferences {
    private SharedPreferences pref;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context ctx;
    private int PRIVATE_MODE = 0;
    private String PREFERENCE_NAME = "configuration";
    private static final String TAG = "AppSharedPreferences";

    public AppSharedPreferences(Context context) {
        preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
        ctx = context;
    }

    public void setLogin(Boolean isLogin) {
        Log.e(TAG, "setLogin: "+isLogin+"" );
        editor.putBoolean("isLogin", isLogin);
        editor.commit();

    }

    public Boolean isLogin() {
        return preferences.getBoolean("isLogin", false);
    }

    public void setUserData(UserData user) {
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString("User", json);
        Log.e(TAG, "setUserData: " + user.getFullName());
        editor.commit();

    }

    public UserData getUser() {
        Gson gson = new Gson();
        String json = preferences.getString("User", "a");
        UserData obj = gson.fromJson(json, UserData.class);
        return obj;
    }
    public String getPass() {
        return preferences.getString("pass", "p");
    }

    public void setPassword(String password) {
        editor.putString("pass", password);
        Log.e(TAG, "password: " + password);
        editor.commit();
    }
}
