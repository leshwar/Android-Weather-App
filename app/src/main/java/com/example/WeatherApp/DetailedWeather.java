package com.example.WeatherApp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailedWeather extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    public String dark_sky_response;
    public static String response_data;
    public static String response_city_state_country;
    public static String response_city;
    public JSONObject dark_sky_json;
    public String temperature;
    public static RelativeLayout spinner;

    public static String returnDarkSkyData() {
        return response_data;
    }
    public static String returnCity() {
        return response_city_state_country;
    }

    public static RelativeLayout getSpinner() {
        return spinner;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "Twitter").setIcon(R.drawable.twitter).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == 0) {
            String url = "https://twitter.com/intent/tweet?text=Check Out "+ response_city_state_country +"'s Weather! It is "+ temperature +"%0A %23CSCI571 WeatherSearch.";
            Uri uri = Uri.parse(url); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
        if(id == 16908332) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_weather);

        spinner = findViewById(R.id.progress_bar_layout);
        spinner.setVisibility(View.GONE);

        //Getting the content from the Main_Activity.java Class
        Bundle bundle = getIntent().getExtras();
        dark_sky_response = bundle.getString("data");
        try {
            dark_sky_json = new JSONObject(dark_sky_response);
            temperature = dark_sky_json.getJSONObject("currently").getString("temperature");
            float i = Float.valueOf(temperature);
            int j = (int)Math.round(i);
            temperature = Integer.toString(j);
            temperature = temperature + "\u00B0 F!";

        } catch (JSONException e) {
            e.printStackTrace();
        }
        response_data = bundle.getString("data");
        response_city_state_country = bundle.getString("city_state_country");
        String[] location = response_city_state_country.split(",");
        response_city = location[0];

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        final int icons_unselected[] = {R.drawable.today, R.drawable.weekly, R.drawable.photos};
        final int icons_selected[] = {R.drawable.today_white, R.drawable.weekly_white, R.drawable.photos_white};

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(icons_selected[0]);
        tabLayout.getTabAt(1).setIcon(icons_unselected[1]);
        tabLayout.getTabAt(2).setIcon(icons_unselected[2]);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position_clicked = tab.getPosition();
                if(position_clicked == 0) {
                    tabLayout.getTabAt(0).setIcon(icons_selected[0]);
                    tabLayout.getTabAt(1).setIcon(icons_unselected[1]);
                    tabLayout.getTabAt(2).setIcon(icons_unselected[2]);
                }
                if(position_clicked == 1) {
                    tabLayout.getTabAt(0).setIcon(icons_unselected[0]);
                    tabLayout.getTabAt(1).setIcon(icons_selected[1]);
                    tabLayout.getTabAt(2).setIcon(icons_unselected[2]);

                }
                if(position_clicked == 2) {
                    tabLayout.getTabAt(0).setIcon(icons_unselected[0]);
                    tabLayout.getTabAt(1).setIcon(icons_unselected[1]);
                    tabLayout.getTabAt(2).setIcon(icons_selected[2]);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle(response_city_state_country);
    }
}