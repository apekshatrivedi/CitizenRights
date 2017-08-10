package com.grid.appy.citizenrights.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import static com.grid.appy.citizenrights.config.AppConfig.GET_DEPTNAME;
import static com.grid.appy.citizenrights.config.AppConfig.GET_DESIG;

public class DeptregistrationActivity extends AppCompatActivity
        implements Spinner.OnItemSelectedListener
{
    public static final String JSON_ARRAY = "result";
    public static final String JSON_ARRAY1 ="result";

    //Declaring a spinner
    private Spinner spinnerdesignation;
    private Spinner spinnerdept;

    //An ArrayList for Spinner Items
    private ArrayList<String> designation;
    private ArrayList<String> departmentname;

    //JSON Array
    private JSONArray result;
    private JSONArray result1;


    String dname="";
    String desig="";


    public static final String TAG_DESIG = "desig";
    public static final String TAG_DEPT = "deptname";

    private static final String TAG = DeptregistrationActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    EditText et_empname,et_userempname,et_uniqueid,et_phone,et_email,et_password,et_retypepassword;
    Button btn_reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deptregistration);


        designation = new ArrayList<String>();
        departmentname=new ArrayList<String>();

        //Initializing Spinner
        spinnerdesignation = (Spinner) findViewById(R.id.spin_desig);
        spinnerdept=(Spinner)findViewById(R.id.spin_dept);
        //Adding an Item Selected Listener to our Spinner
        //As we have implemented the class Spinner.OnItemSelectedListener to this class iteself we are passing this to setOnItemSelectedListener
        spinnerdesignation.setOnItemSelectedListener(this);
        spinnerdept.setOnItemSelectedListener(this);

        getData();
        getData1();



        et_empname = (EditText) findViewById(R.id.name);
        et_userempname = (EditText) findViewById(R.id.nikname);
        et_uniqueid = (EditText) findViewById(R.id.otp);

        et_phone = (EditText) findViewById(R.id.phone);
        et_email = (EditText) findViewById(R.id.email);
        et_password = (EditText) findViewById(R.id.password);
        et_retypepassword= (EditText) findViewById(R.id.repassword);
        btn_reg = (Button) findViewById(R.id.btnRegister);


        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String empname = et_empname.getText().toString();
                String userempname = et_userempname.getText().toString();
                String uniqueid = et_uniqueid.getText().toString().trim();
                String phone = et_phone.getText().toString().trim();
                String email = et_email.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String retypepassword = et_retypepassword.getText().toString().trim();


                String validEmailId = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+";

                String validpassword = "((?=.*\\d)(?=.*[a-z]).{6,20})";
                String validretypepassword = "((?=.*\\d)(?=.*[a-z]).{6,20})";
                //Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
                Matcher matcherEmailId = Pattern.compile(validEmailId).matcher(email);
                Matcher matcherretypepassword = Pattern.compile(validretypepassword).matcher(retypepassword);
                Matcher matcherpassword = Pattern.compile(validpassword).matcher(password);
                if (TextUtils.isEmpty(userempname)) {
                    et_userempname.setError("This field is required");
                    return;
                }
                if (TextUtils.isEmpty(empname)) {
                    et_empname.setError("This field is required");
                    return;
                }
                if (TextUtils.isEmpty(uniqueid)) {
                    et_uniqueid.setError("This field is required");
                    return;
                }  if(!(uniqueid.length()==12))

                {
                    Toast.makeText(getApplicationContext(), "Incorrect Aadhar Number", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(phone))
                {
                    et_phone.setError("This field is required");
                    return;
                }
                if ((phone.length() < 8 || phone.length() > 13) )
                {
                    Toast.makeText(getApplicationContext(), "Incorrect Mobile Number", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    et_email.setError("This field is required");
                    return;
                }
                if (!matcherEmailId.matches())
                {
                    Toast.makeText(getApplicationContext(), "Enter Valid Email-Id", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(password))
                {
                    et_password.setError("please enter the password");
                    return;
                }

                else
                {
                    if (!matcherpassword.matches())
                    {
                        Toast.makeText(getApplicationContext(), "Enter Valid Password", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                if (TextUtils.isEmpty(retypepassword))
                {
                    et_retypepassword.setError("This field is required");
                    return;
                }
                if (!password.equals(retypepassword))
                {
                    Toast.makeText(getApplicationContext(), "Password missmatch", Toast.LENGTH_LONG).show();
                    return;
                }

                TelephonyManager mngr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);

                String imei= mngr.getDeviceId();
                String type="deptmember";




                registerUser( uniqueid, empname, phone, imei, email, password,type,dname,desig,userempname);


            }
        });


    }

    private void registerUser(final String aadhar,final String name,final String phone,final String imei, final String email,
                              final String password,final String type,final String dname,final String designation,final String username) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DREGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    Log.e("response",response);
                    boolean error = jObj.getBoolean("error");

                        if (!error) {

                            // User successfully stored in MySQL
                            // Now store the user in sqlite

                            JSONObject user = jObj.getJSONObject("user");
                            String dept = user.getString("deptid");
                            String name = user.getString("name");
                            String phone = user.getString("phone");
                            String imei = user.getString("imei");
                            String email = user.getString("email");
                            String type=user.getString("type");
                            String username=user.getString("username");
                            Log.e("response",dept+name+phone+imei+email+type+username);

                            // Inserting row in users table
                            db.addUser(dept,name,phone,imei, email,type,username);

                            Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                            // Launch login activity
                        Intent intent = new Intent(
                                DeptregistrationActivity.this,
                                RegloginActivity.class);
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
                params.put("aadhar",aadhar);
                params.put("name", name);
                params.put("phone", phone);
                params.put("imei", imei);
                params.put("deptmail", email);
                params.put("type",type);
                params.put("password", password);
                params.put("deptid", dname);
                params.put("designation", designation);
                params.put("username",username);

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




    private void getData(){
        //Creating a string request
        StringRequest stringRequest = new StringRequest(GET_DESIG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            result = j.getJSONArray(JSON_ARRAY);

                            //Calling method getStudents to get the students from the JSON Array
                            getdesignation(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void getdesignation(JSONArray j){
        //Traversing through all the items in the json array
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                designation.add(json.getString(TAG_DESIG));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinnerdesignation.setAdapter(new ArrayAdapter<String>(DeptregistrationActivity.this, android.R.layout.simple_spinner_dropdown_item, designation));
    }



    private void getData1(){
        //Creating a string request
        StringRequest stringRequest = new StringRequest(GET_DEPTNAME,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            result1 = j.getJSONArray(JSON_ARRAY1);

                            //Calling method getStudents to get the students from the JSON Array
                            getdept(result1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void getdept(JSONArray j){
        //Traversing through all the items in the json array
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                departmentname.add(json.getString(TAG_DEPT));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinnerdept.setAdapter(new ArrayAdapter<String>(DeptregistrationActivity.this, android.R.layout.simple_spinner_dropdown_item, departmentname));
    }

    //this method will execute when we pic an item from the spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Setting the values to textviews for a selected item
        switch(parent.getId()) {
            case R.id.spin_desig: {

                desig = parent.getItemAtPosition(position).toString();
            return;
            }

            case R.id.spin_dept:{

                Toast.makeText(
                        getApplicationContext(),
                        parent.getItemAtPosition(position).toString() + " Selected",
                        Toast.LENGTH_LONG).show();
                dname = parent.getItemAtPosition(position).toString();


            }

 }

    }

    //When no item is selected this method would execute
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }




}