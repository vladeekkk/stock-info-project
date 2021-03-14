package com.stockinfo;

public class Constants {
    public static final String TABLE_NAME = "my_table";
    public static final String _ID = "_id";
    public static final String TICKER = "ticker";
    public static final String PRICE = "price";
    public static final String DB_NAME = "my_db.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_STRUCTURE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY," + TICKER + " TEXT," +
            PRICE + " TEXT)";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;



}
