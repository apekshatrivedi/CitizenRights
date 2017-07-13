package com.grid.appy.citizenrights.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.grid.appy.citizenrights.R;
import com.grid.appy.citizenrights.activity.IssuedetailActivity;
import com.grid.appy.citizenrights.interfaces.ItemClickListener;
import com.grid.appy.citizenrights.model.Yourissue;

import java.util.List;

/**
 * Created by Appy on 13-Jul-17.
 */

public class YourissueAdapter extends RecyclerView.Adapter<YourissueAdapter.MyViewHolder>{

    private List<Yourissue> yourissueList;
    Context context ;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        public TextView title,  date;

        private ItemClickListener itemClickListener;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            date = (TextView) view.findViewById(R.id.date);


            view.setOnClickListener(this);
            view.setOnLongClickListener(this);

        }

        public void setItemClickListener(ItemClickListener itemClickListener){

            this.itemClickListener=itemClickListener;

        }

        @Override
        public void onClick(View v) {

            itemClickListener.onClick(v,getAdapterPosition(),false);
            Intent intent = new Intent(context, IssuedetailActivity.class);
            context.startActivity(intent);




        }

        @Override
        public boolean onLongClick(View v) {

            itemClickListener.onClick(v,getAdapterPosition(),true);
            return true;
        }
    }


    public YourissueAdapter(List<Yourissue> yourissueList,Context context) {
        this.yourissueList = yourissueList;
        this.context=context;
    }

    @Override
    public YourissueAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.your_issue_list, parent, false);

        return new YourissueAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(YourissueAdapter.MyViewHolder holder, int position) {


        Yourissue yourissue = yourissueList.get(position);
        holder.title.setText(yourissue.getTitle());
        holder.date.setText(yourissue.getDate());


        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {



                if(isLongClick)
                {
                    Toast.makeText(context,"Long click" +yourissueList.get(position),Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(context, "click" + yourissueList.get(position), Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return yourissueList.size();
    }
}
