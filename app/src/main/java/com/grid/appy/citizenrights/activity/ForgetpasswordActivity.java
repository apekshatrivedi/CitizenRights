package com.grid.appy.citizenrights.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.grid.appy.citizenrights.R;

import java.util.concurrent.TimeUnit;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.grid.appy.citizenrights.config.AppConfig.GET_ISSUE_DATA;
import static com.grid.appy.citizenrights.config.AppConfig.PHONECHECK;


//import static com.grid.appy.citizenrights.R.id.btnreset;

public class ForgetpasswordActivity extends AppCompatActivity {

    String num="";
    String JSON_PHONE = "phone";
    public static final String JSON_ARRAY = "result";
    private ProgressDialog loading;

    EditText MobileNumber,OTPEditview;
    Button Submit,OTPButton;
    TextView Textview,Otp;
    // [START declare_auth]

    private FirebaseAuth mAuth;
    // [END declare_auth]

    boolean mVerificationInProgress = false;
    String mVerificationId;
    PhoneAuthProvider.ForceResendingToken mResendToken;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        MobileNumber = (EditText) findViewById(R.id.mobileNumber);
        Submit = (Button) findViewById(R.id.submit);
        OTPEditview = (EditText) findViewById(R.id.otp_editText);
        OTPButton = (Button) findViewById(R.id.otp_button);
        Textview = (TextView) findViewById(R.id.textView);
        Otp = (TextView) findViewById(R.id.otp);


        mAuth = FirebaseAuth.getInstance();


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                Toast.makeText(ForgetpasswordActivity.this,"verifucation done"+ phoneAuthCredential,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(ForgetpasswordActivity.this,"verifucation fail",Toast.LENGTH_LONG).show();
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // [START_EXCLUDE]
                    Toast.makeText(ForgetpasswordActivity.this,"invalid mob no",Toast.LENGTH_LONG).show();
                    // [END_EXCLUDE]
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
                    Toast.makeText(ForgetpasswordActivity.this,"quta over" ,Toast.LENGTH_LONG).show();
                    // [END_EXCLUDE]
                }
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                //Log.d(TAG, "onCodeSent:" + verificationId);
                Toast.makeText(ForgetpasswordActivity.this,"Verification code sent to mobile",Toast.LENGTH_LONG).show();
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
                MobileNumber.setVisibility(View.GONE);
                Submit.setVisibility(View.GONE);
                Textview.setVisibility(View.GONE);
                OTPButton.setVisibility(View.VISIBLE);
                OTPEditview.setVisibility(View.VISIBLE);
                Otp.setVisibility(View.VISIBLE);
                // ...
            }
        };



        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(

                     //   getphone();




                        "+91"+MobileNumber.getText().toString(),        // Phone number to verify
                        60,                 // Timeout duration
                        TimeUnit.SECONDS,   // Unit of timeout
                        ForgetpasswordActivity.this,               // Activity (for callback binding)
                        mCallbacks);        // OnVerificationStateChangedCallbacks
            }
        });

        OTPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, OTPEditview.getText().toString());
                signInWithPhoneAuthCredential(credential);
            }
        });

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(ForgetpasswordActivity.this, "Verification done", Toast.LENGTH_LONG).show();
                            FirebaseUser user = task.getResult().getUser();
                            Intent newissue = new Intent(getApplicationContext(), ResetpasswordActivity.class);
                            String message = MobileNumber.getText().toString();
                            newissue.putExtra("message", message);
                            startActivity(newissue);
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(ForgetpasswordActivity.this, "Verification failed code invalid", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }


    public void getphone() {

        loading = ProgressDialog.show(this, "Please wait...", "Fetching...", false, false);

        String url = PHONECHECK + "?phone=" + MobileNumber.getText().toString() ;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ForgetpasswordActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void showJSON(String response) {


        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(JSON_ARRAY);
            JSONObject issueData = result.getJSONObject(0);
            num = issueData.getString(JSON_PHONE);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    }