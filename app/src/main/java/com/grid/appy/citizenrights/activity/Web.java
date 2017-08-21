package com.grid.appy.citizenrights.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.grid.appy.citizenrights.R;

public class Web extends AppCompatActivity {
    private WebView wbv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.web);
        wbv = (WebView) findViewById(R.id.web);

        wbv.getSettings().setJavaScriptEnabled(true);
        wbv.setWebViewClient(new WebViewClient());
        wbv.loadUrl("http://192.168.0.119/registration/register.php");
    }
}

