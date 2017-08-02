package com.grid.appy.citizenrights.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.grid.appy.citizenrights.R;
import com.grid.appy.citizenrights.activity.AdminviewActivity;
import com.grid.appy.citizenrights.activity.DeptissueActivity;
import com.grid.appy.citizenrights.activity.PeopleviewActivity;

import java.util.List;

import static com.grid.appy.citizenrights.R.id.parent;

/**
 * Created by HP on 02-08-2017.
 */



public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder> {
    Context context;

    List<GetDataAdapter> getDataAdapter;

   ImageLoader imageLoader2;

    public AdminAdapter(List<GetDataAdapter> getDataAdapter, Context context){

        super();
        this.getDataAdapter = getDataAdapter;
        this.context = context;
    }



    @Override
    public AdminAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.people_list, parent, false);

       ViewHolder viewHolder=new ViewHolder(v);

        return viewHolder;
    }




    @Override
    public void onBindViewHolder(AdminAdapter.ViewHolder ViewHolder, int position) {

        final GetDataAdapter getDataAdapter1 =  getDataAdapter.get(position);

       imageLoader2 = ServerImageParseAdapter.getInstance(context).getImageLoader();

        imageLoader2.get(getDataAdapter1.getImageServerUrl2(),
                ImageLoader.getImageListener(
                        ViewHolder.icon,//Server Image
                        R.drawable.citizen,//Before loading server image the default showing image.
                        android.R.drawable.ic_dialog_alert //Error image if requested image dose not found on server.
                )
        );
        ViewHolder.icon.setImageUrl(getDataAdapter1.getImageServerUrl2(), imageLoader2);

        ViewHolder.deptemail.setText(getDataAdapter1.getimagetitlename2());

        ViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context, PeopleviewActivity.class);
                String message=getDataAdapter1.getimagetitlename2();
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

        public TextView deptemail;
        public NetworkImageView icon ;
        public CardView cardView;


        public ViewHolder(View itemView) {

            super(itemView);

    deptemail = (TextView) itemView.findViewById(R.id.textView_item2) ;

            icon = (NetworkImageView) itemView.findViewById(R.id.VollyNetworkImageView2) ;

            cardView =(CardView)itemView.findViewById(R.id.cardview1);



        }



    }
}





