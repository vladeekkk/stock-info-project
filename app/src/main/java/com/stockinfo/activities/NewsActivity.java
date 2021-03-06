package com.stockinfo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.stockinfo.R;
import com.stockinfo.stockpackage.StockApi;
import com.stockinfo.stockpackage.StockNewsItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsActivity extends AppCompatActivity {

    private String ticker;

    private String dateFrom = "2021-03-17";
    private String dateTo;

    private final int NEWS_NUMBER = 3;
    private TextView textViewFirst;
    private TextView textViewSecond;
    private TextView textViewThird;

    static List<StockNewsItem> list;
    static List<StockNewsItem> newsList = new ArrayList<>();

    private int getNewsSize() {
        int newsNumber = 0;
        for (StockNewsItem item : newsList) {
            if (item.getRelated().equals(ticker)) {
                newsNumber++;
            }
        }
        return newsNumber;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Intent intent = getIntent();
        ticker = intent.getStringExtra("ticker");
        dateTo = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        setupNews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupNews();
    }

    public void setupNews() {
        if (getNewsSize() == 0) {
            if (isNetworkAvailable()) {
                loadNews();
            } else {
                showToast();
            }
        } else {
            setTextFields();
        }
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void setTextFields() {
        textViewFirst = findViewById(R.id.news_text_view_1);
        textViewSecond = findViewById(R.id.news_text_view_2);
        textViewThird = findViewById(R.id.news_text_view_3);
        List<StockNewsItem> tmp = MainActivity.dbManager.getNewsListFromDb(ticker);
        for (StockNewsItem item : tmp) {
            newsList.add(item);
        }
        int occurence = 1;
        for (StockNewsItem item : newsList) {
            if (item.getRelated().equals(ticker)) {
                switch (occurence) {
                    case 1:
                        textViewFirst.setLinksClickable(true);
                        textViewFirst.setMovementMethod(LinkMovementMethod.getInstance());
                        textViewFirst.setText(Html.fromHtml(item.getLinkTRUE()));
                        break;
                    case 2:
                        textViewSecond.setLinksClickable(true);
                        textViewSecond.setMovementMethod(LinkMovementMethod.getInstance());
                        textViewSecond.setText(Html.fromHtml(item.getLinkTRUE()));
                        break;
                    case 3:
                        textViewThird.setLinksClickable(true);
                        textViewThird.setMovementMethod(LinkMovementMethod.getInstance());
                        textViewThird.setText(Html.fromHtml(item.getLinkTRUE()));
                        break;
                    default:
                        break;
                }
                occurence++;
            }
            if (occurence == 4) {
                break;
            }
        }
    }


    private void loadNews() {
        Log.i("RE_TAG", "loadNews: ");
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
                Log.i("RESP_TAG", "News onResponse: ok ");
                setTextFields();
            }

            @Override
            public void onFailure(Call<List<StockNewsItem>> call, Throwable t) {
                Log.i("MY_TAG", "onFailure: " + t.getMessage());
            }
        });
    }

    private void showToast() {
        Toast toast = Toast.makeText(getApplicationContext(),
                "No internet connection!", Toast.LENGTH_LONG);
        toast.show();
    }
}