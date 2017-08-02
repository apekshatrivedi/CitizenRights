package com.grid.appy.citizenrights.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.grid.appy.citizenrights.R;
import com.grid.appy.citizenrights.adapter.CommentAdapter;
import com.grid.appy.citizenrights.helper.SQLiteHandler;
import com.grid.appy.citizenrights.helper.SessionManager;
import com.grid.appy.citizenrights.model.CheckNetwork;
import com.grid.appy.citizenrights.model.Comment;
import com.grid.appy.citizenrights.model.DividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.grid.appy.citizenrights.config.AppConfig.GET_ISSUE_DATA;

public class IssuedetailActivity extends AppCompatActivity {


    private SessionManager session;
    private SQLiteHandler db;

    public static final String KEY_TITLE = "title";
    public static final String KEY_USEREMAIL = "useremail";
    public static final String KEY_issuedatetime = "issuedatetime";
    public static final String KEY_desc = "description";
    public static final String KEY_IMGPATH= "proof";
    public static final String JSON_ARRAY = "result";


    TextView issue_title;
    TextView issue_useremail;
    TextView issue_datetime;
    TextView issue_description;


    private ProgressDialog loading;

    //recycleview adapter
    private List<Comment> commentList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CommentAdapter cAdapter;

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



                //Floating fab
                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                            showInputDialog();

                    }
                });


                recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                cAdapter = new CommentAdapter(commentList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
                recyclerView.setAdapter(cAdapter);
                prepareCommentData();


        }

        else
        {

            Intent newissue = new Intent(getApplicationContext(), NointernetActivity.class);
            startActivity(newissue);

        }

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
            String title="";
            String useremail="";
            String date = "";
            String desc = "";
            String imgpaths;

            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray result = jsonObject.getJSONArray(JSON_ARRAY);
                JSONObject issueData = result.getJSONObject(0);
                title = issueData.getString(KEY_TITLE);
                useremail = issueData.getString(KEY_USEREMAIL);
                date = issueData.getString(KEY_issuedatetime);
                desc = issueData.getString(KEY_desc);
                imgpaths=issueData.getString(KEY_IMGPATH);

                issue_title=(TextView)findViewById(R.id.issue_title);
                issue_useremail=(TextView)findViewById(R.id.issue_useremail);
                issue_datetime=(TextView)findViewById(R.id.issue_datetime);
                issue_description=(TextView)findViewById(R.id.issue_description);

                issue_title.setText(title);
                issue_useremail.setText(useremail);
                issue_datetime.setText(date);
                issue_description.setText(desc);

                Toast.makeText(this,"imgpath"+imgpaths,Toast.LENGTH_LONG).show();




            } catch (JSONException e) {
            e.printStackTrace();
        }
        //textViewResult.setText("Name:\t"+name+"\nAddress:\t" +address+ "\nVice Chancellor:\t"+ vc);
    }






    private void prepareCommentData() {
        Comment comment = new Comment("Department name", "\n" +"Reply-"+"\n"+


               "\n" +
                "Mei no idque augue minim, regione ornatus has ut. Mei ne meis debitis propriae. Ut vis quod indoctum. Eos probo fabulas cu, bonorum tractatos persequeris eos ea.\n" +
                "\n" +
                "hjnbvbn jnm", "12-03-2015","11:45 AM");
        commentList.add(comment);

         comment = new Comment("Username", "\n" +"Reply-"+"\n"+


               "\n" +
                "Mei no idque augue minim, regione ornatus has ut. Mei ne meis debitis propriae. Ut vis quod indoctum. Eos probo fabulas cu, bonorum tractatos persequeris eos ea.\n" +
                "\n" +

                "Agam docendi mea no, quem diceret incorrupte ei ius. No quo i", "12-03-2015","11:45 AM");
        commentList.add(comment);




        cAdapter.notifyDataSetChanged();

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            return true;
        }
        if(id==R.id.action_edit)
        {
            Intent newedit = new Intent(getApplicationContext(), EditissueActivity.class);
            startActivity(newedit);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void showInputDialog() {


        if (!session.isLoggedIn()) {

            Intent newissue = new Intent(getApplicationContext(), RegloginActivity.class);
            startActivity(newissue);

        }

        else {


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
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
