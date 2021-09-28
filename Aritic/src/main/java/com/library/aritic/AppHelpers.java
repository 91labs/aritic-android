package com.library.aritic;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;

import java.util.regex.Pattern;

public class AppHelpers {




    public enum SchemaType {
        DATA("data"),
        HTTPS("https"),
        HTTP("http"),
        ;

        private final String text;

        SchemaType(final String text) {
            this.text = text;
        }

        public static SchemaType fromString(String text) {
            for (SchemaType type : SchemaType.values()) {
                if (type.text.equalsIgnoreCase(text)) {
                    return type;
                }
            }
            return null;
        }
    }

    static boolean areNotificationsEnabled(Context context) {
        try {
            return NotificationManagerCompat.from(context).areNotificationsEnabled();
        } catch (Throwable t) {}

        return true;
    }


    static boolean hasFCMLibrary() {
        try {
            com.google.firebase.messaging.FirebaseMessaging.class.getName();
            return true;
        } catch (NoClassDefFoundError e) {
            return false;
        }
    }


    Integer checkForGooglePushLibrary() {
        boolean hasFCMLibrary = hasFCMLibrary();

        if (!hasFCMLibrary) {
//            Log(OneSignal.LOG_LEVEL.FATAL, "The Firebase FCM library is missing! Please make sure to include it in your project.");
//            return UserState.PUSH_STATUS_MISSING_FIREBASE_FCM_LIBRARY;
        }

        return null;
    }



    static void openURLInBrowser(@NonNull String url) {
        openURLInBrowser(Uri.parse(url.trim()));
    }

    private static void openURLInBrowser(@NonNull Uri uri) {
        SchemaType type = uri.getScheme() != null ? SchemaType.fromString(uri.getScheme()) : null;
        if (type == null) {
            type = SchemaType.HTTP;
            if (!uri.toString().contains("://")) {
                uri = Uri.parse("http://" + uri.toString());
            }
        }
        Intent intent;
        switch (type) {
            case DATA:
                intent = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_BROWSER);
                intent.setData(uri);
                break;
            case HTTPS:
            case HTTP:
            default:
                intent = new Intent(Intent.ACTION_VIEW, uri);
                break;
        }
        intent.addFlags(
                Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
//        con.startActivity(intent);
    }


    static int getTargetSdkVersion(Context context) {
        String packageName = context.getPackageName();
        PackageManager packageManager = context.getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            return applicationInfo.targetSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1;
    }

    public static boolean isValidResourceName(String name) {
        return (name != null && !name.matches("^[0-9]"));
    }



    static Bundle getManifestMetaBundle(Context context) {
        ApplicationInfo ai;
        try {
            ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return ai.metaData;
        } catch (PackageManager.NameNotFoundException e) {
            AriticLogger.Log("Exception in Manifest Meta Bundle");
        }

        return null;
    }

    static boolean getManifestMetaBoolean(Context context, String metaName) {
        Bundle bundle = getManifestMetaBundle(context);
        if (bundle != null) {
            return bundle.getBoolean(metaName);
        }

        return false;
    }

    static String getManifestMeta(Context context, String metaName) {
        Bundle bundle = getManifestMetaBundle(context);
        if (bundle != null) {
            return bundle.getString(metaName);
        }

        return null;
    }

    static String getResourceString(Context context, String key, String defaultStr) {
        Resources resources = context.getResources();
        int bodyResId = resources.getIdentifier(key, "string", context.getPackageName());
        if (bodyResId != 0)
            return resources.getString(bodyResId);
        return defaultStr;
    }

    static boolean isValidEmail(String email) {
        if (email == null)
            return false;

        String emRegex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern pattern = Pattern.compile(emRegex);
        return pattern.matcher(email).matches();
    }

    static boolean isStringNotEmpty(String body) {
        return !TextUtils.isEmpty(body);
    }


}
