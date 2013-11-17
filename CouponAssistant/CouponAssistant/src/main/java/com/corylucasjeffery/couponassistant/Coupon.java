package com.corylucasjeffery.couponassistant;

import android.util.Log;


public class Coupon {
    private String upc;
    private String exp_date;
    private String discount="";
    private String limitations="";
    private String image_blob="";

    private final String TAG = "COUPON";

    public Coupon(String upc, String exp_date, String image) {
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
    public String getImage() { return image_blob; }
    public String getDisc() { return discount; }
    public String getLimits() { return limitations; }
}
