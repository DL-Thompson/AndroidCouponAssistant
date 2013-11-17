package com.corylucasjeffery.couponassistant;


import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class BMPtoBlob extends AsyncTask<String, String, String>{

    private Bitmap image;
    private boolean completed = false;
    private String image_blob;

    public BMPtoBlob(Bitmap image) {
        this.image = image;
    }
    @Override
    protected String doInBackground(String... strings) {
        //Convert bmp to image_blob
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] b = byteArrayOutputStream.toByteArray();
        this.image_blob = Base64.encodeToString(b, Base64.DEFAULT);
        return this.image_blob;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        completed = true;
    }

    public boolean isConverted() {
        return this.completed;
    }

    public String getImage() {
        return this.image_blob;
    }
}
