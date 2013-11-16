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

import com.corylucasjeffery.couponassistant.PhpWrapper;
import com.corylucasjeffery.couponassistant.R;
import com.corylucasjeffery.couponassistant.UserInfo;

public class LoginActivity extends Activity {

    private Button register;
    private EditText firstName;
    private EditText lastName;
    private EditText user;
    private EditText pw;
    private Context context;
    //private Activity activity;
    private final String TAG = "LOGIN";

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_login);

        context = this;
        //activity = this;

        user = (EditText) findViewById(R.id.field_login);
        pw = (EditText) findViewById(R.id.field_password);
        firstName = (EditText) findViewById(R.id.field_firstName);
        lastName = (EditText) findViewById(R.id.field_lastName);
        register = (Button) findViewById(R.id.button_register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userText = user.getText().toString();
                String pwText = pw.getText().toString();
                String firstText = firstName.getText().toString();
                String lastText = lastName.getText().toString();
                if (userText == null || userText.equals("") || pwText == null || pwText.equals(""))
                {
                    Toast.makeText(context, "Email and password are required fields",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    PhpWrapper db = new PhpWrapper();
                    boolean LoginSuccess = db.submitLogin(
                                            userText, pwText, firstText, lastText);

                    if (LoginSuccess) {
                        Toast.makeText(context, "Logging in", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context, MainActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(context, "Register failure", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

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
