package com.corylucasjeffery.couponassistant;

/**
 * Created by Caleb on 11/9/13.
 */

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

public class DbSubmitItem extends AsyncTask<String, String, String>{

    private String POST_COUPON_URL = "http://dlthompson81.byethost24.com/CouponPHP/form_code/submit_item.php";
    private String username;
    private String password;
    private String barcode;
    private String description;

    public DbSubmitItem(String username, String password, String barcode, String description)  {
        this.username = username;
        this.password = password;
        this.barcode = barcode;
        this.description = description;
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            //Prepare the post values
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("username", this.username));
            parameters.add(new BasicNameValuePair("password", this.password));
            parameters.add(new BasicNameValuePair("barcode", this.barcode));
            parameters.add(new BasicNameValuePair("description", this.description));

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

            //Grab data from JSON string
            JSONObject jObject = new JSONObject(result);
            String success = jObject.getString("success");
            String jmessage = jObject.getString("message");
            Log.d("DbSubmitItem", "Success: " + success + " Message: " + jmessage);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
