package com.corylucasjeffery.couponassistant;

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

/**
 * Created by cory on 11/17/13.
 */
public class DbPurchaseItem extends AsyncTask<String, String, Boolean> {
    private String POST_COUPON_URL = "http://dlthompson81.byethost24.com/CouponPHP/form_code/buy_item.php";
    private String username;
    private String password;
    private String itemBarcode;
    private String couponBarcode;
    private String expDate;

    private final String TAG = "PURCH";

    /*
    <form action="../form_code/buy_item.php" method="post">
    Login:<br>
    <input type="text" name="username"/><br>
    Password:<br>
    <input type="password" name="password"><br>
    Item Bar Code:<br>
    <input type="text" name="item_barcode"><br>
    Coupon Barcode Used:<br>
    <input type="text" name="coupon_barcode"/><br>
    Coupon Exp Date:<br>
    <input type="text" name="exp_date"/><br>
    <input type="submit" value="Buy Item"/>
</form>
     */

    public DbPurchaseItem(String username, String password, String itemBarcode, String couponBarcode, String expDate)  {
        this.username = username;
        this.password = password;
        this.itemBarcode = itemBarcode;
        this.couponBarcode = couponBarcode;
        this.expDate = expDate;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        int success = 0;
        try {
            //Prepare the post values
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("username", this.username));
            parameters.add(new BasicNameValuePair("password", this.password));
            parameters.add(new BasicNameValuePair("item_barcode", this.itemBarcode));
            parameters.add(new BasicNameValuePair("coupon_barcode", this.couponBarcode));
            parameters.add(new BasicNameValuePair("exp_date", this.expDate));
            Log.v(TAG, "i:"+itemBarcode+" c:"+couponBarcode);
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
            success = jObject.getInt("success");
            String jmessage = jObject.getString("message");
            Log.d("DbSubmitItem", "Success: " + success + " Message: " + jmessage);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "success:"+success);
        return success == PhpWrapper.SUCCESS_VALUE;
    }
}
