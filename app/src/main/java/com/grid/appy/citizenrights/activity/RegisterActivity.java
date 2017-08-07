package com.grid.appy.citizenrights.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

        import com.grid.appy.citizenrights.R;
import com.grid.appy.citizenrights.config.AppConfig;
import com.grid.appy.citizenrights.config.AppController;
import com.grid.appy.citizenrights.helper.SQLiteHandler;
import com.grid.appy.citizenrights.helper.SessionManager;

        import java.util.regex.Matcher;
        import java.util.regex.Pattern;

public class RegisterActivity extends Activity {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
String imei,type;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.activity_register);
       TelephonyManager mngr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);



        imei= mngr.getDeviceId();

        type="user";

        // Progress dialog
        pDialog = new ProgressDialog(this);

        Button login = (Button) findViewById(R.id.btnLinkToLoginScreen);
        // Listening to Login Screen link
     login.setOnClickListener(new View.OnClickListener() {

                                           public void onClick(View arg0) {
                                               // Closing registration screen
                                               // Switching to Login Screen/closing register screen
                                               Intent logout = new Intent(getApplicationContext(), LoginActivity.class);
                                               startActivity(logout);
                                           }
                                       });
        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Check if user is already logged in or not
      //  if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
           // Intent intent = new Intent(RegisterActivity.this,
                 //   HomeActivity.class);
         //   startActivity(intent);
        //    finish();
      //  }

        Button deptrScreen = (Button) findViewById(R.id.deptregister);
        // Listening to Login Screen link
        deptrScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Closing registration screen
                // Switching to Login Screen/closing register screen
                Intent logout = new Intent(getApplicationContext(), DeptregistrationActivity.class);
                startActivity(logout);

            }
        });

        Button register = (Button) findViewById(R.id.btnRegister);
        register.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Closing registration screen
                // closing register screen

               // TelephonyManager tManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                //String imei = tManager.getDeviceId();








         //       TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
          //      telephonyManager.getDeviceId();


                // String imei =telephonyManager.toString();;
                //form validation


                //imei="1234";
                EditText aadhar;
                aadhar = (EditText) findViewById(R.id.aadhar1);
                EditText nameedit;
                nameedit = (EditText) findViewById(R.id.name);
                EditText phoneedit;
                phoneedit = (EditText) findViewById(R.id.phone);
                EditText emailedit;
                emailedit = (EditText) findViewById(R.id.email);
                EditText pass;
                pass = (EditText) findViewById(R.id.password);
                EditText repass;
                repass = (EditText) findViewById(R.id.repassword);

                final String aadhar1 = aadhar.getText().toString();
                final String email = emailedit.getText().toString();
                final String name = nameedit.getText().toString();
                final String phone = phoneedit.getText().toString();
                final String password = pass.getText().toString();
                final String repassword = repass.getText().toString();

                if (!isValidName(name)) {
                    nameedit.setError("Invalid Name");
                } else if (!isValidPhone(phone)) {
                    phoneedit.setError("Invalid Phone");
                } else if (!isValidEmail(email)) {
                    emailedit.setError("Invalid Email");
                } else if (!isValidPassword(password)) {
                    pass.setError("Password must be greater than 4 characters");
                } else if (!isValidrePassword(repassword, password)) {
                    repass.setError("Password does not match");
                } else {
                    //switching to home activity
                    registerUser(aadhar1, name, phone, imei, email, password,type);
                }
            }
        });
    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    //validating name
    private boolean isValidName(String email) {
        String EMAIL_PATTERN = "^[A-Za-z\\\\s]{1,}[\\\\.]{0,1}[A-Za-z\\\\s]{0,}$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    //validating phone
    private boolean isValidPhone(String email) {
        String EMAIL_PATTERN = "^[2-9]{1}[0-9]{9}$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    //validating password
    private boolean isValidPassword(String pass) {
        String EMAIL_PATTERN = "((?=.*\\d)(?=.*[a-z]).{6,20})";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(pass);
        return matcher.matches();

    }

    //validating re-enter password
    private boolean isValidrePassword(String repass, String pass) {
        if (repass.contentEquals(pass)) {
            return true;
        } else {
            return false;
        }
    }

    private void registerUser(final String aadhar,final String name,final String phone,final String imei, final String email,
                              final String password,final String type) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite

                        JSONObject user = jObj.getJSONObject("user");
                        String aadhar = user.getString("aadhar");
                        String name = user.getString("name");
                        String phone = user.getString("phone");
                        String imei = user.getString("imei");
                        String email = user.getString("email");
                        String type=user.getString("type");
                        Log.e("values================",aadhar+name+phone+imei+email+type);


                        // Inserting row in users table
                        db.addUser(aadhar,name,phone,imei, email,type);

                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(
                                RegisterActivity.this,
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
                params.put("aadhar",aadhar);
                params.put("name", name);
                params.put("phone", phone);
                params.put("imei", imei);
                params.put("email", email);
                params.put("password", password);
                params.put("type",type);

                Log.e("post---------------",aadhar+name+phone+imei+email+password+type);

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
}






