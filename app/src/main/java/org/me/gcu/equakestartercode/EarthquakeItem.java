package org.me.gcu.equakestartercode;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

// Nicola Dochnenko S1915348
public class EarthquakeItem {
    private String title;
    private String description;
    private String link;
    private String category;
    private float Lat, Long;
    private EarthquakeData Data = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        Data = new EarthquakeData(description.split(";"));
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getLat() {
        return Lat;
    }

    public void setLat(float lat) {
        Lat = lat;
    }

    public float getLong() {
        return Long;
    }

    public void setLong(float aLong) {
        Long = aLong;
    }

    public EarthquakeData getData() {
        return Data;
    }
}
