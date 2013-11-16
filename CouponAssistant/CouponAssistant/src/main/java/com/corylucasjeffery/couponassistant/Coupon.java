package com.corylucasjeffery.couponassistant;

/**
 * Created by cory on 11/11/13.
 */
public class Coupon {
    private String upc;
    private String exp_date;
    private String discount;
    private String limitations;

    public Coupon(String upc, String exp_date, String description) {
        this.upc = upc;
        this.exp_date = exp_date;
        this.discount = "10%";
        this.limitations = description;
    }

    public String getUpc() { return upc; }
    public String getExp() { return exp_date; }
    public String getDisc() { return discount; }
    public String getLimits() { return limitations; }
}
