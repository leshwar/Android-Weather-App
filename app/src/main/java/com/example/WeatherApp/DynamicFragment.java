package com.example.WeatherApp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.health.SystemHealthManager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;


public class DynamicFragment extends Fragment {
    View view;
    int val;
    TextView c;
    String send_city_state_country;
    String lat_fav;
    String lon_fav;
    int searchQuery = 0;
    public static ArrayList<Fragment> mFragments = new ArrayList<>();
    public static Fragment mFragment = new Fragment();

    public static ArrayList<Fragment> getFragmentsList() {
        return  mFragments;
    }

    public static Fragment getFragment() {
        return  mFragment;
    }


    //Calling the Dark Sky API in Node Server to get Data.
    public void darkSkyAPI(String url, final String location) {
        //Pass Data Variable
        final TextView dark_sky_data = view.findViewById(R.id.dark_sky_data);

        //Card1 Variables
        final TextView card1_temp = view.findViewById(R.id.card1_temp);
        final TextView card1_summary = view.findViewById(R.id.card1_summary);
        final TextView card1_location = view.findViewById(R.id.card1_location);
        final ImageView card1_icon = view.findViewById(R.id.card1_icon);

        //Card2 Variables
        final TextView card2_humi_value = view.findViewById(R.id.card2_humi_value);
        final TextView card2_wind_value = view.findViewById(R.id.card2_wind_value);
        final TextView card2_visi_value = view.findViewById(R.id.card2_visi_value);
        final TextView card2_pres_value = view.findViewById(R.id.card2_pres_value);

        //Card3 Variables
        final TextView date1 = view.findViewById(R.id.date1);
        final ImageView icon1 = view.findViewById(R.id.icon1);
        final TextView low_temp1 = view.findViewById(R.id.low_temp1);
        final TextView high_temp1 = view.findViewById(R.id.high_temp1);

        final TextView date2 = view.findViewById(R.id.date2);
        final ImageView icon2 = view.findViewById(R.id.icon2);
        final TextView low_temp2 = view.findViewById(R.id.low_temp2);
        final TextView high_temp2 = view.findViewById(R.id.high_temp2);

        final TextView date3 = view.findViewById(R.id.date3);
        final ImageView icon3 = view.findViewById(R.id.icon3);
        final TextView low_temp3 = view.findViewById(R.id.low_temp3);
        final TextView high_temp3 = view.findViewById(R.id.high_temp3);

        final TextView date4 = view.findViewById(R.id.date4);
        final ImageView icon4 = view.findViewById(R.id.icon4);
        final TextView low_temp4 = view.findViewById(R.id.low_temp4);
        final TextView high_temp4 = view.findViewById(R.id.high_temp4);

        final TextView date5 = view.findViewById(R.id.date5);
        final ImageView icon5 = view.findViewById(R.id.icon5);
        final TextView low_temp5 = view.findViewById(R.id.low_temp5);
        final TextView high_temp5 = view.findViewById(R.id.high_temp5);

        final TextView date6 = view.findViewById(R.id.date6);
        final ImageView icon6 = view.findViewById(R.id.icon6);
        final TextView low_temp6 = view.findViewById(R.id.low_temp6);
        final TextView high_temp6 = view.findViewById(R.id.high_temp6);

        final TextView date7 = view.findViewById(R.id.date7);
        final ImageView icon7 = view.findViewById(R.id.icon7);
        final TextView low_temp7 = view.findViewById(R.id.low_temp7);
        final TextView high_temp7 = view.findViewById(R.id.high_temp7);

        final TextView date8 = view.findViewById(R.id.date8);
        final ImageView icon8 = view.findViewById(R.id.icon8);
        final TextView low_temp8 = view.findViewById(R.id.low_temp8);
        final TextView high_temp8 = view.findViewById(R.id.high_temp8);

        final LinearLayout spinner = view.findViewById(R.id.progress_bar_layout);
        final RelativeLayout main_layout = view.findViewById(R.id.main_view);

        //Setting Fab Icon
        final FloatingActionButton fabAdd = view.findViewById(R.id.fabAdd);
        final FloatingActionButton fabRemove = view.findViewById(R.id.fabRemove);
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", Context.MODE_WORLD_READABLE); // 0 - for private mode
        String shared_value = pref.getString(send_city_state_country,null);
        if(shared_value != null) {
            fabAdd.hide();
            fabRemove.show();
        }
        else {
            fabAdd.show();
            fabRemove.hide();
        }
        if(val == 0 && searchQuery == 0) {
            fabAdd.hide();
            fabRemove.hide();
        }

        try {
            RequestQueue queue = Volley.newRequestQueue(getContext());
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //Converting the String to JSON
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.d("Dark Sky Response", response);
                        dark_sky_data.setText(response.toString());
                        String temperature = jsonObject.getJSONObject("currently").getString("temperature");
                        String summary = jsonObject.getJSONObject("currently").getString("summary");
                        String icon = jsonObject.getJSONObject("currently").getString("icon");

                        //Converting Temp to Round value
                        float i = Float.valueOf(temperature);
                        int j = (int)Math.round(i);
                        temperature = Integer.toString(j);
                        temperature = temperature + "\u00B0 F";
                        card1_temp.setText(temperature);

                        card1_summary.setText(summary);
                        card1_location.setText(location);
                        card1_icon.setImageResource(R.drawable.weather_sunny);

                        if(icon.equals("clear-day")) {
                            card1_icon.setImageResource(R.drawable.weather_sunny);
                        }
                        if(icon.equals("clear-night")) {
                            card1_icon.setImageResource(R.drawable.weather_night);
                        }
                        if(icon.equals("rain")) {
                            card1_icon.setImageResource(R.drawable.weather_rainy);
                        }
                        if(icon.equals("sleet")) {
                            card1_icon.setImageResource(R.drawable.weather_snowy_rainy);
                        }
                        if(icon.equals("snow")) {
                            card1_icon.setImageResource(R.drawable.weather_snowy);
                        }
                        if(icon.equals("wind")) {
                            card1_icon.setImageResource(R.drawable.weather_windy_variant);
                        }
                        if(icon.equals("fog")) {
                            card1_icon.setImageResource(R.drawable.weather_fog);
                        }
                        if(icon.equals("cloudy")) {
                            card1_icon.setImageResource(R.drawable.weather_cloudy);
                        }
                        if(icon.equals("partly-cloudy-night")) {
                            card1_icon.setImageResource(R.drawable.weather_night_partly_cloudy);
                        }
                        if(icon.equals("partly-cloudy-day")) {
                            card1_icon.setImageResource(R.drawable.weather_partly_cloudy);
                        }

                        DecimalFormat df = new DecimalFormat("0.00");

                        String humidity = jsonObject.getJSONObject("currently").getString("humidity");
                        Float humidity_float = Float.parseFloat(humidity);
                        humidity_float = humidity_float * 100;
                        int humidity_int = Math.round(humidity_float);
                        String windSpeed = jsonObject.getJSONObject("currently").getString("windSpeed");
                        String visibility = jsonObject.getJSONObject("currently").getString("visibility");
                        String pressure = jsonObject.getJSONObject("currently").getString("pressure");

                        card2_humi_value.setText(humidity_int + " %");
                        double windSpeed_float = Math.round(Float.valueOf(windSpeed) * 100.0) / 100.0;
                        card2_wind_value.setText(df.format(windSpeed_float) + " mph");
                        double visibility_float = Math.round(Float.valueOf(visibility) * 100.0) / 100.0;
                        card2_visi_value.setText(df.format(visibility_float) + " km");
                        double pressure_float = Math.round(Float.valueOf(pressure) * 100.0) / 100.0;
                        card2_pres_value.setText(df.format(pressure_float) + " mb");

                        JSONObject data = jsonObject.getJSONObject("daily");
                        JSONArray data_array = data.getJSONArray("data");

                        //List 1
                        String time = data_array.getJSONObject(0).getString("time");
                        Date date = new java.util.Date(Long.parseLong(time)*1000L);
                        SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy");
                        //sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
                        String formattedDate = sdf.format(date);

                        summary = data_array.getJSONObject(0).getString("icon");
                        String temperatureLow = data_array.getJSONObject(0).getString("temperatureLow");
                        i = Float.valueOf(temperatureLow);
                        j = (int)Math.round(i);
                        temperatureLow = Integer.toString(j);

                        String temperatureHigh = data_array.getJSONObject(0).getString("temperatureHigh");
                        i = Float.valueOf(temperatureHigh);
                        j = (int)Math.round(i);
                        temperatureHigh = Integer.toString(j);

                        summary = summary.toLowerCase();

                        icon1.setImageResource(R.drawable.weather_sunny);

                        date1.setText(formattedDate);
                        if(summary.equals("clear-day")) {
                            icon1.setImageResource(R.drawable.weather_sunny);
                        }
                        if(summary.equals("clear-night")) {
                            icon1.setImageResource(R.drawable.weather_night);
                        }
                        if(summary.equals("rain")) {
                            icon1.setImageResource(R.drawable.weather_rainy);
                        }
                        if(summary.equals("sleet")) {
                            icon1.setImageResource(R.drawable.weather_snowy_rainy);
                        }
                        if(summary.equals("snow")) {
                            icon1.setImageResource(R.drawable.weather_snowy);
                        }
                        if(summary.equals("wind")) {
                            icon1.setImageResource(R.drawable.weather_windy_variant);
                        }
                        if(summary.equals("fog")) {
                            icon1.setImageResource(R.drawable.weather_fog);
                        }
                        if(summary.equals("cloudy")) {
                            icon1.setImageResource(R.drawable.weather_cloudy);
                        }
                        if(summary.equals("partly-cloudy-night")) {
                            icon1.setImageResource(R.drawable.weather_night_partly_cloudy);
                        }
                        if(summary.equals("partly-cloudy-day")) {
                            icon1.setImageResource(R.drawable.weather_partly_cloudy);
                        }
                        low_temp1.setText(temperatureLow);
                        high_temp1.setText(temperatureHigh);

                        //List 2
                        time = data_array.getJSONObject(1).getString("time");
                        date = new java.util.Date(Long.parseLong(time)*1000L);
                        sdf = new java.text.SimpleDateFormat("MM/dd/yyyy");
                        //sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
                        formattedDate = sdf.format(date);

                        summary = "";
                        summary = data_array.getJSONObject(1).getString("icon");
                        temperatureLow = data_array.getJSONObject(1).getString("temperatureLow");
                        i = Float.valueOf(temperatureLow);
                        j = (int)Math.round(i);
                        temperatureLow = Integer.toString(j);

                        temperatureHigh = data_array.getJSONObject(1).getString("temperatureHigh");
                        i = Float.valueOf(temperatureHigh);
                        j = (int)Math.round(i);
                        temperatureHigh = Integer.toString(j);

                        summary = summary.toLowerCase();

                        icon2.setImageResource(R.drawable.weather_sunny);

                        date2.setText(formattedDate);
                        if(summary.equals("clear-day")) {
                            icon2.setImageResource(R.drawable.weather_sunny);
                        }
                        if(summary.equals("clear-night")) {
                            icon2.setImageResource(R.drawable.weather_night);
                        }
                        if(summary.equals("rain")) {
                            icon2.setImageResource(R.drawable.weather_rainy);
                        }
                        if(summary.equals("sleet")) {
                            icon2.setImageResource(R.drawable.weather_snowy_rainy);
                        }
                        if(summary.equals("snow")) {
                            icon2.setImageResource(R.drawable.weather_snowy);
                        }
                        if(summary.equals("wind")) {
                            icon2.setImageResource(R.drawable.weather_windy_variant);
                        }
                        if(summary.equals("fog")) {
                            icon2.setImageResource(R.drawable.weather_fog);
                        }
                        if(summary.equals("cloudy")) {
                            icon2.setImageResource(R.drawable.weather_cloudy);
                        }
                        if(summary.equals("partly-cloudy-night")) {
                            icon2.setImageResource(R.drawable.weather_night_partly_cloudy);
                        }
                        if(summary.equals("partly-cloudy-day")) {
                            icon2.setImageResource(R.drawable.weather_partly_cloudy);
                        }
                        low_temp2.setText(temperatureLow);
                        high_temp2.setText(temperatureHigh);

                        //List 3
                        time = data_array.getJSONObject(2).getString("time");
                        date = new java.util.Date(Long.parseLong(time)*1000L);
                        sdf = new java.text.SimpleDateFormat("MM/dd/yyyy");
                        //sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
                        formattedDate = sdf.format(date);

                        summary = "";
                        summary = data_array.getJSONObject(2).getString("icon");
                        temperatureLow = data_array.getJSONObject(2).getString("temperatureLow");
                        i = Float.valueOf(temperatureLow);
                        j = (int)Math.round(i);
                        temperatureLow = Integer.toString(j);

                        temperatureHigh = data_array.getJSONObject(2).getString("temperatureHigh");
                        i = Float.valueOf(temperatureHigh);
                        j = (int)Math.round(i);
                        temperatureHigh = Integer.toString(j);

                        summary = summary.toLowerCase();

                        icon3.setImageResource(R.drawable.weather_sunny);

                        date3.setText(formattedDate);
                        if(summary.equals("clear-day")) {
                            icon3.setImageResource(R.drawable.weather_sunny);
                        }
                        if(summary.equals("clear-night")) {
                            icon3.setImageResource(R.drawable.weather_night);
                        }
                        if(summary.equals("rain")) {
                            icon3.setImageResource(R.drawable.weather_rainy);
                        }
                        if(summary.equals("sleet")) {
                            icon3.setImageResource(R.drawable.weather_snowy_rainy);
                        }
                        if(summary.equals("snow")) {
                            icon3.setImageResource(R.drawable.weather_snowy);
                        }
                        if(summary.equals("wind")) {
                            icon3.setImageResource(R.drawable.weather_windy_variant);
                        }
                        if(summary.equals("fog")) {
                            icon3.setImageResource(R.drawable.weather_fog);
                        }
                        if(summary.equals("cloudy")) {
                            icon3.setImageResource(R.drawable.weather_cloudy);
                        }
                        if(summary.equals("partly-cloudy-night")) {
                            icon3.setImageResource(R.drawable.weather_night_partly_cloudy);
                        }
                        if(summary.equals("partly-cloudy-day")) {
                            icon3.setImageResource(R.drawable.weather_partly_cloudy);
                        }
                        low_temp3.setText(temperatureLow);
                        high_temp3.setText(temperatureHigh);

                        //List 4
                        time = data_array.getJSONObject(3).getString("time");
                        date = new java.util.Date(Long.parseLong(time)*1000L);
                        sdf = new java.text.SimpleDateFormat("MM/dd/yyyy");
                        //sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
                        formattedDate = sdf.format(date);

                        String summary4 = data_array.getJSONObject(3).getString("icon");
                        temperatureLow = data_array.getJSONObject(3).getString("temperatureLow");
                        i = Float.valueOf(temperatureLow);
                        j = (int)Math.round(i);
                        temperatureLow = Integer.toString(j);

                        temperatureHigh = data_array.getJSONObject(3).getString("temperatureHigh");
                        i = Float.valueOf(temperatureHigh);
                        j = (int)Math.round(i);
                        temperatureHigh = Integer.toString(j);

                        summary = summary.toLowerCase();

                        icon4.setImageResource(R.drawable.weather_sunny);

                        date4.setText(formattedDate);
                        if(summary.equals("clear-day")) {
                            icon4.setImageResource(R.drawable.weather_sunny);
                        }
                        if(summary.equals("clear-night")) {
                            icon4.setImageResource(R.drawable.weather_night);
                        }
                        if(summary.equals("rain")) {
                            icon4.setImageResource(R.drawable.weather_rainy);
                        }
                        if(summary.equals("sleet")) {
                            icon4.setImageResource(R.drawable.weather_snowy_rainy);
                        }
                        if(summary.equals("snow")) {
                            icon4.setImageResource(R.drawable.weather_snowy);
                        }
                        if(summary.equals("wind")) {
                            icon4.setImageResource(R.drawable.weather_windy_variant);
                        }
                        if(summary.equals("fog")) {
                            icon4.setImageResource(R.drawable.weather_fog);
                        }
                        if(summary.equals("cloudy")) {
                            icon4.setImageResource(R.drawable.weather_cloudy);
                        }
                        if(summary.equals("partly-cloudy-night")) {
                            icon4.setImageResource(R.drawable.weather_night_partly_cloudy);
                        }
                        if(summary.equals("partly-cloudy-day")) {
                            icon4.setImageResource(R.drawable.weather_partly_cloudy);
                        }
                        low_temp4.setText(temperatureLow);
                        high_temp4.setText(temperatureHigh);

                        //List 5
                        time = data_array.getJSONObject(4).getString("time");
                        date = new java.util.Date(Long.parseLong(time)*1000L);
                        sdf = new java.text.SimpleDateFormat("MM/dd/yyyy");
                        //sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
                        formattedDate = sdf.format(date);

                        summary = data_array.getJSONObject(4).getString("icon");
                        temperatureLow = data_array.getJSONObject(4).getString("temperatureLow");
                        i = Float.valueOf(temperatureLow);
                        j = (int)Math.round(i);
                        temperatureLow = Integer.toString(j);

                        temperatureHigh = data_array.getJSONObject(4).getString("temperatureHigh");
                        i = Float.valueOf(temperatureHigh);
                        j = (int)Math.round(i);
                        temperatureHigh = Integer.toString(j);

                        summary = summary.toLowerCase();

                        icon5.setImageResource(R.drawable.weather_sunny);

                        date5.setText(formattedDate);
                        if(summary.equals("clear-day")) {
                            icon5.setImageResource(R.drawable.weather_sunny);
                        }
                        if(summary.equals("clear-night")) {
                            icon5.setImageResource(R.drawable.weather_night);
                        }
                        if(summary.equals("rain")) {
                            icon5.setImageResource(R.drawable.weather_rainy);
                        }
                        if(summary.equals("sleet")) {
                            icon5.setImageResource(R.drawable.weather_snowy_rainy);
                        }
                        if(summary.equals("snow")) {
                            icon5.setImageResource(R.drawable.weather_snowy);
                        }
                        if(summary.equals("wind")) {
                            icon5.setImageResource(R.drawable.weather_windy_variant);
                        }
                        if(summary.equals("fog")) {
                            icon5.setImageResource(R.drawable.weather_fog);
                        }
                        if(summary.equals("cloudy")) {
                            icon5.setImageResource(R.drawable.weather_cloudy);
                        }
                        if(summary.equals("partly-cloudy-night")) {
                            icon5.setImageResource(R.drawable.weather_night_partly_cloudy);
                        }
                        if(summary.equals("partly-cloudy-day")) {
                            icon5.setImageResource(R.drawable.weather_partly_cloudy);
                        }
                        low_temp5.setText(temperatureLow);
                        high_temp5.setText(temperatureHigh);

                        //List 6
                        time = data_array.getJSONObject(5).getString("time");
                        date = new java.util.Date(Long.parseLong(time)*1000L);
                        sdf = new java.text.SimpleDateFormat("MM/dd/yyyy");
                        //sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
                        formattedDate = sdf.format(date);

                        summary = data_array.getJSONObject(5).getString("icon");
                        temperatureLow = data_array.getJSONObject(5).getString("temperatureLow");
                        i = Float.valueOf(temperatureLow);
                        j = (int)Math.round(i);
                        temperatureLow = Integer.toString(j);

                        temperatureHigh = data_array.getJSONObject(5).getString("temperatureHigh");
                        i = Float.valueOf(temperatureHigh);
                        j = (int)Math.round(i);
                        temperatureHigh = Integer.toString(j);

                        icon6.setImageResource(R.drawable.weather_sunny);

                        summary = summary.toLowerCase();

                        icon6.setImageResource(R.drawable.weather_sunny);

                        date6.setText(formattedDate);
                        if(summary.equals("clear-day")) {
                            icon6.setImageResource(R.drawable.weather_sunny);
                        }
                        if(summary.equals("clear-night")) {
                            icon6.setImageResource(R.drawable.weather_night);
                        }
                        if(summary.equals("rain")) {
                            icon6.setImageResource(R.drawable.weather_rainy);
                        }
                        if(summary.equals("sleet")) {
                            icon6.setImageResource(R.drawable.weather_snowy_rainy);
                        }
                        if(summary.equals("snow")) {
                            icon6.setImageResource(R.drawable.weather_snowy);
                        }
                        if(summary.equals("wind")) {
                            icon6.setImageResource(R.drawable.weather_windy_variant);
                        }
                        if(summary.equals("fog")) {
                            icon6.setImageResource(R.drawable.weather_fog);
                        }
                        if(summary.equals("cloudy")) {
                            icon6.setImageResource(R.drawable.weather_cloudy);
                        }
                        if(summary.equals("partly-cloudy-night")) {
                            icon6.setImageResource(R.drawable.weather_night_partly_cloudy);
                        }
                        if(summary.equals("partly-cloudy-day")) {
                            icon6.setImageResource(R.drawable.weather_partly_cloudy);
                        }

                        low_temp6.setText(temperatureLow);
                        high_temp6.setText(temperatureHigh);

                        //List 7
                        time = data_array.getJSONObject(6).getString("time");
                        date = new java.util.Date(Long.parseLong(time)*1000L);
                        sdf = new java.text.SimpleDateFormat("MM/dd/yyyy");
                        //sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
                        formattedDate = sdf.format(date);

                        summary = data_array.getJSONObject(6).getString("icon");
                        temperatureLow = data_array.getJSONObject(6).getString("temperatureLow");
                        i = Float.valueOf(temperatureLow);
                        j = (int)Math.round(i);
                        temperatureLow = Integer.toString(j);

                        temperatureHigh = data_array.getJSONObject(6).getString("temperatureHigh");
                        i = Float.valueOf(temperatureHigh);
                        j = (int)Math.round(i);
                        temperatureHigh = Integer.toString(j);

                        summary = summary.toLowerCase();

                        icon7.setImageResource(R.drawable.weather_sunny);

                        date7.setText(formattedDate);
                        if(summary.equals("clear-day")) {
                            icon7.setImageResource(R.drawable.weather_sunny);
                        }
                        if(summary.equals("clear-night")) {
                            icon7.setImageResource(R.drawable.weather_night);
                        }
                        if(summary.equals("rain")) {
                            icon7.setImageResource(R.drawable.weather_rainy);
                        }
                        if(summary.equals("sleet")) {
                            icon7.setImageResource(R.drawable.weather_snowy_rainy);
                        }
                        if(summary.equals("snow")) {
                            icon7.setImageResource(R.drawable.weather_snowy);
                        }
                        if(summary.equals("wind")) {
                            icon7.setImageResource(R.drawable.weather_windy_variant);
                        }
                        if(summary.equals("fog")) {
                            icon7.setImageResource(R.drawable.weather_fog);
                        }
                        if(summary.equals("cloudy")) {
                            icon7.setImageResource(R.drawable.weather_cloudy);
                        }
                        if(summary.equals("partly-cloudy-night")) {
                            icon7.setImageResource(R.drawable.weather_night_partly_cloudy);
                        }
                        if(summary.equals("partly-cloudy-day")) {
                            icon7.setImageResource(R.drawable.weather_partly_cloudy);
                        }

                        low_temp7.setText(temperatureLow);
                        high_temp7.setText(temperatureHigh);

                        //List 8
                        time = data_array.getJSONObject(7).getString("time");
                        date = new java.util.Date(Long.parseLong(time)*1000L);
                        sdf = new java.text.SimpleDateFormat("MM/dd/yyyy");
                        //sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
                        formattedDate = sdf.format(date);

                        summary = data_array.getJSONObject(7).getString("icon");
                        temperatureLow = data_array.getJSONObject(7).getString("temperatureLow");
                        i = Float.valueOf(temperatureLow);
                        j = (int)Math.round(i);
                        temperatureLow = Integer.toString(j);

                        temperatureHigh = data_array.getJSONObject(7).getString("temperatureHigh");
                        i = Float.valueOf(temperatureHigh);
                        j = (int)Math.round(i);
                        temperatureHigh = Integer.toString(j);

                        summary = summary.toLowerCase();

                        icon8.setImageResource(R.drawable.weather_sunny);

                        date8.setText(formattedDate);
                        if(summary.equals("clear-day")) {
                            icon8.setImageResource(R.drawable.weather_sunny);
                        }
                        if(summary.equals("clear-night")) {
                            icon8.setImageResource(R.drawable.weather_night);
                        }
                        if(summary.equals("rain")) {
                            icon8.setImageResource(R.drawable.weather_rainy);
                        }
                        if(summary.equals("sleet")) {
                            icon8.setImageResource(R.drawable.weather_snowy_rainy);
                        }
                        if(summary.equals("snow")) {
                            icon8.setImageResource(R.drawable.weather_snowy);
                        }
                        if(summary.equals("wind")) {
                            icon8.setImageResource(R.drawable.weather_windy_variant);
                        }
                        if(summary.equals("fog")) {
                            icon8.setImageResource(R.drawable.weather_fog);
                        }
                        if(summary.equals("cloudy")) {
                            icon8.setImageResource(R.drawable.weather_cloudy);
                        }
                        if(summary.equals("partly-cloudy-night")) {
                            icon8.setImageResource(R.drawable.weather_night_partly_cloudy);
                        }
                        if(summary.equals("partly-cloudy-day")) {
                            icon8.setImageResource(R.drawable.weather_partly_cloudy);
                        }
                        low_temp8.setText(temperatureLow);
                        high_temp8.setText(temperatureHigh);
                        spinner.setVisibility(View.GONE);
                        main_layout.setVisibility(View.VISIBLE);
                    }
                    catch (JSONException err){
                        Log.d("Dark Sky Error", err.toString());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("DarkSky Fails","Volley didn't work" + error);
                }
            });
            // Add the request to the RequestQueue.
            queue.add(stringRequest);

        }
        catch (Exception err) {
            Log.d("Error", err.toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Here, the previous Main activity must be called. and inflate that XML code.
        //Then all the functions in Main Activity.java must be called.
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.main_page, container, false);
        val = getArguments().getInt("someInt", 0);

        //Calling the Functions to Load Data in Home Screen.
        if(val == 0) {
            RequestQueue queue = Volley.newRequestQueue(getContext());
            String url = "http://ip-api.com/json";
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //Converting the String to JSON
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        //Calling the DarkSky API to fetch the Weather Details
                        String lat = jsonObject.getString("lat");
                        String lon = jsonObject.getString("lon");
                        String city = jsonObject.getString("city");
                        String region = jsonObject.getString("region");
                        String country_code = jsonObject.getString("countryCode");

                        if(country_code.equals("US")) {
                            country_code = "USA";
                        }

                        String location = city + ", " + region + ", " + country_code;
                        send_city_state_country = location;
                        String url = "http://elanka-hw8-final.appspot.com/forecast?lat="+lat+"&lon="+lon;
                        darkSkyAPI(url, location);
                    }
                    catch (JSONException err){
                        Log.d("Error", err.toString());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Volley didn't work", error.toString());
                }
            });
            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }
        else {
            //Adding Fragments to a List.
            TabAdapter adapter = MainActivity.returnTabAdapter();
            mFragments.add(adapter.getItem(val));
            ArrayList<String> cities = MainActivity.returnCities();
            String[] arrOfStr = cities.get(val-1).split("\\|");
            send_city_state_country = arrOfStr[0];
            String url = "http://elanka-hw8-final.appspot.com/forecast?lat="+arrOfStr[1]+"&lon="+arrOfStr[2];
            darkSkyAPI(url, send_city_state_country);
        }

        //Clicking the Card1 Calls this function
        CardView card1_info = view.findViewById(R.id.card1);
        card1_info.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                Log.d("image", "clicked");
                Intent intent = new Intent(getContext(),DetailedWeather.class);
                TextView dark_sky_data = view.findViewById(R.id.dark_sky_data);
                String dark_sky_response = dark_sky_data.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("data", dark_sky_response);
                bundle.putString("city_state_country", send_city_state_country);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        final FloatingActionButton fabAdd = view.findViewById(R.id.fabAdd);
        final FloatingActionButton fabRemove = view.findViewById(R.id.fabRemove);
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", Context.MODE_WORLD_READABLE); // 0 - for private mode
        String shared_value = pref.getString(send_city_state_country,null);
        if(shared_value != null) {
            fabAdd.hide();
            fabRemove.show();
        }
        else {
            fabAdd.show();
            fabRemove.hide();
        }
        if(val == 0) {
            fabAdd.hide();
            fabRemove.hide();
        }
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), send_city_state_country+ " was added to favourites",  Toast.LENGTH_SHORT).show();
                fabAdd.hide();
                fabRemove.show();
                SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", Context.MODE_WORLD_READABLE); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putString(send_city_state_country,lat_fav + "|" +lon_fav);
                editor.commit();
            }
        });

        fabRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), send_city_state_country+ " was removed from favourites",  Toast.LENGTH_SHORT).show();
                fabAdd.show();
                fabRemove.hide();
                SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", Context.MODE_WORLD_READABLE); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.remove(send_city_state_country);
                editor.commit();
                //editor.clear();
                //Removing the Tab on home page only
                if(searchQuery == 0) {
                    TabLayout tabLayout = MainActivity.returnTabs();
                    tabLayout.removeTabAt(val);
                    startActivity(new Intent(getContext(), MainActivity.class));
                }
            }
        });
        return view;
    }

    public static DynamicFragment addfrag(int val) {
        DynamicFragment fragment = new DynamicFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", val);
        fragment.setArguments(args);
        return fragment;
    }

    //Code to inflate Menu class
    public ArrayAdapter<String> dataAdapter;
    public String query2;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == 0) {

        }
        if(id == 16908332) {
            startActivity(new Intent(getContext(), MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu, menu);

        final MenuItem searchMenu = menu.findItem(R.id.search);
        final androidx.appcompat.widget.SearchView search = (SearchView) MenuItemCompat.getActionView(searchMenu);
        final SearchView.SearchAutoComplete searchAutoComplete = search.findViewById(androidx.appcompat.R.id.search_src_text);
        searchAutoComplete.setBackgroundColor(Color.DKGRAY);
        searchAutoComplete.setDropDownBackgroundResource(R.color.colorWhite);

        searchMenu.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                if(query2 != null) {
                    startActivity(new Intent(getContext(), MainActivity.class));
                }
                TabLayout tab = MainActivity.returnTabs();
                tab.setVisibility(View.VISIBLE);
                return true;
            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchQuery = 1;
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                searchMenu.collapseActionView();
                searchMenu.setVisible(false);
                ActionBar actionbar = ((AppCompatActivity)getActivity()).getSupportActionBar();
                actionbar.setDisplayHomeAsUpEnabled(true);
                actionbar.setTitle(query);

                FloatingActionButton fab = view.findViewById(R.id.fabAdd);
                RelativeLayout main_layout = view.findViewById(R.id.main_view);
                main_layout.setVisibility(View.GONE);

                LinearLayout progress_bar_layout = view.findViewById(R.id.progress_bar_layout);
                progress_bar_layout.setVisibility(View.VISIBLE);

                fab.show();
                TextView search_result = view.findViewById(R.id.search_result);
                search_result.setVisibility(View.VISIBLE);

                TabLayout tab = MainActivity.returnTabs();
                tab.setVisibility(View.GONE);

                CustomViewPager viewPager = (CustomViewPager) MainActivity.returnViewPager();
                viewPager.setBackgroundColor(Color.BLACK);
                viewPager.setEnableSwipe(false);


                view.setPadding(0, 10, 0, 0);


                query2 = query;
                //Call the API Again to direct to right text
                RequestQueue queue = Volley.newRequestQueue(getContext());
                String url = "http://elanka-hw8-final.appspot.com/googleGeoCode?address="+query;
                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Converting the String to JSON
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            //Calling the DarkSky API to fetch the Weather Details
                            String lat = jsonObject.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lat");
                            lat_fav = lat;
                            String lon = jsonObject.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lng");
                            lon_fav = lon;
                            send_city_state_country = query2;
                            String location = query2;
                            String url = "http://elanka-hw8-final.appspot.com/forecast?lat="+lat+"&lon="+lon;
                            darkSkyAPI(url, location);
                        }
                        catch (JSONException err){
                            Log.d("Error", err.toString());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Volley didn't work", error.toString());
                    }
                });
                // Add the request to the RequestQueue.
                queue.add(stringRequest);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String url = "http://elanka-hw8-final.appspot.com/api/weatherCard?city="+newText;
                try {
                    RequestQueue queue = Volley.newRequestQueue(getContext());
                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Converting the String to JSON
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                List<String> data = new ArrayList<String>();
                                JSONArray predictions = jsonObject.getJSONArray("predictions");
                                for(int i = 0; i < predictions.length(); i++) {
                                    data.add(predictions.getJSONObject(i).getString("description"));
                                }
                                dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, data);
                                searchAutoComplete.setAdapter(dataAdapter);
                            }
                            catch (JSONException err){
                                Log.d("Weather Error", err.toString());
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Weather Card Fails","Volley didn't work" + error);
                        }
                    });
                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);
                }
                catch (Exception err) {
                    Log.d("Error", err.toString());
                }
                return false;
            }
        });

        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                search.setQuery(dataAdapter.getItem(position).toString(), false);
            }
        });
    }
}