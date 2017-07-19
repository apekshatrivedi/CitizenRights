package com.grid.appy.citizenrights.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.grid.appy.citizenrights.R;
import com.grid.appy.citizenrights.adapter.CommentAdapter;
import com.grid.appy.citizenrights.model.Comment;
import com.grid.appy.citizenrights.model.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class IssuedetailActivity extends AppCompatActivity {

    //recycleview adapter
    private List<Comment> commentList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CommentAdapter cAdapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issuedetail);



        //Floating fab
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newedit = new Intent(getApplicationContext(), EditissueActivity.class);
                startActivity(newedit);

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

    } private void prepareCommentData() {
        Comment comment = new Comment("Department name", "\n" +"Reply-"+"\n"+


                "Usu cu officiis placerat recusabo, ea mea numquam intellegebat. Pro suas hinc viris an, populo efficiendi intellegebat mea ad! Nam minim delicata ut, ne aperiri adipisci prodesset sea? Ubique civibus eleifend sea at, sea cibo semper facilisis et, ne nusquam accusam vix. Audire appetere concludaturque ei eos, pro suas placerat te. Deleniti constituto cum ne, sit vitae fastidii corrumpit eu?\n" +
                "\n" +
                "Mei no idque augue minim, regione ornatus has ut. Mei ne meis debitis propriae. Ut vis quod indoctum. Eos probo fabulas cu, bonorum tractatos persequeris eos ea.\n" +
                "\n" +
                "Ea vix consequat conc\n" +
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

        return super.onOptionsItemSelected(item);
    }




}
