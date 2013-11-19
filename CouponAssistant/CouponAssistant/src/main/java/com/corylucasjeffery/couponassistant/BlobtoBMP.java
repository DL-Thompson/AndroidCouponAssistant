package com.corylucasjeffery.couponassistant;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.ImageView;

public class BlobtoBMP extends AsyncTask<String, String, Bitmap>{

    private ImageView iv;
    private String imageBlob;
    private Bitmap image;

    public BlobtoBMP(ImageView iv, String image) {
        this.imageBlob = image;
        this.iv = iv;
    }
    @Override
    protected Bitmap doInBackground(String... strings) {
        byte[] decodeByte = Base64.decode(this.imageBlob, 0);
        image = BitmapFactory.decodeByteArray(decodeByte, 0, decodeByte.length);
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        iv.setImageBitmap(image);
    }
}
