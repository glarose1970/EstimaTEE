package com.suncoastsoftware.estimateepro.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Command Center on 10/19/2017.
 */

public class DBCustomerHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBCustomerHelper";
    private static final String TABLE_NAME = "customers_table";
    private static final String COL_ID = "ID";
    private static final String COL_CUSTOMER_ID = "CUSTOMER_ID";
    private static final String COL_COMPANY_NAME = "COMPANY_NAME";
    private static final String COL_CONTACT_NAME = "CONTACT";
    private static final String COL_PHONE = "PHONE";

    public DBCustomerHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_CUSTOMER_ID + "TEXT, " + COL_COMPANY_NAME + "TEXT, " + COL_CONTACT_NAME + "TEXT, " + COL_PHONE + "TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Boolean addData(String customerID, String name, String contact, String phone) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_CUSTOMER_ID, customerID);
        Log.d(TAG, "addData : Adding " + customerID + " to " + TABLE_NAME);
        contentValues.put(COL_COMPANY_NAME, name);
        contentValues.put(COL_CONTACT_NAME, contact);
        contentValues.put(COL_PHONE, phone);

        long result = db.insertOrThrow(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        }else {
            return false;
        }
    }
}
