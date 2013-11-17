package com.corylucasjeffery.couponassistant.activities;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.ImageView;

import com.corylucasjeffery.couponassistant.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class CheckoutActivity extends Activity {

    private int imageIndex = 0;
    private int numImages = 0;

    private Context context;

    private ArrayList<String> upcs = new ArrayList<String>();
    /*
    private Integer[] mImageIds = {
            R.raw.image1,
            R.raw.image2,
            R.raw.image3
    };
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        /*
        context = this;
        upcs.add("123456789012");
        upcs.add("210987654321");
        */
        //showImage();
       // AndroidBarcodeView view = new AndroidBarcodeView(this, "123456789012");
        //setContentView(view);
    }
    /*
    private void showImage() {

        drawBarcode(upcs.get(0));
        //ImageView imgView = (ImageView) findViewById(R.id.checkout_barcode_image);
        //BitmapDrawable mDrawable = getDrawable("123456789012");
        //int imageResource = getResources().getIdentifier(getPackageName()+":drawable/"+mDrawable.toString(), null, null);

        if (mDrawable != null) {
            imgView.setImageDrawable(mDrawable);
        }
        else {
            imgView.setImageDrawable(getResources().getDrawable(R.drawable.poop));
        }

    }
    */
    /*
    private void drawBarcode(String upc) {
        AndroidBarcodeView view = new AndroidBarcodeView(context, upc);
        setContentView(view);
    }
    */
    /*
    private BitmapDrawable getDrawable(String upc) {
        AndroidBarcodeView view = new AndroidBarcodeView(context, upc);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        BitmapDrawable mDrawable = new BitmapDrawable(getResources(), bitmap);
        return mDrawable;
    }
    */
    /*
    public void onClick(View v) {

        switch (v.getId()) {
            case (R.id.previousBtn):
                imageIndex--;
                if (imageIndex == -1) {
                    imageIndex = numImages - 1;
                }
                showImage();
                break;
            case (R.id.nextBtn):
                imageIndex++;
                if (imageIndex == numImages) {
                    imageIndex = 0;
                }
                showImage();
                break;
        }
    }
    */

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }

    public void onStop() {
        super.onStop();
        finish();
    }

    public void onDestroy() {
        super.onDestroy();
        finish();
    }
}
