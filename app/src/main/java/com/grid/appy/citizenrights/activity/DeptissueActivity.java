package com.grid.appy.citizenrights.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.grid.appy.citizenrights.R;
import com.grid.appy.citizenrights.adapter.DeptAdapter;
import com.grid.appy.citizenrights.model.Dept;
import com.grid.appy.citizenrights.model.DividerItemDecoration;


import java.util.ArrayList;
import java.util.List;

public class DeptissueActivity extends AppCompatActivity {

    private List<Dept> deptList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DeptAdapter dAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deptissue);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newissue = new Intent(getApplicationContext(), NewissueActivity.class);
                startActivity(newissue);
            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        dAdapter = new DeptAdapter(deptList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));



        recyclerView.setAdapter(dAdapter);





        prepareDeptData();


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

}
