package com.hpa.dev.android.honnavigator;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.hpa.dev.android.honnavigator.lib.ServiceSettings;

// adb shell am start -n com.hpa.dev.android.honnavigator/.ActivitySplashScreen

public class ActivitySplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(ActivitySplashScreen.this,    ActivityScanner.class);
                startActivity(i);
                finish();
            }
        },SPLASH_TIME_OUT);
        // new ServiceSettings(getApplicationContext()).setValue("identity","H306101");
        // new ServiceSettings(getApplicationContext()).setValue("identity",null);
    }

}
