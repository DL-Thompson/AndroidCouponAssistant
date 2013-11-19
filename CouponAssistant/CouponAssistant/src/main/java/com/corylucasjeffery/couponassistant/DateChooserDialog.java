package com.corylucasjeffery.couponassistant;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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

    public DateChooserDialog() {}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        this.year = c.get(Calendar.YEAR);
        this.month = DEFAULT_MONTH - 1; //for some reason, setting it to 12 moves it up to Jan?
        this.day = DEFAULT_DAY;

        Log.v(TAG, "on create: "+Integer.toString(year)+ " "+Integer.toString(month)+" "+Integer.toString(day));

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                (MainActivity)getActivity(), year, month, day);
        dpd.setTitle(R.string.date_chooser_title);
        return dpd;
    }
}
