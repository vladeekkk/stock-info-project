package com.stockinfo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StockRequest {

    private int id;

    private String ticker;

    public void setStarred(boolean starred) {
        isStarred = starred;
    }

    public void setPriceClose(double priceClose) {
        this.priceClose = priceClose;
    }

    public void setPriceHigh(double priceHigh) {
        this.priceHigh = priceHigh;
    }

    public void setPriceLow(double priceLow) {
        this.priceLow = priceLow;
    }

    public void setPriceOpen(double priceOpen) {
        this.priceOpen = priceOpen;
    }

    public void setPricePrevClose(double pricePrevClose) {
        this.pricePrevClose = pricePrevClose;
    }

    public void setT(int t) {
        this.t = t;
    }

    public void setNewsFirst(String newsFirst) {
        this.newsFirst = newsFirst;
    }

    public void setNewsSecond(String newsSecond) {
        this.newsSecond = newsSecond;
    }

    public void setNewsThird(String newsThird) {
        this.newsThird = newsThird;
    }

    private boolean isStarred;

    @SerializedName("c")
    private double priceClose;

    @SerializedName("h")
    private double priceHigh;

    @SerializedName("l")
    private double priceLow;

    @SerializedName("o")
    private double priceOpen;

    @SerializedName("pc")
    private double pricePrevClose;

    @SerializedName("t")
    private int t;

    private String newsFirst;

    private String newsSecond;

    private String newsThird;

    public void setNews(List<String> news) {
        newsFirst = news.get(0);
        newsSecond = news.get(1);
        newsThird = news.get(2);
    }

    public StockRequest(String ticker, double priceClose, double priceHigh, double priceLow, double priceOpen, double pricePrevClose, int t) {
        this.ticker = ticker;
        this.priceClose = priceClose;
        this.priceHigh = priceHigh;
        this.priceLow = priceLow;
        this.priceOpen = priceOpen;
        this.pricePrevClose = pricePrevClose;
        this.t = t;
    }

    @NonNull
    @Override
    public String toString() {
        return "\nlow is " + priceLow + "; high is " + priceHigh + "\n";
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

    public double getPriceClose() {
        return priceClose;
    }

    public double getPriceHigh() {
        return priceHigh;
    }

    public double getPriceLow() {
        return priceLow;
    }

    public double getPriceOpen() {
        return priceOpen;
    }

    public double getPricePrevClose() {
        return pricePrevClose;
    }

    public int getT() {
        return t;
    }

    public String getNewsFirst() {
        return newsFirst;
    }

    public String getNewsSecond() {
        return newsSecond;
    }

    public String getNewsThird() {
        return newsThird;
    }
}
