package com.softthenext.gymmanagementsystem.Pref;

import android.content.Context;
import android.content.SharedPreferences;

import com.softthenext.gymmanagementsystem.Model.PackageModel;

/**
 * Created by AABHALI on 26-12-2017.
 */

public class SharedPreferencesManager {

    private static SharedPreferencesManager mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "AdminLogin";

    private static final String KEY_USER_ID = "keyuserid";
    private static final String KEY_USER_NAME = "keyusername";
    private static final String KEY_USER_EMAIL = "keyuseremail";
    private static final String KEY_USER_GENDER = "keyusergender";
    private static final String KEY_USER_MONNO = "keyusermobno";
    private static final String KEY_USER_ADDRESS = "keyuseraddress";


    private SharedPreferencesManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPreferencesManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPreferencesManager(context);
        }
        return mInstance;
    }

    public boolean userLogin(PackageModel user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ID, user.getId());
        editor.putString(KEY_USER_NAME, user.getName());
        editor.putString(KEY_USER_EMAIL, user.getEmail());
        editor.putString(KEY_USER_GENDER, user.getGender());
        editor.putString(KEY_USER_MONNO, user.getMobno());
        editor.putString(KEY_USER_ADDRESS, user.getAddress());
        editor.apply();
        return true;
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USER_EMAIL, null) != null)
            return true;
        return false;
    }

    public PackageModel getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new PackageModel(
                sharedPreferences.getInt(KEY_USER_ID, 0),
                sharedPreferences.getString(KEY_USER_NAME, null),
                sharedPreferences.getString(KEY_USER_EMAIL, null),
                sharedPreferences.getString(KEY_USER_GENDER, null),
                sharedPreferences.getString(KEY_USER_MONNO,null),
                sharedPreferences.getString(KEY_USER_ADDRESS,null)
        );
    }

    public boolean logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
}
