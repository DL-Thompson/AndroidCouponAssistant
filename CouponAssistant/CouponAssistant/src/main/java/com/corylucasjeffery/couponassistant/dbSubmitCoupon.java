package com.corylucasjeffery.couponassistant;

import java.io.IOException;
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

import android.util.Log;

import com.google.gson.Gson;

public class dbSubmitCoupon {
	
	private String barcode;
	private String expiration;
	private String login;
	private String pword;
	private String image;

    private static String TAG = "DBSubmit";

	private static String url_submit_coupon = "http://dlthompson81.byethost24.com/CouponPHP/forms/submit_coupon_form.php";
	
	public void test(String barcode){
		this.barcode = barcode;
	}

	public void postCoupon(){
		
		//Does this need to be here, or up there ^ ??
		String submit = "Login:"+ login + "Password:"+ pword + "Bar Code:"+ barcode + "Coupon Expiration Date:" + expiration + "Coupon Image Blob:" + image;
		
		Gson gson = new Gson();
	 		
	    HttpParams myParams = new BasicHttpParams();
	    HttpConnectionParams.setConnectionTimeout(myParams, 10000);
	    HttpConnectionParams.setSoTimeout(myParams, 10000);
	    HttpParams HttpConnectionParams = null;
	    //????
		HttpClient httpclient = new DefaultHttpClient(HttpConnectionParams);
	    String json = gson.toJson(submit);
	    
	    try {

	    	HttpPost httppost = new HttpPost(url_submit_coupon);
	        httppost.setHeader("Content-type", "application/json");
	                
	        StringEntity se = new StringEntity(json);
	        //StringEntity se = new StringEntity(json.toString()); May need to go this route??
	        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
	        httppost.setEntity(se); 

	        HttpResponse response = httpclient.execute(httppost);
	        String temp = EntityUtils.toString(response.getEntity());
	        Log.i(TAG, temp);


	    } catch (ClientProtocolException e) {

	    } catch (IOException e) {
	    }
		
	}
	   
}
