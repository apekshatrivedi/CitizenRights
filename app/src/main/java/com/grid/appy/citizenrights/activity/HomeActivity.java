package com.grid.appy.citizenrights.activity;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
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
import android.widget.Toast;

import com.grid.appy.citizenrights.model.CheckNetwork;
import com.grid.appy.citizenrights.model.News;
import com.grid.appy.citizenrights.adapter.NewsAdapter;
import com.grid.appy.citizenrights.R;
import com.grid.appy.citizenrights.model.DividerItemDecoration;

import com.grid.appy.citizenrights.helper.SQLiteHandler;
import com.grid.appy.citizenrights.helper.SessionManager;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static final int EXTERNAL_READ_PHONE_STATE= 100;
    private SharedPreferences permissionStatus;


    private SQLiteHandler db;
    private SessionManager session;


    //recycleview adapters
    private List<News> newsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NewsAdapter nAdapter;

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);

        if (CheckNetwork.isInternetAvailable(this)) {
            checkFirstRun();

            // SqLite database handler
            db = new SQLiteHandler(getApplicationContext());

            // session manager
            session = new SessionManager(getApplicationContext());

           // if (!session.isLoggedIn()) {
               // logoutUser();
         //   }


            // Fetching user details from sqlite
            HashMap<String, String> user = db.getUserDetails();


            if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this, Manifest.permission.READ_PHONE_STATE)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                    builder.setTitle("Need phone state Permission");
                    builder.setMessage("This app needs read phone state permission.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, EXTERNAL_READ_PHONE_STATE);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }

            else {
                //just request the permission
                ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE},EXTERNAL_READ_PHONE_STATE);
            }


            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(Manifest.permission.READ_PHONE_STATE,true);
            editor.commit();




        } else {
            //You already have the permission, just go ahead.
            proceedAfterPermission();
        }



        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        nAdapter = new NewsAdapter(newsList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(nAdapter);
        prepareNewsData();

        //Floating fab
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newissue = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(newissue);
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

    }

    public void checkFirstRun() {
        boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRun", true);
        if (isFirstRun){
            // Place your dialog code here to display the dialog


            new AlertDialog.Builder(this).setTitle("Terms and conditions").setMessage("By installing this application by default you agree to the terms and conditions of this app").setNeutralButton("OK", null).show();

            getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                    .edit()
                    .putBoolean("isFirstRun", false)
                    .apply();
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
    private void proceedAfterPermission() {
        //We've got the permission, now we can proceed further
        Toast.makeText(getBaseContext(), "We got the read phone state Permission", Toast.LENGTH_LONG).show();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
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

                logoutUser();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//recycler view
    private void prepareNewsData() {
        News news = new News("School issue", "username","12-09-2017");
        newsList.add(news);

         news = new News("Office issue", "username1","12-09-2017");
        newsList.add(news);

        news = new News("Office issue", "username9","12-09-2017");
        newsList.add(news);

        news = new News("Office issue", "username1","12-09-2017");
        newsList.add(news);

        news = new News("Office issue", "username9","12-09-2017");
        newsList.add(news);

        news = new News("Office issue", "username1","12-09-2017");
        newsList.add(news);

        news = new News("Office issue", "username9","12-09-2017");
        newsList.add(news);

        news = new News("Office issue", "username1","12-09-2017");
        newsList.add(news);

        news = new News("Office issue", "username9","12-09-2017");
        newsList.add(news);

        news = new News("Office issue", "username1","12-09-2017");
        newsList.add(news);

        news = new News("Office issue", "username9","12-09-2017");
        newsList.add(news);

        news = new News("Office issue", "username1","12-09-2017");
        newsList.add(news);

        news = new News("Office issue", "username9","12-09-2017");
        newsList.add(news);


        nAdapter.notifyDataSetChanged();
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





}
