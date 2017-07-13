package com.grid.appy.citizenrights.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.grid.appy.citizenrights.R;
import com.grid.appy.citizenrights.activity.NewissueActivity;
import com.grid.appy.citizenrights.adapter.NewsAdapter;
import com.grid.appy.citizenrights.adapter.YourissueAdapter;
import com.grid.appy.citizenrights.model.DividerItemDecoration;
import com.grid.appy.citizenrights.model.News;
import com.grid.appy.citizenrights.model.Yourissue;

import java.util.ArrayList;
import java.util.List;

public class ViewissueActivity extends AppCompatActivity {


    private List<Yourissue> yourissueList = new ArrayList<>();
    private RecyclerView recyclerView;
    private YourissueAdapter yiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewissue);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        yiAdapter = new YourissueAdapter(yourissueList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));



        recyclerView.setAdapter(yiAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent issues = new Intent(getApplicationContext(), NewissueActivity.class);
                startActivity(issues);
            }
        });

        prepareYourissueData();

    }

    private void prepareYourissueData() {
        Yourissue yourissue = new Yourissue("School issue", " 12-03-2010");
        yourissueList.add(yourissue);
         yourissue = new Yourissue("School issue", " 12-03-2010");
        yourissueList.add(yourissue);
        yourissue = new Yourissue("School issue", " 12-03-2010");
        yourissueList.add(yourissue);
         yourissue = new Yourissue("School issue", " 12-03-2010");
        yourissueList.add(yourissue);
         yourissue = new Yourissue("School issue", " 12-03-2010");
        yourissueList.add(yourissue);
         yourissue = new Yourissue("School issue", " 12-03-2010");
        yourissueList.add(yourissue);







        yiAdapter.notifyDataSetChanged();
    }

}
