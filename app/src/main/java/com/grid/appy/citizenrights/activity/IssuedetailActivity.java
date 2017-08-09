package com.grid.appy.citizenrights.activity;

import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.grid.appy.citizenrights.R;

import com.grid.appy.citizenrights.adapter.GetDataAdapter;
import com.grid.appy.citizenrights.adapter.ReplyAdapter;
import com.grid.appy.citizenrights.config.AppConfig;
import com.grid.appy.citizenrights.config.AppController;
import com.grid.appy.citizenrights.helper.RequestHandler;
import com.grid.appy.citizenrights.helper.SQLiteHandler;
import com.grid.appy.citizenrights.helper.SessionManager;
import com.grid.appy.citizenrights.model.CheckNetwork;
import com.grid.appy.citizenrights.model.DividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.grid.appy.citizenrights.config.AppConfig.ADDREPLY;

import static com.grid.appy.citizenrights.config.AppConfig.GET_ISSUE_DATA;
import static com.grid.appy.citizenrights.config.AppConfig.PATH;
import static com.grid.appy.citizenrights.config.AppConfig.REPLY;

public class IssuedetailActivity extends AppCompatActivity {

    String paths;
    ProgressBar pb;
    Dialog dialog;
    int downloadedSize = 0;
    int totalSize = 0;
    // TextView cur_val;


    String title="";
    String useremail="";
    String date = "";
    String desc = "";
    String issueid="";



    private ProgressDialog pDialog;

    private static final String TAG = IssuedetailActivity.class.getSimpleName();


    private SessionManager session;
    private SQLiteHandler db;



    List<GetDataAdapter> GetDataAdapter1;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager recyclerViewlayoutManager;

    RecyclerView.Adapter replyadapter;

    String JSON_EMAIL = "email";
    String JSON_REPLY = "reply";
    String JSON_REPLYDATETIME = "replydatetime";
    String JSON_ISSUEID ="issueid";


    JsonArrayRequest jsonArrayRequest ;

    RequestQueue requestQueue ;


    public static  final String KEY_ISSUEID= "issueid";
    public static final String KEY_TITLE = "title";
    public static final String KEY_USEREMAIL = "useremail";
    public static final String KEY_issuedatetime = "issuedatetime";
    public static final String KEY_desc = "description";
    public static final String KEY_IMGPATH= "proof";
    public static final String JSON_ARRAY = "result";

    String imgpaths;



    TextView issue_title;
    TextView issue_useremail;
    TextView issue_datetime;
    TextView issue_description;

    DownloadManager downloadManager;



    private ProgressDialog loading;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issuedetail);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);






        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());






        if(CheckNetwork.isInternetAvailable(this)) {

            getData();





            GetDataAdapter1 = new ArrayList<>();

            recyclerView = (RecyclerView) findViewById(R.id.recycler_view6);

            recyclerView.setHasFixedSize(true);

            recyclerViewlayoutManager = new LinearLayoutManager(this);

            recyclerView.setLayoutManager(recyclerViewlayoutManager);

            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

            Log.e("test","test");










            //Floating fab
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    showInputDialog();

                }
            });









        }

        else
        {

            Intent newissue = new Intent(getApplicationContext(), NointernetActivity.class);
            startActivity(newissue);

        }

    }



    public void JSON_DATA_WEB_CALL(){



        Log.e("url",REPLY+issueid);
        jsonArrayRequest = new JsonArrayRequest(REPLY+issueid,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        JSON_PARSE_DATA_AFTER_WEBCALL(response);
                        Log.e("-------",response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            GetDataAdapter GetDataAdapter2 = new GetDataAdapter();

            JSONObject json = null;
            try {



                json = array.getJSONObject(i);

                String test;

                GetDataAdapter2.setReply_email(json.getString(JSON_EMAIL));
                GetDataAdapter2.setReply_reply(json.getString(JSON_REPLY));
                GetDataAdapter2.setReply_date(json.getString(JSON_REPLYDATETIME));
                GetDataAdapter2.setReply_issueid(json.getString(JSON_ISSUEID));

                test=json.getString(JSON_REPLY);
                Log.e("response",test);


            } catch (JSONException e) {

                e.printStackTrace();
            }
            GetDataAdapter1.add(GetDataAdapter2);
        }

        replyadapter = new ReplyAdapter(GetDataAdapter1, this);

        recyclerView.setAdapter(replyadapter);
    }











    private void getData() {

        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("message");
        String issueid = message;

        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        String url = GET_ISSUE_DATA+"?issueid="+issueid;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(IssuedetailActivity.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response){



        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(JSON_ARRAY);
            JSONObject issueData = result.getJSONObject(0);
            title = issueData.getString(KEY_TITLE);
            useremail = issueData.getString(KEY_USEREMAIL);
            date = issueData.getString(KEY_issuedatetime);
            desc = issueData.getString(KEY_desc);
            imgpaths=issueData.getString(KEY_IMGPATH);
            issueid=issueData.getString(KEY_ISSUEID);

            issue_title=(TextView)findViewById(R.id.issue_title);
            issue_useremail=(TextView)findViewById(R.id.issue_useremail);
            issue_datetime=(TextView)findViewById(R.id.issue_datetime);
            issue_description=(TextView)findViewById(R.id.issue_description);

            issue_title.setText(title);
            issue_useremail.setText(useremail);
            issue_datetime.setText(date);
            issue_description.setText(desc);
            JSON_DATA_WEB_CALL();






            paths=PATH+imgpaths;
            Log.e(paths,paths);




              Button b = (Button) findViewById(R.id.download);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    downloadManager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
                    Uri uri = Uri.parse(paths);
                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    Long reference = downloadManager.enqueue(request);
                }
            });





            //   Toast.makeText(this,"imgpath"+paths,Toast.LENGTH_LONG).show();




        } catch (JSONException e) {
            e.printStackTrace();
        }

    }












    //action on back
    @Override
    public void onBackPressed() {
        finish();
    }


    //action bar icon
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.issue_menu, menu);



        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        HashMap<String, String> user = db.getUserDetails();
        String email = user.get("imei");

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {



            if(email.trim().equals(useremail)) {

                deleteissue(issueid);
            }

            else{
                Toast.makeText(getApplicationContext(),
                        "You do not have permission to perform this action!", Toast.LENGTH_LONG).show();
            }
            return true;
        }
        if(id==R.id.action_edit)
        {

            if(email.trim().equals(useremail)) {
                Intent newedit = new Intent(getApplicationContext(), EditissueActivity.class);
                String message = title;
                String message1 = desc;
                String message2 = issueid;
                newedit.putExtra("message", message);
                newedit.putExtra("message1", message1);
                newedit.putExtra("message2", message2);
                startActivity(newedit);
            }

            else{
                Toast.makeText(getApplicationContext(),
                        "You do not have permission to perform this action!", Toast.LENGTH_LONG).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void showInputDialog() {


        if (!session.isLoggedIn()) {

            Intent newissue = new Intent(getApplicationContext(), Chooselogin.class);
            startActivity(newissue);

        }

        else {

            HashMap<String, String> user = db.getUserDetails();
            String e= user.get("imei");
            String t=user.get("type");
            if(e.trim().equals(useremail)) {


                // get prompts.xml view
                LayoutInflater layoutInflater = LayoutInflater.from(this);
                View promptView = layoutInflater.inflate(R.layout.reply, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setView(promptView);
                final EditText editText = (EditText) promptView.findViewById(R.id.edittext);


                // setup a dialog window
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // resultText.setText("Hello, " + editText.getText());

                                addreply(editText.getText().toString());

                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create an alert dialog
                AlertDialog alert = alertDialogBuilder.create();
                alert.show();
            }
            else if(t.trim().equals("deptmember")){


                LayoutInflater layoutInflater = LayoutInflater.from(IssuedetailActivity.this);
                View promptView = layoutInflater.inflate(R.layout.reply, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(IssuedetailActivity.this);
                alertDialogBuilder.setView(promptView);
                final EditText editText = (EditText) promptView.findViewById(R.id.edittext);


                // setup a dialog window
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // resultText.setText("Hello, " + editText.getText());

                                addreply(editText.getText().toString());

                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create an alert dialog
                AlertDialog alert = alertDialogBuilder.create();
                alert.show();





            }
            else
            {

                Toast.makeText(getApplicationContext(),
                        "You do not have permission to reply!", Toast.LENGTH_LONG).show();
            }

        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    public void addreply(final String reply){

        class addreply extends AsyncTask<Void,Void,String> {


            HashMap<String, String> user = db.getUserDetails();
            String useremail = user.get("imei");


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //  Toast.makeText(NewissueActivity.this, s, Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), IssuedetailActivity.class);
                i.putExtra("message",issueid);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);

            }

            @Override
            protected String doInBackground(Void... params) {

                // Fetching user details from sqlite

                RequestHandler rh = new RequestHandler();
                HashMap<String, String> param = new HashMap<String, String>();

                Log.e("post data-------------",issueid+useremail+reply);
                param.put(KEY_ISSUEID,issueid);
                param.put(JSON_EMAIL, useremail);
                param.put(JSON_REPLY,reply);


                String result = rh.sendPostRequest(ADDREPLY, param);
                return result;
            }
        }

        addreply u = new addreply();
        u.execute();
    }













    private void deleteissue(final String issueid)
    {
        String tag_string_req = "req_register";
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Deleting ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.DELETE_ISSUE, new Response.Listener<String>() {

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