package com.corylucasjeffery.couponassistant;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import com.corylucasjeffery.couponassistant.activities.MainActivity;

import java.util.Calendar;

public class DateChooserDialog extends DialogFragment {
    private int year;
    private int month;
    private int day;

    private final String TAG = "DATECHOOSER";
    private final int DEFAULT_MONTH = 12;
    private final int DEFAULT_DAY = 31;
    private final String MYDATE_TITLE = "Pick expiration date";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        this.year = c.get(Calendar.YEAR);
        this.month = DEFAULT_MONTH;
        this.day = DEFAULT_DAY;

        Log.v(TAG, "Set: "+Integer.toString(year)+ " " +Integer.toString(month) + " " + Integer.toString(day));
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), (MainActivity)getActivity(), year, month, day);
    }
    /*
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        this.year = year;
        this.month = month;
        this.day = day;
    }
    */
}
