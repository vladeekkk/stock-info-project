package com.stockinfo;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Stock {

    private String ticker;

    private boolean isStarred = false;

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

    public Stock(String ticker, double priceClose, double priceHigh, double priceLow, double priceOpen, double pricePrevClose, int t) {
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

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public boolean getIsStarred() {
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
}
