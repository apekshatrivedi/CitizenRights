package com.grid.appy.citizenrights.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.grid.appy.citizenrights.R;

public class Chooselogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooselogin);
        Button userlogin = (Button) findViewById(R.id.btnLoginuser);
        // Listening to forgetpassword link
        userlogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to activity_forgetpassword screen
                Intent i1 = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i1);

            }
        });

        Button departmentlogin = (Button) findViewById(R.id.btnLogindept);
        // Listening to forgetpassword link
        departmentlogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to activity_forgetpassword screen
                Intent i1 = new Intent(getApplicationContext(), RegloginActivity.class);
                startActivity(i1);
            }
        });
    }
}
