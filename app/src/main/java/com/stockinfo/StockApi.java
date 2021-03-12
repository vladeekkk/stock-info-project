package com.stockinfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StockApi {

    @GET("api/v1/quote")
    Call<Stock> getInfo(@Query("symbol") String symbol,
                              @Query("token") String token);

///company-news?symbol=AAPL&from=2020-04-30&to=2020-05-01
    @GET("api/v1/company-news")
    Call<List<StockNewsItem>> getNews(@Query("symbol") String symbol,
                                @Query("from") String dateFrom,
                                @Query("to") String dateTo,
                                @Query("token") String token);

}
