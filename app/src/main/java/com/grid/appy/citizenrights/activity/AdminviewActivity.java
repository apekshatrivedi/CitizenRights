package com.grid.appy.citizenrights.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.grid.appy.citizenrights.R;
import com.grid.appy.citizenrights.adapter.AdminAdapter;
import com.grid.appy.citizenrights.adapter.GetDataAdapter;
import com.grid.appy.citizenrights.model.DividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.grid.appy.citizenrights.config.AppConfig.GET_IMAGEJSON_HTTP_URL;
import static com.grid.appy.citizenrights.config.AppConfig.PATH2;

public class AdminviewActivity extends AppCompatActivity {
    List<GetDataAdapter> GetDataAdapter1;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager recyclerViewlayoutManager;

    RecyclerView.Adapter adminadapter;

    //String GET_JSON_DATA_HTTP_URL = "http://androidblog.esy.es/ImageJsonData.php";

    String JSON_IMAGE_TITLE_NAME = "deptmail";
   String JSON_IMAGE_URL = "icon";

    JsonArrayRequest jsonArrayRequest ;

    RequestQueue requestQueue ;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        GetDataAdapter1 = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);

        recyclerView.setHasFixedSize(true);

        recyclerViewlayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(recyclerViewlayoutManager);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));





        JSON_DATA_WEB_CALL();




    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }


    public void JSON_DATA_WEB_CALL(){

        jsonArrayRequest = new JsonArrayRequest(GET_IMAGEJSON_HTTP_URL,

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

                GetDataAdapter2.setimagetitlename2(json.getString(JSON_IMAGE_TITLE_NAME));
                String imageurl=PATH2+json.getString(JSON_IMAGE_URL);

               GetDataAdapter2.setImageServerUrl2(imageurl);

            } catch (JSONException e) {

                e.printStackTrace();
            }
            GetDataAdapter1.add(GetDataAdapter2);
        }

        adminadapter = new AdminAdapter(GetDataAdapter1, this);

        recyclerView.setAdapter(adminadapter);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);

    }

}


