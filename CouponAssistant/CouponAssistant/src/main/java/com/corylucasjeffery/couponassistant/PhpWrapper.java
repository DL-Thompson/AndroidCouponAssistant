package com.corylucasjeffery.couponassistant;

import android.util.Log;

public class PhpWrapper {

    private final String TAG = "PHP";
    boolean connected;
    UserInfo user;

    public PhpWrapper() {
        user = new UserInfo("user1", "pass1");
        connected = true;
    }

    public void disconnect() { connected = false; }
    public void connect() { connected = true; }

    public void submitItem(String upc) {
        if(connected) {
            DbSubmitItem submitItem = new DbSubmitItem(user.getUserName(), user.getPass(), upc);
            submitItem.execute();
        }
    }

    public void submitCoupon(String upc, String exp_date, String image) {
        if(connected) {
            if (image == null)
                image = "image-file-not-found";
            DbSubmitCoupon submitCoupon = new DbSubmitCoupon(user.getUserName(), user.getPass(), upc, exp_date, image);
            submitCoupon.execute();
        }
    }

    public void getCoupons(String itemUpc) {
        //TODO implement getCoupons()
        Log.v(TAG, "getCoupons() not implemented yet");
    }

    public void getItems(String couponUPC) {
        //TODO implement getItems()
        Log.v(TAG, "getItems() not implemented yet");
    }
}
