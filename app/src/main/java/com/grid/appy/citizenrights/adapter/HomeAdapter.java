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

import com.grid.appy.citizenrights.R;
import com.grid.appy.citizenrights.activity.IssuedetailActivity;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    Context context;

    List<GetDataAdapter> getDataAdapter;

    public HomeAdapter(List<GetDataAdapter> getDataAdapter, Context context){

        super();
        this.getDataAdapter = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_row, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder Viewholder, int position) {

        final GetDataAdapter getDataAdapter1 =  getDataAdapter.get(position);


        Viewholder.home_title.setText(getDataAdapter1.getHome_title());
        Viewholder.home_username.setText(getDataAdapter1.getHome_username());
        Viewholder.home_date.setText(getDataAdapter1.getHome_date());

        Viewholder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context, IssuedetailActivity.class);
                String message=getDataAdapter1.getHome_issueid();
                intent.putExtra("message", message);
                context.startActivity(intent);


            }

        });



        Viewholder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(v.getContext(), "Position/" , Toast.LENGTH_SHORT).show();
                return false;
            }
        });








        Viewholder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context, IssuedetailActivity.class);
                String message=getDataAdapter1.getHome_issueid();
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


        public TextView home_title;
        public TextView home_date;
        public TextView home_username;
        public CardView cardView;


        public ViewHolder(View itemView) {

            super(itemView);

            home_title = (TextView) itemView.findViewById(R.id.home_title) ;
            home_username = (TextView) itemView.findViewById(R.id.home_username) ;
            home_date = (TextView) itemView.findViewById(R.id.home_date) ;
            cardView =(CardView)itemView.findViewById(R.id.cardview2);



        }



    }
}