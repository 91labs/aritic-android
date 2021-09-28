package com.library.aritic;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.library.aritic.InAppMessaging.InAppMessaging;
import com.library.aritic.InAppRegisteration.AriticRegisteration;
import com.library.aritic.PushNotification.AriticRemoteMessage;
import com.library.aritic.PushNotification.MyFirebaseMessagingService;
import com.library.aritic.PushNotification.PushNotification;
import com.library.aritic.SharedPref.SharedPref;

public class Aritic implements MyFirebaseMessagingService.AriticPushListener{

        private static Aritic single_instance = null;

        private static final String EXTENSION_SERVICE_META_DATA_TAG_NAME = "com.aritic.PushServiceExtension";

    private AriticRegistrationStatus listener;
    static AriticPushHandler remoteNotificationReceivedHandler;
    private MyFirebaseMessagingService mService;

    public static Aritic getInstance() {
                if (single_instance == null) {
                        single_instance = new Aritic();
                }
                return single_instance;
        }

        public void setListener(AriticRegistrationStatus listener) {
                this.listener = listener;
        }


        public void setAppURL(Context c, String url) {
                SharedPref.init(c);
                SharedPref.updateOrInsertValue(SharedPref.BASE_URL, url);

        }

        public void pauseInApp(Context context) {
                SharedPref.setInAppDisplayBoolean(false);
        }


        public void resumeInAppMessages() {
                SharedPref.setInAppDisplayBoolean(true);
        }

        public void showInAppMessage(String pagename , Context context){
                InAppMessaging inAppMessaging = new InAppMessaging();
                if(SharedPref.getInAppDisplayBoolean()) {
                        // In App is not paused, Displau InApp Messages
                        inAppMessaging.showInAppMessage(pagename , context);
                } else {
                        // Do nothhing
                }

        }

        public void register(Context context) {
                SharedPref.init(context);
                boolean isSet = setupNotificationServiceExtension(context);
                PushNotification pushNotification = new PushNotification();
                pushNotification.setTokenListener(new PushNotification.tokenListener() {
                        @Override
                        public void onTokenSaved(String token) {
                                Log.d("Register", "Registering with Interface");
                                performRegistration(null,null,null);

                        }

                        @Override
                        public void onTokenFailure() {
                                listener.ononAriticRegisrtationFailure(0, "Firebase Push Generation Failure");
                        }
                });
                if(isSet) {
                    mService = new MyFirebaseMessagingService();
                    mService.setPushListener(this);
                }
                // Generates FCM Token
                pushNotification.getFcmToken();
        }


        public void registerUser( Context context, @Nullable String email,
                                                          @Nullable String userId,
                                                          @Nullable String phone
                                                         ){
                SharedPref.init(context);

                PushNotification pushNotification = new PushNotification();
                pushNotification.setTokenListener(new PushNotification.tokenListener() {
                        @Override
                        public void onTokenSaved(String token) {
                                Log.d("Register", "Registering with Interface");
                                performRegistration(email,userId,phone);
                        }

                        @Override
                        public void onTokenFailure() {

                        }
                });
                pushNotification.getFcmToken();

        }

        private void performRegistration(@Nullable String email,@Nullable String userId,@Nullable String phone) {
                AriticRegisteration ariticRegisteration = new AriticRegisteration();
                ariticRegisteration.setListener(new AriticRegisteration.statusListener() {
                        @Override
                        public void onRegisterSuccessFull() {
                                // Proprage to FrontEnd
                        }

                        @Override
                        public void onRegisterFailure(String errorCode) {
                                // proporate to backend
                        }
                });

                // Performs Registration
                ariticRegisteration.registerUserWithDetails(email, userId, phone);
        }

        public void setupNewTokenHandler(Context context){
                SharedPref.init(context);
        }
        /**
         *
         *  We just received a push message from GCM
         *  propagting to Listeners in client App
         *
         * */
    @Override
    public void onPushMessageReceived(AriticRemoteMessage message) {
        if(remoteNotificationReceivedHandler!=null) {
            remoteNotificationReceivedHandler.remoteNotificationReceived(message);
        }

    }

    public interface AriticRegistrationStatus {
                // These methods are the different events and
                // need to pass relevant arguments related to the event triggered
                public void onAriticRegisrtationSuccess();
                public void ononAriticRegisrtationFailure(int code, String error);
                // or when data has been loaded

        }


    static boolean setupNotificationServiceExtension(Context context) {
        String className = AppHelpers.getManifestMeta(context, EXTENSION_SERVICE_META_DATA_TAG_NAME);

        // No meta data containing extension service class name
        AriticLogger.Log("Classname: " + className);
        if (className == null) {
            AriticLogger.Log("No Class Found for Notification service Extension");
            return false;
        }

        // Pass an instance of the given class to set any overridden handlers
        try {
            Class<?> clazz = Class.forName(className);
            Object clazzInstance = clazz.newInstance();
            if (clazzInstance instanceof Aritic.AriticPushHandler && remoteNotificationReceivedHandler == null) {
                Aritic.setRemoteNotificationReceivedHandler((Aritic.AriticPushHandler) clazzInstance);
                return true;

            }
        } catch (IllegalAccessException e) {
            AriticLogger.Log("Exception Illegal Access");
            return false;

        } catch (InstantiationException e) {
            AriticLogger.Log("Instation Exception");
            return false;

        } catch (ClassNotFoundException e) {
            AriticLogger.Log("Class not found Exception");
            return false;

        }
        return false;
    }

    public static void setRemoteNotificationReceivedHandler(AriticPushHandler callback) {
        if (remoteNotificationReceivedHandler == null)
            remoteNotificationReceivedHandler = callback;
        // settting Listener to listen on this

    }



    public interface AriticPushHandler {

        void remoteNotificationReceived(AriticRemoteMessage pushMessage);
    }




}
