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

import com.grid.appy.citizenrights.R;
import com.grid.appy.citizenrights.activity.IssuedetailActivity;

import java.util.List;

public class YourissueAdapter extends RecyclerView.Adapter<YourissueAdapter.ViewHolder> {

    Context context;

    List<GetDataAdapter> getDataAdapter;

    public YourissueAdapter(List<GetDataAdapter> getDataAdapter, Context context){

        super();
        this.getDataAdapter = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.your_issue_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder Viewholder, int position) {

        final GetDataAdapter getDataAdapter1 =  getDataAdapter.get(position);


        Viewholder.history_title.setText(getDataAdapter1.getHistory_title());
        Viewholder.history_date.setText(getDataAdapter1.getHistory_date());

        Viewholder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context, IssuedetailActivity.class);
                String message=getDataAdapter1.getHistory_issueid();
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


        public TextView history_title;
        public TextView history_date;
        public CardView cardView;


        public ViewHolder(View itemView) {

            super(itemView);

            history_title = (TextView) itemView.findViewById(R.id.history_title) ;
            history_date = (TextView) itemView.findViewById(R.id.history_date) ;
            cardView =(CardView)itemView.findViewById(R.id.cardview3);



        }



    }
}