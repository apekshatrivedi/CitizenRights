package com.grid.appy.citizenrights.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.grid.appy.citizenrights.R;

public class LoginActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting default screen to activity_login.xml

        setContentView(R.layout.activity_login);

        Button forgetScreen = (Button) findViewById(R.id.btnLinkToForgetScreen);

        // Listening to forgetpassword link
        forgetScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to activity_forgetpassword screen
                Intent i1 = new Intent(getApplicationContext(), ForgetpasswordActivity.class);
                startActivity(i1);
            }
        });

        Button login=(Button)findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to activity_forgetpassword screen
                Intent i2 = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i2);
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
}