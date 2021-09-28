package com.library.aritic.Util.Util;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.library.aritic.Data.Model.Response.InAppResponseNew.InappResponse2;
import com.library.aritic.Data.Model.Response.InAppResponseNew.MessageTemplate;
import com.library.aritic.Data.Model.Response.InAppResponseNew.Properties;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ConfigureDialog {

    public void configureTitle(TextView title, InappResponse2 inAppResponse){

       Properties p = getTitle(inAppResponse);
        title.setText(p.getText());
//        title.setText("Hurry Get 50! off on Purchases NOW! what is this was a big Title what would do?");
        title.setTextSize(Float.parseFloat(p.getTextSize()));
        title.setPadding(0,20,0,20);
        // TODO: send Propoer Hex Color Code
        title.setTextColor(Color.parseColor("#FFFFFF"));
//                    title.setBackgroundColor(Color.parseColor(p.getBgColor()));


    }

    public void configureBackground(View dialog, InappResponse2 inAppResponse){
            String colorResponse = inAppResponse.getData()
                    .getInAppMessage()
                    .getBackground()
                    .getColor();
           // int myColor = Color.parseColor(colorResponse);
         //   dialog.setBackgroundColor(myColor);
    }

    public void configureSubtitle(TextView subtitle, InappResponse2 inAppResponse){
        Properties p = getSubTitle(inAppResponse);
        subtitle.setText(p.getText());
//        subtitle.setTextColor(Color.parseColor(p.getTextColor())); TODO:
         subtitle.setTextColor(Color.parseColor("#FFFFFF"));



    }

    public void configureImage(ImageView image, InappResponse2 inAppResponse, Integer length ){
                        Picasso.get()
                                .load(getImageURL(inAppResponse))
                                .resize(length, length)
                                .onlyScaleDown()    // the image will only be resized if it's bigger than 600x600 pixels.
                                .into(image);

    }

    public void configureButtons(Button positiveButton, InappResponse2 inAppResponse){
                positiveButton.setText("open");
    }


    public String getImageURL ( InappResponse2 inAppResponse) {
        List<MessageTemplate> messageTemplate = inAppResponse.getData().getInAppMessage().getMessageTemplate();
        String[] arrangementTypes = new String[messageTemplate.size()];
        for(int i =0;i < messageTemplate.size();i++) {
            if(messageTemplate.get(i).getType().equals("image")) {
                return inAppResponse.getData().getInAppMessage().getMessageTemplate().get(i).getProperties().getImageURL();
            };
        }
        return null;

    }

    public Properties getTitle (InappResponse2 inAppResponse) {
        List<MessageTemplate> messageTemplate = inAppResponse.getData().getInAppMessage().getMessageTemplate();
        String[] arrangementTypes = new String[messageTemplate.size()];
        for(int i =0;i < messageTemplate.size();i++) {
            if(messageTemplate.get(i).getType().equals("title")) {
                return inAppResponse.getData().getInAppMessage().getMessageTemplate().get(i).getProperties();
            };
        }


        return null;
    }

    public Properties getSubTitle (InappResponse2 inAppResponse) {
        List<MessageTemplate> messageTemplate = inAppResponse.getData().getInAppMessage().getMessageTemplate();
        String[] arrangementTypes = new String[messageTemplate.size()];
        for(int i =0;i < messageTemplate.size();i++) {
            if(messageTemplate.get(i).getType().equals("subtitle")) {
                return inAppResponse.getData().getInAppMessage().getMessageTemplate().get(i).getProperties();
            };
        }


        return null;
    }



//     Full Screen Dialog Configuation

    public static void configureCenterCardSubTitle(TextView subTitle, Properties p, LinearLayout l) {

        subTitle.setText(p.getText());
        subTitle.setTextSize(Float.parseFloat(p.getTextSize()));
        subTitle.setGravity(Gravity.CENTER);
        subTitle.setPadding(0,20,0,20);
        // TODO: send Propoer Hex Color Code
        subTitle.setTextColor(Color.parseColor("#FFFFFF"));
//                    title.setBackgroundColor(Color.parseColor(p.getBgColor()));
        l.addView(subTitle);

    }


    public static void configureCenterCardTitle(TextView title, Properties p, LinearLayout l) {
        title.setText(p.getText());
//        title.setText("Hurry Get 50! off on Purchases NOW! what is this was a big Title what would do?");
        title.setTextSize(Float.parseFloat(p.getTextSize()));
        title.setGravity(Gravity.CENTER);
        title.setPadding(0,20,0,20);
        // TODO: send Propoer Hex Color Code
        title.setTextColor(Color.parseColor("#FFFFFF"));
//                    title.setBackgroundColor(Color.parseColor(p.getBgColor()));
        l.addView(title);

    }

    public static void configureButtons(Button viewButton, com.library.aritic.Data.Model.Response.InAppResponseNew.Button p, LinearLayout l) {
//        viewButton.setText("hello");
        viewButton.setText(p.getName());
        viewButton.setTextSize(Float.parseFloat(p.getTextSize()));
        viewButton.setGravity(Gravity.CENTER);
        viewButton.setPadding(0,20,0,20);
        // TODO: send Propoer Hex Color Code
        viewButton.setTextColor(Color.parseColor("#FFFFFF"));
//                    title.setBackgroundColor(Color.parseColor(p.getBgColor()));
        l.addView(viewButton);

    }
}
