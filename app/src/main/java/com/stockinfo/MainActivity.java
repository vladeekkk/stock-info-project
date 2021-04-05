package com.stockinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    public static DbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        deleteDatabase(Constants.DB_NAME);

        dbManager = new DbManager(this);

        viewPager = findViewById(R.id.viewpager_id);
        tabLayout = findViewById(R.id.tablayout_id);
        setTabLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbManager.openDb();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.closeDb();
    }

    private void setTabLayout() {
        final String fstFragmentName = "Stock list";
        final String sndFragmentName = "Favourites";

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentStockList(), fstFragmentName);
        adapter.addFragment(new FragmentStarList(), sndFragmentName);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_portfolio_24);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_star_24);
    }
}