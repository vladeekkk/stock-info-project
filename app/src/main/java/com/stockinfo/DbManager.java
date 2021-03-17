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
        String headLine = itemFst.getHeadline();
        int id = getIdByStock(itemFst.getRelated());
        Log.i("KEK", "insertNewsToDb: " + id);
        db = dbHelper.getWritableDatabase();
        Cursor cursorc = db.rawQuery("SELECT * FROM " + Constants.TABLE_NAME + " WHERE _ID = " + id, null);
        cursorc.moveToFirst();

        String ticker = cursorc.getString(cursorc.getColumnIndex(Constants.TICKER));
        String priceCur = cursorc.getString(cursorc.getColumnIndex(Constants.PRICE_CURRENT));
        String priceClose = cursorc.getString(cursorc.getColumnIndex(Constants.PRICE_CLOSE));
        String isFav = cursorc.getString(cursorc.getColumnIndex(Constants.IS_FAVOURITE));

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.TICKER, ticker);
        contentValues.put(Constants.PRICE_CURRENT, priceCur);
        contentValues.put(Constants.PRICE_CLOSE, priceClose);
        contentValues.put(Constants.IS_FAVOURITE, isFav);
        contentValues.put(Constants.NEWS_FST, itemFst.getLink());
        contentValues.put(Constants.NEWS_SND, itemSnd.getLink());
        contentValues.put(Constants.NEWS_THR, itemThr.getLink());

        db.update(Constants.TABLE_NAME, contentValues, " _ID=" + id, null);
//        db.execSQL("UPDATE " + Constants.TABLE_NAME + " SET news_one='"+headLine+"' WHERE _id="+id+"");
//        Cursor cursor = db.query(Constants.TABLE_NAME,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null
//        );
//        String ticker = "", isFav = "";
//        double priceCur = 0, priceClose = 0;
//        while (cursor.moveToNext()) {
//            int current_id = cursor.getInt(cursor.getColumnIndex(Constants._ID));
//            ticker = cursor.getString(cursor.getColumnIndex(Constants.TICKER));
//            isFav = cursor.getString(cursor.getColumnIndex(Constants.IS_FAVOURITE));
//
//            priceCur = Double.parseDouble(cursor.getString(cursor.getColumnIndex(Constants.PRICE_CURRENT)));
//            priceClose = Double.parseDouble(cursor.getString(cursor.getColumnIndex(Constants.PRICE_CLOSE)));
//            if (current_id == id) {
//                break;
//            }
//        }
//        Log.i("DB_TAG", "insertNewsToDb: " + cursor.getString(cursor.getColumnIndex(Constants.TICKER)));
//        Log.i("DB_TAG", "insertNewsToDb: " + cursor.getString(cursor.getColumnIndex(Constants.PRICE_CURRENT)));
//
//        values.put(Constants.TICKER, ticker);
//        values.put(Constants.IS_FAVOURITE, isFav);
//        values.put(Constants.PRICE_CURRENT, priceCur);
//        values.put(Constants.PRICE_CLOSE, priceClose);
//        values.put(Constants.NEWS_FST, itemFst.getLink() + "|" + itemFst.getSource());
//        values.put(Constants.NEWS_SND, itemSnd.getLink() + "|" + itemSnd.getSource());
//        values.put(Constants.NEWS_THR, itemThr.getLink() + "|" + itemThr.getSource());
//        db.update(Constants.TABLE_NAME, values, "_ID=" + id, null);
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

//    public List<StockNewsItem> getNewsListFromDb() {
//
//    }

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
        return -1;
    }

    public void closeDb() {
        dbHelper.close();
    }
}
