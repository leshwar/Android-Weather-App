package com.example.WeatherApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static TabAdapter adapter;
    public static TabLayout tab;
    public static CustomViewPager viewPager;
    public static ArrayList<String> cities = new ArrayList<>();

    public static ArrayList<String> returnCities() {
        return cities;
    }

    public static TabLayout returnTabs() {
        return tab;
    }

    public static ViewPager returnViewPager() {
        return viewPager;
    }

    public static TabAdapter returnTabAdapter() {
        return adapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState );
        setContentView(R.layout.activity_main);

        viewPager =  findViewById(R.id.viewPager);
        tab = findViewById(R.id.tabLayout);
        tab.addTab(tab.newTab().setText(""));

        //Changing the for loop based on the length of the shared preference.
        SharedPreferences prefs = this.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        int no_of_fav = prefs.getAll().size();

        Map<String, ?> allEntries = prefs.getAll();
        cities = new ArrayList<>();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            cities.add(entry.getKey() + "|" + entry.getValue().toString());
        }

        for (int k = 0; k < no_of_fav; k++) {
            tab.addTab(tab.newTab().setText(""));
        }
        adapter = new TabAdapter (getSupportFragmentManager(), tab.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(10);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
    }
}