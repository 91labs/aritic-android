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
//    public static final String BASE_URL = "\"https://sprint.ctrmv.com/\"";
    public static final String BASE_URL = "base_url";
    public static final String PAUSE_INAPP = "inApp";


    public static void init(Context context)
    {
        if(sharedPreferences == null)
            sharedPreferences = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
    }

    public static Boolean getInAppDisplayBoolean() {
        return sharedPreferences.getBoolean(PAUSE_INAPP, true);
    }

    public static  void setInAppDisplayBoolean(Boolean display) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putBoolean(PAUSE_INAPP, display);
        Log.d("Register: ", "saving Token in Shard Prefs");
        prefsEditor.apply();
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
