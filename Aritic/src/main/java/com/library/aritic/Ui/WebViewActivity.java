package com.library.aritic.Ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.library.aritic.R;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    private String redirectionUrl = "http://www.google.com";
    // click my button -> come to webView ->
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webView = findViewById(R.id.webview);
        Bundle i  = getIntent().getExtras();
        assert i != null;
        redirectionUrl = i.getString("URL");
        Log.d("WEbview", "Opening WEbview" + redirectionUrl);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.loadUrl(redirectionUrl);
        webView.setWebViewClient(
                new SSLTolerentWebViewClient()
        );

    }

    private class SSLTolerentWebViewClient extends WebViewClient {
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed(); // Ignore SSL certificate errors
        }
    }
}