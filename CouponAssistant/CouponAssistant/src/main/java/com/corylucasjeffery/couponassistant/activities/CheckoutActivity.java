package com.corylucasjeffery.couponassistant.activities;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

public class CheckoutActivity extends Activity implements View.OnClickListener {

    private int imageIndex = 0;
    private int numImages = 0;

    private Context context;
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
        context = this;
        /*
        Button btnPrevious = (Button)findViewById(R.id.previous_btn);
        btnPrevious.setOnClickListener(this);
        Button btnNext = (Button)findViewById(R.id.next_btn);
        btnNext.setOnClickListener(this);
        */
        showImage();
    }

    private void showImage() {
        /*
        ImageView imgView = (ImageView) findViewById(R.id.myimage);
        imgView.setImageResource(mImageIds[imageIndex]);
        */
    }

    public void onClick(View v) {
        /*
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
        */
    }

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
