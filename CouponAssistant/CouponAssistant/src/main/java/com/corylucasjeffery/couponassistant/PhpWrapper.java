package com.corylucasjeffery.couponassistant;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class PhpWrapper {

    private final String TAG = "PHP";
    boolean connected;
    UserInfo user;

    public PhpWrapper() {
        if(user == null)
            user = new UserInfo("user1", "pass1", "name", "name");
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

    public ArrayList<Coupon> getCoupons(String itemUpc) {
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        String fakeExp = "2013/12/31";
        Coupon c = new Coupon(itemUpc, fakeExp);
        coupons.add(c);
        coupons.add(c);
        return coupons;
    }

    public void getItems(String couponUPC) {
        //TODO implement getItems()
        Log.v(TAG, "getItems() not implemented yet");
    }

    public boolean submitLogin(String user, String pass, String first, String last, Activity activity) {
        DbUserRegister dbUser = new DbUserRegister(user, pass, first, last, activity);
        boolean result = false;
        try {
            result = dbUser.execute().get();
        } catch (InterruptedException ie) {
            Log.v(TAG, "Interrupted Exception");
        } catch (ExecutionException ee) {
            Log.v(TAG, "Execution Exception");
        }

        return result;
    }
}
