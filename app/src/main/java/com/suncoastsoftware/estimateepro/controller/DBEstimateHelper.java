package com.suncoastsoftware.estimateepro.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Command Center on 10/20/2017.
 */

public class DBEstimateHelper extends SQLiteOpenHelper{

    private static final String TAG = "DBEstimateHelper";
    private static final String DATABASE_NAME = "estimatee_data";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "estimates_table";
    private static final String COL_CUST_ID = "CUST_ID";
    private static final String COL_ESTIMATE_ID = "ESTIMATE_ID";
    private static final String COL_TITLE = "TITLE";
    private static final String COL_DESC = "DESC";
    private static final String COL_NOTES = "NOTES";
    private static final String COL_QUANTITY = "QUANTITY";
    private static final String COL_TOTAL_PRICE = "TOTAL_PRICE";
    private static final String COL_PER_PIECE_PRICE = "PER_PIECE_PRICE";
    private static final String COL_SHOP_BASE_CHARGE = "SHOP_BASE_CHARGE";
    private static final String COL_SCREEN_CHARGE = "SCREEN_CHARGE";
    private static final String COL_NUM_COLORS = "NUM_COLORS";
    private static final String COL_DUE_DATE = "DUE_DATE";
    private static final String COL_SHIRT_SIZES = "SHIRT_SIZES";
    private static final String COL_BOTH_SIDES = "BOTH_SIDES";


    public DBEstimateHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_CUST_ID + " TEXT, " + COL_ESTIMATE_ID + " TEXT, " + COL_TITLE + " TEXT, " + COL_DESC + " TEXT, " +
                COL_NOTES + " TEXT, " + COL_QUANTITY + " LONG, " + COL_TOTAL_PRICE + " DOUBLE, " + COL_PER_PIECE_PRICE + " DOUBLE, " +
                COL_SHOP_BASE_CHARGE + " DOUBLE, " + COL_SCREEN_CHARGE + " DOUBLE, " + COL_NUM_COLORS + " INT, " + COL_DUE_DATE + " TEXT, " +
                COL_SHIRT_SIZES + " TEXT, " + COL_BOTH_SIDES + " BOOLEAN)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String custID, String estimateID, String title, String desc, String notes,
                            long quantity, double totalPrice, double perPiecePrice, double shopBaseCharge,
                           double screenCharge, int numColors, String dueDate, String shirtSizes, boolean bothSides) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_CUST_ID, custID);
        values.put(COL_ESTIMATE_ID, estimateID);
        values.put(COL_TITLE, title);
        values.put(COL_DESC, desc);
        values.put(COL_NOTES, notes);
        values.put(COL_QUANTITY, quantity);
        values.put(COL_TOTAL_PRICE, totalPrice);
        values.put(COL_PER_PIECE_PRICE, perPiecePrice);
        values.put(COL_SHOP_BASE_CHARGE, shopBaseCharge);
        values.put(COL_SCREEN_CHARGE, screenCharge);
        values.put(COL_NUM_COLORS, numColors);
        values.put(COL_DUE_DATE, dueDate);
        values.put(COL_SHIRT_SIZES, shirtSizes);
        values.put(COL_BOTH_SIDES, bothSides);

        long result = db.insertOrThrow(TABLE_NAME, null, values);

        if (result == -1) {
            Log.d(TAG, " WRITE ERROR : " + result);
            return false;
        }else {
            return true;
        }
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
}
