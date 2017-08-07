package com.grid.appy.citizenrights.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.grid.appy.citizenrights.R;
import com.grid.appy.citizenrights.config.AppConfig;
import com.grid.appy.citizenrights.config.AppController;
import com.grid.appy.citizenrights.helper.SQLiteHandler;
import com.grid.appy.citizenrights.helper.SessionManager;
import com.grid.appy.citizenrights.model.CheckNetwork;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.support.design.R.id.title;
import static com.grid.appy.citizenrights.R.id.issue_title;
import static com.grid.appy.citizenrights.R.id.name;
import static com.grid.appy.citizenrights.config.AppConfig.GET_ISSUE_DATA;
import static com.grid.appy.citizenrights.config.AppConfig.GET_MEMBERVIEW_DATA;

public class PeopleviewActivity extends AppCompatActivity {
    private SessionManager session;
    private SQLiteHandler db;

    private ProgressDialog pDialog;

    private static final String TAG = PeopleviewActivity.class.getSimpleName();
    public static final String KEY_NAME = "name";
    public static final String KEY_UID = "aadhar";
    public static final String KEY_DEPTID = "deptid";
   public static final String KEY_DESIGNATION= "designation";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_EMAIL= "deptmail";
 //   public  static final String KEY_IMEI="imei";
   public static final  String KEY_STATUS="status";

    public static final String JSON_ARRAY = "result";


    TextView  name;
    TextView aadhar;
    TextView deptid;
    TextView designation;
    TextView phone;
    TextView deptmail;
    TextView status;
    private ProgressDialog loading;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peopleview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());


        if (CheckNetwork.isInternetAvailable(this)) {

            getData();

        }


        ImageButton isvalid = (ImageButton) findViewById(R.id.valid);
        isvalid.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to activity_forgetpassword screen

                updatemember("valid");

            }
        });
        ImageButton invalid = (ImageButton) findViewById(R.id.invalid);
        invalid.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to activity_forgetpassword screen

                updatemember("invalid");
               // Toast.makeText(getApplicationContext(), "Department employee is deathorised", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void updatemember(final String status)
    {
        String tag_string_req = "req_register";
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Updating ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.UPDATE_MEMBER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        Intent intent = new Intent(
                                PeopleviewActivity.this,
                                AdminviewActivity.class);
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


                Bundle bundle = getIntent().getExtras();
                String message = bundle.getString("message");
                String issueid = message;


                Map<String, String> params = new HashMap<String, String>();
                params.put("deptmail",issueid);
                params.put("status", status);


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






    private void getData() {

        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("message");
        String issueid = message;

        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        String url = GET_MEMBERVIEW_DATA+issueid;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
               //Toast.makeText(PeopleviewActivity.this, "got the response==="+response, Toast.LENGTH_SHORT).show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PeopleviewActivity.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

                RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void showJSON(String response){
        String Name="";
        String UID="";
        String DEPTNAME="";
        String Designation="";
        String Phone="";
        String Email="";
       // String imei;
     String Status="";

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(JSON_ARRAY);
            JSONObject pissueData = result.getJSONObject(0);
           Name = pissueData.getString(KEY_NAME);
           UID = pissueData.getString(KEY_UID);
          DEPTNAME = pissueData.getString(KEY_DEPTID);
           Designation = pissueData.getString(KEY_DESIGNATION);
            Phone=pissueData.getString(KEY_PHONE);
            Email=pissueData.getString(KEY_EMAIL);
            Status=pissueData.getString(KEY_STATUS);



        name=(TextView)findViewById(R.id.name1);


            aadhar=(TextView)findViewById(R.id.otp);

            deptid=(TextView)findViewById(R.id.deptname);


            designation=(TextView)findViewById(R.id.designation);
            phone=(TextView)findViewById(R.id.phone);
            deptmail=(TextView)findViewById(R.id.email);
            status=(TextView)findViewById(R.id.status1);




            name.setText(Name);
           aadhar.setText(UID);
            deptid.setText(DEPTNAME);
            designation.setText(Designation);
            phone.setText(Phone);
           deptmail.setText(Email);
            status.setText(Status);

           // Toast.makeText(this,"name"+Name,Toast.LENGTH_LONG).show();




        } catch (JSONException e) {
            e.printStackTrace();
        }



    }



    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }



}
