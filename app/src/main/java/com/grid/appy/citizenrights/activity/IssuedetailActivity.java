package com.grid.appy.citizenrights.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.grid.appy.citizenrights.R;
import com.grid.appy.citizenrights.adapter.CommentAdapter;
import com.grid.appy.citizenrights.model.Comment;
import com.grid.appy.citizenrights.model.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class IssuedetailActivity extends AppCompatActivity {

    private List<Comment> commentList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CommentAdapter cAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issuedetail);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newcomment = new Intent(getApplicationContext(), NewcommentActivity.class);
                startActivity(newcomment);

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
        Comment comment = new Comment("Username", "Comment", "12-03-2015");
        commentList.add(comment);


        comment = new Comment("Username", "Comment", "12-03-2015");
        commentList.add(comment);

        comment = new Comment("Username", "Comment", "12-03-2015");
        commentList.add(comment);

        comment = new Comment("Username", "Comment", "12-03-2015");
        commentList.add(comment);

        comment = new Comment("Username", "Comment", "12-03-2015");
        commentList.add(comment);
        comment = new Comment("Username", "Comment", "12-03-2015");
        commentList.add(comment);
        comment = new Comment("Username", "Comment", "12-03-2015");
        commentList.add(comment);

        comment = new Comment("Username", "Comment", "12-03-2015");
        commentList.add(comment);
        comment = new Comment("Username", "Comment", "12-03-2015");
        commentList.add(comment);
        comment = new Comment("Username", "Comment", "12-03-2015");
        commentList.add(comment);

        cAdapter.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed() {
        Intent back = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(back);
    }

}
