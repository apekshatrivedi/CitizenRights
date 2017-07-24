package com.grid.appy.citizenrights.activity;


import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.grid.appy.citizenrights.R;

import com.grid.appy.citizenrights.adapter.PeopleAdapter;

import com.grid.appy.citizenrights.model.CheckNetwork;
import com.grid.appy.citizenrights.model.DividerItemDecoration;

import com.grid.appy.citizenrights.model.People;


import java.util.ArrayList;
import java.util.List;

public class AdminviewActivity extends AppCompatActivity {





    private List<People> peopleList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PeopleAdapter pAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if(CheckNetwork.isInternetAvailable(this)){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view1);


        pAdapter = new PeopleAdapter(peopleList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));



        recyclerView.setAdapter(pAdapter);



        preparePeopleData();}

        else
        {
            Intent newissue = new Intent(getApplicationContext(), NointernetActivity.class);
            startActivity(newissue);
        }

    }

    private void preparePeopleData() {
        People people = new People("Name", "Education");
        peopleList.add(people);

        people = new People("Name", "Work");
        peopleList.add(people);
        pAdapter.notifyDataSetChanged();
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


}
