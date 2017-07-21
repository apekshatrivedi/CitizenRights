package com.grid.appy.citizenrights.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.grid.appy.citizenrights.R;

public class PeopleviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peopleview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageButton isvalid=(ImageButton)findViewById(R.id.valid);
        isvalid.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to activity_forgetpassword screen

                Toast.makeText(getApplicationContext(), "Department employee is validated", Toast.LENGTH_LONG).show();
            }
        });
        ImageButton invalid=(ImageButton)findViewById(R.id.invalid);
        invalid.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to activity_forgetpassword screen

                Toast.makeText(getApplicationContext(), "Department employee is deathorised", Toast.LENGTH_LONG).show();
            }
        });



    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }



}
