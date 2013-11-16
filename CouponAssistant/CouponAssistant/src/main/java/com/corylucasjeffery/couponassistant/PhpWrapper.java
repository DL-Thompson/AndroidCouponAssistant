package com.corylucasjeffery.couponassistant;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class PhpWrapper {

    public static final int SUCCESS_VALUE = 1;

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

    public Boolean submitItem(String upc) {
        boolean result = false;
        if(connected) {
            DbSubmitItem submitItem = new DbSubmitItem(user.getUserName(), user.getPass(), upc);

            try {
                result = submitItem.execute().get();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            } catch (ExecutionException ee) {
                ee.printStackTrace();
            }
        }

        return result;
    }

    public Boolean submitCoupon(String upc, String exp_date, String image) {
        boolean result = false;

        if (connected) {
            DbSubmitCoupon submitCoupon = new DbSubmitCoupon(user.getUserName(), user.getPass(), upc, exp_date, image);

            try {
                result = submitCoupon.execute().get();
            } catch (InterruptedException ie) {
                Log.v(TAG, "Interrupted Exception");
            } catch (ExecutionException ee) {
                Log.v(TAG, "Execution Exception");
            }
        }
        return result;
    }

    public ArrayList<Coupon> getCoupons(String itemUpc) {
        if (connected) {
        }


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

    public boolean submitLogin(String user, String pass, String first, String last) {
        DbUserRegister dbUser = new DbUserRegister(user, pass, first, last);
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
