package com.stockinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsActivity extends AppCompatActivity {

    private String ticker;

    private String dateFrom = "2021-02-15";
    private String dateTo = "2021-03-09";

    private final int NEWS_NUMBER = 3;
    private TextView textViewFirst;
    private TextView textViewSecond;
    private TextView textViewThird;

    List<StockNewsItem> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Intent intent = getIntent();
        ticker = intent.getStringExtra("ticker");
        Log.i("MY_TAG", "onCreate: " + ticker);
        loadNews();
    }

    private void setTextFields() {
        textViewFirst = findViewById(R.id.news_text_view_1);
        textViewSecond = findViewById(R.id.news_text_view_2);
        textViewThird = findViewById(R.id.news_text_view_3);
//        List<String> newsList = MainActivity.dbManager.getNewsListFromDb();
        textViewFirst.setText(Html.fromHtml(list.get(0).getLink()));
        textViewSecond.setText(Html.fromHtml(list.get(1).getLink()));
        textViewThird.setText(Html.fromHtml(list.get(2).getLink()));
    }


    private void loadNews() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StockApi.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        StockApi stockApi = retrofit.create(StockApi.class);
        list = new ArrayList<>();

        Call<List<StockNewsItem>> call = stockApi.getNews(ticker,
                dateFrom, dateTo, StockApi.TOKEN);
        call.enqueue(new Callback<List<StockNewsItem>>() {
            @Override
            public void onResponse(Call<List<StockNewsItem>> call, Response<List<StockNewsItem>> response) {
                list = response.body();
                MainActivity.dbManager.insertNewsToDb(list.get(0), list.get(1), list.get(2));
                Log.i("MY_TAG", "News onResponse: ok ");
                setTextFields();
            }

            @Override
            public void onFailure(Call<List<StockNewsItem>> call, Throwable t) {
                Log.i("MY_TAG", "onFailure: " + t.getMessage());
            }
        });
    }
}