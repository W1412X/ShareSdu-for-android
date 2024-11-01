package com.sharesdu.android.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.RelativeLayout;

import com.sharesdu.android.R;

public class CustomWebView extends RelativeLayout {

    private WebView webView;
    private ProgressBar progressBar;

    public CustomWebView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public CustomWebView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomWebView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        // Inflate the layout for this custom view
        inflate(context, R.layout.component_webview, this);

        webView = findViewById(R.id.component_web_view);
        progressBar = findViewById(R.id.component_web_view_progress_bar);

        // Set up the WebView
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Enable JavaScript
        webSettings.setSupportZoom(true); // Support zooming
        webSettings.setBuiltInZoomControls(true); // Enable built-in zoom controls
        webSettings.setDisplayZoomControls(false); // Hide zoom controls
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT); // Enable caching
        webSettings.setDomStorageEnabled(true); // Enable DOM storage
        webSettings.setAllowFileAccess(true); // Allow file access
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW); // Allow mixed content (HTTP and HTTPS)

        // Set WebViewClient to handle loading
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE); // Show progress bar
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE); // Hide progress bar
            }
        });

        // Set WebChromeClient to handle progress updates
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
            }
        });
    }

    public void loadUrl(String url) {
        webView.loadUrl(url);
    }

    public WebView getWebView() {
        return webView;
    }
}
