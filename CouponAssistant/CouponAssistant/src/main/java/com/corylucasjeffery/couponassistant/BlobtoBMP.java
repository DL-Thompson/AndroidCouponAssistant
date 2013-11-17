package com.corylucasjeffery.couponassistant;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;

public class BlobtoBMP extends AsyncTask<String, String, Bitmap>{

    private String imageBlob;

    public BlobtoBMP(String image) {
        this.imageBlob = image;
    }
    @Override
    protected Bitmap doInBackground(String... strings) {
        byte[] decodeByte = Base64.decode(this.imageBlob, 0);
        Bitmap image = BitmapFactory.decodeByteArray(decodeByte, 0, decodeByte.length);
        return image;
    }
}
