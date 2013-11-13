package com.corylucasjeffery.couponassistant.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.corylucasjeffery.couponassistant.R;

public class LoginActivity extends Activity {

    private Button register;
    private EditText user;
    private EditText pw;
    private Context context;
    private final String TAG = "LOGIN";

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_login);

        context = this;

        user = (EditText) findViewById(R.id.field_login);
        pw = (EditText) findViewById(R.id.field_password);
        register = (Button) findViewById(R.id.button_register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userText = user.getText().toString();
                String pwText = user.getText().toString();
                if (userText == null || userText.equals("") || pwText == null || pwText.equals(""))
                {
                    Toast.makeText(context, "Both fields are required", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Log.v(TAG, "user: ["+userText+"] pw: ["+pwText+"]");
                    Toast.makeText(context, "Logging in", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }
}
