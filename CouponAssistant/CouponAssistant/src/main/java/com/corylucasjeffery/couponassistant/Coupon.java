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
    private Bitmap image;

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

        byte[] decodeByte = Base64.decode(img, 0);
        image = BitmapFactory.decodeByteArray(decodeByte, 0, decodeByte.length);
    }

    public String getUpc() { return upc; }
    public String getExp() { return exp_date; }
    public String getDisc() { return discount; }
    public String getLimits() { return limitations; }
    public Bitmap getImage() { return image; }
}
