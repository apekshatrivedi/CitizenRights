package com.grid.appy.citizenrights.activity;

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
                emailedit = (EditText) findViewById(R.id.email);
                final String email = emailedit.getText().toString();
                  if (!isValidEmail(email)) {
                    emailedit.setError("Invalid Email");
                }
            }
        });

    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}