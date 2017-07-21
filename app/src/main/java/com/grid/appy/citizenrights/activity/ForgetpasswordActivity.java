package com.grid.appy.citizenrights.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.grid.appy.citizenrights.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgetpasswordActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to activity_forgetpassword.xml
        setContentView(R.layout.activity_forgetpassword);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Button loginScreen = (Button) findViewById(R.id.btnLinkToLoginScreen);

        // Listening to Login Screen link
        loginScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Closing Forgetpassword screen
                // Switching to Login Screen/closing forgetpassword screen
                finish();
            }
        });


        Button reset =(Button)findViewById(R.id.btnreset);
        reset.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // reset email button
                EditText emailedit;
                emailedit = (EditText) findViewById(R.id.phone);
                final String email = emailedit.getText().toString();
                  if (!isValidEmail(email)) {
                    emailedit.setError("Invalid Phone");
                }
                else
                  {
                      Intent home = new Intent(getApplicationContext(), OtpActivity.class);
                      startActivity(home);
                  }
            }
        });




    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[2-9]{2}[0-9]{8}$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}