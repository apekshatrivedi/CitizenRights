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
import com.grid.appy.citizenrights.activity.PeopleviewActivity;
import com.grid.appy.citizenrights.interfaces.ItemClickListener;
import com.grid.appy.citizenrights.model.People;


import java.util.List;

/**
 * Created by Appy on 20-Jul-17.
 */

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.MyViewHolder>{

    private List<People> peopleList;
    Context context ;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        public TextView name,  dept;

        private ItemClickListener itemClickListener;
        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            dept = (TextView) view.findViewById(R.id.dept);


            view.setOnClickListener(this);
            view.setOnLongClickListener(this);

        }

        public void setItemClickListener(ItemClickListener itemClickListener){

            this.itemClickListener=itemClickListener;

        }

        @Override
        public void onClick(View v) {

            itemClickListener.onClick(v,getAdapterPosition(),false);
            Intent intent = new Intent(context, PeopleviewActivity.class);
            context.startActivity(intent);




        }

        @Override
        public boolean onLongClick(View v) {

            itemClickListener.onClick(v,getAdapterPosition(),true);
            return true;
        }
    }


    public PeopleAdapter(List<People> peopleList,Context context) {
        this.peopleList = peopleList;
        this.context=context;
    }

    @Override
    public PeopleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.people_list, parent, false);

        return new PeopleAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PeopleAdapter.MyViewHolder holder, int position) {


        People people = peopleList.get(position);
        holder.name.setText(people.getName());
        holder.dept.setText(people.getDept());


        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {



                if(isLongClick)
                {
                    Toast.makeText(context,"Long click" +peopleList.get(position),Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(context, "click" + peopleList.get(position), Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return peopleList.size();
    }
}

