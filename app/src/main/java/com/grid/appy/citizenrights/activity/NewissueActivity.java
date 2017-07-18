package com.grid.appy.citizenrights.activity;



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
import com.grid.appy.citizenrights.R;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class NewissueActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newissue);

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


}