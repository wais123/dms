package com.example.dms;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.dms.ui.MainActivity;
import com.example.dms.ui.login.LoginActivity;

import java.util.HashMap;

/**
 * Created by HP on 2/27/2018.
 */

public class SessionManager {
    public static final String PREF_NAME = "dms";
    public static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_USER_ID = "userId";
    public static final String KEY_NIP = "userNip";
    public static final String KEY_PASSWORD = "userPassword";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;

    public SessionManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    public void login() {
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return preferences.getBoolean(IS_LOGIN, false);
    }

    public void logoutUser(Activity activity) {
        editor.clear();
        editor.commit();

        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);
        activity.finish();


    }

    public void createLoginSession(String email, String usercode, String token) {
        editor.putString(KEY_NAME, email);
        editor.putString(KEY_EMAIL, usercode);
        editor.putString(KEY_USER_ID, token);
        editor.commit();
    }

    public void createRegister(String nip, String password) {
        editor.putString(KEY_NIP, nip);
        editor.putString(KEY_PASSWORD, password);
        editor.commit();
    }

    public void checkLogin() {
        if (!this.isLoggedIn()) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);
        }
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();

        //user
        user.put(KEY_NAME, preferences.getString(KEY_NAME, ""));
        user.put(KEY_EMAIL, preferences.getString(KEY_EMAIL, ""));
        user.put(KEY_USER_ID, preferences.getString(KEY_USER_ID, ""));

        return user;
    }

    public HashMap<String, String> getDataRegister() {
        HashMap<String, String> register = new HashMap<String, String>();

        //user
        register.put(KEY_NIP, preferences.getString(KEY_NIP, ""));
        register.put(KEY_PASSWORD, preferences.getString(KEY_PASSWORD, ""));

        return register;
    }


    public String getUserId() {
        return preferences.getString(KEY_USER_ID, "");
    }


}
