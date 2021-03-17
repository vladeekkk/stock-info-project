package com.stockinfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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

    public List<Stock> getFromDb() {
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

}
