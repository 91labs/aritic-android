package com.library.aritic.SharedPref;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.library.aritic.Aritic;

public class SharedPref {
    private static SharedPreferences sharedPreferences;
    public static final String EMAIL = "aritic_email";
    public static final String USERID = "aritic_userid";
    public static final String PHONE = "aritic_phone";
    public static final String DEVICE_TOKEN = "aritic_device_token";


    public static void init(Context context)
    {
        if(sharedPreferences == null)
            sharedPreferences = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
    }

    public static String getValue(String key) {
        return sharedPreferences.getString(key, "");
    }
    public static void updateOrInsertValue(String key, String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(key, value);
        Log.d("Register: ", "saving Token in Shard Prefs");
        prefsEditor.apply();
    }
}
