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
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.corylucasjeffery.couponassistant.activities.MainActivity;


public class ManualEntryDialog extends DialogFragment {

    private final String TAG = "MAN-ENT";
    private String barcode = "";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //use builder to make the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //layout infalater

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View inflateView = inflater.inflate(R.layout.dialog_manual_add, null);
        //final Activity activity = getActivity();

        builder.setView(inflateView);
        final EditText txtUpc = (EditText) inflateView.findViewById(R.id.dialog_manual_add_barcode);
        txtUpc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                barcode = txtUpc.getText().toString();
            }
        });


        builder.setPositiveButton(R.string.dialog_ok_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // add to DB
                        Log.d(TAG, "ok button : "+barcode);
                    }
        });
        builder.setNegativeButton(R.string.dialog_cancel_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // anything?
            }
        });

        AlertDialog dialog = builder.create();
        return dialog;
    }
}
