package com.corylucasjeffery.couponassistant;

/**
 * Created by Caleb on 11/16/13.
 */

import android.app.Activity;
import android.app.ListActivity;
import android.os.AsyncTask;
import android.util.Log;

import com.corylucasjeffery.couponassistant.activities.Statistics;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DbUserStats extends AsyncTask<String, Void, Statistics> {
    private String POST_COUPON_URL = "http://dlthompson81.byethost24.com/CouponPHP/form_code/statistics.php";

    private final String TAG = "GET-COUP";
    private String username;
    private String password;

    private Activity parent;

    public DbUserStats(String username, String password, Activity activity)  {
        this.username = username;
        this.password = password;
        this.parent = activity;
    }

    @Override
    protected Statistics doInBackground(String... params) {
        Statistics stats;
        Log.v(TAG, "Started do in background");
        try {
            //Prepare the post values
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("username", this.username));
            parameters.add(new BasicNameValuePair("password", this.password));
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
            int success = jObject.getInt("success");
            String jmessage = jObject.getString("message");
            //Log.v("STAT", "size=" + Integer.toString(success));
            if (success == PhpWrapper.SUCCESS_VALUE){
                /*
                    {"success":1,"message":"Statistics found!","count_bought":1,"count_total":53,"count_day":33,"count_week":38,"count_month":53,"count_year":53}
                 */





                    String bought = jObject.getString("count_bought");
                    String total = jObject.getString("count_total");
                    String day = jObject.getString("count_day");
                    String week = jObject.getString("count_week");
                    String month = jObject.getString("count_month");
                    String year = jObject.getString("count_year");

                    Log.v("STAT", "here");
                    stats = new Statistics(bought, total, day, week, month, year);


            }
            else
            {
                String emptyBought = "";
                String emptyTotal = "";
                String emptyDay = "";
                String emptyWeek = "";
                String emptyMonth = "";
                String emptyYear = "";
                stats = new Statistics(emptyBought, emptyTotal, emptyDay, emptyWeek, emptyMonth, emptyYear);
            }

        } catch (IOException e) {
            String emptyBought = "";
            String emptyTotal = "";
            String emptyDay = "";
            String emptyWeek = "";
            String emptyMonth = "";
            String emptyYear = "";
            stats = new Statistics(emptyBought, emptyTotal, emptyDay, emptyWeek, emptyMonth, emptyYear);
            e.printStackTrace();
        } catch (JSONException e) {
            String emptyBought = "";
            String emptyTotal = "";
            String emptyDay = "";
            String emptyWeek = "";
            String emptyMonth = "";
            String emptyYear = "";
            stats = new Statistics(emptyBought, emptyTotal, emptyDay, emptyWeek, emptyMonth, emptyYear);
            e.printStackTrace();
        }
        Log.v("STAT", "yep");


        return stats;
    }


    @Override
    protected void onPreExecute() {
        ProgressBarHelper.initializeProgressBar(parent);
    }

    @Override
    protected void onPostExecute (Statistics result) {
        //super.onPostExecute(result);
        ProgressBarHelper.closeProgressBar();
    }


}
