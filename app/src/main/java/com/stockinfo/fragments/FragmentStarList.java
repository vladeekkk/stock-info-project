package com.stockinfo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.stockinfo.R;
import com.stockinfo.stockpackage.Stock;
import com.stockinfo.activities.MainActivity;
import com.stockinfo.adapters.FavAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentStarList extends Fragment {

    View view;
    private List<Stock> favStockList = new ArrayList<>();
    private RecyclerView recyclerStar;


    public static FavAdapter favAdapter;

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
        List<Stock> tmpList = MainActivity.dbManager.getStockListFromDb();
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
