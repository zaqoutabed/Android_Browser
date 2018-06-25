package com.zaqoutabed.github.android_browser;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ProgressBar loadingProgressBar;
    private WebView browserWebView;
    private SwipeRefreshLayout browserSwipeLayout;
    private String url;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();

        getSupportActionBar().setTitle("");

        browserWebView.loadUrl("https://www.google.com");
        browserWebView.getSettings().setJavaScriptEnabled(true);
        browserWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                loadingProgressBar.setVisibility(View.GONE);
                browserSwipeLayout.setRefreshing(false);
                MainActivity.this.url = url;
                super.onPageFinished(view, url);
            }
        });
        browserWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                loadingProgressBar.setProgress(newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                getSupportActionBar().setTitle(title);
            }
        });

        browserWebView.setDownloadListener((url, userAgent, contentDisposition, mimetype, contentLength) -> {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

            DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            if (downloadManager != null) {
                downloadManager.enqueue(request);
                Toast.makeText(this, "downloading file started....", Toast.LENGTH_SHORT).show();
            }
        });
        browserSwipeLayout.setOnRefreshListener(()->browserWebView.reload());
    }

    private void bindViews() {
        loadingProgressBar = findViewById(R.id.loading_progress_bar);
        browserWebView = findViewById(R.id.browser_web_view);
        browserSwipeLayout = findViewById(R.id.browser_swipe_layout);
    }

    @Override
    public void onBackPressed() {
        if (browserWebView.canGoBack()) browserWebView.goBack();
        else super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.back_menu_opt:
                onBackPressed();
                break;
            case R.id.forward_menu_opt:
                if (browserWebView.canGoForward())
                    browserWebView.goForward();
                break;
            case R.id.refresh_menu_opt:
                loadingProgressBar.setVisibility(View.VISIBLE);
                browserWebView.reload();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
