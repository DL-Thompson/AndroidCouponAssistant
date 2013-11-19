package com.corylucasjeffery.couponassistant;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.onbarcode.barcode.android.AndroidColor;
import com.onbarcode.barcode.android.AndroidFont;
import com.onbarcode.barcode.android.IBarcode;
import com.onbarcode.barcode.android.UPCA;

public class BarcodeGenerator {

        private ImageView iv;
        private String barcode_number;
        private Bitmap barcode_image;

        public BarcodeGenerator(ImageView iv, String barcode) {
            this.iv = iv;
            this.barcode_number = barcode;
        }

        public void getGeneratedBarcode() {
            //Create an image for drawing the barcode
            barcode_image = Bitmap.createBitmap(230,150, Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(barcode_image);

            //Create newly drawn barcode
            UPCA barcode = new UPCA();
            barcode.setData(barcode_number);
            barcode.setUom(IBarcode.UOM_PIXEL);
            barcode.setX(2f);
            barcode.setY(100f);
            barcode.setLeftMargin(0f);
            barcode.setRightMargin(0f);
            barcode.setTopMargin(0f);
            barcode.setBottomMargin(1f);
            barcode.setBarAlignment(1);
            barcode.setResolution(720);
            barcode.setShowText(true);
            barcode.setTextFont(new AndroidFont("Arial", Typeface.NORMAL, 12));
            barcode.setTextMargin(6);
            barcode.setTextColor(AndroidColor.black);
            barcode.setForeColor(AndroidColor.black);
            barcode.setBackColor(AndroidColor.white);
            RectF bounds = new RectF(0,0,0,0);
            try {
                barcode.drawBarcode(c, bounds);
            } catch (Exception e) {
                e.printStackTrace();
            }
            iv.setImageBitmap(barcode_image);
        }
    }
