package com.grid.appy.citizenrights.activity;

        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;

        import com.grid.appy.citizenrights.R;

        import java.util.regex.Matcher;
        import java.util.regex.Pattern;

public class RegisterActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.activity_register);

        Button loginScreen = (Button) findViewById(R.id.btnLinkToLoginScreen);
        // Listening to Login Screen link
        loginScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Closing registration screen
                // Switching to Login Screen/closing register screen
                finish();
            }
        });
        Button deptrScreen = (Button) findViewById(R.id.deptregister);
        // Listening to Login Screen link
        deptrScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Closing registration screen
                // Switching to Login Screen/closing register screen
                Intent logout = new Intent(getApplicationContext(), DeptregistrationActivity.class);
                startActivity(logout);
            }
        });

        Button register =(Button)findViewById(R.id.btnRegister);
        register.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Closing registration screen
                // closing register screen

                //form validation
                EditText nameedit;
                nameedit = (EditText) findViewById(R.id.name);
                EditText phoneedit;
                phoneedit = (EditText) findViewById(R.id.phone);
                EditText emailedit;
                emailedit = (EditText) findViewById(R.id.email);
                EditText pass;
                pass= (EditText) findViewById(R.id.password);
                EditText repass;
                repass = (EditText) findViewById(R.id.repassword);

                final String email = emailedit.getText().toString();
                final String name = nameedit.getText().toString();
                final String phone = phoneedit.getText().toString();
                final String password = pass.getText().toString();
                final String repassword = repass.getText().toString();

                if (!isValidName(name)) {
                    nameedit.setError("Invalid Name");
                }

               else if (!isValidPhone(phone)) {
                    phoneedit.setError("Invalid Phone");
                }

               else if (!isValidEmail(email)) {
                    emailedit.setError("Invalid Email");
                }

              else if (!isValidPassword(password)) {
                    pass.setError("Password must be greater than 4 characters");
                }

            else    if (!isValidrePassword(repassword,password)) {
                    repass.setError("Password does not match");
                }

                else{
                    //switching to home activity
                    Intent logout = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(logout);
            }}
        });
    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
        //validating name
        private boolean isValidName(String email) {
        String EMAIL_PATTERN = "^[A-Za-z\\\\s]{1,}[\\\\.]{0,1}[A-Za-z\\\\s]{0,}$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    //validating phone
    private boolean isValidPhone(String email) {
        String EMAIL_PATTERN = "^[2-9]{2}[0-9]{8}$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    //validating password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 4) {
            return true;
        }
        return false;
    }

    //validating re-enter password
    private boolean isValidrePassword(String repass,String pass) {
        if (repass.contentEquals(pass)) {
            return true;
        } else {
            return false;
        }
    }

}