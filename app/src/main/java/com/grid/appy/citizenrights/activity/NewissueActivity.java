package com.grid.appy.citizenrights.activity;


import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.grid.appy.citizenrights.R;
import com.grid.appy.citizenrights.activity.HomeActivity;
import com.grid.appy.citizenrights.fragment.DatePickerFragment;


public class NewissueActivity extends AppCompatActivity  {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newissue);



        Button issuesubmit=(Button)findViewById(R.id.issuesubmit);
        issuesubmit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to activity_home screen
                Intent issues = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(issues);
            }
        });
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

}
