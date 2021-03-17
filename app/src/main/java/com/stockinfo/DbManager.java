package com.stockinfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DbManager {

    private Context context;

    private DbHelper dbHelper;

    private SQLiteDatabase db;


    public DbManager(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    public void openDb() {
        db = dbHelper.getWritableDatabase();
    }

    public void insertToDb(Stock stock) {
        ContentValues values = new ContentValues();

        values.put(Constants.TICKER, stock.getTicker());
        values.put(Constants.PRICE_CURRENT, stock.getPriceCurrent());
        values.put(Constants.PRICE_CLOSE, stock.getPricePrevClose());
        values.put(Constants.IS_FAVOURITE, stock.getIsFavourite());
        db.insert(Constants.TABLE_NAME, null, values);
    }



    public void updateData(Stock stock) {
        int id = getIdByStock(stock);
        Log.i("UPDATE_ID", "updateData: " + id + "; " + stock.getTicker());
        db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.TICKER, stock.getTicker());
        contentValues.put(Constants.PRICE_CURRENT, stock.getPriceCurrent());
        contentValues.put(Constants.PRICE_CLOSE, stock.getPricePrevClose());
        contentValues.put(Constants.IS_FAVOURITE, "true");
        db.update(Constants.TABLE_NAME, contentValues, " _ID=" + id, null);
    }



    public List<Stock> getStockListFromDb() {
        List<Stock> tempList = new ArrayList<>();
        Cursor cursor = db.query(
                Constants.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        while (cursor.moveToNext()) {
            tempList.add(new Stock(
                    cursor.getString(cursor.getColumnIndex(Constants.TICKER)),
                    cursor.getString(cursor.getColumnIndex(Constants.IS_FAVOURITE)),
                    Double.parseDouble(cursor.getString(cursor.getColumnIndex(Constants.PRICE_CURRENT))),
                    Double.parseDouble(cursor.getString(cursor.getColumnIndex(Constants.PRICE_CLOSE))))
            );
        }
        cursor.close();
        return tempList;
    }

    public void closeDb() {
        dbHelper.close();
    }

    public int getIdByStock(Stock stock) {
        String ticker = stock.getTicker();
        Cursor cursor = db.query(
                Constants.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        while (cursor.moveToNext()) {
            if (cursor.getString(cursor.getColumnIndex(Constants.TICKER)).equals(ticker)) {
                return cursor.getInt(cursor.getColumnIndex(Constants._ID));
            }
        }
        return -1;
    }

}
