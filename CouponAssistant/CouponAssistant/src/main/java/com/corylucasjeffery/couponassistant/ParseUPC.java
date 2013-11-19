package com.corylucasjeffery.couponassistant;

import android.util.Log;

public class ParseUPC {
    public static final int UPC_IS_ITEM = 1;
    public static final int UPC_IS_COUPON = 2;

    private static final String TAG = "PARSE";

    public ParseUPC() {}

    public int determineBarcode(String upc) {
        int barcodeStyle;
        int leadingDigit = Integer.parseInt(upc.substring(0, 1));
        Log.v(TAG, "barcode: "+upc);
        Log.v(TAG, "leading digit: "+leadingDigit);
        switch(leadingDigit)
        {
            case 5:
                barcodeStyle = UPC_IS_COUPON;
                break;
            case 9:
                barcodeStyle = UPC_IS_COUPON;
                break;
            default:
                barcodeStyle = UPC_IS_ITEM;
                break;
        }
        Log.v(TAG, "style: "+barcodeStyle);
        return barcodeStyle;
    }

    public String getValueCode(String upc) {
        if (upc.equals("empty")) {
            return "--";
        }
        String valueCode = upc.substring(9, 11);
        return valueCode;
    }
}
