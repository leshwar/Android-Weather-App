package com.example.WeatherApp;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TabFragment extends Fragment {

    int position;
    private TextView textView;
    String dark_sky_response;
    String response_city;

    public static Fragment getInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        TabFragment tabFragment = new TabFragment();
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("pos");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        textView = (TextView) view.findViewById(R.id.textView);
        LineChart chart =  view.findViewById(R.id.lineChart);
        LinearLayout row1 = view.findViewById(R.id.row1);
        LinearLayout row2 = view.findViewById(R.id.row2);
        LinearLayout row3 = view.findViewById(R.id.row3);

        //Page 1 Values
        TextView wind_speed_value = view.findViewById(R.id.wind_speed_value);
        TextView pressure_value = view.findViewById(R.id.pressure_value);
        TextView precipitation_value = view.findViewById(R.id.precipitation_value);
        TextView temperature_value = view.findViewById(R.id.temperature_value);
        TextView summary_value = view.findViewById(R.id.summary_value);
        TextView humidity_value = view.findViewById(R.id.humidity_value);
        TextView visibility_value = view.findViewById(R.id.visibility_value);
        TextView cloud_cover_value = view.findViewById(R.id.cloud_cover_value);
        TextView ozone_value = view.findViewById(R.id.ozone_value);
        ImageView summary_icon = view.findViewById(R.id.summary_icon);
        RelativeLayout weekly_data = view.findViewById(R.id.weekly_data);
        TextView weekly_text = view.findViewById(R.id.weekly_text);
        ImageView weekly_icon = view.findViewById(R.id.weekly_icon);

        final RecyclerView imagesRecyclerView = view.findViewById(R.id.imagesRecyclerView);
        imagesRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        imagesRecyclerView.setLayoutManager(layoutManager);


        //dark_sky_response = getArguments().getString("json_data");
        dark_sky_response = DetailedWeather.returnDarkSkyData();
        response_city = DetailedWeather.returnCity();
        JSONObject dark_sky_json = null;
        try {
            dark_sky_json = new JSONObject(dark_sky_response);
        }
        catch (Exception e) {

        }

        int setPosition = (position + 1);
        if(setPosition == 1) {
            //Hiding Other Pages
            weekly_data.setVisibility(View.GONE);

            //Set Page1 Details here
            chart.setNoDataText("");
            try {
                String windSpeed = dark_sky_json.getJSONObject("currently").getString("windSpeed");
                String pressure = dark_sky_json.getJSONObject("currently").getString("pressure");
                String precipIntensity = dark_sky_json.getJSONObject("currently").getString("precipIntensity");
                String temperature = dark_sky_json.getJSONObject("currently").getString("temperature");
                String icon = dark_sky_json.getJSONObject("currently").getString("icon");
                String humidity = dark_sky_json.getJSONObject("currently").getString("humidity");
                String visibility = dark_sky_json.getJSONObject("currently").getString("visibility");
                String cloudCover = dark_sky_json.getJSONObject("currently").getString("cloudCover");
                String ozone = dark_sky_json.getJSONObject("currently").getString("ozone");

                summary_icon.setImageResource(R.drawable.weather_sunny);

                if(icon.equals("clear-day")) {
                    summary_icon.setImageResource(R.drawable.weather_sunny);
                }
                if(icon.equals("clear-night")) {
                    summary_icon.setImageResource(R.drawable.weather_night);
                }
                if(icon.equals("rain")) {
                    summary_icon.setImageResource(R.drawable.weather_rainy);
                }
                if(icon.equals("sleet")) {
                    summary_icon.setImageResource(R.drawable.weather_snowy_rainy);
                }
                if(icon.equals("snow")) {
                    summary_icon.setImageResource(R.drawable.weather_snowy);
                }
                if(icon.equals("wind")) {
                    summary_icon.setImageResource(R.drawable.weather_windy_variant);
                }
                if(icon.equals("fog")) {
                    summary_icon.setImageResource(R.drawable.weather_fog);
                }
                if(icon.equals("cloudy")) {
                    summary_icon.setImageResource(R.drawable.weather_cloudy);
                }
                if(icon.equals("partly-cloudy-night")) {
                    summary_icon.setImageResource(R.drawable.weather_night_partly_cloudy);
                }
                if(icon.equals("partly-cloudy-day")) {
                    summary_icon.setImageResource(R.drawable.weather_partly_cloudy);
                }

                DecimalFormat df = new DecimalFormat("0.00");

                double windSpeed_float = Math.round(Float.valueOf(windSpeed) * 100.0) / 100.0;
                wind_speed_value.setText(df.format(windSpeed_float) + " mph");
                double pressure_float = Math.round(Float.valueOf(pressure) * 100.0) / 100.0;
                pressure_value.setText(df.format(pressure_float) + " mb");
                double precipIntensity_float = Math.round(Float.valueOf(precipIntensity) * 100.0) / 100.0;
                precipitation_value.setText(df.format(precipIntensity_float) + " mmph");

                float i = Float.valueOf(temperature);
                int j = (int)Math.round(i);
                temperature = Integer.toString(j);
                temperature = temperature + "\u00B0 F";
                temperature_value.setText(temperature);

                String icon_text = icon.replace("-", " ");
                icon_text = icon_text.replace("partly", " ");
                icon_text = icon_text.trim();
                icon_text = icon_text.substring(0, 1).toUpperCase() + icon_text.substring(1);
                summary_value.setText(icon_text);


                Float humidity_float = Float.parseFloat(humidity);
                humidity_float = humidity_float * 100;
                int humidity_int = Math.round(humidity_float);
                humidity_value.setText(humidity_int + "%");

                double visibility_float = Math.round(Float.valueOf(visibility) * 100.0) / 100.0;
                visibility_value.setText(df.format(visibility_float) + " km");

                Float cloudCover_float = Float.parseFloat(cloudCover);
                cloudCover_float = cloudCover_float * 100;
                int cloudCover_int = Math.round(cloudCover_float);
                cloud_cover_value.setText(cloudCover_int + "%");
                double ozone_float = Math.round(Float.valueOf(ozone) * 100.0) / 100.0;
                ozone_value.setText(df.format(ozone_float) + " DU");
            }
            catch (Exception e) {

            }
        }
        if(setPosition == 2) {
            String icon = "";
            try {
                icon = dark_sky_json.getJSONObject("daily").getString("icon");
            }
            catch (Exception e) {

            }
            //Hiding Other Pages
            textView.setVisibility(View.GONE);
            row1.setVisibility(View.GONE);
            row2.setVisibility(View.GONE);
            row3.setVisibility(View.GONE);

            //Set Page 2 Details here
            try {
                weekly_text.setText(dark_sky_json.getJSONObject("daily").getString("summary"));
            }
            catch (Exception e) {

            }

            weekly_icon.setImageResource(R.drawable.weather_sunny);

            if(icon.equals("clear-day")) {
                weekly_icon.setImageResource(R.drawable.weather_sunny);
            }
            if(icon.equals("clear-night")) {
                weekly_icon.setImageResource(R.drawable.weather_night);
            }
            if(icon.equals("rain")) {
                weekly_icon.setImageResource(R.drawable.weather_rainy);
            }
            if(icon.equals("sleet")) {
                weekly_icon.setImageResource(R.drawable.weather_snowy_rainy);
            }
            if(icon.equals("snow")) {
                weekly_icon.setImageResource(R.drawable.weather_snowy);
            }
            if(icon.equals("wind")) {
                weekly_icon.setImageResource(R.drawable.weather_windy_variant);
            }
            if(icon.equals("fog")) {
                weekly_icon.setImageResource(R.drawable.weather_fog);
            }
            if(icon.equals("cloudy")) {
                weekly_icon.setImageResource(R.drawable.weather_cloudy);
            }
            if(icon.equals("partly-cloudy-night")) {
                weekly_icon.setImageResource(R.drawable.weather_night_partly_cloudy);
            }
            if(icon.equals("partly-cloudy-day")) {
                weekly_icon.setImageResource(R.drawable.weather_partly_cloudy);
            }

            LineDataSet lineDataSet1 = new LineDataSet(getData(dark_sky_json, 1), "Maximum Temperature");
            lineDataSet1.setAxisDependency(YAxis.AxisDependency.LEFT);

            LineDataSet lineDataSet2 = new LineDataSet(getData(dark_sky_json, 2), "Minimum Temperature");
            lineDataSet2.setAxisDependency(YAxis.AxisDependency.LEFT);

            lineDataSet1.setColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.lineChart1));
            lineDataSet2.setColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.lineChart2));

            List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(lineDataSet2);
            dataSets.add(lineDataSet1);

            Legend l = chart.getLegend();
            l.setFormSize(10f); // set the size of the legend forms/shapes
            l.setForm(Legend.LegendForm.SQUARE); // set what type of form/shape should be used
            l.setTextSize(16f);
            l.setTextColor(Color.WHITE);
            l.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
            l.setYEntrySpace(5f); // set the space between the legend entries on the y-axis

            chart.getAxisLeft().setTextColor(Color.WHITE);
            chart.getAxisRight().setTextColor(Color.WHITE);
            chart.getXAxis().setTextColor(Color.WHITE);

            LineData data = new LineData(dataSets);
            chart.setData(data);
            chart.invalidate();
        }
        if(setPosition == 3) {
            //Hide previous pages Data.
            chart.setNoDataText("");
            textView.setVisibility(View.GONE);
            row1.setVisibility(View.GONE);
            row2.setVisibility(View.GONE);
            row3.setVisibility(View.GONE);
            weekly_data.setVisibility(View.GONE);
            chart.setVisibility(View.GONE);

            //Load this page data.

            try {
                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                // Request a string response from the provided URL.
                String url = "http://elanka-hw8-final.appspot.com/googleSearchEngine?city=" + response_city;
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Converting the String to JSON
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            List<Images> images;
                            images = new ArrayList<>();
                            JSONArray data = jsonObject.getJSONArray("items");
                            for(int i = 0; i < data.length(); i++) {
                                String value = data.getJSONObject(i).getString("link");
                                images.add(new Images(value));
                            }
                            ImageAdapter adapter = new ImageAdapter(getActivity().getApplicationContext(), images);
                            imagesRecyclerView.setAdapter(adapter);
                        }
                        catch (JSONException err){
                            Log.d("Images Error", err.toString());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Images Fails","Volley didn't work" + error);
                    }
                });
                // Add the request to the RequestQueue.
                queue.add(stringRequest);

            }
            catch (Exception err) {
                Log.d("Error", err.toString());
            }
        }
    }
    private ArrayList getData(JSONObject dark_sky_json, int flag){
        ArrayList<Entry> entries = new ArrayList<>();
        try {
            JSONArray data = dark_sky_json.getJSONObject("daily").getJSONArray("data");
            for(int i = 0; i < data.length(); i++) {
                if(flag == 1) {
                    float value =  Float.parseFloat(data.getJSONObject(i).getString("temperatureHigh"));
                    entries.add(new Entry(i, value));
                }
                if(flag == 2) {
                    float value =  Float.parseFloat(data.getJSONObject(i).getString("temperatureLow"));
                    entries.add(new Entry(i, value));
                }
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return entries;
    }
}
