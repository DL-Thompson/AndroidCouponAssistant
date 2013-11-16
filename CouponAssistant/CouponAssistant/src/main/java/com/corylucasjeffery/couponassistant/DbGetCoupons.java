package com.corylucasjeffery.couponassistant;


import android.app.ListActivity;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DbGetCoupons extends AsyncTask<String, Void, ArrayList<Coupon>> {
    private String POST_COUPON_URL = "http://dlthompson81.byethost24.com/CouponPHP/form_code/get_coupon.php";

    private final String TAG = "GET-COUP";
    private String username;
    private String password;
    private String barcode;

    private ListActivity parent;

    public DbGetCoupons(String username, String password, String barcode, ListActivity activity)  {
        this.username = username;
        this.password = password;
        this.barcode = barcode;
        this.parent = activity;
    }

    @Override
    protected ArrayList<Coupon> doInBackground(String... params) {
        ArrayList<Coupon> coupons = new ArrayList<Coupon>();
        Log.v(TAG, "Started do in background");
        try {
            //Prepare the post values
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("username", this.username));
            parameters.add(new BasicNameValuePair("password", this.password));
            parameters.add(new BasicNameValuePair("barcode", this.barcode));

            //Create the connection to the website
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(POST_COUPON_URL);
            //Load parameters
            post.setEntity(new UrlEncodedFormEntity(parameters));
            //Send parameters and get the response
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            InputStream message = entity.getContent();

            //Convert returned data to a string
            BufferedReader bReader = new BufferedReader(new InputStreamReader(message, "iso-8859-1"));
            StringBuilder sBuilder = new StringBuilder();
            String line = null;
            while ((line = bReader.readLine()) != null) {
                sBuilder.append(line + "\n");
            }
            message.close();
            String result = sBuilder.toString();
            Log.v(TAG, "got result");
            //Grab data from JSON string
            JSONObject jObject = new JSONObject(result);
            int success = jObject.getInt("success");
            Log.v(TAG, "success code: "+success);
            String jmessage = jObject.getString("message");
            Log.d(TAG, "message: "+jmessage);
            if (success == PhpWrapper.SUCCESS_VALUE){


            }
            else
            {
                Log.d(TAG, "not found");
                String emptyBarcode = "";
                String emptyDate = "";
                String emptyDescription = "No coupons found";
                Coupon c = new Coupon(emptyBarcode, emptyDate, emptyDescription);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return coupons;
    }

    @Override
    protected void onPreExecute() {
        ProgressBarHelper.initializeProgressBar(parent);
    }

    @Override
    protected void onPostExecute(ArrayList<Coupon> result) {
        //super.onPostExecute(result);
        ProgressBarHelper.closeProgressBar();
    }
}
