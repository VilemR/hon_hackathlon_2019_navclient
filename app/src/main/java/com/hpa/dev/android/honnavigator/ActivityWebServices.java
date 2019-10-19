package com.hpa.dev.android.honnavigator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class ActivityWebServices extends Activity {

    // adb shell am start -n com.hpa.dev.android.honnavigator/.ActivityWebServices --es "url" "http://10.0.2.2//phpdev/PHP.MPRJ.002_HonNavigator/services/s005_personal_door_lock.php" -t "text/plain"
    // adb shell am start -n com.hpa.dev.android.honnavigator/.ActivityWebServices --es "url" "http://10.0.2.2/phpdev/PHP.MPRJ.002_HonNavigator/services/s004_req_printer_supply.php" -t "text/plain"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String url = null;
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        if (url.contentEquals("TEST")){
            url = "http://10.0.2.2//phpdev/PHP.MPRJ.002_HonNavigator/services/s005_personal_door_lock.php";
        }
        url = (url == null) ? "http://10.0.2.2/phpdev/PHP.MPRJ.002_HonNavigator/index.php" : url;
        WebView webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUserAgentString("Desktop");
        webView.loadUrl(url);
    }
}

