package com.grid.appy.citizenrights.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import org.json.JSONArray;
import java.util.ArrayList;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import java.util.List;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.grid.appy.citizenrights.R;
import com.grid.appy.citizenrights.adapter.GetDataAdapter;
import com.grid.appy.citizenrights.adapter.RecyclerViewAdapter;
import com.grid.appy.citizenrights.model.DividerItemDecoration;


import org.json.JSONException;
import org.json.JSONObject;

import static com.grid.appy.citizenrights.config.AppConfig.GET_JSON_DATA_HTTP_URL;


public class ViewdeptActivity extends AppCompatActivity {


    String HTTP_JSON_URL = "http://192.168.1.102/grid/ImageJsonData.php";


    List<GetDataAdapter> GetDataAdapter1;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager recyclerViewlayoutManager;

    RecyclerView.Adapter recyclerViewadapter;

    //String GET_JSON_DATA_HTTP_URL = "http://androidblog.esy.es/ImageJsonData.php";
   // String GET_JSON_DATA_HTTP_URL = "http://192.168.1.101/Grid/ImageJsonData.php";
    String JSON_IMAGE_TITLE_NAME = "deptname";
    String JSON_IMAGE_URL = "deptpath";

    JsonArrayRequest jsonArrayRequest ;

    RequestQueue requestQueue ;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdept);
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

        jsonArrayRequest = new JsonArrayRequest(GET_JSON_DATA_HTTP_URL,

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

                GetDataAdapter2.setImageTitleNamee(json.getString(JSON_IMAGE_TITLE_NAME));

                GetDataAdapter2.setImageServerUrl(json.getString(JSON_IMAGE_URL));

            } catch (JSONException e) {

                e.printStackTrace();
            }
            GetDataAdapter1.add(GetDataAdapter2);
        }

        recyclerViewadapter = new RecyclerViewAdapter(GetDataAdapter1, this);

        recyclerView.setAdapter(recyclerViewadapter);
    }

}
