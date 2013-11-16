package com.corylucasjeffery.couponassistant.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.corylucasjeffery.couponassistant.R;

public class StatisticsActivity extends Activity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("STAT", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        Log.v("STAT", "finish onCreate");
        context = this;
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
