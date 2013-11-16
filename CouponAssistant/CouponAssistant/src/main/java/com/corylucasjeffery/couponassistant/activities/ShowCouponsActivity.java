package com.corylucasjeffery.couponassistant.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListActivity;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.corylucasjeffery.couponassistant.Coupon;
import com.corylucasjeffery.couponassistant.CouponAdapter;
import com.corylucasjeffery.couponassistant.PhpWrapper;
import com.corylucasjeffery.couponassistant.R;

import java.util.ArrayList;

public class ShowCouponsActivity extends ListActivity {

    public final String TAG = "SH_COUP";
    private Context context;
    private String upc;
    private String imageFileName;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        Log.v(TAG, "Show coupons started");
        setContentView(R.layout.activity_show_coupons);

        Log.v(TAG, "Loaded content");

        //get the upc from intent
        Intent intent = getIntent();
        upc = intent.getStringExtra(MainActivity.EXTRA_MESSAGE_UPC);
        imageFileName = intent.getStringExtra(MainActivity.EXTRA_MESSAGE_FILE_NAME);

        PhpWrapper db = new PhpWrapper();
        //setup the list display
        ArrayList<Coupon> coupons = db.getCoupons(upc, this);
        CouponAdapter adapter = new CouponAdapter(this, R.layout.list_coupon, coupons);
        setListAdapter(adapter);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Do something when a list item is clicked
        Toast.makeText(context, "Loading Coupon to Cart", Toast.LENGTH_LONG).show();
        Coupon selectedCoupon = (Coupon) l.getAdapter().getItem(position);

        // add to shopping cart
        SharedPreferences cart = getSharedPreferences(MainActivity.PREFS_CART, 0);
        SharedPreferences.Editor editor = cart.edit();
        String imageFileName = "";
        editor.putString(selectedCoupon.getUpc(), imageFileName);
        editor.commit();

        // go back to scan page
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }
}
