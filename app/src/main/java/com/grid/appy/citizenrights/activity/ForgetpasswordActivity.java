package com.grid.appy.citizenrights.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.grid.appy.citizenrights.R;

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
    }
}