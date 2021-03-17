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

public class FragmentStarList extends Fragment {

    View view;
    private List<Stock> favStockList = new ArrayList<>();
    private RecyclerView recyclerStar;

    private FavAdapter favAdapter;

    public FragmentStarList() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.star_list_fragment, container, false);
        recyclerStar = view.findViewById(R.id.star_list_recycler_view);
        recyclerStar.setLayoutManager(new LinearLayoutManager(getActivity()));
//        favStockList.add(new Stock("KEK", "true", 231, 213));
//        favStockList.add(new Stock("KEK", "true", 231, 213));
//        favStockList.add(new Stock("KEK", "true", 231, 213));
        List<Stock> tmpList = MainActivity.dbManager.getStockListFromDb();
        tmpList.set(0, new Stock("aapl", "true", 12, 12));
        for (Stock s : tmpList) {
            if (s.getIsFavourite().equals("true")) {
                favStockList.add(s);
            }
        }

        favAdapter = new FavAdapter(getActivity(), favStockList);
        recyclerStar.setAdapter(favAdapter);
        return view;
    }
}
