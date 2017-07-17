package com.grid.appy.citizenrights.adapter;

/**
 * Created by Appy on 12-Jul-17.
 */
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
import com.grid.appy.citizenrights.model.News;

import java.util.List;
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private List<News> newsList;
    Context context ;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        public TextView title,  username, date;

        private ItemClickListener itemClickListener;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            username = (TextView) view.findViewById(R.id.username);
            date=(TextView)view.findViewById(R.id.date);


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


    public NewsAdapter(List<News> moviesList,Context context) {
        this.newsList = moviesList;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        News news = newsList.get(position);
        holder.title.setText(news.getTitle());
        holder.username.setText(news.getusername());


        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {



                if(isLongClick)
                {
                    Toast.makeText(context,"Long click" +newsList.get(position),Toast.LENGTH_SHORT).show();

                }
                else {
                    //Toast.makeText(context, "click" + newsList.get(position), Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
}

