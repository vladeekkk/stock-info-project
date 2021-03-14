package com.stockinfo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Stock {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "ticker")
    private String ticker;

    private boolean isStarred = false;

    private boolean hasNews = false;

    private double priceCurrent;

    private double pricePrevClose;

    private String headerOne; // store only three latest news in database
    private String urlOne;
    private String picOne;

    private String headerTwo;
    private String urlTwo;
    private String picTwo;

    private String headerThree;
    private String urlThree;
    private String picThree;

    public void setStockInfo(StockRequest stock) {
        this.id = stock.getId();
        this.ticker = stock.getTicker();
        this.priceCurrent = stock.getPriceCurrent();
        this.pricePrevClose = stock.getPricePrevClose();
    }

    public void setNewsInfo(StockNewsItem item1, StockNewsItem item2, StockNewsItem item3) {
        headerOne = item1.getHeadline();
        urlOne = item1.getLink(); // or URL, idk
        picOne = item1.getImage();
        headerTwo = item2.getHeadline();
        urlTwo = item2.getLink();
        picTwo = item2.getImage();
        headerThree = item3.getHeadline();
        urlThree = item3.getLink();
        picThree = item3.getImage();
        hasNews = true;
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

    public boolean isStarred() {
        return isStarred;
    }

    public void setStarred(boolean starred) {
        isStarred = starred;
    }

    public boolean isHasNews() {
        return hasNews;
    }

    public void setHasNews(boolean hasNews) {
        this.hasNews = hasNews;
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

    public String getPicOne() {
        return picOne;
    }

    public void setPicOne(String picOne) {
        this.picOne = picOne;
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

    public String getPicTwo() {
        return picTwo;
    }

    public void setPicTwo(String picTwo) {
        this.picTwo = picTwo;
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

    public String getPicThree() {
        return picThree;
    }

    public void setPicThree(String picThree) {
        this.picThree = picThree;
    }
}
