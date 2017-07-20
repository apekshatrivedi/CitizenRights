package com.grid.appy.citizenrights.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.grid.appy.citizenrights.R;

import java.util.ArrayList;
import java.util.List;

public class DeptregistrationActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deptregistration);



        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Spinner spinner1=(Spinner)findViewById(R.id.spinner1);
        Spinner spinner2=(Spinner)findViewById(R.id.spinner2);
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);
        spinner1.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();

        categories.add("Administrator");
        categories.add("Manager");
        categories.add("Intern");
        categories.add("Other");


        List<String> categories1 = new ArrayList<String>();
        categories1.add("Education");
        categories1.add("Work");
        categories1.add("Government");
        categories1.add("Bank");
        categories1.add("Retail");
        categories1.add("NGO");

        List<String> categories2 = new ArrayList<String>();
        categories2.add("Branch 1");
        categories2.add("Branch 2");
        categories2.add("Branch 3");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories1);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories1);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner1.setAdapter(dataAdapter1);
        spinner2.setAdapter(dataAdapter2);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        // Showing selected spinner item
        //  Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
