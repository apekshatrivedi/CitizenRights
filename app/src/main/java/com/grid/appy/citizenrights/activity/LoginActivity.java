package com.grid.appy.citizenrights.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.grid.appy.citizenrights.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends Activity {

    private EditText emailedit, password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting default screen to activity_login.xml
        setContentView(R.layout.activity_login);

        //Forget Screen
        Button forgetScreen = (Button) findViewById(R.id.btnLinkToForgetScreen);
        // Listening to forgetpassword link
        forgetScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to activity_forgetpassword screen
                Intent i1 = new Intent(getApplicationContext(), ForgetpasswordActivity.class);
                startActivity(i1);
            }
        });

        //Home page
        Button login=(Button)findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to activity_forgetpassword screen

                emailedit = (EditText) findViewById(R.id.email);
                password = (EditText) findViewById(R.id.password);

                final String email = emailedit.getText().toString();
                final String pass = password.getText().toString();

                if (!isValidEmail(email)) {
                    emailedit.setError("Invalid Email");
                }
               else if (!isValidPassword(pass)) {
                    password.setError("Invalid Password");
                }
                else{
                Intent i2 = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i2);}
            }
        });

        // Listening to register new account link
        Button registerScreen = (Button) findViewById(R.id.btnLinkToRegisterScreen);

        // Listening to register new account link
        registerScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to Register screen
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
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

    // validating password with retype password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 1) {
            return true;
        }
        return false;
    }
}