package com.grid.appy.citizenrights.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.grid.appy.citizenrights.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeptregistrationActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener
{
EditText et_empname,et_uniqueid,et_education,et_edu,et_admin,et_phone,et_email,et_password,et_retypepassword;
    Button btn_reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deptregistration);
        et_empname = (EditText) findViewById(R.id.name);
        et_uniqueid = (EditText) findViewById(R.id.otp);

        et_phone = (EditText) findViewById(R.id.phone);
        et_email = (EditText) findViewById(R.id.email);
        et_password = (EditText) findViewById(R.id.password);
        et_retypepassword= (EditText) findViewById(R.id.repassword);
        btn_reg = (Button) findViewById(R.id.btnRegister);
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String empname = et_empname.getText().toString().trim();
                String uniqueid = et_uniqueid.getText().toString().trim();
               // String education= et_education.getText().toString().trim();
                //String edu = et_edu.getText().toString().trim();
                //String admin = et_admin.getText().toString().trim();
                String phone = et_phone.getText().toString().trim();
                String email = et_email.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String retypepassword = et_retypepassword.getText().toString().trim();
              //  String city = et_reg.getText().toString().trim();
              //  String aadharrnum = et_aadharrnum.getText().toString().trim();

                String validEmailId = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+";

                String validpassword = "((?=.*\\d)(?=.*[a-z]).{6,20})";
                String validretypepassword = "((?=.*\\d)(?=.*[a-z]).{6,20})";
                //Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
                Matcher matcherEmailId = Pattern.compile(validEmailId).matcher(email);
                Matcher matcherretypepassword = Pattern.compile(validretypepassword).matcher(retypepassword);
                Matcher matcherpassword = Pattern.compile(validpassword).matcher(password);
                if (TextUtils.isEmpty(empname)) {
                    et_empname.setError("This field is required");
                    return;
                }
                if (TextUtils.isEmpty(uniqueid)) {
                    et_uniqueid.setError("This field is required");
                    return;
                }

                if (TextUtils.isEmpty(phone))
                {
                    et_phone.setError("This field is required");
                    return;
                }
                if ((phone.length() < 8 || phone.length() > 13) )
                {
                    Toast.makeText(getApplicationContext(), "Incorrect Mobile Number", Toast.LENGTH_LONG).show();
                    return;
                }





                if (TextUtils.isEmpty(email)) {
                    et_email.setError("This field is required");
                    return;
                }
                if (!matcherEmailId.matches())
                {
                    Toast.makeText(getApplicationContext(), "Enter Valid Email-Id", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(password))
                {
                    et_password.setError("please enter the password");
                    return;
                }
                else
                {
                    if (!matcherpassword.matches())
                    {
                        Toast.makeText(getApplicationContext(), "Enter Valid Password", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                if (TextUtils.isEmpty(retypepassword))
                {
                    et_retypepassword.setError("This field is required");
                    return;
                }
                if (!password.equals(retypepassword))
                {
                    Toast.makeText(getApplicationContext(), "Password missmatch", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(DeptregistrationActivity.this, "success", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(DeptregistrationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();


            }});


        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Spinner spinner1=(Spinner)findViewById(R.id.spinner1);
        Spinner spinner2=(Spinner)findViewById(R.id.spinner2);
        // Spinner click listener
        spinner.setOnItemSelectedListener(DeptregistrationActivity.this);
        spinner1.setOnItemSelectedListener(DeptregistrationActivity.this);
        spinner2.setOnItemSelectedListener(DeptregistrationActivity.this);
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
