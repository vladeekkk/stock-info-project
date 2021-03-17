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

    public void insertStockToDb(Stock stock) {
        ContentValues values = new ContentValues();
        values.put(Constants.TICKER, stock.getTicker());
        values.put(Constants.PRICE_CURRENT, stock.getPriceCurrent());
        values.put(Constants.PRICE_CLOSE, stock.getPricePrevClose());
        values.put(Constants.IS_FAVOURITE, stock.getIsFavourite());
        values.put(Constants.NEWS_FST, "");
        values.put(Constants.NEWS_SND, "");
        values.put(Constants.NEWS_THR, "");
        db.insert(Constants.TABLE_NAME, null, values);
    }

    public void insertNewsToDb(StockNewsItem itemFst, StockNewsItem itemSnd, StockNewsItem itemThr) {
        int id = getIdByStock(itemFst.getRelated());
        Log.i("KEK", "insertNewsToDb: " + id);
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.TABLE_NAME + " WHERE _ID = " + id, null);
        cursor.moveToFirst();

        String ticker = cursor.getString(cursor.getColumnIndex(Constants.TICKER));
        String priceCur = cursor.getString(cursor.getColumnIndex(Constants.PRICE_CURRENT));
        String priceClose = cursor.getString(cursor.getColumnIndex(Constants.PRICE_CLOSE));
        String isFav = cursor.getString(cursor.getColumnIndex(Constants.IS_FAVOURITE));
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.TICKER, ticker);
        contentValues.put(Constants.PRICE_CURRENT, priceCur);
        contentValues.put(Constants.PRICE_CLOSE, priceClose);
        contentValues.put(Constants.IS_FAVOURITE, isFav);
        contentValues.put(Constants.NEWS_FST, itemFst.getLink());
        contentValues.put(Constants.NEWS_SND, itemSnd.getLink());
        contentValues.put(Constants.NEWS_THR, itemThr.getLink());
        cursor.close();
        db.update(Constants.TABLE_NAME, contentValues, " _ID=" + id, null);

    }

    public void changeFavourites(Stock stock) {
        int id = getIdByStock(stock.getTicker());
        Log.i("UPDATE_ID", "updateData: " + id + "; " + stock.getTicker());
        db = dbHelper.getWritableDatabase();
        String newBoolean = ((Boolean) !Boolean.parseBoolean(stock.getIsFavourite())).toString();
        stock.setIsFavourite(newBoolean);
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.TICKER, stock.getTicker());
        contentValues.put(Constants.PRICE_CURRENT, stock.getPriceCurrent());
        contentValues.put(Constants.PRICE_CLOSE, stock.getPricePrevClose());
        contentValues.put(Constants.IS_FAVOURITE, newBoolean);
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

    public List<StockNewsItem> getNewsListFromDb(String ticker) {
        int id = getIdByStock(ticker);
        Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.TABLE_NAME + " WHERE _ID = " + id, null);
        cursor.moveToFirst();
        List<StockNewsItem> tempList = new ArrayList<>();
        tempList.add(new StockNewsItem(cursor.getString(cursor.getColumnIndex(Constants.TICKER)),
                cursor.getString(cursor.getColumnIndex(Constants.NEWS_FST))));
        Log.i("MANAGER", "getNewsListFromDb: " + cursor.getString(cursor.getColumnIndex(Constants.NEWS_FST)));
        tempList.add(new StockNewsItem(cursor.getString(cursor.getColumnIndex(Constants.TICKER)),
                cursor.getString(cursor.getColumnIndex(Constants.NEWS_SND))));
        Log.i("MANAGER", "getNewsListFromDb: " + cursor.getString(cursor.getColumnIndex(Constants.NEWS_SND)));
        tempList.add(new StockNewsItem(cursor.getString(cursor.getColumnIndex(Constants.TICKER)),
                cursor.getString(cursor.getColumnIndex(Constants.NEWS_THR))));
        Log.i("MANAGER", "getNewsListFromDb: " + cursor.getString(cursor.getColumnIndex(Constants.NEWS_THR)));
        cursor.close();
        return tempList;
    }

    public int getIdByStock(String ticker) {
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
        cursor.close();
        return -1;
    }

    public void closeDb() {
        dbHelper.close();
    }
}
