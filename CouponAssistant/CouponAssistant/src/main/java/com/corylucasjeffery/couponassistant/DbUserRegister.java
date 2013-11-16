package com.corylucasjeffery.couponassistant;

import android.app.Activity;
import android.content.Intent;
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

public class DbUserRegister extends AsyncTask<String, String, Boolean> {
    private final String HOST = "http://dlthompson81.byethost24.com";
    private final String FOLDER = "/CouponPHP/form_code/";
    private final String SCRIPT = "/register.php";
    private final String POST_REGISTER_URL = HOST+FOLDER+SCRIPT;
    public static final int SUCCESS_VALUE = 1;

    private String username;
    private String password;
    private String firstName;
    private String lastName;

    public DbUserRegister(String user, String pass, String first, String last) {
        this.username = user;
        this.password = pass;
        this.firstName = first;
        this.lastName = last;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        int success = 0;
        try {
            //Prepare the post values
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("username", this.username));
            parameters.add(new BasicNameValuePair("password", this.password));
            parameters.add(new BasicNameValuePair("first_name", this.firstName));
            parameters.add(new BasicNameValuePair("last_name", this.lastName));

            //Create the connection to the website
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(POST_REGISTER_URL);
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
            success = jObject.getInt("success");
            String jmessage = jObject.getString("message");
            Log.d("DbUserItem", "Success: " + success + " Message: " + jmessage);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // {"success":0,"message":"One of the submitted coupon fields was empty."}
        return success == PhpWrapper.SUCCESS_VALUE;
    }

}
