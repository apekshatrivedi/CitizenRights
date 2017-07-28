package com.grid.appy.citizenrights.activity;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.grid.appy.citizenrights.adapter.GetDataAdapter;
import com.grid.appy.citizenrights.adapter.HomeAdapter;
import com.grid.appy.citizenrights.model.CheckNetwork;
import com.grid.appy.citizenrights.R;
import com.grid.appy.citizenrights.model.DividerItemDecoration;

import com.grid.appy.citizenrights.helper.SQLiteHandler;
import com.grid.appy.citizenrights.helper.SessionManager;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.grid.appy.citizenrights.config.AppConfig.GET_JSON_DATA_HTTP_URL1;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private SQLiteHandler db;
    private SessionManager session;
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    String[] permissionsRequired = new String[]{Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private SharedPreferences permissionStatus;
    private boolean sentToSettings = false;

    List<GetDataAdapter> GetDataAdapter1;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager recyclerViewlayoutManager;

    RecyclerView.Adapter homeadapter;

    String JSON_TITLE = "title";
    String JSON_USEREMAIL = "useremail";
    String JSON_ISSUEDATETIME = "issuedatetime";
    String JSON_ISSUEID ="issueid";

    JsonArrayRequest jsonArrayRequest ;

    RequestQueue requestQueue ;


    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (CheckNetwork.isInternetAvailable(this)) {
            checkFirstRun();

            // SqLite database handler
            db = new SQLiteHandler(getApplicationContext());

            // session manager
          session = new SessionManager(getApplicationContext());


            GetDataAdapter1 = new ArrayList<>();

            recyclerView = (RecyclerView) findViewById(R.id.recycler_view1);

            recyclerView.setHasFixedSize(true);

            recyclerViewlayoutManager = new LinearLayoutManager(this);

            recyclerView.setLayoutManager(recyclerViewlayoutManager);

            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

            JSON_DATA_WEB_CALL();



        //Floating fab
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!session.isLoggedIn()) {
                    logoutUser();
                }

                else {
                    Intent newissue = new Intent(getApplicationContext(), NewissueActivity.class);
                    startActivity(newissue);
                }
            }
        });

    }

        else{
            //no connection
            // Toast toast = Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG);
            //toast.show();


                Intent newissue = new Intent(getApplicationContext(), NointernetActivity.class);
                startActivity(newissue);


        }


        //navigation view
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // load nav menu header data
       // loadNavHeader();

    }

/*
    private void loadNavHeader() {
        // name, website


        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        // Displaying the user details on the screen
        username.setText(name);
        email_nav.setText(email);

    }
*/



    public void checkFirstRun() {
        boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRun", true);
        if (isFirstRun){
            // Place your dialog code here to display the dialog


            new AlertDialog.Builder(this).setTitle("Terms and conditions").setMessage("By installing this application by default you agree to the terms and conditions of this app").setNeutralButton("OK", null).show();

            getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                    .edit()
                    .putBoolean("isFirstRun", false)
                    .apply();
            permissionStatus = getSharedPreferences("permissionStatus",MODE_PRIVATE);
            if(ActivityCompat.checkSelfPermission(HomeActivity.this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(HomeActivity.this, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED)
            {
                if(ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,permissionsRequired[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,permissionsRequired[1])
                        ){
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                    builder.setTitle("Need Multiple Permissions");
                    builder.setMessage("This app needs Phone State and Storage permissions.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(HomeActivity.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else if (permissionStatus.getBoolean(permissionsRequired[0],false)) {
                    //Previously Permission Request was cancelled with 'Dont Ask Again',
                    // Redirect to Settings after showing Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                    builder.setTitle("Need Multiple Permissions");
                    builder.setMessage("This app needs Phone State and Storage permissions.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            sentToSettings = true;
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                            Toast.makeText(getBaseContext(), "Go to Permissions to Grant  Phone State and Storage", Toast.LENGTH_LONG).show();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }  else {
                    //just request the permission
                    ActivityCompat.requestPermissions(HomeActivity.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
                }



                SharedPreferences.Editor editor = permissionStatus.edit();
                editor.putBoolean(permissionsRequired[0],true);
                editor.commit();
            } else {
                //You already have the permission, just go ahead.
                proceedAfterPermission();
            }


        }
    }
    //on back action
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            super.onBackPressed();
        }
    }

    //action bar icon
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {

                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
            return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            // Handle the home action

                    Intent home = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(home);

        } else if (id == R.id.issue) {
            // Handle the viewissue action

            Intent viewissue = new Intent(getApplicationContext(), ViewissueActivity.class);
            startActivity(viewissue);

        } else if (id == R.id.dept) {
            // Handle the view dept action
            Intent viewdept = new Intent(getApplicationContext(), ViewdeptActivity.class);
            startActivity(viewdept);

        }else if (id == R.id.admin) {
            // Handle the setting action
            Intent settings = new Intent(getApplicationContext(), AdminviewActivity.class);
            startActivity(settings);

        }
        else if (id == R.id.setting) {
            // Handle the setting action
            Intent settings = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(settings);

        } else if (id == R.id.help) {
            // Handle the help action
            Intent help = new Intent(getApplicationContext(), Helpactivity.class);
            startActivity(help);

        } else if (id == R.id.logout) {
            // Handle the logout action

            //Intent newissue = new Intent(getApplicationContext(), LoginActivity.class);
            //startActivity(newissue);

            logoutUser();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void JSON_DATA_WEB_CALL(){

        jsonArrayRequest = new JsonArrayRequest(GET_JSON_DATA_HTTP_URL1,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        JSON_PARSE_DATA_AFTER_WEBCALL(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            GetDataAdapter GetDataAdapter2 = new GetDataAdapter();

            JSONObject json = null;
            try {

                json = array.getJSONObject(i);

                GetDataAdapter2.setHome_title(json.getString(JSON_TITLE));
                GetDataAdapter2.setHome_username(json.getString(JSON_USEREMAIL));
                GetDataAdapter2.setHome_date(json.getString(JSON_ISSUEDATETIME));
                GetDataAdapter2.setHome_issueid(json.getString(JSON_ISSUEID));

            } catch (JSONException e) {

                e.printStackTrace();
            }
            GetDataAdapter1.add(GetDataAdapter2);
        }

        homeadapter = new HomeAdapter(GetDataAdapter1, this);

        recyclerView.setAdapter(homeadapter);
    }



    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */

    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if(requestCode == PERMISSION_CALLBACK_CONSTANT){
        //check if all permissions are granted
        boolean allgranted = false;
        for(int i=0;i<grantResults.length;i++){
            if(grantResults[i]==PackageManager.PERMISSION_GRANTED){
                allgranted = true;
            } else {
                allgranted = false;
                break;
            }
        }

        if(allgranted){
            proceedAfterPermission();
        } else if(ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,permissionsRequired[0])
                || ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,permissionsRequired[1])
                ){
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
            builder.setTitle("Need Multiple Permissions");
            builder.setMessage("This app needs Phone State and Storage permissions.");
            builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    ActivityCompat.requestPermissions(HomeActivity.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        } else {
            Toast.makeText(getBaseContext(),"Unable to get Permission",Toast.LENGTH_LONG).show();
        }
    }
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(HomeActivity.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }

    private void proceedAfterPermission() {

        Toast.makeText(getBaseContext(), "We got All Permissions", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(HomeActivity.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }
}





