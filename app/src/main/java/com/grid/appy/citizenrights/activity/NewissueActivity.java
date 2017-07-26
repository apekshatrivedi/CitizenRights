package com.grid.appy.citizenrights.activity;

//Note file upload MAX 2MB


import android.app.Activity;
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
import android.util.Log;
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
import com.grid.appy.citizenrights.config.FilePath;
import com.grid.appy.citizenrights.helper.RequestHandler;
import com.grid.appy.citizenrights.helper.SQLiteHandler;
import com.grid.appy.citizenrights.helper.SessionManager;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static android.R.attr.bitmap;
import static android.R.attr.description;
import static android.R.attr.title;
import static com.grid.appy.citizenrights.config.AppConfig.SERVER_URL;
import static com.grid.appy.citizenrights.config.AppConfig.UPLOADMYSQL_URL;


public class NewissueActivity extends AppCompatActivity  {


    private static final int PICK_FILE_REQUEST = 1;
    private static final String TAG = NewissueActivity.class.getSimpleName();
    private String selectedFilePath;
    ProgressDialog dialog;

    EditText desc;
    EditText titleedit;


    private TextView txtName;
    private SQLiteHandler db;
    private SessionManager session;


    public static final String KEY_USEREMAIL = "useremail";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_PROOF = "proof";





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

                       //uploadImage();

                       //on upload button Click
                       if(selectedFilePath != null){
                           dialog = ProgressDialog.show(NewissueActivity.this,"","Uploading File...",true);

                           new Thread(new Runnable() {
                               @Override
                               public void run() {
                                   //creating new thread to handle Http Operations
                                   uploadFile(selectedFilePath);
                               }
                           }).start();
                       }else{
                           Toast.makeText(NewissueActivity.this,"Please choose a File First",Toast.LENGTH_SHORT).show();
                       }




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

    private void showFileChooser() {
        Intent intent = new Intent();
        //sets the select file to all types of files
        intent.setType("*/*");
        //allows to select data and return it
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //starts new activity to select file and return data
        startActivityForResult(Intent.createChooser(intent,"Choose File to Upload.."),PICK_FILE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == PICK_FILE_REQUEST){
                if(data == null){
                    //no data present
                    return;
                }


                Uri selectedFileUri = data.getData();
                selectedFilePath = FilePath.getPath(this,selectedFileUri);
                Log.i(TAG,"Selected File Path:" + selectedFilePath);

                if(selectedFilePath != null && !selectedFilePath.equals("")){
                   // tvFileName.setText(selectedFilePath);
                }else{
                    Toast.makeText(this,"Cannot upload file to server",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }



    //android upload file to server
    public int uploadFile(final String selectedFilePath){

        HashMap<String, String> user = db.getUserDetails();


        String useremail = user.get("imei");



        int serverResponseCode = 0;

        HttpURLConnection connection;
        DataOutputStream dataOutputStream;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";


        int bytesRead,bytesAvailable,bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File selectedFile = new File(selectedFilePath);




        String[] parts = selectedFilePath.split("/");
        final String fileName = parts[parts.length-1];

        if (!selectedFile.isFile()){
            dialog.dismiss();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //tvFileName.setText("Source File Doesn't Exist: " + selectedFilePath);
                }
            });
            return 0;
        }else{
            try{



                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                URL url = new URL(SERVER_URL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);//Allow Inputs
                connection.setDoOutput(true);//Allow Outputs
                connection.setUseCaches(false);//Don't use a cached Copy
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                connection.setRequestProperty("uploaded_file",selectedFilePath);

                //creating new dataoutputstream
                dataOutputStream = new DataOutputStream(connection.getOutputStream());

                //writing bytes to data outputstream
                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + selectedFilePath + "\"" +lineEnd);

                dataOutputStream.writeBytes(lineEnd);

                //returns no. of bytes present in fileInputStream
                bytesAvailable = fileInputStream.available();
                //selecting the buffer size as minimum of available bytes or 1 MB
                bufferSize = Math.min(bytesAvailable,maxBufferSize);
                //setting the buffer as byte array of size of bufferSize
                buffer = new byte[bufferSize];

                //reads bytes from FileInputStream(from 0th index of buffer to buffersize)
                bytesRead = fileInputStream.read(buffer,0,bufferSize);

                //loop repeats till bytesRead = -1, i.e., no bytes are left to read
                while (bytesRead > 0){
                    //write the bytes read from inputstream
                    dataOutputStream.write(buffer,0,bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable,maxBufferSize);
                    bytesRead = fileInputStream.read(buffer,0,bufferSize);
                }

                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                serverResponseCode = connection.getResponseCode();
                String serverResponseMessage = connection.getResponseMessage();

                Log.i(TAG, "Server Response is: " + serverResponseMessage + ": " + serverResponseCode);

                //response code of 200 indicates the server status OK
                if(serverResponseCode == 200){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            final String proof= fileName;
                           uploadImage(proof);



                            //tvFileName.setText("File Upload completed.\n\n You can see the uploaded file here: \n\n" + "http://coderefer.com/extras/uploads/"+ fileName);
                        }
                    });
                }

                //closing the input and output streams
                fileInputStream.close();
                dataOutputStream.flush();
                dataOutputStream.close();



            } catch (FileNotFoundException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(NewissueActivity.this,"File Not Found",Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Toast.makeText(NewissueActivity.this, "URL error!", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(NewissueActivity.this, "Cannot Read/Write File!", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
            return serverResponseCode;
        }

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
/*
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
*/

    public void uploadImage(final String proof){

        class UploadImage extends AsyncTask<Void,Void,String> {


            final String title = titleedit.getText().toString();
            final String description = desc.getText().toString();
            HashMap<String, String> user = db.getUserDetails();


            String useremail = user.get("imei");




            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
              //  Toast.makeText(NewissueActivity.this, s, Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i);
            }

            @Override
            protected String doInBackground(Void... params) {

                // Fetching user details from sqlite




                RequestHandler rh = new RequestHandler();
                HashMap<String, String> param = new HashMap<String, String>();
                param.put(KEY_USEREMAIL, useremail);
                param.put(KEY_PROOF,proof);
                param.put(KEY_TITLE, title);
                param.put(KEY_DESCRIPTION, description);

                String result = rh.sendPostRequest(UPLOADMYSQL_URL, param);
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