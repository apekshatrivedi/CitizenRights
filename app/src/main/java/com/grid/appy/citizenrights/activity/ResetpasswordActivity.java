package com.grid.appy.citizenrights.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.grid.appy.citizenrights.R;
import com.grid.appy.citizenrights.config.AppConfig;
import com.grid.appy.citizenrights.config.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResetpasswordActivity extends AppCompatActivity {


    private ProgressDialog pDialog;
    private static final String TAG = PeopleviewActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



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
                    pass.setError("min 6 characters which includes a digit ");
                }

                else    if (!isValidrePassword(repassword,password)) {
                    repass.setError("Password does not match");
                }

                else{
                    //switching to home activity
                    //Intent logout = new Intent(getApplicationContext(), LoginActivity.class);
                    //startActivity(logout);

                    Bundle bundle = getIntent().getExtras();
                    String message = bundle.getString("message");
                    String issueid = message;

                    resetpassword(issueid,password);

                }}
        });

    }

    private void resetpassword(final String phone,final String password)
    {
        String tag_string_req = "req_register";
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Resetting ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.PASS_RESET, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        Intent intent = new Intent(
                                ResetpasswordActivity.this,
                                LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url


                Map<String, String> params = new HashMap<String, String>();
                params.put("phone",phone);
                params.put("password",password);


                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }












    private boolean isValidPassword(String pass) {
        String EMAIL_PATTERN = "((?=.*\\d)(?=.*[a-z]).{6,20})";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(pass);
        return matcher.matches();

    }

    //validating re-enter password
    private boolean isValidrePassword(String repass,String pass) {
        if (repass.contentEquals(pass)) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }



}
