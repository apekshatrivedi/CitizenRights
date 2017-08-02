package com.grid.appy.citizenrights.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.grid.appy.citizenrights.R;
import com.grid.appy.citizenrights.helper.SQLiteHandler;
import com.grid.appy.citizenrights.helper.SessionManager;
import com.grid.appy.citizenrights.model.CheckNetwork;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.support.design.R.id.title;
import static com.grid.appy.citizenrights.R.id.issue_title;
import static com.grid.appy.citizenrights.R.id.name;
import static com.grid.appy.citizenrights.config.AppConfig.GET_ISSUE_DATA;
import static com.grid.appy.citizenrights.config.AppConfig.GET_MEMBERVIEW_DATA;

public class PeopleviewActivity extends AppCompatActivity {
    private SessionManager session;
    private SQLiteHandler db;

    public static final String KEY_NAME = "name";
    public static final String KEY_UID = "aadhar";
    public static final String KEY_DEPTID = "deptid";
    public static final String KEY_BRANCH = "branch";
    public static final String KEY_DESIGNATION= "designation";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_EMAIL= "deptmail";
    public  static final String KEY_IMEI="imei";
    public static final  String KEY_STATUS="status";

    public static final String JSON_ARRAY = "result";


    TextView name;
    TextView aadhar;
    TextView deptid;
    TextView branch;
    TextView designation;
    TextView phone;
    TextView deptmail;
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
     /*   isvalid.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to activity_forgetpassword screen

                Toast.makeText(getApplicationContext(), "Department employee is validated", Toast.LENGTH_LONG).show();
            }
        });*/
        ImageButton invalid = (ImageButton) findViewById(R.id.invalid);
       /* invalid.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to activity_forgetpassword screen

                Toast.makeText(getApplicationContext(), "Department employee is deathorised", Toast.LENGTH_LONG).show();
            }
        });*/

    }
    private void getData() {

        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("message");
        String issueid = message;

      //  loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        String url = GET_MEMBERVIEW_DATA+"?deptmail="+deptmail;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // loading.dismiss();
                //showJSON(response);
                Toast.makeText(PeopleviewActivity.this, "got the response==="+response, Toast.LENGTH_SHORT).show();
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
        String Name;
        String UID;
        String DEPTNAME;
        String Branch;
        String Designation;
        String Phone;
        String Email;
       // String imei;
        //String status;

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(JSON_ARRAY);
            JSONObject issueData = result.getJSONObject(0);
           Name = issueData.getString(KEY_NAME);
           UID = issueData.getString(KEY_UID);
          DEPTNAME = issueData.getString(KEY_DEPTID);
            Branch=issueData.getString(KEY_BRANCH);
            Designation = issueData.getString(KEY_DESIGNATION);
            Phone=issueData.getString(KEY_PHONE);
            Email=issueData.getString(KEY_EMAIL);
           // imei=issueData.getString(KEY_IMEI);
          //  status=issueData.getString(KEY_STATUS);



        name=(TextView)findViewById(R.id.name1);


            aadhar=(TextView)findViewById(R.id.otp);

            deptid=(TextView)findViewById(R.id.deptname);

            branch=(TextView)findViewById(R.id.branchname);
            designation=(TextView)findViewById(R.id.designation);
            phone=(TextView)findViewById(R.id.phone);
            deptmail=(TextView)findViewById(R.id.email);


         name.setText(Name);
           aadhar.setText(UID);
            deptid.setText(DEPTNAME);
            branch.setText(Branch);
           designation.setText(Designation);
            phone.setText(Phone);
           deptmail.setText(Email);

           // Toast.makeText(this,"imgpath"+imgpaths,Toast.LENGTH_LONG).show();




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
