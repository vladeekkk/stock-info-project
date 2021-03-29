package com.stockinfo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Stock {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = Constants.TICKER)
    private String ticker;

    @ColumnInfo(name = Constants.IS_FAVOURITE)
    private String isFavourite = "False";

    private boolean hasNews = false;

    @ColumnInfo(name = Constants.PRICE_CURRENT)
    private double priceCurrent;

    @ColumnInfo(name = Constants.PRICE_CLOSE)
    private double pricePrevClose;

    @ColumnInfo(name = Constants.NEWS_FST)
    private String headerOne; // store only three latest news in database
    private String urlOne;

    @ColumnInfo(name = Constants.NEWS_SND)
    private String headerTwo;
    private String urlTwo;

    @ColumnInfo(name = Constants.NEWS_THR)
    private String headerThree;
    private String urlThree;

    public Stock(String ticker, String isFavourite, double priceCurrent, double pricePrevClose) {
        this.ticker = ticker;
        this.isFavourite = isFavourite;
        this.priceCurrent = priceCurrent;
        this.pricePrevClose = pricePrevClose;
    }

    public void setStockPriceInfo(StockRequest stock) {
        this.id = stock.getId();
        this.ticker = stock.getTicker();
        this.priceCurrent = stock.getPriceCurrent();
        this.pricePrevClose = stock.getPricePrevClose();
    }

    public void setNewsInfo(StockNewsItem item1, StockNewsItem item2, StockNewsItem item3) {
        headerOne = item1.getHeadline();
        urlOne = item1.getLink(); // or URL
        headerTwo = item2.getHeadline();
        urlTwo = item2.getLink();
        headerThree = item3.getHeadline();
        urlThree = item3.getLink();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String isFavourite() {
        return isFavourite;
    }

    public void setStarred(boolean starred) {
        isFavourite = "true";
    }

    public double getPriceCurrent() {
        return priceCurrent;
    }

    public void setPriceCurrent(double priceCurrent) {
        this.priceCurrent = priceCurrent;
    }

    public double getPricePrevClose() {
        return pricePrevClose;
    }

    public void setPricePrevClose(double pricePrevClose) {
        this.pricePrevClose = pricePrevClose;
    }

    public String getHeaderOne() {
        return headerOne;
    }

    public void setHeaderOne(String headerOne) {
        this.headerOne = headerOne;
    }

    public String getUrlOne() {
        return urlOne;
    }

    public void setUrlOne(String urlOne) {
        this.urlOne = urlOne;
    }

    public String getHeaderTwo() {
        return headerTwo;
    }

    public void setHeaderTwo(String headerTwo) {
        this.headerTwo = headerTwo;
    }

    public String getUrlTwo() {
        return urlTwo;
    }

    public void setUrlTwo(String urlTwo) {
        this.urlTwo = urlTwo;
    }

    public String getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(String isFavourite) {
        this.isFavourite = isFavourite;
    }

    public String getHeaderThree() {
        return headerThree;
    }

    public void setHeaderThree(String headerThree) {
        this.headerThree = headerThree;
    }

    public String getUrlThree() {
        return urlThree;
    }

    public void setUrlThree(String urlThree) {
        this.urlThree = urlThree;
    }

}
