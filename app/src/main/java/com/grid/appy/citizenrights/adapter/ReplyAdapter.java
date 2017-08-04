package com.grid.appy.citizenrights.adapter;

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

/**
 * Created by Appy on 04-Aug-17.
 */

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ViewHolder> {

    Context context;

    List<GetDataAdapter> getDataAdapter;

    public ReplyAdapter(List<GetDataAdapter> getDataAdapter, Context context){

        super();
        this.getDataAdapter = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_list_row, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder Viewholder, int position) {

        final GetDataAdapter getDataAdapter1 =  getDataAdapter.get(position);


        Viewholder.reply_email.setText(getDataAdapter1.getReply_email());
        Viewholder.reply_reply.setText(getDataAdapter1.getReply_reply());
        Viewholder.reply_date.setText(getDataAdapter1.getReply_date());

        Viewholder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context, IssuedetailActivity.class);
                String message=getDataAdapter1.getReply_issueid();
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


        public TextView reply_email;
        public TextView reply_date;
        public TextView reply_reply;
        public CardView cardView;


        public ViewHolder(View itemView) {

            super(itemView);

            reply_email = (TextView) itemView.findViewById(R.id.reply_email) ;
            reply_reply = (TextView) itemView.findViewById(R.id.reply_reply) ;
            reply_date = (TextView) itemView.findViewById(R.id.reply_date) ;
            cardView =(CardView)itemView.findViewById(R.id.cardview6);



        }



    }
}