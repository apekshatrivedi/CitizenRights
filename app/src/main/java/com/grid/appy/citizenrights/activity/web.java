package com.grid.appy.citizenrights.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.grid.appy.citizenrights.R;

public class web extends AppCompatActivity {
  private WebView wbv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        wbv = (WebView) findViewById(R.id.myweb);

        wbv.getSettings().setJavaScriptEnabled(true);
        wbv.setWebViewClient(new WebViewClient());
        wbv.loadUrl("http://192.168.43.10/registration/register.php");
    }
}

