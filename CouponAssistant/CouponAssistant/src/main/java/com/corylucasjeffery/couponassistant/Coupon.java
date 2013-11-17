package com.corylucasjeffery.couponassistant;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;


public class Coupon {
    private String upc;
    private String exp_date;
    private String discount="";
    private String limitations="";
    private String image;

    private final String TAG = "COUPON";

    public Coupon(String upc, String exp_date, String img) {
        this.upc = upc;
        this.exp_date = exp_date;

        ValueCodeDict valDict = new ValueCodeDict();
        ParseUPC parse = new ParseUPC();

        String valueCode = parse.getValueCode(upc);
        String tempDiscount = valDict.getValue(valueCode);

        this.discount = valDict.extractDiscount(tempDiscount);
        this.limitations = valDict.extractLimitations(tempDiscount);

    }

    public String getUpc() { return upc; }
    public String getExp() { return exp_date; }
    public String getDisc() { return discount; }
    public String getLimits() { return limitations; }
    public String getImage() { return image; }
}
