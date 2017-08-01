package com.grid.appy.citizenrights.adapter;

/**
 * Created by Appy on 27-Jul-17.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.grid.appy.citizenrights.R;
import com.grid.appy.citizenrights.activity.DeptissueActivity;
import com.grid.appy.citizenrights.activity.IssuedetailActivity;
import com.grid.appy.citizenrights.activity.NewissueActivity;
import com.grid.appy.citizenrights.activity.ViewdeptActivity;
import com.grid.appy.citizenrights.interfaces.ItemClickListener;
import com.grid.appy.citizenrights.model.Yourissue;

import java.util.List;

public class DeptAdapter extends RecyclerView.Adapter<DeptAdapter.ViewHolder> {

    Context context;

    List<GetDataAdapter> getDataAdapter;

    public DeptAdapter(List<GetDataAdapter> getDataAdapter, Context context){

        super();
        this.getDataAdapter = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dept_issue_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder Viewholder, int position) {

        final GetDataAdapter getDataAdapter1 =  getDataAdapter.get(position);


        Viewholder.dept_title.setText(getDataAdapter1.getdept_title());
        Viewholder.dept_username.setText(getDataAdapter1.getdept_username());
        Viewholder.dept_date.setText(getDataAdapter1.getdept_date());

        Viewholder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context, IssuedetailActivity.class);
                String message=getDataAdapter1.getdept_issueid();
                intent.putExtra("message", message);
                context.startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {

        return getDataAdapter.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{


        public TextView dept_title;
        public TextView dept_date;
        public TextView dept_username;
        public CardView cardView;


        public ViewHolder(View itemView) {

            super(itemView);

            dept_title = (TextView) itemView.findViewById(R.id.dept_title) ;
            dept_username = (TextView) itemView.findViewById(R.id.dept_username) ;
            dept_date = (TextView) itemView.findViewById(R.id.dept_date) ;
            cardView =(CardView)itemView.findViewById(R.id.cardview4);



        }



    }
}