package com.library.aritic.InAppMessaging;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.github.andreilisun.swipedismissdialog.SwipeDismissDialog;
import com.library.aritic.ApiService.ApiService_InApp;
import com.library.aritic.Data.Model.Request.InAppRequest;
import com.library.aritic.Data.Model.Response.InAppResponseNew.InappResponse2;
import com.library.aritic.Data.Model.Response.InAppResponseNew.MessageTemplate;
import com.library.aritic.Data.Model.Response.InAppResponseNew.Properties;
import com.library.aritic.HitApi.HitApiInAppMessaging;
import com.library.aritic.R;
import com.library.aritic.SharedPref.SharedPref;
import com.library.aritic.Util.Util.ConfigureDialog;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.ListPopupWindow.MATCH_PARENT;


/*     Hit the API
            Get the Response
            ShowDialogBox
            implement Click and Dismiss Listener
     */
public class InAppMessaging {

    private static final String CLOSE_DIALOG = "close";
    private String CARD_TOP = "top";
    private String CARD_BOTTOM = "bottom";
    private String CARD_CENTER = "center";
    private String CARD_FULLSCREEN = "fullscreen";
    private ApiService_InApp apiService;
    private InappResponse2 inAppResponse;
    private String pageName;
    private TextView title;
    private TextView subtitle;
    private Button positiveButton;
    private ImageButton cancelButton;
    private ImageView advertisementImage;
    private CardView dialogView;
    private SwipeDismissDialog swipeDismissDialog;
    private Context context;
    private String position;
    DisplayMetrics metrics;

    public void showInAppMessage(String pageName, Context context) {
        this.pageName = pageName;
        log("pageName:" + pageName);
        this.context = context;
        setupRetrofit();
        reportMetrics();
        getAndShowInAppMessage();
    }

    private void setupRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                // TODO(" Change the API ")
                .baseUrl(SharedPref.getValue("base_url"))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService_InApp.class);
    }

    private void getAndShowInAppMessage() {
        log("calling API");
        InAppRequest inAppRequest = new InAppRequest(pageName);
        Call<InappResponse2>  call = apiService.getInAppResponse(inAppRequest);
        call.enqueue(new Callback<InappResponse2>() {
            @Override
            public void onResponse(Call<InappResponse2> call, Response<InappResponse2> response) {
//                            log("Response " + response.toString());
                inAppResponse = response.body();

//
                if(inAppResponse!=null && inAppResponse.getData().getShowInAppMessage()){
                    position = inAppResponse.getData().getInAppMessage().getPosition();
//                    position = CARD_TOP;
                    String MessageId = inAppResponse.getData().getInAppMessage().getMessageId();
                    log("Message Id " + MessageId);
//
                    showDialogBox();
                }
            }
            @Override
            public void onFailure(Call<InappResponse2> call, Throwable t) {
                log("On Failure response");

            }
        });
    }

    private void showDialogBox() {

        // First get which dialog to show
        View dialog = getDialogFromPosition();          // returns configured Dialog View
                        /*
                                Note that handleCancelButtonClick can only be called
                                after showSwipeDismissDialogBox()
                         */
        showSwipeDismissDialogBox(context, dialog, 0.01f);

        if (position.equals(CARD_FULLSCREEN)) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) dialog.getLayoutParams();
            layoutParams.width = MATCH_PARENT;
            layoutParams.height = MATCH_PARENT;
            buildFullScreenDialog(dialog);
            handleCancelButtonClick(cancelButton);
        }
        if (position.equals(CARD_TOP)) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) dialog.getLayoutParams();
            layoutParams.gravity = Gravity.TOP;
            layoutParams.width = MATCH_PARENT;
            handleCancelButtonClick(cancelButton);
//            buildCenterDialog(dialog);
        }
        if (position.equals(CARD_BOTTOM)) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) dialog.getLayoutParams();
            layoutParams.gravity = Gravity.BOTTOM;
            layoutParams.width = MATCH_PARENT;
            handleCancelButtonClick(cancelButton);
        }
        if (position.equals(CARD_CENTER)) {
            handleCancelButtonClick(cancelButton);
            buildCenterDialog(dialog);
        }
    }
    public void reportMetrics() {
        metrics = context.getResources().getDisplayMetrics();


    }

    public void log(String msg) {
        Log.w("InApp Page : ", msg);
    }

    private void buildCenterDialog(View dialog) {
        String[] arrangementType = getArrangementType();
        LinearLayout l = dialog.findViewById(R.id.linear_layout_dialog_center);
        l.setMinimumWidth((int) (metrics.widthPixels*0.8));
        setBackground(dialog,l, R.id.dialog_cardCenter);


//       l.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        String s = arrangementType[0] + arrangementType[1] + arrangementType[2];
        for (int i = 0; i < arrangementType.length; i++) {
            switch (arrangementType[i]) {
                case "title":

                    addTitleToDialog(dialog, getTypeProperTies(i), CARD_CENTER);
                    break;
                case "image":
                    addImageToDialog(dialog, getTypeProperTies(i), CARD_CENTER);
                    break;
                case "subtitle":
                    addSubTitleToDialog(dialog, getTypeProperTies(i), CARD_CENTER);
                    break;
            }
        }
        List<com.library.aritic.Data.Model.Response.InAppResponseNew.Button> buttonsToAdd = inAppResponse.getData().getInAppMessage().getButtons();

        for (int i = 0; i < buttonsToAdd.size(); i++) {
            addButtonToDialog(dialog,buttonsToAdd.get(i), CARD_CENTER);
        }


    }

    private void setBackground(View dialog, LinearLayout l, int Id) {
        // First check if imageURL exits
        String imageURL = inAppResponse.getData().getInAppMessage().getBackground().getImageURL();
        String bgColor = inAppResponse.getData().getInAppMessage().getBackground().getColor();
        if(imageURL.equals("") || imageURL.isEmpty()) {
            // TODO: no image URL exist set background color as Background
            l.setBackgroundColor(Color.parseColor("#36353F"));
            dialog.findViewById(Id).setBackgroundColor(Color.parseColor("#36353F"));
        }
    }

    private void buildFullScreenDialog(View dialog) {
        String[] arrangementType = getArrangementType();
        LinearLayout l = dialog.findViewById(R.id.full_screen_inner);
        setBackground(dialog,l,R.id.dialog_cardFullscreen);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                MATCH_PARENT, MATCH_PARENT);
//        layoutParams.setMargins(30, 20, 30, 30);
//        l.setLayoutParams(layoutParams);

        String s = arrangementType[0] + arrangementType[1] + arrangementType[2];
        for (int i = 0; i < arrangementType.length; i++) {
            switch (arrangementType[i]) {
                case "title":

                    addTitleToDialog(dialog, getTypeProperTies(i), CARD_FULLSCREEN);
                    break;
                case "image":
                    addImageToDialog(dialog, getTypeProperTies(i), CARD_FULLSCREEN);
                    break;
                case "subtitle":
                    addSubTitleToDialog(dialog, getTypeProperTies(i), CARD_FULLSCREEN);
                    break;
            }
        }

        List<com.library.aritic.Data.Model.Response.InAppResponseNew.Button> buttonsToAdd = inAppResponse.getData().getInAppMessage().getButtons();

        for (int i = 0; i < buttonsToAdd.size(); i++) {
            addButtonToDialog(dialog,buttonsToAdd.get(i),CARD_FULLSCREEN);
        }

    }

    private Properties getTypeProperTies(int i) {
        return inAppResponse.getData().getInAppMessage().getMessageTemplate().get(i).getProperties();
    }

    private void addSubTitleToDialog(View d, Properties p, String viewType) {
        LinearLayout l;
        TextView title = new TextView(context);
        if(viewType.equals("center")) {
            l = d.findViewById(R.id.linear_layout_dialog_center);
            ConfigureDialog.configureCenterCardSubTitle(title,p,l);
        } else {
            l = d.findViewById(R.id.full_screen_inner);
            ConfigureDialog.configureCenterCardSubTitle(title,p,l);
        }




    }

    private void addImageToDialog(View d, Properties p, String viewType) {
        ImageView i  = new ImageView(context);

        int dim = (int) (viewType.equals(CARD_CENTER)?metrics.widthPixels * 0.4: metrics.widthPixels*0.6);
        Picasso.get()
                .load(p.getImageURL())
                .centerCrop()
                .resize(dim,dim)
                .into(i);
        i.setPadding(0,20,0,20);
        LinearLayout l;
        if (viewType.equals(CARD_CENTER)){
            l = d.findViewById(R.id.linear_layout_dialog_center);
            l.addView(i);
        } else {
            l = d.findViewById(R.id.full_screen_inner);
            l.addView(i);
        }



    }
    private void addButtonToDialog(View d, com.library.aritic.Data.Model.Response.InAppResponseNew.Button p, String viewType) {
        Button b  = new Button(context);
        LinearLayout l;
        if(viewType.equals(CARD_CENTER))  {
            l = d.findViewById(R.id.linear_layout_dialog_center);
            ConfigureDialog.configureButtons(b,p,l);
        } else {
            l = d.findViewById(R.id.full_screen_inner);
            ConfigureDialog.configureButtons(b,p,l);
        }


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(p.getActionRedirectType().equals(CLOSE_DIALOG)) {


                } else {
                    log("Button Clicked");
                    handleButtonClick("Clicked",p.getActionRedirectType(),p.getActionValue());
                }
            }
        });


    }


    private void addTitleToDialog(View d, Properties p, String viewType) {
        TextView title = new TextView(context);
        LinearLayout l;
        if(viewType.equals(CARD_CENTER)) {
            l = d.findViewById(R.id.linear_layout_dialog_center);
        } else {
            l = d.findViewById(R.id.full_screen_inner);
        }

        ConfigureDialog.configureCenterCardTitle(title,p,l);

    }

    private View getDialogFromPosition () {

        if (position.equals(CARD_FULLSCREEN)) {
            View dialog = LayoutInflater.from(context).inflate(R.layout.dialog_fullscreen, null);
            dialogView = dialog.findViewById(R.id.dialog_cardFullscreen);
            cancelButton = (ImageButton) dialog.findViewById(R.id.button_cancelFullscreen);
            return dialog;
        }

        if (position.equals(CARD_CENTER)) {
            View dialog = LayoutInflater.from(context).inflate(R.layout.dialog_centercard, null);
            dialog.setMinimumHeight((int) (metrics.heightPixels*0.6));
            dialog.setMinimumWidth((int) (metrics.widthPixels*0.6));
            cancelButton = (ImageButton) dialog.findViewById(R.id.button_cancel);
            return dialog;
        }

        if (position.equals(CARD_TOP) || position.equals(CARD_BOTTOM)) {
            View dialog = LayoutInflater.from(context).inflate(R.layout.dialog_top_bottom, null);
            dialogView = dialog.findViewById(R.id.dialog_cardTopBottom);
            title = (TextView) dialog.findViewById(R.id.textView_title);
            subtitle = (TextView) dialog.findViewById(R.id.textView_message);
            advertisementImage = (ImageView) dialog.findViewById(R.id.imageView_advertisementImage);
            cancelButton = (ImageButton) dialog.findViewById(R.id.button_cancel);
            setupDialogConfiguration(dialog);
            return dialog;
        }
        return null;
    }


    /** This function is used only to set Up configuration for Top or Bottom Dialog Box **/

    private void setupDialogConfiguration (View dialog) {
        ConfigureDialog configureDialog = new ConfigureDialog();
                 /*
                        Hardcoded for Testing purposes,
                        else set Configuration from the response API

                 */
        if (position.equals(CARD_TOP)) {
            configureDialog.configureImage(advertisementImage, inAppResponse, 200);
            configureDialog.configureTitle(title, inAppResponse);
            configureDialog.configureSubtitle(subtitle, inAppResponse);
            configureDialog.configureBackground(dialog, inAppResponse);
        }
        if (position.equals(CARD_BOTTOM)) {
            configureDialog.configureImage(advertisementImage, inAppResponse, 200);
            configureDialog.configureTitle(title, inAppResponse);
            configureDialog.configureSubtitle(subtitle, inAppResponse);
            configureDialog.configureBackground(dialog, inAppResponse);
        }
    }

    private void handleCancelButtonClick(ImageButton cancelButton) {
        String msgId = inAppResponse.getData().getInAppMessage().getMessageId();
        cancelButton.setOnClickListener(v -> {
            swipeDismissDialog.cancel();
            hitApiOnCancelled(msgId, "cancelled");
        });
    }

    private String[] getArrangementType() {
        List<MessageTemplate> messageTemplate = inAppResponse.getData().getInAppMessage().getMessageTemplate();
        String[] arrangementTypes = new String[messageTemplate.size()];
        for(int i =0;i < messageTemplate.size();i++) {
            arrangementTypes[i] = messageTemplate.get(i).getType();
        }
        return arrangementTypes;
    }


    private void showSwipeDismissDialogBox (Context context, View dialog, Float flingVelocity) {

        // We are sending events to server based on the messageId,
        // ie. whether the in App was opened or not
        String msgId = inAppResponse.getData().getInAppMessage().getMessageId();
        swipeDismissDialog = new SwipeDismissDialog.Builder(context)
                .setView(dialog)
                .setOnSwipeDismissListener((view, direction) -> {
                    hitApiOnCancelled(msgId, "cancelled");
                })
                .setFlingVelocity(flingVelocity)
                .build();
        if(position.equals(CARD_FULLSCREEN) || position.equals(CARD_CENTER)) {
            // Some Addition Customisations, If Required
        }
            swipeDismissDialog.show();
             hitApiOnShowed(msgId, "shown");
    }

    private void hitApiOnCancelled(String objectId, String event) {
        HitApiInAppMessaging hitApiInAppMessaging = new HitApiInAppMessaging();
        hitApiInAppMessaging.hitApi(objectId, event);
    }

    private void hitApiOnShowed(String objectId, String event) {
        HitApiInAppMessaging hitApiInAppMessaging = new HitApiInAppMessaging();
        hitApiInAppMessaging.hitApi(objectId, event);
    }

    private void hitApiOnClicked(String objectId, String event){
        HitApiInAppMessaging hitApiInAppMessaging = new HitApiInAppMessaging();
        hitApiInAppMessaging.hitApi(objectId, event);
//                redirectUserToUrl();
    }

    private void handleButtonClick(String action, String actionRedirectType, String actionValue) {
        HitApiInAppMessaging hitApiInAppMessaging = new HitApiInAppMessaging();
        String currentMessageId = inAppResponse.getData().getInAppMessage().getMessageId();
        hitApiInAppMessaging.hitApi(currentMessageId, "clicked");

        if(actionRedirectType.equals("web")) {
            log("Redirecting web");
            redirectUserToUrl(actionValue);
        }
        if(actionRedirectType.equals("deep_link")) {
            handleDeepLink(actionValue);
        }
        if(actionRedirectType.equals("page")) {
            openAcitivity(actionValue);
        }

    }

    private void openAcitivity(String activityName) {
//        Intent intent= new Intent(context, Class.forName(activityName));
//        context.startActivity(intent);
    }

    private void handleDeepLink(String deepLink) {

    }


    private void handleWebButtonClicke(String msgId, String event,String url){
        HitApiInAppMessaging hitApiInAppMessaging = new HitApiInAppMessaging();
        hitApiInAppMessaging.hitApi(msgId, event);
        redirectUserToUrl(url);
    }

    private void redirectUserToUrl(String url) {

        Intent intent= new Intent("com.library.aritic.Ui.WebViewActivity");
        intent.putExtra("URL", url);
        context.startActivity(intent);
    }

    private void ThrowError () {

    }






}
