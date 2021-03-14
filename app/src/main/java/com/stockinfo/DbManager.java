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

    public void insertToDb(String ticker, String price) {
        ContentValues values = new ContentValues();

        values.put(Constants.TICKER, ticker);
        values.put(Constants.PRICE, price);
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
            Stock stock = new Stock();
            String ticker = cursor.getString(cursor.getColumnIndex(Constants.TICKER));
            double price = Double.parseDouble(cursor.getString(cursor.getColumnIndex(Constants.PRICE)));
            stock.setPriceCurrent(price);
            stock.setTicker(ticker);
            tempList.add(stock);
        }
        cursor.close();
        return tempList;
    }

    public void closeDb() {
        dbHelper.close();
    }

}
