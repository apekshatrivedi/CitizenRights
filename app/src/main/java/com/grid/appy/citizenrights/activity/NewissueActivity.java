package com.grid.appy.citizenrights.activity;

//Note file upload MAX 2MB


import android.app.Activity;
import android.app.DownloadManager;
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
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
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
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;


import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.R.attr.bitmap;
import static android.R.attr.checkable;
import static android.R.attr.description;
import static android.R.attr.title;
import static com.grid.appy.citizenrights.config.AppConfig.SERVER_URL;
import static com.grid.appy.citizenrights.config.AppConfig.UPLOADMYSQL_URL;


public class NewissueActivity extends AppCompatActivity  {


    File f;
    String content_type;
    String file_path;

    private static final int PICK_FILE_REQUEST = 1;
    private static final String TAG = "message======";
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

    StringBuilder proof = new StringBuilder(100);





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





                                    uploadFile();


                   }
            }
        });

        //Listening to upload
        Button upload=(Button) findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to activity_home screen
                //openImageIntent();
                //showFileChooser();

                new MaterialFilePicker()
                        .withActivity(NewissueActivity.this)
                        .withRequestCode(10).start();
            }
        });




    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data)
    {




        if (requestCode == 10 && resultCode == RESULT_OK) {

            f = new File(data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));
            content_type = getMimeType(f.getPath());
            file_path = f.getAbsolutePath();

        }
    }


    public void uploadFile()
    {
        dialog = new ProgressDialog(NewissueActivity.this);
        dialog.setTitle("Posting");
        dialog.setMessage("Please wait...");
        dialog.show();



        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {


        OkHttpClient client = new OkHttpClient();
        RequestBody file_body = RequestBody.create(MediaType.parse(content_type), f);

        HashMap<String, String> user = db.getUserDetails();
        String useremail = user.get("imei");
        proof.append(useremail);
        proof.append(file_path.substring(file_path.lastIndexOf
                ("/") + 1));

        RequestBody request_body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("type", content_type)
                .addFormDataPart("uploaded_file",proof.toString(), file_body)
                .build();

        Request request = new Request.Builder()
                .url(SERVER_URL)
                .post(request_body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if(!response.isSuccessful()){
                throw new IOException("Error: "+response);

            }
            else {
                dialog.dismiss();
                final String s = proof.toString();
                uploadImage(s);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


            }
        });
        t.start();

    }

    private String getMimeType(String path)
    {
       String extension = MimeTypeMap.getFileExtensionFromUrl(path);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }



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
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);

    }


}