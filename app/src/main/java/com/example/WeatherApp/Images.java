package com.example.WeatherApp;

import java.util.ArrayList;
import java.util.List;

public class Images {
    String photo_url;

    Images(String photo_url) {
        this.photo_url = photo_url;
    }

    private List<Images> images;

    private void initializeData() {
        images = new ArrayList<>();
    }
}
