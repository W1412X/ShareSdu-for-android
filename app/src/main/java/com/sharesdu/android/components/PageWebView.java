package com.sharesdu.android.components;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.util.AttributeSet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageWebView extends WebView {

    public PageWebView(Context context) {
        super(context);
        init();
    }

    public PageWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PageWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //启用 JavaScript
        getSettings().setJavaScriptEnabled(true);
        //启用缓存
        getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        getSettings().setAppCacheEnabled(true);
        getSettings().setDomStorageEnabled(true);
        // 设置 WebViewClient 以处理网页内的跳转
        setWebViewClient(new PageWebViewClient());
        // 设置 WebChromeClient（可选，用于处理 JS 接口等）
        setWebChromeClient(new WebChromeClient());
    }
    //对于跳转的网页进行拦截，对于sharesdu内的网页，尝试判断是否可以在APP中显示
    private class PageWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            //judge if from sharesdu
            //对于sharesdu的内容，在APP内显示，其他的跳转浏览器
            //
            String url=request.getUrl().toString();
            //String regex = "^(https?:\\/\\/sharesdu\\/\\/#\\/)(article|question|course)\\/(\\d+)$";
            //暂时只对文章内容利用网页显示
            String regex = "^(https?:\\/\\/sharesdu\\/\\/#\\/)(article)\\/(\\d+)$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(url);
            if (matcher.matches()) {
                String baseUrl = matcher.group(1);
                String category = matcher.group(2);
                String number = matcher.group(3);
                String to_url="";
                view.loadUrl(to_url);
            } else {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                getContext().startActivity(browserIntent);
            }
            return true;
        }
    }

    //处理返回事件
    public boolean canGoBack() {
        return canGoBack();
    }

    public void goBack() {
        if (canGoBack()) {
            goBack();
        }
    }
}
