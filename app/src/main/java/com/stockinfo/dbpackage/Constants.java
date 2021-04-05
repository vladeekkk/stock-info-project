package com.stockinfo.dbpackage;

public class Constants {
    public static final String TABLE_NAME = "my_table";
    public static final String _ID = "_id";
    public static final String TICKER = "ticker";
    public static final String PRICE_CURRENT = "price_current";
    public static final String PRICE_CLOSE = "price_closed";
    public static final String IS_FAVOURITE = "favourite";
    public static final String NEWS_FST = "news_fst";
    public static final String NEWS_SND = "news_snd";
    public static final String NEWS_THR = "news_thr";


    public static final String DB_NAME = "my_db.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_STRUCTURE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY," + TICKER + " TEXT," +
            PRICE_CURRENT + " TEXT," + PRICE_CLOSE + " TEXT," + IS_FAVOURITE + " TEXT," +
            NEWS_FST + " TEXT," + NEWS_SND + " TEXT," + NEWS_THR + " TEXT)";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

}
