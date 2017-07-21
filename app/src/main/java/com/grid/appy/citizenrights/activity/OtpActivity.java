package com.grid.appy.citizenrights.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.grid.appy.citizenrights.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OtpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);


        Button reset =(Button)findViewById(R.id.btnreset);
        reset.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // reset email button
                EditText emailedit;
                emailedit = (EditText) findViewById(R.id.otp);
                final String otp = emailedit.getText().toString();
                if (!isValidOTP(otp)) {
                    emailedit.setError("Invalid OTP");
                }
                else
                {
                    Intent home = new Intent(getApplicationContext(), ResetpasswordActivity.class);
                    startActivity(home);
                }
            }
        });



    }

    private boolean isValidOTP(String email) {
        String EMAIL_PATTERN = "^[0-9]{4}$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}