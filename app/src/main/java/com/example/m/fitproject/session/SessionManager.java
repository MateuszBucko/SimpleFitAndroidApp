package com.example.m.fitproject.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.m.fitproject.LoginActivity;

import java.util.HashMap;

/**
 * Created by M on 28.12.2016.
 */

public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "AndroidHivePref";

    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_USERNAME = "username";

    public static final String KEY_ID = "id";

    public SessionManager(Context context) {
        this._context = context;
        sharedPreferences = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(String username, Long id) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USERNAME, username);
        editor.putLong(KEY_ID, id);
        editor.commit();
    }

    //TODO napisać funkcję przekierowującą gdy użytkownik nie jest zalogowany
    public void checkLogin() {

    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_USERNAME, sharedPreferences.getString(KEY_USERNAME, null));
        Long userId = sharedPreferences.getLong(KEY_ID, 0);
        user.put(KEY_ID, userId.toString());


        return user;
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();

        Intent i = new Intent(_context, LoginActivity.class);

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        
        _context.startActivity(i);
    }



    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }
}
