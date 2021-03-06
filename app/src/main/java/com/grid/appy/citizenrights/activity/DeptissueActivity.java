package com.grid.appy.citizenrights.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.grid.appy.citizenrights.R;
import com.grid.appy.citizenrights.adapter.DeptAdapter;
import com.grid.appy.citizenrights.adapter.GetDataAdapter;
import com.grid.appy.citizenrights.helper.SQLiteHandler;
import com.grid.appy.citizenrights.model.CheckNetwork;
import com.grid.appy.citizenrights.model.DividerItemDecoration;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;
import static com.grid.appy.citizenrights.config.AppConfig.GET_JSON_DATA_HTTP_URL3;

public class DeptissueActivity extends AppCompatActivity {


    private SQLiteHandler db;

    List<GetDataAdapter> GetDataAdapter1;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager recyclerViewlayoutManager;

    RecyclerView.Adapter deptadapter;

    String JSON_TITLE = "title";
    String JSON_USEREMAIL = "useremail";
    String JSON_ISSUEDATETIME = "issuedatetime";
    String JSON_ISSUEID ="issueid";


    JsonArrayRequest jsonArrayRequest ;

    RequestQueue requestQueue ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deptissue);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (id == R.id.action_search) {

            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }


        if(CheckNetwork.isInternetAvailable(this)) {


            db = new SQLiteHandler(getApplicationContext());





            GetDataAdapter1 = new ArrayList<>();

            recyclerView = (RecyclerView) findViewById(R.id.recyclerview4);

            recyclerView.setHasFixedSize(true);

            recyclerViewlayoutManager = new LinearLayoutManager(this);

            recyclerView.setLayoutManager(recyclerViewlayoutManager);

            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

            JSON_DATA_WEB_CALL();


            //new issue fab
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent newissue = new Intent(getApplicationContext(), NewissueActivity.class);
                    startActivity(newissue);
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


        // jsonArrayRequest = new JsonArrayRequest("http://192.168.1.101/Grid/issuehistory.php?useremail=test@test.com",

        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("message");
        jsonArrayRequest = new JsonArrayRequest(GET_JSON_DATA_HTTP_URL3+message,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        JSON_PARSE_DATA_AFTER_WEBCALL(response);
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

                GetDataAdapter2.setdept_title(json.getString(JSON_TITLE));
                GetDataAdapter2.setdept_username(json.getString(JSON_USEREMAIL));
                GetDataAdapter2.setdept_date(json.getString(JSON_ISSUEDATETIME));
                GetDataAdapter2.setdept_issueid(json.getString(JSON_ISSUEID));


            } catch (JSONException e) {

                e.printStackTrace();
            }
            GetDataAdapter1.add(GetDataAdapter2);
        }

        deptadapter = new DeptAdapter(GetDataAdapter1, this);

        recyclerView.setAdapter(deptadapter);
    }

    //action bar icon
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
