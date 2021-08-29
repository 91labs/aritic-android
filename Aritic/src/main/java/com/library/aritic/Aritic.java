package com.library.aritic;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.library.aritic.InAppMessaging.InAppMessaging;
import com.library.aritic.InAppRegisteration.AriticRegisteration;
import com.library.aritic.PushNotification.MyFirebaseMessagingService;
import com.library.aritic.PushNotification.PushNotification;
import com.library.aritic.SharedPref.SharedPref;

public class Aritic {

        private static Aritic single_instance = null;
        private AriticRegistrationStatus listener;
        public static Aritic getInstance() {
                if (single_instance == null) {
                        single_instance = new Aritic();
                }
                return single_instance;
        }

        public void setListener(AriticRegistrationStatus listener) {
                this.listener = listener;
        }

        public void showInAppMessage(String pagename , Context context){
                InAppMessaging inAppMessaging = new InAppMessaging();
                inAppMessaging.showInAppMessage(pagename , context);
        }

        public void register(Context context) {
                SharedPref.init(context);

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
                pushNotification.getFcmToken();
        }


        public void registerUser(  Context context, @Nullable String email,
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
                ariticRegisteration.registerUserWithDetails(email, userId, phone);
        }

        public void setupNewTokenHandler(Context context){
                SharedPref.init(context);
        }

        public interface AriticRegistrationStatus {
                // These methods are the different events and
                // need to pass relevant arguments related to the event triggered
                public void onAriticRegisrtationSuccess();
                public void ononAriticRegisrtationFailure(int code, String error);
                // or when data has been loaded

        }
}
