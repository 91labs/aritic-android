package com.example.ariticproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.library.aritic.Aritic;
import com.library.aritic.InAppMessaging.InAppMessaging;
import com.library.aritic.PushNotification.PushNotification;

public class MainActivity extends AppCompatActivity implements Aritic.AriticRegistrationStatus {

    private Aritic aritic;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        aritic = Aritic.getInstance();
        aritic.setAppURL(this, "https://sprint.ctrmv.com/");
        aritic.resumeInAppMessages();
        aritic.register(this);
        aritic.setListener(this);
//        aritic.pauseInApp();

//        aritic.registerUser(this, "nandhakumar1411@gmail.commm", "12345678", "70222412471");

        /** incase user wants to register with email, phone details
         *
         *  aritic.register(this, email, userId, phone)
         *
         *
         **/



        handleOnClickListeners();
    }

    // Before register/login -> registerOnInitialLoad();  TODO : does not check for email..
    // After registering -> registerUser(email,..); checks/updates for email...
    // In MainActivity -> handleNewToken()

    private void handleOnClickListeners() {
        Button btn_centerCard = findViewById(R.id.btn_centerCard);




        btn_centerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aritic.showInAppMessage("home" , context);
            }
        });


    }


    @Override
    public void onAriticRegisrtationSuccess() {

    }

    @Override
    public void ononAriticRegisrtationFailure(int code, String error) {

    }
}