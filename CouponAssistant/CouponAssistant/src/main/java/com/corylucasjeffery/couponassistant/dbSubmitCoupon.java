package com.corylucasjeffery.couponassistant;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

public class DbSubmitCoupon extends AsyncTask<String, Void, String> {

    private ActionBarActivity parent;
    private Intent currentIntent;

	private String barcode;
	private String expiration;
	private String login;
	private String pword;
	private String image;

    private static String TAG = "DBSubmit";

    private static String host = "http://dlthompson81.byethost24.com";
    private static String folder = "/CouponPHP/forms/";
    private static String script = "submit_coupon_form.php";
	private static String url_submit_coupon = host+folder+script;


    @Override
    protected String doInBackground(String... params) {
        Log.v(TAG, "do in background");
        postCoupon();
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.v(TAG, "post execute");
        parent.setResult(Activity.RESULT_OK, currentIntent);
    }

    public DbSubmitCoupon(String barcode, String exp, String login, String pass, String image, ActionBarActivity activity) {
        this.barcode = barcode;
        this.expiration = exp;
        this.login = login;
        this.pword = pass;
        this.image = image;
        this.parent = activity;
        currentIntent = activity.getIntent();
    }

    public class Submit {
        private String username;
        private String password;
        private String barcode;
        private String exp_date;
        private String image_blob;

        public Submit(String user, String pass, String code, String date, String img) {
            this.username = user;
            this.password = pass;
            this.barcode = code;
            this.exp_date = date;
            this.image_blob = img;
        }
    }
	public void postCoupon(){
		Log.v(TAG, "post coupon");

	    HttpParams myParams = new BasicHttpParams();
	    HttpConnectionParams.setConnectionTimeout(myParams, 10000);
	    HttpConnectionParams.setSoTimeout(myParams, 10000);
	    HttpParams HttpConnectionParams = null;
	    //????
		HttpClient httpclient = new DefaultHttpClient(HttpConnectionParams);

        Submit submit = new Submit(login, pword, barcode, expiration, image);
        //String submit = "Login:"+ login + "Password:"+ pword + "Bar Code:"+ barcode + "Coupon Expiration Date:" + expiration + "Coupon Image Blob:" + image;
        //String submit = "Login:"+ login + "&Password:"+ pword + "&Bar Code:"+ barcode + "&Coupon Expiration Date:" + expiration + "&Coupon Image Blob:" + image;
        Gson gson = new Gson();
        String json = gson.toJson(submit);
        Log.v(TAG, "submitted: "+json);
	    Log.v(TAG, "before try statement");
	    try {

	    	HttpPost httppost = new HttpPost(url_submit_coupon);
	        httppost.setHeader("Content-type", "application/json");
	                
	        StringEntity se = new StringEntity(json);
	        //StringEntity se = new StringEntity(json.toString()); May need to go this route??
	        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
	        httppost.setEntity(se);

	        HttpResponse response = httpclient.execute(httppost);
	        String temp = EntityUtils.toString(response.getEntity());
            Log.v(TAG, "status: "+response.getStatusLine().toString());
	        Log.i(TAG, "entity: " + temp);
            Header[] headers = response.getAllHeaders();
            for(Header h : headers) {
                Log.v(TAG, "header: "+h);
            }

	    } catch (ClientProtocolException e) {
            showError(e);
	    } catch (IOException e) {
            showError(e);
	    }
		
	}

    private void showError(Exception e) {
        Toast.makeText(parent, "Error sending json", Toast.LENGTH_LONG).show();
    }
	   
}
