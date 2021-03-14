package com.stockinfo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    public static DbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbManager = new DbManager(this);

        tabLayout = findViewById(R.id.tablayout_id);
        viewPager = findViewById(R.id.viewpager_id);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new FragmentStockList(), "Stock list");
        adapter.addFragment(new FragmentStarList(), "Favourites");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_portfolio_24);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_star_24);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbManager.openDb();
    }

    public static void insert(Stock stock) {
        dbManager.insertToDb(stock.getTicker(), String.valueOf(stock.getPriceCurrent()));
//        for (String ticker : dbManager.getFromDb()) {
//            Log.i("DB_TAG", "insert: " + ticker);
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.closeDb();
    }
}