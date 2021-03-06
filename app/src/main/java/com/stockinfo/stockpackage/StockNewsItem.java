package com.stockinfo.stockpackage;

import com.stockinfo.stockpackage.Stock;

public class StockNewsItem {

    private String category;
    private int datetime;
    private String headline;
    private int id;
    private String image;
    private String related;
    private String source;
    private String summary;
    private String url;

    private String link;

    public String getLinkTRUE() {
        return link;
    }

    public StockNewsItem(String ticker, String link) {
        this.related = ticker;
        this.link = link;
    }

    private Stock stock;

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public String getLink() {
        return "<html><a href=\"" + url + "\">" + headline + "</a></html>";
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getDatetime() {
        return datetime;
    }

    public void setDatetime(int datetime) {
        this.datetime = datetime;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRelated() {
        return related;
    }

    public void setRelated(String related) {
        this.related = related;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "StockNewsItem{" +
                "category='" + category + '\'' +
                ", datetime=" + datetime +
                ", headline='" + headline + '\'' +
                ", id=" + id +
                ", image='" + image + '\'' +
                ", related='" + related + '\'' +
                ", source='" + source + '\'' +
                ", summary='" + summary + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
