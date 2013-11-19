package com.corylucasjeffery.couponassistant.activities;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.corylucasjeffery.couponassistant.Coupon;
import com.corylucasjeffery.couponassistant.GlobalCart;
import com.corylucasjeffery.couponassistant.Item;
import com.corylucasjeffery.couponassistant.PhpWrapper;
import com.corylucasjeffery.couponassistant.R;
import com.onbarcode.barcode.android.AndroidColor;
import com.onbarcode.barcode.android.AndroidFont;
import com.onbarcode.barcode.android.IBarcode;
import com.onbarcode.barcode.android.UPCA;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class CheckoutActivity extends Activity implements View.OnClickListener {

    private ImageView couponImage;
    private ImageView generatedImage;

    private Context context;

    private final String TAG = "CHECKOUT";

    private boolean returnToMain = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        getImageViews();
        showImage();
    }

    public void getImageViews() {
        ImageView backButton = (ImageView) findViewById(R.id.footer_cart_back);
        ImageView forwardButton = (ImageView) findViewById(R.id.footer_cart_forward);
        ImageView discardButton = (ImageView) findViewById(R.id.footer_cart_discard);
        couponImage = (ImageView) findViewById(R.id.checkout_barcode_image);
        generatedImage = (ImageView) findViewById(R.id.checkout_barcode_generated);

        //listeners
        backButton.setOnClickListener(this);
        forwardButton.setOnClickListener(this);
        discardButton.setOnClickListener(this);
        couponImage.setOnClickListener(this);
        generatedImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.footer_cart_back:
                previousCoupon();
                return;
            case R.id.footer_cart_forward:
                nextCoupon();
                return;
            case R.id.footer_cart_discard:
                removeCoupon();
                return;
            case R.id.checkout_barcode_image:
                purchased();
                return;
            case R.id.checkout_barcode_generated:
                purchased();
                return;
            default:
        }
    }

    public void showImage() {
        GlobalCart cart = ((GlobalCart)getApplicationContext());
        if (!cart.isEmpty()) {
            Coupon c = cart.getCurrentCoupon();
            c.getConvertedImage(couponImage);
            generateBarcode(c);
        }
        else {
            purchased();
        }
    }

    public void previousCoupon() {
        GlobalCart cart = ((GlobalCart)getApplicationContext());
        if (cart.isEmpty()) {
            Toast.makeText(this, "cart is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        Coupon c = cart.getPreviousCoupon();
        Log.v(TAG, "prev upc:"+c.getUpc());
        if (c.getUpc().equals("empty")) {
            Toast.makeText(this, "at first coupon", Toast.LENGTH_SHORT).show();
            return;
        }
        c.getConvertedImage(couponImage);
        generateBarcode(c);
    }

    public void nextCoupon() {
        GlobalCart cart = ((GlobalCart)getApplicationContext());
        if (cart.isEmpty()) {
            Toast.makeText(this, "cart is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        Coupon c = cart.getNextCoupon();
        Log.v(TAG, "prev upc:"+c.getUpc());
        if (c.getUpc().equals("empty")) {
            Toast.makeText(this, "at last coupon", Toast.LENGTH_SHORT).show();
            return;
        }

        c.getConvertedImage(couponImage);
        generateBarcode(c);
    }

    public void removeCoupon() {
        GlobalCart cart = ((GlobalCart)getApplicationContext());
        cart.removeFromCart();
        if (cart.isEmpty()) {
            Toast.makeText(this, "cart is empty", Toast.LENGTH_SHORT).show();
            purchased();
        }
        else {
            Coupon c = cart.getCurrentCoupon();
            c.getConvertedImage(couponImage);
            generateBarcode(c);
        }
    }

    public void purchased() {
        GlobalCart cart = ((GlobalCart)getApplicationContext());
        // if there is a coupon in list
        if (!cart.isEmpty()) {
            // send statistics
            PhpWrapper db = new PhpWrapper();
            Coupon c = cart.getCurrentCoupon();
            Item i = cart.getCurrentItem();
            boolean success = db.purchaseItem(i.getUpc(), c.getUpc(), c.getExp());
            if (success) {
                Toast.makeText(this, "Submitted to db", Toast.LENGTH_SHORT).show();
            }
            //remove it
            cart.removeFromCart();
            //if there's more, display next one
            if (!cart.isEmpty()) {
                Coupon coupon = cart.getCurrentCoupon();
                coupon.getConvertedImage(couponImage);
                generateBarcode(c);
            }
        }
        // if there were no more to display, show cart finished.  next click will return to main
        if (cart.isEmpty() && !returnToMain) {
            Drawable drawable = getResources().getDrawable(R.drawable.thumbs_up);
            couponImage.setImageDrawable(drawable);

            Toast.makeText(this, "Shopping Cart Empty", Toast.LENGTH_LONG).show();
            returnToMain = true;
        }
        else if (cart.isEmpty() && returnToMain) {
            returnToMain = false;
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    public void onStop() {
        super.onStop();
        finish();
    }

    public void onDestroy() {
        super.onDestroy();
        finish();
    }

    private void generateBarcode(Coupon c) {
        ImageView v = (ImageView) findViewById(R.id.checkout_barcode_generated);
        Bitmap pallet = Bitmap.createBitmap(300,150,Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(pallet);
        UPCA barcode = new UPCA();
        String upc = c.getUpc();
        upc = upc.substring(0,11);
        barcode.setData(upc);
        barcode.setUom(IBarcode.UOM_PIXEL);
        barcode.setX(2f);
        barcode.setY(100f);
        barcode.setLeftMargin(0f);
        barcode.setRightMargin(0f);
        barcode.setTopMargin(0f);
        barcode.setBottomMargin(1f);
        barcode.setBarAlignment(1);
        barcode.setResolution(720);
        barcode.setShowText(true);
        barcode.setTextFont(new AndroidFont("Arial", Typeface.NORMAL, 12));
        barcode.setTextMargin(6);
        barcode.setTextColor(AndroidColor.black);
        barcode.setForeColor(AndroidColor.black);
        barcode.setBackColor(AndroidColor.white);
        RectF bounds = new RectF(0, 0, 0, 0);
        try {
            barcode.drawBarcode(cv, bounds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int w = getWindowManager().getDefaultDisplay().getWidth();
        int h = getWindowManager().getDefaultDisplay().getHeight();
        v.setImageBitmap(Bitmap.createScaledBitmap(pallet, w, h/2, false));
    }
}
