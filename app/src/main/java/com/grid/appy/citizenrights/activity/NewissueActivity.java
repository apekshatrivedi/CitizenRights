package com.grid.appy.citizenrights.activity;

//Note file upload MAX 2MB


import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.grid.appy.citizenrights.R;
import com.grid.appy.citizenrights.config.AppConfig;
import com.grid.appy.citizenrights.helper.RequestHandler;
import com.grid.appy.citizenrights.helper.SQLiteHandler;
import com.grid.appy.citizenrights.helper.SessionManager;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static android.R.attr.bitmap;
import static android.R.attr.description;
import static android.R.attr.title;
import static com.grid.appy.citizenrights.config.AppConfig.UPLOAD_URL;


public class NewissueActivity extends AppCompatActivity  {

    EditText desc;
    EditText titleedit;


    private TextView txtName;
    private SQLiteHandler db;
    private SessionManager session;

    public static final String KEY_PROOF = "proof";
    public static final String KEY_USEREMAIL = "useremail";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";


    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private ImageView imageView;


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


        String useremail = user.get("imei");

        // Displaying the user details on the screen
        txtName.setText(useremail);




        Button issuesubmit=(Button)findViewById(R.id.issuesubmit);
        issuesubmit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                imageView = (ImageView) findViewById(R.id.logo);

              //form validation
                desc =(EditText)findViewById(R.id.desc);
                final String description = desc.getText().toString();



                titleedit =(EditText)findViewById(R.id.title);
                final String title = titleedit.getText().toString();


                   if (!isValidTitle(title)) {
                    titleedit.setError("Enter a title");}

                  else  if (!isValidDesc(description)) {
                    desc.setError("Give some description for the issue");
                }
                  else {

                       uploadImage();



                      // Switching to activity_home screen
                       //Intent issues = new Intent(getApplicationContext(), HomeActivity.class);
                       //startActivity(issues);
                   }
            }
        });

        //Listening to upload
        Button upload=(Button) findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to activity_home screen
                //openImageIntent();
                showFileChooser();
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
       // fsIntent.setType("*/*");
        fsIntent.setAction(Intent.ACTION_GET_CONTENT);
        cameraIntents.add(fsIntent);
        //Create the Chooser
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));
        startActivityForResult(chooserIntent, 200);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
         //  imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
            e.printStackTrace();
            }
        }
    }
    public String getStringImage(Bitmap bmp){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    public void uploadImage(){

        class UploadImage extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            final String title = titleedit.getText().toString();
            final String description = desc.getText().toString();
            final String proof = getStringImage(bitmap);


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(NewissueActivity.this, "Please wait...", "uploading", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(NewissueActivity.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {

                // Fetching user details from sqlite
                HashMap<String, String> user = db.getUserDetails();


                String useremail = user.get("imei");



                RequestHandler rh = new RequestHandler();
                HashMap<String, String> param = new HashMap<String, String>();
                param.put(KEY_USEREMAIL, useremail);
                param.put(KEY_TITLE, title);
                param.put(KEY_PROOF, proof);
                param.put(KEY_DESCRIPTION, description);

                String result = rh.sendPostRequest(UPLOAD_URL, param);
                return result;
            }
        }

            UploadImage u = new UploadImage();
        u.execute();
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
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


}