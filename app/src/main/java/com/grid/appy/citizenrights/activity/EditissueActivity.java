package com.grid.appy.citizenrights.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
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

public class EditissueActivity extends AppCompatActivity {

    EditText et_title;
    EditText et_description;

    String title;
     String desc;
    String issueid;



    private ProgressDialog pDialog;

    private static final String TAG = EditissueActivity.class.getSimpleName();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editissue);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("message");
        String message1=bundle.getString("message1");
        String message2=bundle.getString("message2");
      title = message;
       desc = message1;
        issueid=message2;


        et_title=(EditText)findViewById(R.id.edit_title);
        et_description=(EditText)findViewById(R.id.edit_desc);

        et_title.setText(title);
        et_description.setText(desc);

        Button edit=(Button)findViewById(R.id.editpost);
        edit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // reset email button

                editissue(issueid,et_title.getText().toString(),et_description.getText().toString());

            }
        });

    }

    private void editissue(final String issueid,final String title,final String desc)
    {
        String tag_string_req = "req_register";
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Updating ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.EDIT_ISSUE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
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
                params.put("issueid",issueid);
                params.put("title", title);
                params.put("description",desc);

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






    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
