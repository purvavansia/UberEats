package com.example.purva.ubereats.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.purva.ubereats.model.CartContract;

public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String dbname = "ubereats.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " integer";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_CART_ENTRIES =
            "CREATE TABLE " + CartContract.CartEntry.TABLE_NAME + " ("
                    + CartContract.CartEntry.CART_ID + INT_TYPE +" PRIMARY KEY AUTOINCREMENT NOT NULL" + COMMA_SEP
                    + CartContract.CartEntry.COLUMN_NAME_FOOD_ID + TEXT_TYPE + COMMA_SEP
                    + CartContract.CartEntry.COLUMN_NAME_FOOD_NAME + TEXT_TYPE + COMMA_SEP
                    + CartContract.CartEntry.COLUMN_NAME_FOOD_PRICE + TEXT_TYPE + COMMA_SEP
                    + CartContract.CartEntry.COLUMN_NAME_FLAVOR + TEXT_TYPE + COMMA_SEP
                    + CartContract.CartEntry.COLUMN_NAME_QUANTITY+ TEXT_TYPE + COMMA_SEP
                    + CartContract.CartEntry.COLUMN_NAME_IMAGE + TEXT_TYPE +
                    ");";
    public DbOpenHelper(Context context) {
        super(context, dbname,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CART_ENTRIES);
    }
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + CartContract.CartEntry.TABLE_NAME;
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CartContract.CartEntry.TABLE_NAME);
    }
}
