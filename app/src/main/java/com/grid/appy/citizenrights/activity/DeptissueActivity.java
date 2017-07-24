package com.grid.appy.citizenrights.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.grid.appy.citizenrights.R;
import com.grid.appy.citizenrights.adapter.DeptAdapter;
import com.grid.appy.citizenrights.model.CheckNetwork;
import com.grid.appy.citizenrights.model.Dept;
import com.grid.appy.citizenrights.model.DividerItemDecoration;


import java.util.ArrayList;
import java.util.List;

import static android.R.id.message;

public class DeptissueActivity extends AppCompatActivity {



    //Recycler view adapter objects
    private List<Dept> deptList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DeptAdapter dAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deptissue);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(CheckNetwork.isInternetAvailable(this)) {
            Bundle bundle = getIntent().getExtras();
            String message = bundle.getString("message");

            TextView txtView = (TextView) findViewById(R.id.deptname);
            txtView.setText(message);

            //new issue fab
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent newissue = new Intent(getApplicationContext(), NewissueActivity.class);
                    startActivity(newissue);
                }
            });


            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            dAdapter = new DeptAdapter(deptList, this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            recyclerView.setAdapter(dAdapter);
            prepareDeptData();
        }
        else
        {
            Intent newissue = new Intent(getApplicationContext(), NointernetActivity.class);
            startActivity(newissue);
        }

    }

    private void prepareDeptData() {
        Dept dept = new Dept("Title", "username", "12-03-2015");
        deptList.add(dept);
        dept = new Dept("Title", "username", "12-03-2015");
        deptList.add(dept);
         dept = new Dept("Title", "username", "12-03-2015");
        deptList.add(dept);
        dept = new Dept("Title", "username", "12-03-2015");
        deptList.add(dept);

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
