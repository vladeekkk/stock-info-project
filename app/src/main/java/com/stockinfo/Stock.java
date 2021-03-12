package com.stockinfo;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Stock {

    private String ticker;

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

    public double getPriceClose() {
        return priceClose;
    }

    public void setPriceClose(double priceClose) {
        this.priceClose = priceClose;
    }

    public double getPriceHigh() {
        return priceHigh;
    }

    public void setPriceHigh(double priceHigh) {
        this.priceHigh = priceHigh;
    }

    public double getPriceLow() {
        return priceLow;
    }

    public void setPriceLow(double priceLow) {
        this.priceLow = priceLow;
    }

    public double getPriceOpen() {
        return priceOpen;
    }

    public void setPriceOpen(double priceOpen) {
        this.priceOpen = priceOpen;
    }

    public double getPricePrevClose() {
        return pricePrevClose;
    }

    public void setPricePrevClose(double pricePrevClose) {
        this.pricePrevClose = pricePrevClose;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }
}
