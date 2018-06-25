package com.zaqoutabed.github.android_browser;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private ProgressBar loadingProgressBar;
    private WebView browserWebView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();

        browserWebView.loadUrl("https://www.google.com");
        browserWebView.getSettings().setJavaScriptEnabled(true);
        browserWebView.setWebViewClient(new WebViewClient());
        browserWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                loadingProgressBar.setProgress(newProgress);
                if (newProgress == 100)
                    loadingProgressBar.setVisibility(View.GONE);
            }
        });
    }

    private void bindViews() {
        loadingProgressBar = findViewById(R.id.loading_progress_bar);
        browserWebView = findViewById(R.id.browser_web_view);
    }

    @Override
    public void onBackPressed() {
        if (browserWebView.canGoBack()) browserWebView.goBack();
        else super.onBackPressed();
    }
}
