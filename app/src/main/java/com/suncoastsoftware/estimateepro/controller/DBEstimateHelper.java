package com.suncoastsoftware.estimateepro.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Command Center on 10/19/2017.
 */

public class DBEstimateHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBEstimateHelper";
    private static final String TABLE_NAME = "estimate_table";
    private static final String COL_ID = "ID";
    private static final String COL_Company_ID = "COMPANY_ID";
    private static final String COL_COMPANY_NAME = "COMPANY_NAME";

    //insert column names here

    public DBEstimateHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
