package com.stockinfo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

    public static final String TAG = "MY_TAG";

    View view;

    private RecyclerView recycler;
    public static RecyclerViewAdapter recyclerAdapter;
    private List<Stock> trueStockList = new ArrayList<>();
    private Button newsButton;

    public FragmentStockList() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.stock_list_fragment, container, false);
        trueStockList = MainActivity.dbManager.getStockListFromDb();
        if (trueStockList.size() > 0) {
            setRecyclerView();
            return view;
        }

        if (trueStockList.size() == 0) {
            parseJSON();
        }
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setRecyclerView() {
        Log.i(TAG, "setRecyclerView: ok");
        recycler = view.findViewById(R.id.stock_list_recycler_view);
        recyclerAdapter = new RecyclerViewAdapter(getContext(), trueStockList);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setAdapter(recyclerAdapter);
    }

    private void parseJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StockApi.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        StockApi stockApi = retrofit.create(StockApi.class);
        List<String> names = new ArrayList<>();
        names.add("AAPL"); names.add("IBM"); names.add("MSFT");
        names.add("TSLA"); names.add("YNDX"); names.add("AMZN");
        names.add("FB"); names.add("JPM"); names.add("NVDA");
        names.add("TSLA"); names.add("YNDX"); names.add("AMZN");
        names.add("FB"); names.add("JPM"); names.add("NVDA");
        names.add("FB"); names.add("JPM"); names.add("NVDA");
        names.add("TSLA"); names.add("YNDX"); names.add("AMZN");

        for (String name : names) {
            Call<StockRequest> call = stockApi.getInfo(name, StockApi.TOKEN);
            call.enqueue(new Callback<StockRequest>() {
                @Override
                public void onResponse(Call<StockRequest> call, Response<StockRequest> response) {
                    if (!response.isSuccessful()) {
                        Log.i(TAG, "onResponseErrorCode : " + response.code());
                        return;
                    }
                    Log.i(TAG, "onResponse:  ok");
                    StockRequest stockRequest = response.body();
                    stockRequest.setTicker(name);

                    MainActivity.insert(new Stock(
                            stockRequest.getTicker(),
                            "false",
                            stockRequest.getPriceCurrent(),
                            stockRequest.getPricePrevClose()));

                    trueStockList = MainActivity.dbManager.getStockListFromDb();
                    if (trueStockList.size() == names.size()) {
                        Log.i(TAG, "call.enqueue() finished");
                        setRecyclerView();
                    }
                }

                @Override
                public void onFailure(Call<StockRequest> call, Throwable t) {
                    Log.i(TAG, "onFailure: " + t.getMessage());
                }
            });
        }
    }
}
