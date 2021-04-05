package com.stockinfo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.stockinfo.dbpackage.DbManager;
import com.stockinfo.fragments.FragmentStarList;
import com.stockinfo.fragments.FragmentStockList;
import com.stockinfo.R;
import com.stockinfo.adapters.ViewPagerAdapter;

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