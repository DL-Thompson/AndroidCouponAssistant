package com.corylucasjeffery.couponassistant.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.corylucasjeffery.couponassistant.PhpWrapper;
import com.corylucasjeffery.couponassistant.R;

import java.util.ArrayList;

public class StatisticsActivity extends Activity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("STAT", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        Log.v("STAT", "finish onCreate");
        context = this;

        PhpWrapper db = new PhpWrapper();
        //setup the list display
        Statistics stats = db.getStatistics(this);

        TextView boughtText = (TextView) findViewById(R.id.list_statistics_bought);
        boughtText.setText("Unique Items Bought: " + stats.getBought());
        TextView totalText = (TextView) findViewById(R.id.list_statistics_total);
        totalText.setText("Total Coupons Submitted: " + stats.getTotal());
        TextView dayText = (TextView) findViewById(R.id.list_statistics_day);
        dayText.setText("Submitted Today: " + stats.getDay());
        TextView weekText = (TextView) findViewById(R.id.list_statistics_week);
        weekText.setText("Submitted this Week: " + stats.getWeek());
        TextView monthText = (TextView) findViewById(R.id.list_statistics_month);
        monthText.setText("Submitted this Month: " + stats.getMonth());
        TextView yearText = (TextView) findViewById(R.id.list_statistics_year);
        yearText.setText("Submitted this Year: " + stats.getYear());


    }



    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }

    public void onStop() {
        super.onStop();
        finish();
    }

    public void onDestroy() {
        super.onDestroy();
        finish();
    }

}
