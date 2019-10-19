package com.hpa.dev.android.honnavigator;

/*
adb shell am start -n com.hpa.dev.android.honnavigator/.ActivityMapLayout

adb shell am start -n com.hpa.dev.android.honnavigator/.ActivityMapLayout --es "token" "B2.2_COPYC" -t "text/plain"
adb shell am start -n com.hpa.dev.android.honnavigator/.ActivityMapLayout --es "token" "A2.8_CR_PANDORA" -t "text/plain"
adb shell am start -n com.hpa.dev.android.honnavigator/.ActivityMapLayout --es "token" "A2.8_CR_THALIA" -t "text/plain"

*/

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.ImageView;

import com.hpa.dev.android.honnavigator.lib.CropArea;


public class ActivityMapLayout extends AppCompatActivity {
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_layout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent intent = getIntent();
        String token = intent.getStringExtra("token");
        token = (token == null) ? "A2.8_CR_THALIA" : token;
        System.err.println("Token received : " + token);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int maxY = displayMetrics.heightPixels;
        int maxX = displayMetrics.widthPixels;

        mImageView = (ImageView) findViewById(R.id.imageView2);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.company_cz27_layout_iinp);
        Bitmap cropMapLayout = new CropArea(maxX, maxY, token).crop(bmp);
        mImageView.setImageBitmap(cropMapLayout);
    }

}
