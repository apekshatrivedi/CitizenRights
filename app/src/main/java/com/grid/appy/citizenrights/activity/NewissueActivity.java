package com.grid.appy.citizenrights.activity;

//Note file upload MAX 2MB


import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.grid.appy.citizenrights.R;
import com.grid.appy.citizenrights.helper.SQLiteHandler;
import com.grid.appy.citizenrights.helper.SessionManager;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class NewissueActivity extends AppCompatActivity  {


    private TextView txtName;
    private SQLiteHandler db;
    private SessionManager session;


    private int PICK_IMAGE_REQUEST = 1;
    public static final String UPLOAD_URL = "http:///PhotoUploadWithText/upload.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newissue);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtName = (TextView) findViewById(R.id.username);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        String userid = user.get("imei");


        // Displaying the user details on the screen
        txtName.setText(userid);




        Button issuesubmit=(Button)findViewById(R.id.issuesubmit);
        issuesubmit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

              //form validation
                EditText desc =(EditText)findViewById(R.id.desc);
                final String description = desc.getText().toString();



                EditText titleedit =(EditText)findViewById(R.id.title);
                final String title = titleedit.getText().toString();


                   if (!isValidTitle(title)) {
                    titleedit.setError("Enter a title");}

                  else  if (!isValidDesc(description)) {
                    desc.setError("Give some description for the issue");
                }
                  else {
                      // Switching to activity_home screen
                       Intent issues = new Intent(getApplicationContext(), HomeActivity.class);
                       startActivity(issues);
                   }
            }
        });

        //Listening to upload
        Button upload=(Button) findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to activity_home screen
                openImageIntent();
            }
        });


    }


        public void openImageIntent() {
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fname = "ABCD_" + timeStamp;
        final File sdImageMainDirectory = new File(storageDir, fname);
        Uri outputFileUri = Uri.fromFile(sdImageMainDirectory);
        // Camera.
        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for(ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            cameraIntents.add(intent);
        }
        //Gallery.
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Filesystem.
        final Intent fsIntent = new Intent();
        fsIntent.setType("*/*");
        fsIntent.setAction(Intent.ACTION_GET_CONTENT);
        cameraIntents.add(fsIntent);
        //Create the Chooser
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));
        startActivityForResult(chooserIntent, 200);
    }

/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
*/
    //validating description
    private boolean isValidDesc(String desc) {
        if (desc != null && desc.length() > 1) {
            return true;
        }
        return false;
    }

    //validating title
    private boolean isValidTitle(String title) {
        if (title != null&& title.length() > 1 ) {
            return true;
        }
        return false;
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


}