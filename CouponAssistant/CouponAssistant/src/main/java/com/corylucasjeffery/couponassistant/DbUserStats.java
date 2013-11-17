package com.corylucasjeffery.couponassistant;

/**
 * Created by Caleb on 11/16/13.
 */

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

public class DbUserStats extends AsyncTask<String, Void, ArrayList<Statistics>> {
    private String POST_COUPON_URL = "http://dlthompson81.byethost24.com/CouponPHP/form_code/get_coupon.php";

    private final String TAG = "GET-COUP";
    private String username;
    private String password;

    private ListActivity parent;

    public DbUserStats(String username, String password, String barcode, ListActivity activity)  {
        this.username = username;
        this.password = password;
        this.parent = activity;
    }

    @Override
    protected ArrayList<Statistics> doInBackground(String... params) {
        ArrayList<Statistics> stats = new ArrayList<Statistics>();
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
            if (success == PhpWrapper.SUCCESS_VALUE){
                /*
                    {"success":1,"message":"Statistics found!","count_bought":1,"count_total":53,"count_day":33,"count_week":38,"count_month":53,"count_year":53}
                 */
                JSONArray jsonArray = jObject.getJSONArray("statistics");
                int size = jsonArray.length();

                if (size == 0) {
                    Statistics c = new Statistics("0", "0", "0", "0","0", "0");
                    stats.add(c);
                }

                for(int i = 0; i < size; i++)
                {
                    JSONObject jsonItem = jsonArray.getJSONObject(i);
                    String bought = jsonItem.getString("count_bought");
                    String total = jsonItem.getString("count_total");
                    String day = jsonItem.getString("count_day");
                    String week = jsonItem.getString("count_week");
                    String month = jsonItem.getString("count_month");
                    String year = jsonItem.getString("count_year");
                    Statistics c = new Statistics(bought, total, day, week, month, year);
                    stats.add(c);
                }
            }
            else
            {
                String emptyBought = "";
                String emptyTotal = "";
                String emptyDay = "";
                String emptyWeek = "";
                String emptyMonth = "";
                String emptyYear = "";
                Statistics c = new Statistics(emptyBought, emptyTotal, emptyDay, emptyWeek, emptyMonth, emptyYear);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stats;
    }

    @Override
    protected void onPreExecute() {
        ProgressBarHelper.initializeProgressBar(parent);
    }

    @Override
    protected void onPostExecute(ArrayList<Statistics> result) {
        //super.onPostExecute(result);
        ProgressBarHelper.closeProgressBar();
    }


}
