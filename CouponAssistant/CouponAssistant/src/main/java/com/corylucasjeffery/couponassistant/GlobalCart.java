package com.corylucasjeffery.couponassistant;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;


public class GlobalCart extends Application {
    private ArrayList<Coupon> couponsInCart = new ArrayList<Coupon>();
    private ArrayList<Item> itemsInCart = new ArrayList<Item>();
    private int cartSize = 0;
    private int cartIndex = 0;

    private final String TAG = "global";

    public boolean isEmpty() {
        return cartSize == 0;
    }

    public void addToCart(Coupon c, Item i) {
        couponsInCart.add(c);
        itemsInCart.add(i);
        cartSize++;
    }

    public void removeFromCart() {
        if(cartSize != 0)
        {
            Coupon c = getCurrentCoupon();
            Item i = getCurrentItem();
            couponsInCart.remove(c);
            itemsInCart.remove(i);
            cartSize--;
        }
    }

    public Coupon getNextCoupon() {
        Coupon returnC;
        if (cartIndex < cartSize-1) {
            returnC = couponsInCart.get(cartIndex);
            cartIndex++;
        }
        else {
            returnC = new Coupon("empty", "empty", "empty");
        }

        return returnC;
    }

    public Coupon getPreviousCoupon() {
        Coupon returnC;
        Log.v(TAG, "get prev cartSize: "+Integer.toString(cartSize)+" cartIndex: "+Integer.toString(cartIndex));
        if (cartIndex > 0) {
            returnC = couponsInCart.get(cartIndex);
            cartIndex--;
        }
        else {
            returnC = new Coupon("empty", "empty", "empty");
        }

        return returnC;
    }

    public Coupon getCurrentCoupon() {
        Coupon returnC;
        if (cartIndex >= 0 && cartIndex < cartSize) {
            returnC = couponsInCart.get(cartIndex);
        }
        else {
            returnC = new Coupon("empty", "empty", "empty");
        }

        return returnC;
    }

    public Item getCurrentItem() {
        Item returnI;
        if (cartIndex >= 0 && cartIndex < cartSize) {
            returnI = itemsInCart.get(cartIndex);
        }
        else {
            returnI = new Item("empty");
        }
        return returnI;
    }

    public void emptyCart() {
        cartIndex = 0;
        cartSize = 0;
        couponsInCart.clear();
        itemsInCart.clear();
    }
}
