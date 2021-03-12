package com.stockinfo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentStockList extends Fragment {

    private static final String TAG = "MY_TAG";
    private static final String token = "c12jmjn48v6oi252qv6g";
    View v;
    private RecyclerView recycler;
    public static List<Stock> stockList;

    public FragmentStockList() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parseJSON();
        v = inflater.inflate(R.layout.stock_list_fragment, container, false);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stockList = new ArrayList<>();
    }

    private void parseJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://finnhub.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        StockApi stockApi = retrofit.create(StockApi.class);
        List<String> names = new ArrayList<>();
        names.add("AAPL"); names.add("IBM"); names.add("MSFT");
        names.add("TSLA"); names.add("YNDX"); names.add("AMZN");
        names.add("FB"); names.add("JPM"); names.add("NVDA");
        names.add("TSLA"); names.add("YNDX"); names.add("AMZN");
        names.add("FB"); names.add("JPM"); names.add("NVDA");
        for (String name : names) {
            Call<Stock> call = stockApi.getInfo(name, token);
            call.enqueue(new Callback<Stock>() {
                @Override
                public void onResponse(Call<Stock> call, Response<Stock> response) {
                    if (!response.isSuccessful()) {
                        Log.i(TAG, "onResponseError:" + "Code : " + response.code());
                        return;
                    }
                    Stock stock = response.body();
                    stock.setTicker(name);
                    Log.i(TAG, "onResponse:  ok");
                    stockList.add(stock);
                    if (stockList.size() == names.size()) {
                        Log.i(TAG, "I tried");
                        setRecyclerView();
                    }
                }

                @Override
                public void onFailure(Call<Stock> call, Throwable t) {
                    Log.i(TAG, "onFailure: " + t.getMessage());
                }
            });
        }

    }
    private void setRecyclerView() {
        recycler = v.findViewById(R.id.stock_list_recycler_view);
        RecyclerViewAdapter recyclerAdapter = new RecyclerViewAdapter(getContext(), stockList);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setAdapter(recyclerAdapter);
    }
}
