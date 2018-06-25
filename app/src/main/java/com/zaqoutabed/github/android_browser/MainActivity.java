package com.zaqoutabed.github.android_browser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private ProgressBar loadingProgressBar;
    private ImageView logoImageView;
    private WebView browserWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();


    }

    private void bindViews() {
        loadingProgressBar = findViewById(R.id.loading_progress_bar);
        logoImageView = findViewById(R.id.logo_image_view);
        browserWebView = findViewById(R.id.browser_web_view);
    }
}
