package com.grid.appy.citizenrights.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.grid.appy.citizenrights.R;

public class SettingsActivity extends AppCompatActivity


{
    public static final String myPref = "autologin";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //notification switch
        Switch notification = (Switch) findViewById(R.id.notification);
        notification.setChecked(true);
        notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked)
            {

                if (isChecked)
                {

                    Toast.makeText(getApplicationContext(), "Allow notification",
                            Toast.LENGTH_SHORT).show();

                } else
                    {

                    Toast.makeText(getApplicationContext(),
                            "Notification disabled", Toast.LENGTH_SHORT).show();
                    }
            }

        });


        //new comment switch
        Switch newcomment = (Switch) findViewById(R.id.newcomment);
        newcomment.setChecked(true);
        newcomment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {

                    Toast.makeText(getApplicationContext(), "Allow new comment alert",
                            Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(getApplicationContext(),
                            "New comment alert disabled", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //autologin switch
        Switch autologin = (Switch) findViewById(R.id.autologin);
        autologin.setChecked(false);
        autologin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {

                    Toast.makeText(getApplicationContext(), "Auto-login enabled",
                            Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(getApplicationContext(),
                            "Auto-login disabled", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //allowcomments switch


        //autorefresh switch
        Switch autorefresh = (Switch) findViewById(R.id.autorefresh);
        autorefresh.setChecked(true);
        autorefresh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {

                    Toast.makeText(getApplicationContext(), "Auto-refresh enabled",
                            Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(getApplicationContext(),
                            "Auto-refresh disabled", Toast.LENGTH_SHORT).show();
                }
            }
        });

    //oncreate ends here
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
