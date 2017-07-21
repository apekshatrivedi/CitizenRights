package com.grid.appy.citizenrights.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.grid.appy.citizenrights.R;

public class ResetpasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);



        Button register =(Button)findViewById(R.id.btnReset);
        register.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Closing registration screen
                // closing register screen

                //form validation

                EditText pass;
                pass= (EditText) findViewById(R.id.password);
                EditText repass;
                repass = (EditText) findViewById(R.id.repassword);


                final String password = pass.getText().toString();
                final String repassword = repass.getText().toString();



                if (!isValidPassword(password)) {
                    pass.setError("Password must be greater than 4 characters");
                }

                else    if (!isValidrePassword(repassword,password)) {
                    repass.setError("Password does not match");
                }

                else{
                    //switching to home activity
                    Intent logout = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(logout);
                }}
        });



    }

    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 4) {
            return true;
        }
        return false;
    }

    //validating re-enter password
    private boolean isValidrePassword(String repass,String pass) {
        if (repass.contentEquals(pass)) {
            return true;
        } else {
            return false;
        }
    }


}