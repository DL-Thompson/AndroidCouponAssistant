package com.corylucasjeffery.couponassistant.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.View;

import com.onbarcode.barcode.android.AndroidColor;
import com.onbarcode.barcode.android.AndroidFont;
import com.onbarcode.barcode.android.IBarcode;
import com.onbarcode.barcode.android.UPCA;

public class AndroidBarcodeView extends View {

    private String upc;

    public AndroidBarcodeView(Context context, String upc) {
        super(context);
        this.upc = upc;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            androidUPC(canvas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void androidUPC(Canvas canvas) throws Exception {
        UPCA barcode = new UPCA();

        /*
           UPC-A Valid data char set:
                0, 1, 2, 3, 4, 5, 6, 7, 8, 9 (Digits)

           UPC-A Valid data length: 11 digits only, excluding the last checksum digit
        */
        barcode.setData(upc);

        // for UPC-A with supplement data (2 or 5 digits)
        /*
        barcode.setSupData("12");
        // supplement bar height vs bar height ratio
        barcode.setSupHeight(0.8f);
        // space between barcode and supplement barcode (in pixel)
        barcode.setSupSpace(15);
        */

        // Unit of Measure, pixel, cm, or inch
        barcode.setUom(IBarcode.UOM_PIXEL);
        // barcode bar module width (X) in pixel
        barcode.setX(1f);
        // barcode bar module height (Y) in pixel
        barcode.setY(45f);

        // barcode image margins
        barcode.setLeftMargin(10f);
        barcode.setRightMargin(10f);
        barcode.setTopMargin(10f);
        barcode.setBottomMargin(10f);

        // barcode image resolution in dpi
        barcode.setResolution(72);

        // disply barcode encoding data below the barcode
        barcode.setShowText(true);
        // barcode encoding data font style
        barcode.setTextFont(new AndroidFont("Arial", Typeface.NORMAL, 10));
        // space between barcode and barcode encoding data
        barcode.setTextMargin(6);
        barcode.setTextColor(AndroidColor.black);

        // barcode bar color and background color in Android device
        barcode.setForeColor(AndroidColor.black);
        barcode.setBackColor(AndroidColor.white);

        /*
        specify your barcode drawing area
	    */
        RectF bounds = new RectF(50, 30, 0, 0);
        barcode.drawBarcode(canvas, bounds);
    }
}
