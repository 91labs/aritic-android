package com.library.aritic.PushNotification;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.core.app.BundleCompat;
import androidx.legacy.content.WakefulBroadcastReceiver;

import com.library.aritic.AriticLogger;

//import com.onesignal.NotificationBundleProcessor.ProcessedBundleResult;

// This is the entry point when a FCM payload is received from the Google Play services app
// OneSignal does not use FirebaseMessagingService.onMessageReceived as it does not allow multiple
//   to be setup in an app. See the following issue for context on why this this important:
//    - https://github.com/OneSignal/OneSignal-Android-SDK/issues/1355
public class FCMBroadCastReceiver extends WakefulBroadcastReceiver{

    private static final String FCM_RECEIVE_ACTION = "com.google.android.c2dm.intent.RECEIVE";
    private static final String FCM_TYPE = "gcm";
    private static final String MESSAGE_TYPE_EXTRA_KEY = "message_type";

    private static boolean isFCMMessage(Intent intent) {
        if (FCM_RECEIVE_ACTION.equals(intent.getAction())) {
            String messageType = intent.getStringExtra(MESSAGE_TYPE_EXTRA_KEY);
            return (messageType == null || FCM_TYPE.equals(messageType));
        }
        return false;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // Do not process token update messages here.
        // They are also non-ordered broadcasts.
        Bundle bundle = intent.getExtras();
        if (bundle == null || "google.com/iid".equals(bundle.getString("from")))
            return;

//        OneSignal.initWithContext(context);

        AriticLogger.Log("Inside on Received Method");

//        NotificationBundleProcessor.ProcessBundleReceiverCallback bundleReceiverCallback = new NotificationBundleProcessor.ProcessBundleReceiverCallback() {
//
//            @Override
//            public void onBundleProcessed(@Nullable NotificationBundleProcessor.ProcessedBundleResult processedResult) {
//                // Null means this isn't a FCM message
//                if (processedResult == null) {
//                    setSuccessfulResultCode();
//                    return;
//                }
//
//                // Prevent other FCM receivers from firing if:
//                //   1. This is a duplicated FCM message
//                //   2. OR work manager is processing the notification
//                if (processedResult.isDup() || processedResult.isWorkManagerProcessing()) {
//                    // Abort to prevent other FCM receivers from process this Intent.
//                    setAbort();
//                    return;
//                }
//
//                setSuccessfulResultCode();
//            }
//        };
//        processOrderBroadcast(context, intent, bundle, bundleReceiverCallback);
    }

    private void setSuccessfulResultCode() {
        if (isOrderedBroadcast())
            setResultCode(Activity.RESULT_OK);
    }

    private void setAbort() {
        if (isOrderedBroadcast()) {
            // Prevents other BroadcastReceivers from firing
            abortBroadcast();

            // TODO: Previous error and related to this Github issue ticket
            //    https://github.com/OneSignal/OneSignal-Android-SDK/issues/307
            // RESULT_OK prevents the following confusing logcat entry;
            // W/GCM: broadcast intent callback: result=CANCELLED forIntent {
            //    act=com.google.android.c2dm.intent.RECEIVE
            //    flg=0x10000000
            //    pkg=com.onesignal.example (has extras)
            // }

            setResultCode(Activity.RESULT_OK);
        }
    }

    private static void processOrderBroadcast(final Context context, Intent intent, final Bundle bundle,
                                              final NotificationBundleProcessor.ProcessBundleReceiverCallback fcmBundleReceiver) {
        if (!isFCMMessage(intent))
            fcmBundleReceiver.onBundleProcessed(null);

        NotificationBundleProcessor.ProcessBundleReceiverCallback bundleReceiverCallback = new NotificationBundleProcessor.ProcessBundleReceiverCallback() {
            @Override
            public void onBundleProcessed(@Nullable NotificationBundleProcessor.ProcessedBundleResult processedResult) {
                // Return if the notification will NOT be handled by normal FCMIntentService display flow.
                if (processedResult!= null && processedResult.processed()) {
                    fcmBundleReceiver.onBundleProcessed(processedResult);
                    return;
                }

                startFCMService(context, bundle);

                fcmBundleReceiver.onBundleProcessed(processedResult);
            }
        };
        NotificationBundleProcessor.processBundleFromReceiver(context, bundle, bundleReceiverCallback);
    }

    static void startFCMService(Context context, Bundle bundle) {
//        OneSignal.Log(OneSignal.LOG_LEVEL.DEBUG, "startFCMService from: " + context + " and bundle: " + bundle);
//        // If no remote resources have to be downloaded don't create a job which could add some delay.
//        if (!NotificationBundleProcessor.hasRemoteResource(bundle)) {
//            OneSignal.Log(OneSignal.LOG_LEVEL.DEBUG, "startFCMService with no remote resources, no need for services");
//            BundleCompat taskExtras = setCompatBundleForServer(bundle, BundleCompatFactory.getInstance());
//            NotificationBundleProcessor.processFromFCMIntentService(context, taskExtras);
//            return;
//        }

//        boolean isHighPriority = Integer.parseInt(bundle.getString("pri", "0")) > 9;
//        if (!isHighPriority && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//            startFCMServiceWithJobIntentService(context, bundle);
//        else {
//            try {
//                startFCMServiceWithWakefulService(context, bundle);
//            } catch (IllegalStateException e) {
//                // If the high priority FCM message failed to add this app to the temporary whitelist
//                // https://github.com/OneSignal/OneSignal-Android-SDK/issues/498
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
//                    startFCMServiceWithJobIntentService(context, bundle);
//                else
//                    throw e;
//            }
//        }
    }

    /**
     * This function uses a com.OneSignal.JobIntentService in order to enqueue the jobs.
     * Some devices with Api O and upper can't schedule more than 100 distinct jobs,
     * this will process one notification sequentially like an IntentService.
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static void startFCMServiceWithJobIntentService(Context context, Bundle bundle) {
//        BundleCompat taskExtras = setCompatBundleForServer(bundle, BundleCompatFactory.getInstance());
//
//        Intent intent = new Intent(context, FCMIntentJobService.class);
//        intent.putExtra(FCMIntentJobService.BUNDLE_EXTRA, (Parcelable) taskExtras.getBundle());
//
//        FCMIntentJobService.enqueueWork(context, intent);
    }

    private static void startFCMServiceWithWakefulService(Context context, Bundle bundle) {
//        ComponentName componentName =
//                new ComponentName(context.getPackageName(), FCMIntentService.class.getName());
//
//        BundleCompat taskExtras = setCompatBundleForServer(bundle, new BundleCompatBundle());
//        Intent intentForService =
//                new Intent()
//                        .replaceExtras((Bundle)taskExtras.getBundle())
//                        .setComponent(componentName);
//        startWakefulService(context, intentForService);
    }

    private static BundleCompat setCompatBundleForServer(Bundle bundle, BundleCompat taskExtras) {
//        taskExtras.putString("json_payload", NotificationBundleProcessor.bundleAsJSONObject(bundle).toString());
//        taskExtras.putLong("timestamp", OneSignal.getTime().getCurrentTimeMillis() / 1000L);
        return taskExtras;
    }
}
