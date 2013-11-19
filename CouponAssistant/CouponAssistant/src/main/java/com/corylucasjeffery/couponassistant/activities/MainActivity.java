package com.corylucasjeffery.couponassistant.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.corylucasjeffery.couponassistant.BMPtoBlob;
import com.corylucasjeffery.couponassistant.CameraPreview;
import com.corylucasjeffery.couponassistant.DateChooserDialog;
import com.corylucasjeffery.couponassistant.ManualEntryDialog;
import com.corylucasjeffery.couponassistant.ParseUPC;
import com.corylucasjeffery.couponassistant.PhpWrapper;
import com.corylucasjeffery.couponassistant.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;
import java.util.concurrent.ExecutionException;



/*
    TODO Make Shopping Cart Activity with split screen barcode / image
        --ShowCouponsActivity saves image to local folder and appends fileName, upc#, barcode to sharedPrefs
        --Then CheckoutActivity reads the sharedPrefs and loads image full-screen
        --Tap to dismiss image and load next image from sharedPrefs until q empty.
        --Then post all upc's to bought
    TODO Make CouponView class to use in CouponViewAdapter for Shopping Cart Activity
    TODO Make Statistics Activity
    TODO Modify Coupon class and list_coupon.xml to show pertinent data
    TODO Make sure phpwrapper receives in DbShowCoupon correctly
 */

public class MainActivity extends FragmentActivity
        implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private final String TAG = "MAIN";
    private Camera mCamera = null;
    private CameraPreview mPreview = null;
    private String exp_date = "";
    private String upc = "";
    private String imageBlob;
    private Bitmap image;
    private int clicks = 0;
    private Context context;
    private FrameLayout preview;
    private BMPtoBlob imageConvert;
    public static final String PREFS_CART = "CouponShoppingCart";

    public static final String EXTRA_MESSAGE_UPC =
            "com.corylucasjeffery.couponassistant.activities.MESSAGE_UPC";
    public static final String EXTRA_MESSAGE_FILE_NAME =
            "com.corylucasjeffery.couponassistant.activities.MESSAGE_IMAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        initializeClickyThings();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
        preview.removeView(mPreview);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCamera == null)
        {
            mCamera = getCameraInstance();
            if(mCamera != null) {
                mPreview = new CameraPreview(this, mCamera);
            }
        }
        if (mPreview != null) {
            preview = (FrameLayout) findViewById(R.id.camera_preview);
            preview.addView(mPreview);
            mCamera.startPreview();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.scan_button){
            //When the scan button is clicked, a picture is taken of the coupon
            //to upload to the database. Once the picture has been received, the call
            //can be made to the barcode scanner for it to use the camera.
            Camera.PictureCallback mCall = new Camera.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] bytes, Camera camera) {
                    image = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    //instantiate ZXing integration class
                    IntentIntegrator scanIntegrator = new IntentIntegrator(MainActivity.this);
                    //start scanning
                    scanIntegrator.initiateScan();
                }
            };
            mCamera.takePicture(null, null, mCall);
        }
        else if (v.getId()==R.id.footer_shopping_cart) {
            openCart();
        }
        else if (v.getId()==R.id.footer_manual_add) {
            openManualEntry();
        }
    }

    public void initializeClickyThings() {
        Button scanButton = (Button) findViewById(R.id.scan_button);
        scanButton.setOnClickListener(this);

        ImageView cartImage = (ImageView) findViewById(R.id.footer_shopping_cart);
        cartImage.setOnClickListener(this);

        ImageView plusImage = (ImageView) findViewById(R.id.footer_manual_add);
        plusImage.setOnClickListener(this);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        upc = "";
        //retrieve result of scanning - instantiate ZXing object
        IntentResult scanningResult =
                IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        //check we have a valid result
        if (scanningResult != null) {
            upc = scanningResult.getContents();
            if (upc != "")
            {
                ParseUPC parser = new ParseUPC();
                int barcodeType = parser.determineBarcode(upc);
                // check UPC and determine action
                if (barcodeType == ParseUPC.UPC_IS_ITEM)
                {
                    storeItemShowCoupons();
                }
                else
                {
                    storeCoupon();
                }
            }
            else
                Toast.makeText(this, "Scan failed, retry", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.menu_settings:
                openSettings();
                return true;
            case R.id.menu_login:
                openLogin();
                return true;
            case R.id.menu_shopping_cart:
                openCart();
                return true;
            case R.id.menu_stats:
                openStats();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openSettings() {
        Intent intent = new Intent(context, SettingsActivity.class);
        startActivity(intent);
    }

    private void openLogin() {
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
    }

    private void openCart() {
        Intent intent = new Intent(context, CheckoutActivity.class);
        startActivity(intent);
    }

    private void openStats() {
        Log.v(TAG, "start statistics");
        Intent intent = new Intent(context, StatisticsActivity.class);
        startActivity(intent);
    }

    private void openManualEntry() {
        ManualEntryDialog dialog = new ManualEntryDialog();
        dialog.show(getFragmentManager(), "manualEntry");
    }

    public void storeItem() {
        PhpWrapper db = new PhpWrapper();
        boolean success = db.submitItem(upc);
        if (success)
            Toast.makeText(this, "Item submitted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Item failed to submit", Toast.LENGTH_LONG).show();
    }

    public void storeItemShowCoupons() {
        storeItem();

        //open coupon activity
        Intent intent = new Intent(context, ShowCouponsActivity.class);
        String message = upc;
        intent.putExtra(EXTRA_MESSAGE_UPC, message);
        String file = "";
        intent.putExtra(EXTRA_MESSAGE_FILE_NAME, file);
        startActivity(intent);
    }

    public void storeCoupon() {
        //Begin image conversion so it can be working while user is selecting date
        this.imageConvert = new BMPtoBlob(image);
        this.imageConvert.execute();
        //popup, ask for exp-date
        Log.d(TAG, "Date chooser beginning.");
        DateChooserDialog dpd = new DateChooserDialog();
        dpd.show(getFragmentManager(), "DatePicker");
        // when picker finishes, it calls submitCoupon
    }

    public void submitCoupon() {
        PhpWrapper db = new PhpWrapper();
        Log.v(TAG, "upc in submit:"+upc);
        try {
            imageBlob = this.imageConvert.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"Submitting coupon.");
        boolean success = db.submitCoupon(upc, exp_date, imageBlob);
        if (success)
            Toast.makeText(this, "Coupon submitted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Error, coupon not submitted", Toast.LENGTH_LONG).show();
    }

    public void getItemsFromCoupon() {
        //TODO implement getItemsFromCoupon()

    }

    //override of DateChooserDialog method
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        if (clicks == 0) {
            String date =   Integer.toString(year) + "-" +
                            Integer.toString(month+1) + "-" +
                            Integer.toString(day);
            exp_date = date;
            submitCoupon();
            clicks++;
        }
        else  {
            clicks = 0;
        }
    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }
}
