package com.stockinfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StockApi {

    String BASEURL = "https://finnhub.io/";
    String TOKEN = "c12jmjn48v6oi252qv6g";

    @GET("api/v1/quote")
    Call<StockRequest> getInfo(@Query("symbol") String symbol,
                               @Query("token") String token);

    @GET("api/v1/company-news")
    Call<List<StockNewsItem>> getNews(@Query("symbol") String symbol,
                                      @Query("from") String dateFrom,
                                      @Query("to") String dateTo,
                                      @Query("token") String token);

}
