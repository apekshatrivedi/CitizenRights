package com.grid.appy.citizenrights.helper;

/**
 * Created by Appy on 24-Jul-17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 5;

    // Database Name
    private static final String DATABASE_NAME = "citizenrights";

    // Login table name
    private static final String TABLE_USER = "user";

    // Login Table Columns names
    private static final String KEY_DEPT = "deptid";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_IMEI = "imei";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_TYPE= "type";
    private static final String KEY_USERNAME = "username";


    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + "("
                + KEY_DEPT + " TEXT," + KEY_NAME + " TEXT,"
                + KEY_PHONE + " INTEGER," + KEY_IMEI + " INTEGER,"
                + KEY_EMAIL + " TEXT,"
                 + KEY_TYPE + " TEXT,"
                + KEY_USERNAME + " TEXT"  + ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(String dept,String name, String email, String phone, String imei,String type, String username) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DEPT,dept);//dept
        values.put(KEY_NAME, name); // Name
        values.put(KEY_PHONE, phone); // Phone
        values.put(KEY_IMEI, imei); // IMEI
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_TYPE,type);//Type
        values.put(KEY_USERNAME,username);//Username


        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("dept",cursor.getString(0));
            user.put("name", cursor.getString(1));
            user.put("phone", cursor.getString(2));
            user.put("imei", cursor.getString(3));
            user.put("email", cursor.getString(4));
            user.put("type",cursor.getString(5));
            user.put("username",cursor.getString(6));

        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

}