package com.grid.appy.citizenrights.adapter;

/**
 * Created by Appy on 27-Jul-17.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.grid.appy.citizenrights.R;
import com.grid.appy.citizenrights.activity.IssuedetailActivity;
import com.grid.appy.citizenrights.interfaces.ItemClickListener;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;

    List<GetDataAdapter> getDataAdapter;

    ImageLoader imageLoader1;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        public TextView textView_item;

        private ItemClickListener itemClickListener;
        public MyViewHolder(View view) {
            super(view);
            textView_item = (TextView) view.findViewById(R.id.textView_item);



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











    public RecyclerViewAdapter(List<GetDataAdapter> getDataAdapter, Context context){

        super();
        this.getDataAdapter = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder Viewholder, int position) {

        GetDataAdapter getDataAdapter1 =  getDataAdapter.get(position);

        imageLoader1 = ServerImageParseAdapter.getInstance(context).getImageLoader();

        imageLoader1.get(getDataAdapter1.getImageServerUrl(),
                ImageLoader.getImageListener(
                        Viewholder.networkImageView,//Server Image
                        R.drawable.citizen,//Before loading server image the default showing image.
                        android.R.drawable.ic_dialog_alert //Error image if requested image dose not found on server.
                )
        );

        Viewholder.networkImageView.setImageUrl(getDataAdapter1.getImageServerUrl(), imageLoader1);

        Viewholder.ImageTitleNameView.setText(getDataAdapter1.getImageTitleName());

    }

    @Override
    public int getItemCount() {

        return getDataAdapter.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView ImageTitleNameView;
        public NetworkImageView networkImageView ;

        public ViewHolder(View itemView) {

            super(itemView);

            ImageTitleNameView = (TextView) itemView.findViewById(R.id.textView_item) ;

            networkImageView = (NetworkImageView) itemView.findViewById(R.id.VollyNetworkImageView1) ;

        }
    }
}