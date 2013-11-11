package com.corylucasjeffery.couponassistant.activities;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.corylucasjeffery.couponassistant.DateChooserDialog;
import com.corylucasjeffery.couponassistant.ParseUPC;
import com.corylucasjeffery.couponassistant.PhpWrapper;
import com.corylucasjeffery.couponassistant.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends FragmentActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private final String TAG = "MAIN";

    private String exp_date = "";
    private String upc = "";

    private int clicks = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button scanButton = (Button) findViewById(R.id.scan_button);

        scanButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.scan_button){
            //instantiate ZXing integration class
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            //start scanning
            scanIntegrator.initiateScan();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //super.onActivityResult(requestCode, resultCode, intent);

        //retrieve result of scanning - instantiate ZXing object
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        //check we have a valid result
        if (scanningResult != null) {
            upc = scanningResult.getContents();

            ParseUPC parser = new ParseUPC();
            int barcodeType = parser.determineBarcode(upc);

            if (barcodeType == ParseUPC.UPC_IS_ITEM)
            {
                storeItem();
            }
            else
            {
                storeCoupon();
            }
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
            case R.id.action_search:
                openSearch();
                return true;
            case R.id.action_settings:
                openSettings();
                return true;
            case R.id.action_refresh:
                openRefresh();
                return true;
            case R.id.settings_login:
                openLogin();
                return true;
            case R.id.settings_shopping_cart:
                openCart();
                return true;
            case R.id.settings_stats:
                openStats();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openSearch() {
        Toast.makeText(this, "Search", Toast.LENGTH_LONG).show();
    }

    private void openSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void openRefresh() {
        Toast.makeText(this, "Refresh", Toast.LENGTH_LONG).show();
    }

    private void openLogin() {
        Toast.makeText(this, "Login", Toast.LENGTH_LONG).show();
    }

    private void openCart() {
        Toast.makeText(this, "Shopping Cart", Toast.LENGTH_LONG).show();
    }

    private void openStats() {
        Toast.makeText(this, "Statistics", Toast.LENGTH_LONG).show();
    }

    public void storeItem() {
        PhpWrapper db = new PhpWrapper();
        db.disconnect();
        db.submitItem(upc);
        Toast.makeText(this, "Item submitted", Toast.LENGTH_LONG).show();
    }

    public void storeCoupon() {
        //popup, ask for exp-date
        DateChooserDialog dpd = new DateChooserDialog();
        //DialogFragment fragment = dpd;
        Log.v(TAG, "after fragment");
        dpd.show(getFragmentManager(), "DatePicker");
        // when picker finishes, it calls submitCoupon
    }

    public void submitCoupon() {
        PhpWrapper db = new PhpWrapper();
        db.disconnect();
        db.submitCoupon(upc, exp_date, null);
        Toast.makeText(this, "Coupon submitted", Toast.LENGTH_LONG).show();
    }

    public void getCouponsFromItem() {
        //TODO implement getCouponsFromItem()
    }

    public void getItemsFromCoupon() {
        //TODO implement getItemsFromCoupon()
    }

    //override of DateChooserDialog method
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        if (clicks == 0) {
            Log.v(TAG, "Pressed: "+Integer.toString(year)+ " " +Integer.toString(month) + " " + Integer.toString(day));
            String date = Integer.toString(year) + "/" + Integer.toString(month) + "/" + Integer.toString(day);
            Log.v(TAG, "after listener: "+date);
            exp_date = date;
            submitCoupon();
            clicks++;
        }
        else  {
            clicks = 0;
        }

    }
}
