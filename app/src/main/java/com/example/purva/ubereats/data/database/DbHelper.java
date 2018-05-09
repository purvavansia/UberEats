package com.example.purva.ubereats.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.purva.ubereats.data.model.Cart;
import com.example.purva.ubereats.data.model.CartContract;

import java.util.ArrayList;
import java.util.List;

public class DbHelper implements IDbHelper {

    DbOpenHelper mDbOpenHelper;
    SQLiteDatabase mSQLiteDatabase;
    public DbHelper(Context context){
        mDbOpenHelper = new DbOpenHelper(context);
        mSQLiteDatabase = mDbOpenHelper.getWritableDatabase();
    }

    @Override
    public List<Cart.CartBean> getAllCart() {
        List<Cart.CartBean> carts = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + CartContract.CartEntry.TABLE_NAME;
        Cursor cursor = mSQLiteDatabase.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Integer cartid = cursor.getInt(cursor.getColumnIndex(CartContract.CartEntry.CART_ID));
                String foodId = cursor.getString(cursor.getColumnIndex(CartContract.CartEntry.COLUMN_NAME_FOOD_ID));
                String foodName = cursor.getString(cursor.getColumnIndex(CartContract.CartEntry.COLUMN_NAME_FOOD_NAME));
                String foodPrice = cursor.getString(cursor.getColumnIndex(CartContract.CartEntry.COLUMN_NAME_FOOD_PRICE));
                String flavor = cursor.getString(cursor.getColumnIndex(CartContract.CartEntry.COLUMN_NAME_FLAVOR));
                String quantity = cursor.getString(cursor.getColumnIndex(CartContract.CartEntry.COLUMN_NAME_QUANTITY));
                String image = cursor.getString(cursor.getColumnIndex(CartContract.CartEntry.COLUMN_NAME_IMAGE));

                Cart.CartBean cartBean = new Cart.CartBean(cartid, foodId,foodName,foodPrice,flavor,quantity,image);
                carts.add(cartBean);
            }while(cursor.moveToNext());
        }
        return carts;
    }

    @Override
    public int getTCartCount() {
        return (int) DatabaseUtils.queryNumEntries(mSQLiteDatabase, CartContract.CartEntry.TABLE_NAME);

    }

    @Override
    public int insertCartRecord(String foodId, String foodName, String foodPrice, String foodFlavor, String quantity, String image) {
        ContentValues values = new ContentValues();
        values.put(CartContract.CartEntry.COLUMN_NAME_FOOD_ID,foodId);
        values.put(CartContract.CartEntry.COLUMN_NAME_FOOD_NAME,foodName);
        values.put(CartContract.CartEntry.COLUMN_NAME_FOOD_PRICE,foodPrice);
        values.put(CartContract.CartEntry.COLUMN_NAME_FLAVOR,foodFlavor);
        values.put(CartContract.CartEntry.COLUMN_NAME_QUANTITY,quantity);
        values.put(CartContract.CartEntry.COLUMN_NAME_IMAGE,image);
        long row_id = mSQLiteDatabase.insert(CartContract.CartEntry.TABLE_NAME,null,values);

        return (int)row_id;
    }

    @Override
    public void deleteCartById(Integer cartId) {
        mSQLiteDatabase.delete(CartContract.CartEntry.TABLE_NAME, CartContract.CartEntry.CART_ID+ " = ?",
                new String[] {""+String.valueOf(cartId)});
        Log.i("deleterows",""+getTCartCount());
    }

    @Override
    public boolean clearCartTable() {
        mSQLiteDatabase.execSQL("delete from " + CartContract.CartEntry.TABLE_NAME);
        return getTCartCount()==0;
    }
}
