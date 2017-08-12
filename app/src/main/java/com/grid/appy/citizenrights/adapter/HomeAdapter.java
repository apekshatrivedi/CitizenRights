package com.grid.appy.citizenrights.adapter;

/**
 * Created by Appy on 27-Jul-17.
 */

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.grid.appy.citizenrights.R;
import com.grid.appy.citizenrights.activity.HomeActivity;
import com.grid.appy.citizenrights.activity.IssuedetailActivity;
import com.grid.appy.citizenrights.config.AppConfig;
import com.grid.appy.citizenrights.config.AppController;
import com.grid.appy.citizenrights.helper.SQLiteHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    Context context;
    private ProgressDialog pDialog;
    private SQLiteHandler db;
    String email;


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
                Log.e("Homeada",message);
                intent.putExtra("message", message);
                context.startActivity(intent);


            }
        });


/*
        Viewholder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //Toast.makeText(v.getContext(), "Position/"+getDataAdapter1.getHome_issueid() , Toast.LENGTH_SHORT).show();
                HashMap<String, String> user = db.getUserDetails();
                email = user.get("username");
                if (email.trim().equals(getDataAdapter1.getHome_username())) {
                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("Delete this issue");
                alertDialog.setMessage("Are you sure?");
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // here you can add functions
                        deleteissue(getDataAdapter1.getHome_issueid());
                    }
                });
                alertDialog.setIcon(R.drawable.citizen);
                alertDialog.show();
            }
            else{
                    Toast.makeText(v.getContext(), "You do not have permission", Toast.LENGTH_SHORT).show();
                }

                    return false;
                }
            });


*/
    }
    /*
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void deleteissue(final String issueid)
    {
        String tag_string_req = "req_register";
        // Progress dialog
        pDialog = new ProgressDialog(context);
        pDialog.setCancelable(false);
        pDialog.setMessage("Deleting ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.DELETE_ISSUE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d( "Register Response: " , response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        Intent i = new Intent(context, HomeActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(i);

                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(context,
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e( "Registration Error: " ,error.getMessage());
                Toast.makeText(context,
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url




                Map<String, String> params = new HashMap<String, String>();
                params.put("issueid",issueid);


                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    */

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