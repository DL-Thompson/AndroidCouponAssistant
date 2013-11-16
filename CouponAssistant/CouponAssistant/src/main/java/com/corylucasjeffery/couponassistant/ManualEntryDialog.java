package com.corylucasjeffery.couponassistant;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;

import com.corylucasjeffery.couponassistant.activities.MainActivity;


public class ManualEntryDialog extends DialogFragment {

    private final String TAG = "MAN-ENT";
    private EditText barcode;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //use builder to make the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //layout infalater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final Activity activity = getActivity();

        barcode = (EditText) activity.findViewById(R.id.dialog_manual_add_barcode);

        builder.setView(inflater.inflate(R.layout.dialog_manual_add, null))
                .setPositiveButton(R.string.dialog_ok_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // add to DB
                        Log.d(TAG, "get text");
                        try {
                            String barcodeText = barcode.getText().toString();
                            Log.d(TAG, "make toast, good");
                            Toast.makeText(activity, "barcode is filled in", Toast.LENGTH_SHORT).show();
                        } catch (NullPointerException e) {
                            Log.d(TAG, "make toast, bad");
                            Toast.makeText(activity, "re-enter barcode", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(R.string.dialog_cancel_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // anything?
                    }
                });

        /*
        builder.setMessage(R.string.dialog_title_manually_add)
                .setPositiveButton(R.string.dialog_ok_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // add to DB

                    }
                })
                .setNegativeButton(R.string.dialog_cancel_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // anything?

                    }
                });
        */
        AlertDialog dialog = builder.create();
        return dialog;
    }

}
