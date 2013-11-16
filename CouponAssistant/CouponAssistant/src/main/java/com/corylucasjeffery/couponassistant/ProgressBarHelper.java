package com.corylucasjeffery.couponassistant;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.ProgressBar;

public class ProgressBarHelper {
    public static ProgressDialog ProgressBar;

    public static void initializeProgressBar(Activity parent) {
        final ProgressDialog progressDialog = new ProgressDialog(parent);
        progressDialog.setMessage(parent.getString(R.string.labelLoading));
        progressDialog.setCancelable(false);
        ProgressBar = progressDialog;
        ProgressBar.show();
    }

    public static void closeProgressBar() {
        ProgressBar.hide();
    }
}
