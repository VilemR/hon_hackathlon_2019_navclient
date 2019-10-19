package com.hpa.dev.android.honnavigator;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.hpa.dev.android.honnavigator.lib.ServiceSettings;

public class ActivityScanner extends AppCompatActivity {

    // adb shell am start -n com.hpa.dev.android.honnavigator/.ActivityScanner

    ImageView imageView;
    Button btnScan;
    public final static int QRcodeWidth = 350;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_scanner);
        imageView = (ImageView) findViewById(R.id.imageView);
        btnScan = (Button) findViewById(R.id.btnScan);

        String identity = new ServiceSettings(getApplicationContext()).getValue("identity");
        if (identity != null) {
            TextView identityText = (TextView)findViewById(R.id.textView2);
            identityText.setText(identity);
            try {
                bitmap = TextToImageEncode(identity);
                imageView.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        } else {
            Bitmap contact_pic = BitmapFactory.decodeResource(getResources(), R.mipmap.company_sqare_logo);
            imageView.setImageBitmap(contact_pic);
        }

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(ActivityScanner.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });
    }

    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );
        } catch (IllegalArgumentException Illegalargumentexception) {
            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();
        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];
        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;
            for (int x = 0; x < bitMatrixWidth; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.QRCodeBlackColor) : getResources().getColor(R.color.QRCodeWhiteColor);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels, 0, 350, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.e("Scan*******", "Cancelled scan");
            } else {
                Log.e("Scan", "Scanned");
                // tv_qr_readTxt.setText(result.getContents());
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                routeRequest(result.getContents());
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    void routeRequest(String query) {
        if (query.startsWith("http:/")) {
            Intent myIntent = new Intent(ActivityScanner.this, ActivityWebServices.class);
            myIntent.putExtra("url", query); //Optional parameters
            ActivityScanner.this.startActivity(myIntent);
        } else if (query.startsWith("identity:")) {
            new ServiceSettings(getApplicationContext()).setValue("identity", query.split(":")[1]);
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        } else if (query != null) {
            Intent myIntent = new Intent(ActivityScanner.this, ActivityMapLayout.class);
            myIntent.putExtra("token", query); //Optional parameters
            ActivityScanner.this.startActivity(myIntent);
        } else {
            // Do nothing
        }
    }

}
