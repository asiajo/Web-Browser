package com.example.joanna.mybrowser;

import android.webkit.WebView;
import android.webkit.WebViewClient;

class ourViewClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}
