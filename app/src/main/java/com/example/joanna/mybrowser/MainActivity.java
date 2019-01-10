package com.example.joanna.mybrowser;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    EditText urlEdit;
    Button go, fwd, back;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        webView = (WebView) findViewById(R.id.wv_brow);
        urlEdit = (EditText)findViewById(R.id.et_url);
        go = (Button)findViewById(R.id.btn_go);
        fwd = (Button)findViewById(R.id.btn_fwd);
        back = (Button)findViewById(R.id.btn_back);

        // opening links in designed browser
        webView.setWebViewClient(new ourViewClient());
        // adding progress bar
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);

                if(newProgress == 100){
                    progressBar.setVisibility(View.GONE);
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        // setting starting page
        String url = "http://www.google.com";
        webView.loadUrl(url);
        urlEdit.setText(url);


        // implementing pressing enter on keyboard
        urlEdit.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If user presses and releases enter. Action called by release.
                if ((event.getAction() == KeyEvent.ACTION_UP) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    go.performClick();
                    return true;
                }
                return false;
            }
        });

        // implementing browser buttons behaviour
        go.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String url = urlEdit.getText().toString();
                if(!url.startsWith("http"))
                    url = "http://" + url;
                webView.loadUrl(url);

                //Hide keyboard after clicking go
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(urlEdit.getWindowToken(), 0);
            }
        });

        fwd.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(webView.canGoForward()) {
                    // displaying in the address field current url
                    WebBackForwardList backForwardList = webView.copyBackForwardList();
                    urlEdit.setText(backForwardList.getItemAtIndex(backForwardList.getCurrentIndex()+1).getUrl());
                    // go forward
                    webView.goForward();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(webView.canGoBack()){
                    // displaying in the address field current url
                    WebBackForwardList backForwardList = webView.copyBackForwardList();
                    urlEdit.setText(backForwardList.getItemAtIndex(backForwardList.getCurrentIndex()-1).getUrl());
                    // go forward
                    webView.goBack();
                }
            }
        });

    }
}
