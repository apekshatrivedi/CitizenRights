package com.grid.appy.citizenrights.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.grid.appy.citizenrights.R;
import com.grid.appy.citizenrights.activity.HomeActivity;

public class ViewdeptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdept);

        ImageButton eduScreen = (ImageButton) findViewById(R.id.edu);

        // Listening to forgetpassword link
        eduScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to activity_forgetpassword screen
                Intent i1 = new Intent(getApplicationContext(), DeptissueActivity.class);
                startActivity(i1);
            }
        });

        ImageButton officeScreen = (ImageButton) findViewById(R.id.office);

        // Listening to forgetpassword link
        officeScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to activity_forgetpassword screen
                Intent i1 = new Intent(getApplicationContext(), DeptissueActivity.class);
                startActivity(i1);
            }
        });

        ImageButton bankScreen = (ImageButton) findViewById(R.id.bank);

        // Listening to forgetpassword link
        bankScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to activity_forgetpassword screen
                Intent i1 = new Intent(getApplicationContext(), DeptissueActivity.class);
                startActivity(i1);
            }
        });

        ImageButton ngoScreen = (ImageButton) findViewById(R.id.ngo);

        // Listening to forgetpassword link
        ngoScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to activity_forgetpassword screen
                Intent i1 = new Intent(getApplicationContext(), DeptissueActivity.class);
                startActivity(i1);
            }
        });

        ImageButton retailScreen = (ImageButton) findViewById(R.id.retail);

        // Listening to forgetpassword link
        retailScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to activity_forgetpassword screen
                Intent i1 = new Intent(getApplicationContext(), DeptissueActivity.class);
                startActivity(i1);
            }
        });

        ImageButton govtScreen = (ImageButton) findViewById(R.id.govt);

        // Listening to forgetpassword link
        govtScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to activity_forgetpassword screen
                Intent i1 = new Intent(getApplicationContext(), DeptissueActivity.class);
                startActivity(i1);
            }
        });





    }
}
