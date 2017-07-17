package com.grid.appy.citizenrights.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.grid.appy.citizenrights.R;

public class NewcommentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newcomment);

        Button commentScreen = (Button) findViewById(R.id.comment);

        // Listening to forgetpassword link
        commentScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to activity_forgetpassword screen
                Intent i1 = new Intent(getApplicationContext(), IssuedetailActivity.class);
                startActivity(i1);

            }
        });
    }



}
